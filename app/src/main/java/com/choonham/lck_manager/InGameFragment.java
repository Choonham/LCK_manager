package com.choonham.lck_manager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.choonham.lck_manager.map_object.*;
import com.google.android.material.tabs.TabLayout;

import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InGameFragment extends Fragment {

    TabLayout tabLayout;
    TextView realTimeView;
    TextView playTimeView;

    TextView tempText;

    int realTimeSpend;

    int playSecs =  0;
    int playMin = 0;

    Handler handler;

    double diagonal;

    int playerSide = 0;

    List<MapObject> playerSideObjectList;

    MapFogView fogView;

    MapObject [][] coordinate;

    HashMap<String, int[]> pointMap;
    HashMap<String, MapObject> objectMap;

    FrameLayout fogLayout;



    public InGameFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InGameFragment newInstance() {
        InGameFragment fragment = new InGameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_in_game, container, false);

        initPointMap();

        coordinate = new MapObject[100][100];
        pointMap = new HashMap<>();
        objectMap = new HashMap<>();
        playerSideObjectList = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                coordinate[i][j] = null;
            }
        }

        handler = new Handler(Looper.getMainLooper());

        realTimeSpend = 0;

        ImageView map = rootView.findViewById(R.id.in_game_lift_map_image_view);

        //realTimeView = rootView.findViewById(R.id.play_time_view_real);
        playTimeView = rootView.findViewById(R.id.play_time_view);
        tempText = rootView.findViewById(R.id.temp_text);

        fogView = new MapFogView(getContext());

        fogLayout = rootView.findViewById(R.id.in_game_fog_layout);
        fogLayout.addView(fogView);

        ViewTreeObserver vto = map.getViewTreeObserver();

        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                diagonal = Math.sqrt(Math.pow(map.getMeasuredWidth(), 2) + Math.pow(map.getMeasuredHeight(), 2));

                map.getViewTreeObserver().removeOnPreDrawListener(this);
                fogView.setParam(map.getMeasuredWidth(), map.getMeasuredHeight(), (int) diagonal);
                fogView.drawFog();
                //tempText.setText(map.getMeasuredWidth() + "/" + map.getMeasuredHeight());

                fogView.setLoadingImg();

                setMapObjects(map.getMeasuredWidth(),  map.getMeasuredHeight(), playerSide);

                runTimer();
                return true;
            }
        });

        InGameHpFragment hpFragment = new InGameHpFragment();
        InGameActionFragment actionFragment = new InGameActionFragment();
        InGameStatusFragment statusFragment = new InGameStatusFragment();
        InGameGoldFragment goldFragment = new InGameGoldFragment();

        getParentFragmentManager().beginTransaction().replace(R.id.in_game_frame, goldFragment).commit();

        tabLayout = rootView.findViewById(R.id.in_game_tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;

                if(position == 0) {
                    selected = goldFragment;
                } else if(position == 1) {
                    selected = hpFragment;
                } else if(position == 2) {
                    selected = statusFragment;
                } else if(position == 3) {
                    selected = actionFragment;
                } else {
                    selected = goldFragment;
                }

                getParentFragmentManager().beginTransaction().replace(R.id.in_game_frame, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void setCoordinates(HashMap<String, MapObject> objectMap) {
        for (Map.Entry<String, MapObject> set : objectMap.entrySet()) {
            coordinate[set.getValue().x][set.getValue().y] = set.getValue();
        }
    }

    private void initPointMap() {
        String[] pointNameArray = {
                "res_b_top", "res_b_jg", "res_b_mid", "res_b_ad", "res_b_sup",
                "res_r_top", "res_r_jg", "res_r_mid", "res_r_ad", "res_r_sup",
                "b_t_4", "b_t_3", "b_t_2", "b_t_1", "b_t_0",
                "r_t_4", "r_t_3", "r_t_2", "r_t_1", "r_t_0",
                "t_b_b", "t_b_m", "t_b_r", "t_b_v",
                "b_m_4", "b_m_3", "b_m_2", "b_m_1", "b_m_0",
                "r_m_4", "r_m_3", "r_m_2", "r_m_1", "r_m_0",
                "m_b_t", "m_b_b",
                "b_b_4", "b_b_3", "b_b_2", "b_b_1", "b_b_0",
                "r_b_4", "r_b_3", "r_b_2", "r_b_1", "r_b_0",
                "b_b_b", "b_b_m", "b_b_r", "b_b_v"
        };

        int[][] pointArray = {
                {2, 89}, {5, 91}, {3, 94}, {4, 96}, {9, 96},
                {91, 5}, {89, 8}, {93, 7}, {96, 8}, {95, 11},
                {8, 79}, {10, 59}, {12, 39}, {16, 22}, {21, 18},
                {81, 10}, {63, 11}, {44, 11}, {26, 13}, {21, 18},
                {13, 19}, {18, 15}, {21, 12}, {15, 12},
                {79, 19}, {31, 68}, {39, 59}, {48, 50}, {53, 48},
                {82, 18}, {73, 28}, {65, 35}, {68, 43}, {53, 48},
                {46, 42}, {59, 53},
                {17, 90}, {40, 90}, {60, 90}, {83, 87}, {89, 81},
                {91, 20}, {92, 36}, {94, 53}, {94, 73}, {89, 81},
                {88, 89}, {91, 85}, {95, 80}, {96, 88}
        };

        for(int i = 0; i < pointArray.length; i++) {
            pointMap.put(pointNameArray[i], pointArray[i]);
        }
    }

    private void checkInVision(HashMap<String, MapObject> objectMap) {
        for (Map.Entry<String, MapObject> set : objectMap.entrySet()) {
            MapObject tempObject = set.getValue();
            if(tempObject.teamSide == playerSide) {
                tempObject.inVisision = 1;
            } else {
                for(MapObject playerSideObject : playerSideObjectList) {
                    if(inCircle(playerSideObject.x, playerSideObject.y, tempObject.x, tempObject.y, playerSideObject.visionDistance)) {
                        tempObject.inVisision = 1;
                    }
                }
            }
        }
    }

    private boolean inCircle(int a, int b, int x, int y, int radius) {
        int dx = Math.abs(x - a);
        int dy = Math.abs(y - b);

        return ( dx*dx + dy*dy <= radius*radius );
    }

    private void setMapObjects(int width, int height, int playerSide) {

        Inhibitor topInhibitorBlue = new Inhibitor("blueTopInhibitor", 9, 73, 0, 0, playerSide);
        objectMap.put(topInhibitorBlue.objectName, topInhibitorBlue);
        Inhibitor midInhibitorBlue = new Inhibitor("blueMidInhibitor", 23, 75, 0, 0, playerSide);
        objectMap.put(midInhibitorBlue.objectName, midInhibitorBlue);
        Inhibitor bottomInhibitorBlue = new Inhibitor("blueBottomInhibitor", 24, 90, 0,0, playerSide);
        objectMap.put(bottomInhibitorBlue.objectName, bottomInhibitorBlue);

        Turret topTurret1Blue = new Turret("BlueTopTurret1", 12, 29, 4, 0, playerSide);
        objectMap.put(topTurret1Blue.objectName, topTurret1Blue);

        Turret topTurret2Blue = new Turret("BlueTopTurret2", 13, 52, 4, 0, playerSide);
        objectMap.put(topTurret2Blue.objectName, topTurret2Blue);

        Turret topTurret3Blue = new Turret("BlueTopTurret3", 9, 68, 4, 0, playerSide);
        objectMap.put(topTurret3Blue.objectName, topTurret3Blue);

        Turret midTurret1Blue = new Turret("BlueMidTurret1", 42, 54, 4, 0, playerSide);
        Turret midTurret2Blue = new Turret("BlueMidTurret2", 36, 65, 4, 0, playerSide);
        Turret midTurret3Blue = new Turret("BlueMidTurret3", 26, 72, 4, 0, playerSide);
        objectMap.put(midTurret1Blue.objectName, midTurret1Blue);
        objectMap.put(midTurret2Blue.objectName, midTurret2Blue);
        objectMap.put(midTurret3Blue.objectName, midTurret3Blue);

        Turret bottomTurret1Blue = new Turret("BlueBottomTurret1", 75, 93, 4, 0, playerSide);
        Turret bottomTurret2Blue = new Turret("BlueBottomTurret2", 49, 89, 4, 0, playerSide);
        Turret bottomTurret3Blue = new Turret("BlueBottomTurret3Blue", 30, 91, 4, 0, playerSide);
        objectMap.put(bottomTurret1Blue.objectName, bottomTurret1Blue);
        objectMap.put(bottomTurret2Blue.objectName, bottomTurret2Blue);
        objectMap.put(bottomTurret3Blue.objectName, bottomTurret3Blue);

        Nexus  nexusBlue = new Nexus("blueNexus", 10, 87, 18, 0, playerSide);
        objectMap.put(nexusBlue.objectName, nexusBlue);

        Inhibitor topInhibitorRed = new Inhibitor("redTopInhibitor", 75, 11, 0, 1, playerSide);
        Inhibitor midInhibitorRed = new Inhibitor("redMidInhibitor", 78, 22, 0, 1, playerSide);
        Inhibitor bottomInhibitorRed = new Inhibitor("redBottomInhibitor", 91, 35, 0, 1, playerSide);
        objectMap.put(topInhibitorRed.objectName, topInhibitorRed);
        objectMap.put(midInhibitorRed.objectName, midInhibitorRed);
        objectMap.put(bottomInhibitorRed.objectName, bottomInhibitorRed);

        Turret topTurret1Red = new Turret("RedTopTurret1", 33, 10, 4, 1, playerSide);
        Turret topTurret2Red = new Turret("RedTopTurret2", 65, 13, 4, 1, playerSide);
        Turret topTurret3Red = new Turret("RedTopTurret3", 71, 12, 4, 1, playerSide);
        objectMap.put(topTurret1Red.objectName, topTurret1Red);
        objectMap.put(topTurret2Red.objectName, topTurret2Red);
        objectMap.put(topTurret3Red.objectName, topTurret3Red);

        Turret midTurret1Red = new Turret("RedMidTurret1", 62, 41, 4, 1, playerSide);
        Turret midTurret2Red = new Turret("RedMidTurret2", 67, 31, 4, 1, playerSide);
        Turret midTurret3Red = new Turret("RedMidTurret3", 75, 26, 4, 1, playerSide);
        objectMap.put(midTurret1Red.objectName, midTurret1Red);
        objectMap.put(midTurret2Red.objectName, midTurret2Red);
        objectMap.put(midTurret3Red.objectName, midTurret3Red);

        Turret bottomTurret1Red = new Turret("RedBottomTurret1", 97, 67, 4, 1, playerSide);
        Turret bottomTurret2Red = new Turret("RedBottomTurret2", 90, 43, 4, 1, playerSide);
        Turret bottomTurret3Red = new Turret("RedBottomTurret3", 91, 30, 4, 1, playerSide);
        objectMap.put(bottomTurret1Red.objectName, bottomTurret1Red);
        objectMap.put(bottomTurret2Red.objectName, bottomTurret2Red);
        objectMap.put(bottomTurret3Red.objectName, bottomTurret3Red);

        Nexus  nexusRed = new Nexus("redNexus", 87, 13, 18, 1, playerSide);
        objectMap.put(nexusRed.objectName, nexusRed);

        Dragon dragon = new Dragon("dragon", 69, 68,0, 0);
        objectMap.put(dragon.objectName, dragon);

        setCoordinates(objectMap);

        fogView.clearResources();

        drawMapObjects(objectMap);

        setPlayerSideObject(objectMap);

        UpdateMapThread thread = new UpdateMapThread();
        thread.start();

        fogLayout.removeAllViews();
        fogLayout.addView(fogView);
    }

    private void drawMapObjects(HashMap<String, MapObject> objectMap) {
        for (Map.Entry<String, MapObject> set : objectMap.entrySet()) {
            fogView.setVision(set.getValue());
        }
        for (Map.Entry<String, MapObject> set : objectMap.entrySet()) {
            fogView.drawIcon(set.getValue());
        }
    }

    private void setPlayerSideObject(HashMap<String, MapObject> objectMap) {
        playerSideObjectList.clear();
        for (Map.Entry<String, MapObject> set : objectMap.entrySet()) {
            if(set.getValue().teamSide == playerSide) {
                set.getValue().inVisision = 1;
                playerSideObjectList.add(set.getValue());
            }
        }
    }

    private void moveChampion(Champion champion, int[] point) {
        int x1 = champion.x;
        int x2 = point[0];

        int y1 = champion.y;
        int y2 = point[1];

        int x, y;
        int a;

        ArrayList<Integer[]> pathList = new ArrayList<>();

        if(x1 != x2){
            a = (y2 - y1) / (x2 - x1);
        }
    }

    private void updateResources() {
        fogView.clearResources();
        fogView.drawFog();
        checkInVision(objectMap);
        drawMapObjects(objectMap);
    }

    private void runTimer() {
        runTimer.run();
    }


    Runnable runTimer = new Runnable() {
        @Override
        public void run() {
            try {
                int realMilSecs = realTimeSpend * 10 / 6;

                playSecs =  (realTimeSpend % 600) / 10;
                playMin = realTimeSpend / 600;

                String playTime
                        = String.format(Locale.getDefault(), "%02d:%02d", playMin, playSecs);

                playTimeView.setText(playTime);

                if(playSecs % 10 == 5 || playSecs == 0) {
                    fogLayout.removeAllViews();
                    fogLayout.addView(fogView);
                }

                realTimeSpend++;
            } finally {
                handler.postDelayed(runTimer, 10);
            }
        }
    };

    class UpdateMapThread extends Thread {
        public void run() {
            while(true) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateResources();
                    }
                });

                try{
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
    }
}