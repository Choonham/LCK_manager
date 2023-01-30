package com.choonham.lck_manager;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InGameFragment extends Fragment {

    TabLayout tabLayout;
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

        ImageView map = rootView.findViewById(R.id.in_game_lift_map_image_view);

        ViewTreeObserver vto = map.getViewTreeObserver();

        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                map.getViewTreeObserver().removeOnPreDrawListener(this);

                MapFogView fogView = new MapFogView(getContext(), 1, map.getMeasuredWidth(), map.getMeasuredHeight());

                FrameLayout fogLayout = rootView.findViewById(R.id.in_game_fog_layout);
                fogLayout.addView(fogView);

                Log.d("돌아", "돌아");

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
}