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

    HashMap<String, MapPoint> pointMap;
    HashMap<String, MapObject> objectMap;

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
        runTimer();

        fogView = new MapFogView(getContext());

        FrameLayout fogLayout = rootView.findViewById(R.id.in_game_fog_layout);
        fogLayout.addView(fogView);

        ViewTreeObserver vto = map.getViewTreeObserver();

        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                map.getViewTreeObserver().removeOnPreDrawListener(this);
                fogView.setParam(map.getMeasuredWidth(), map.getMeasuredHeight());
                fogView.drawFog();
                tempText.setText(map.getMeasuredWidth() + "/" + map.getMeasuredHeight());

                setMapObjects(map.getMeasuredWidth(),  map.getMeasuredHeight(), playerSide);

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
        diagonal = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

        Inhibitor topInhibitorBlue = new Inhibitor("blueTopInhibitor", 9, 73, 0, 0, playerSide);
        objectMap.put(topInhibitorBlue.objectName, topInhibitorBlue);
        Inhibitor midInhibitorBlue = new Inhibitor("blueMidInhibitor", 23, 75, 0, 0, playerSide);
        objectMap.put(midInhibitorBlue.objectName, midInhibitorBlue);
        Inhibitor bottomInhibitorBlue = new Inhibitor("blueBottomInhibitor", 24, 90, 0, 0, playerSide);
        objectMap.put(bottomInhibitorBlue.objectName, bottomInhibitorBlue);

        Turret topTurret1Blue = new Turret("BlueTopTurret1", 12, 29, 100, 0, playerSide);
        objectMap.put(topTurret1Blue.objectName, topTurret1Blue);

        Turret topTurret2Blue = new Turret("BlueTopTurret2", 13, 52, 100, 0, playerSide);
        objectMap.put(topTurret2Blue.objectName, topTurret2Blue);

        Turret topTurret3Blue = new Turret("BlueTopTurret3", 9, 68, 100, 0, playerSide);
        objectMap.put(topTurret3Blue.objectName, topTurret3Blue);

        Turret midTurret1Blue = new Turret("BlueMidTurret1", 42, 54, 100, 0, playerSide);
        Turret midTurret2Blue = new Turret("BlueMidTurret2", 36, 65, 100, 0, playerSide);
        Turret midTurret3Blue = new Turret("BlueMidTurret3", 26, 72, 100, 0, playerSide);
        objectMap.put(midTurret1Blue.objectName, midTurret1Blue);
        objectMap.put(midTurret2Blue.objectName, midTurret2Blue);
        objectMap.put(midTurret3Blue.objectName, midTurret3Blue);

        Turret bottomTurret1Blue = new Turret("BlueBottomTurret1", 75, 93, 100, 0, playerSide);
        Turret bottomTurret2Blue = new Turret("BlueBottomTurret2", 49, 89, 100, 0, playerSide);
        Turret bottomTurret3Blue = new Turret("BlueBottomTurret3Blue", 30, 91, 100, 0, playerSide);
        objectMap.put(bottomTurret1Blue.objectName, bottomTurret1Blue);
        objectMap.put(bottomTurret2Blue.objectName, bottomTurret2Blue);
        objectMap.put(bottomTurret3Blue.objectName, bottomTurret3Blue);

        Nexus  nexusBlue = new Nexus("blueNexus", 10, 87, (int) (diagonal * 0.18), 0, playerSide);
        objectMap.put(nexusBlue.objectName, nexusBlue);

        Inhibitor topInhibitorRed = new Inhibitor("redTopInhibitor", 75, 11, 0, 1, playerSide);
        Inhibitor midInhibitorRed = new Inhibitor("redMidInhibitor", 78, 22, 0, 1, playerSide);
        Inhibitor bottomInhibitorRed = new Inhibitor("redBottomInhibitor", 91, 35, 0, 1, playerSide);
        objectMap.put(topInhibitorRed.objectName, topInhibitorRed);
        objectMap.put(midInhibitorRed.objectName, midInhibitorRed);
        objectMap.put(bottomInhibitorRed.objectName, bottomInhibitorRed);

        Turret topTurret1Red = new Turret("RedTopTurret1", 33, 10, 100, 1, playerSide);
        Turret topTurret2Red = new Turret("RedTopTurret2", 65, 13, 100, 1, playerSide);
        Turret topTurret3Red = new Turret("RedTopTurret3", 71, 12, 100, 1, playerSide);
        objectMap.put(topTurret1Red.objectName, topTurret1Red);
        objectMap.put(topTurret2Red.objectName, topTurret2Red);
        objectMap.put(topTurret3Red.objectName, topTurret3Red);

        Turret midTurret1Red = new Turret("RedMidTurret1", 62, 41, 100, 1, playerSide);
        Turret midTurret2Red = new Turret("RedMidTurret2", 67, 31, 100, 1, playerSide);
        Turret midTurret3Red = new Turret("RedMidTurret3", 75, 26, 100, 1, playerSide);
        objectMap.put(midTurret1Red.objectName, midTurret1Red);
        objectMap.put(midTurret2Red.objectName, midTurret2Red);
        objectMap.put(midTurret3Red.objectName, midTurret3Red);

        Turret bottomTurret1Red = new Turret("RedBottomTurret1", 97, 67, 100, 1, playerSide);
        Turret bottomTurret2Red = new Turret("RedBottomTurret2", 90, 43, 100, 1, playerSide);
        Turret bottomTurret3Red = new Turret("RedBottomTurret3", 91, 30, 100, 1, playerSide);
        objectMap.put(bottomTurret1Red.objectName, bottomTurret1Red);
        objectMap.put(bottomTurret2Red.objectName, bottomTurret2Red);
        objectMap.put(bottomTurret3Red.objectName, bottomTurret3Red);

        Nexus  nexusRed = new Nexus("redNexus", 87, 13, (int) (diagonal * 0.18), 1, playerSide);
        objectMap.put(nexusRed.objectName, nexusRed);

        Dragon dragon = new Dragon("dragon", 69, 68,0, 0);
        objectMap.put(dragon.objectName, dragon);

        setCoordinates(objectMap);

        drawMapObjects(objectMap);

        setPlayerSideObject(objectMap);
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
                playerSideObjectList.add(set.getValue());
            }
        }
    }

    private void moveChampion(Champion champion, MapPoint point) {

    }

    private void updateResources() {
        fogView.clearResources();
        fogView.drawFog();
        drawMapObjects(objectMap);
        checkInVision(objectMap);
    }

    private void runTimer() {
        runTimer.run();
    }

    Runnable runTimer = new Runnable() {
        @Override
        public void run() {
            try {
                /*int realHour = realTimeSpend / 216000;
                int realMin = realTimeSpend / 3600;
                int realSecs = (realTimeSpend % 3600) / 60;*/
                int realMilSecs = realTimeSpend * 10 / 6;

                /*String realMilSecsString = String.valueOf(realMilSecs);
                String realMilSecsSub = realMilSecsString.length() > 2 ? realMilSecsString.substring(realMilSecsString.length() - 2) : realMilSecsString;

                String realTime
                        = String.format(Locale.getDefault(), "%d:%02d:%02d:", realHour, realMin, realSecs) + realMilSecsSub;*/

                //realTimeView.setText(realTime);

                playSecs =  (realTimeSpend % 600) / 10;
                playMin = realTimeSpend / 600;

                String playTime
                        = String.format(Locale.getDefault(), "%02d:%02d", playMin, playSecs);

                playTimeView.setText(playTime);

                if(playSecs > 100) {
                    updateResources();
                }

                realTimeSpend++;
            } finally {
                handler.postDelayed(runTimer, 10);
            }
        }
    };
}