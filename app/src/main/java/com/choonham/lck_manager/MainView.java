package com.choonham.lck_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

public class MainView extends Fragment {

    ListView matchScheduleListView;

    String[] matchScheduleTeamList = {"T1", "DRX", "Gen.G", "KT", "DWG"};
    int[] matchScheduleResultList = {1, 0, 2, 2, 2};
    int[] matchScheduleRankList = {1, 4, 2, 6, 2};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_view, container, false);

        MatchScheduleAdapter matchScheduleAdapter = new MatchScheduleAdapter(matchScheduleTeamList, matchScheduleResultList, matchScheduleRankList, getContext());
        matchScheduleListView = view.findViewById(R.id.match_schedule_list_view);

        matchScheduleListView.setAdapter(matchScheduleAdapter);

        return view;
    }
}
