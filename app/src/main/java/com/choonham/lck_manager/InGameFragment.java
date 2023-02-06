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