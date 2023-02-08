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
import com.choonham.lck_manager.map_object.Inhibitor;
import com.choonham.lck_manager.map_object.Nexus;
import com.choonham.lck_manager.map_object.Turret;
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

        MapFogView fogView = new MapFogView(getContext());

        FrameLayout fogLayout = rootView.findViewById(R.id.in_game_fog_layout);
        fogLayout.addView(fogView);

        ViewTreeObserver vto = map.getViewTreeObserver();

        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                map.getViewTreeObserver().removeOnPreDrawListener(this);

                fogView.drawFog(map.getMeasuredWidth(), map.getMeasuredHeight());
                tempText.setText(map.getMeasuredWidth() + "/" + map.getMeasuredHeight());

                diagonal = Math.sqrt(Math.pow(map.getMeasuredWidth(), 2) + Math.pow(map.getMeasuredHeight(), 2));

                Inhibitor topInhibitor = new Inhibitor("blueTopInhibitor", (float) (map.getMeasuredWidth() * 0.09), (float) (map.getMeasuredHeight() * 0.73), 0, 0);
                Inhibitor midInhibitor = new Inhibitor("bluemidInhibitor", (float) (map.getMeasuredWidth() * 0.23), (float) (map.getMeasuredHeight() * 0.75), 0, 0);
                Inhibitor bottomInhibitor = new Inhibitor("bluebottomInhibitor", (float) (map.getMeasuredWidth() * 0.24), (float) (map.getMeasuredHeight() * 0.90), 0, 0);

                Turret topTurret1 = new Turret("topTurret1", (float) (map.getMeasuredWidth() * 0.12), (float) (map.getMeasuredHeight() * 0.29), 100, 0);
                Turret topTurret2 = new Turret("topTurret2", (float) (map.getMeasuredWidth() * 0.13), (float) (map.getMeasuredHeight() * 0.52), 100, 0);
                Turret topTurret3 = new Turret("topTurret3", (float) (map.getMeasuredWidth() * 0.09), (float) (map.getMeasuredHeight() * 0.68), 100, 0);

                Turret midTurret1 = new Turret("midTurret1", (float) (map.getMeasuredWidth() * 0.42), (float) (map.getMeasuredHeight() * 0.54), 100, 0);
                Turret midTurret2 = new Turret("midTurret2", (float) (map.getMeasuredWidth() * 0.36), (float) (map.getMeasuredHeight() * 0.65), 100, 0);
                Turret midTurret3 = new Turret("midTurret3", (float) (map.getMeasuredWidth() * 0.26), (float) (map.getMeasuredHeight() * 0.72), 100, 0);

                Turret bottomTurret1 = new Turret("bottomTurret1", (float) (map.getMeasuredWidth() * 0.75), (float) (map.getMeasuredHeight() * 0.93), 100, 0);
                Turret bottomTurret2 = new Turret("bottomTurret2", (float) (map.getMeasuredWidth() * 0.49), (float) (map.getMeasuredHeight() * 0.89), 100, 0);
                Turret bottomTurret3 = new Turret("bottomTurret3", (float) (map.getMeasuredWidth() * 0.30), (float) (map.getMeasuredHeight() * 0.91), 100, 0);

                Nexus  nexus = new Nexus("blueNexus", (float) (map.getMeasuredWidth() * 0.1), (float) (map.getMeasuredHeight() * 0.87), (int) (diagonal * 0.18), 0);

                fogView.setVision(nexus);
                fogView.drawIcon(nexus);

                fogView.setVision(topTurret1);
                fogView.setVision(topTurret2);
                fogView.setVision(topTurret3);

                fogView.setVision(midTurret1);
                fogView.setVision(midTurret2);
                fogView.setVision(midTurret3);

                fogView.setVision(bottomTurret1);
                fogView.setVision(bottomTurret2);
                fogView.setVision(bottomTurret3);

                fogView.drawIcon(topInhibitor);
                fogView.drawIcon(midInhibitor);
                fogView.drawIcon(bottomInhibitor);

                fogView.drawIcon(topTurret1);
                fogView.drawIcon(topTurret2);
                fogView.drawIcon(topTurret3);

                fogView.drawIcon(midTurret1);
                fogView.drawIcon(midTurret2);
                fogView.drawIcon(midTurret3);

                fogView.drawIcon(bottomTurret1);
                fogView.drawIcon(bottomTurret2);
                fogView.drawIcon(bottomTurret3);

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