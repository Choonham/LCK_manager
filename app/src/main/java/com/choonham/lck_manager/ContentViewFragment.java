package com.choonham.lck_manager;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentViewFragment extends Fragment {

    ViewPager2 pager;

    public ContentViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContentViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContentViewFragment newInstance() {
        ContentViewFragment fragment = new ContentViewFragment();
        Bundle args = new Bundle();
        /*args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_content_view, container, false);

        pager = rootView.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(5);

        MyPagerAdapter adapter = new MyPagerAdapter(getActivity());

        TransferWindow transferWindow = new TransferWindow();
        TeamRoster teamRoster = new TeamRoster();

        MainView mainView = new MainView();
        mainView.setParentFragment(this);

        LeagueSchedule leagueSchedule = new LeagueSchedule();
        LeagueRanking leagueRanking = new LeagueRanking();

        adapter.addItem(transferWindow);
        adapter.addItem(teamRoster);
        adapter.addItem(mainView);
        adapter.addItem(leagueSchedule);
        adapter.addItem(leagueRanking);

        pager.setAdapter(adapter);

        pager.setCurrentItem(2);

        BottomNavigationView bottomNavigation = rootView.findViewById(R.id.bottom_navigation);

        bottomNavigation.setSelectedItemId(R.id.tab3);

        bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        pager.setCurrentItem(0);
                        return true;

                    case R.id.tab2:
                        pager.setCurrentItem(1);
                        return true;

                    case R.id.tab3:
                        pager.setCurrentItem(2);
                        return true;

                    case R.id.tab4:
                        pager.setCurrentItem(3);
                        return true;

                    case R.id.tab5:
                        pager.setCurrentItem(4);
                        return true;
                }

                return false;
            }
        });

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigation.setSelectedItemId(R.id.tab1);
                        break;

                    case 1:
                        bottomNavigation.setSelectedItemId(R.id.tab2);
                        break;

                    case 2:
                        bottomNavigation.setSelectedItemId(R.id.tab3);
                        break;

                    case 3:
                        bottomNavigation.setSelectedItemId(R.id.tab4);
                        break;

                    case 4:
                        bottomNavigation.setSelectedItemId(R.id.tab5);
                        break;
                }
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
}