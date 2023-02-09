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

import java.util.Locale;

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

    MapFogView fogView;

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

                fogView.drawFog(map.getMeasuredWidth(), map.getMeasuredHeight());
                tempText.setText(map.getMeasuredWidth() + "/" + map.getMeasuredHeight());

                setMapObjects(map.getMeasuredWidth(),  map.getMeasuredHeight(), 0);

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

    private void setMapObjects(int width, int height, int playerSide) {
        diagonal = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

        Inhibitor topInhibitorBlue = new Inhibitor("blueTopInhibitor", (float) (width * 0.09), (float) (height * 0.73), 0, 0, playerSide);
        Inhibitor midInhibitorBlue = new Inhibitor("blueMidInhibitor", (float) (width * 0.23), (float) (height * 0.75), 0, 0, playerSide);
        Inhibitor bottomInhibitorBlue = new Inhibitor("blueBottomInhibitor", (float) (width * 0.24), (float) (height * 0.90), 0, 0, playerSide);

        Turret topTurret1Blue = new Turret("BlueTopTurret1", (float) (width * 0.12), (float) (height * 0.29), 100, 0, playerSide);
        Turret topTurret2Blue = new Turret("BlueTopTurret2", (float) (width * 0.13), (float) (height * 0.52), 100, 0, playerSide);
        Turret topTurret3Blue = new Turret("BlueTopTurret3", (float) (width * 0.09), (float) (height * 0.68), 100, 0, playerSide);

        Turret midTurret1Blue = new Turret("BlueMidTurret1", (float) (width * 0.42), (float) (height * 0.54), 100, 0, playerSide);
        Turret midTurret2Blue = new Turret("BlueMidTurret2", (float) (width * 0.36), (float) (height * 0.65), 100, 0, playerSide);
        Turret midTurret3Blue = new Turret("BlueMidTurret3", (float) (width * 0.26), (float) (height * 0.72), 100, 0, playerSide);

        Turret bottomTurret1Blue = new Turret("BlueBottomTurret1", (float) (width * 0.75), (float) (height * 0.93), 100, 0, playerSide);
        Turret bottomTurret2Blue = new Turret("BlueBottomTurret2", (float) (width * 0.49), (float) (height * 0.89), 100, 0, playerSide);
        Turret bottomTurret3Blue = new Turret("BlueBottomTurret3Blue", (float) (width * 0.30), (float) (height * 0.91), 100, 0, playerSide);

        Nexus  nexusBlue = new Nexus("blueNexus", (float) (width * 0.1), (float) (height * 0.87), (int) (diagonal * 0.18), 0, playerSide);

        Inhibitor topInhibitorRed = new Inhibitor("redTopInhibitor", (float) (width * 0.75), (float) (height * 0.11), 0, 1, playerSide);
        Inhibitor midInhibitorRed = new Inhibitor("redMidInhibitor", (float) (width * 0.78), (float) (height * 0.22), 0, 1, playerSide);
        Inhibitor bottomInhibitorRed = new Inhibitor("redBottomInhibitor", (float) (width * 0.91), (float) (height * 0.35), 0, 1, playerSide);

        Turret topTurret1Red = new Turret("RedTopTurret1", (float) (width * 0.33), (float) (height * 0.10), 100, 1, playerSide);
        Turret topTurret2Red = new Turret("RedTopTurret2", (float) (width * 0.65), (float) (height * 0.13), 100, 1, playerSide);
        Turret topTurret3Red = new Turret("RedTopTurret3", (float) (width * 0.71), (float) (height * 0.12), 100, 1, playerSide);

        Turret midTurret1Red = new Turret("RedMidTurret1", (float) (width * 0.62), (float) (height * 0.41), 100, 1, playerSide);
        Turret midTurret2Red = new Turret("RedMidTurret2", (float) (width * 0.67), (float) (height * 0.31), 100, 1, playerSide);
        Turret midTurret3Red = new Turret("RedMidTurret3", (float) (width * 0.75), (float) (height * 0.26), 100, 1, playerSide);

        Turret bottomTurret1Red = new Turret("RedBottomTurret1", (float) (width * 0.97), (float) (height * 0.67), 100, 1, playerSide);
        Turret bottomTurret2Red = new Turret("RedBottomTurret2", (float) (width * 0.90), (float) (height * 0.43), 100, 1, playerSide);
        Turret bottomTurret3Red = new Turret("RedBottomTurret3", (float) (width * 0.91), (float) (height * 0.30), 100, 1, playerSide);

        Nexus  nexusRed = new Nexus("redNexus", (float) (width * 0.87), (float) (height * 0.13), (int) (diagonal * 0.18), 1, playerSide);

        Dragon dragon = new Dragon("dragon", (float) (width * 0.69), (float) (height * 0.68),0, 0);

        fogView.drawIcon(nexusBlue);

        fogView.drawIcon(topInhibitorBlue);
        fogView.drawIcon(midInhibitorBlue);
        fogView.drawIcon(bottomInhibitorBlue);

        fogView.drawIcon(topTurret1Blue);
        fogView.drawIcon(topTurret2Blue);
        fogView.drawIcon(topTurret3Blue);

        fogView.drawIcon(midTurret1Blue);
        fogView.drawIcon(midTurret2Blue);
        fogView.drawIcon(midTurret3Blue);

        fogView.drawIcon(bottomTurret1Blue);
        fogView.drawIcon(bottomTurret2Blue);
        fogView.drawIcon(bottomTurret3Blue);

        fogView.drawIcon(nexusRed);

        fogView.drawIcon(topInhibitorRed);
        fogView.drawIcon(midInhibitorRed);
        fogView.drawIcon(bottomInhibitorRed);

        fogView.drawIcon(topTurret1Red);
        fogView.drawIcon(topTurret2Red);
        fogView.drawIcon(topTurret3Red);

        fogView.drawIcon(midTurret1Red);
        fogView.drawIcon(midTurret2Red);
        fogView.drawIcon(midTurret3Red);

        fogView.drawIcon(bottomTurret1Red);
        fogView.drawIcon(bottomTurret2Red);
        fogView.drawIcon(bottomTurret3Red);

        fogView.drawIcon(dragon);
    }

    private void moveChampion(Champion champion) {

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

                realTimeSpend++;
            } finally {
                handler.postDelayed(runTimer, 10);
            }
        }
    };
}