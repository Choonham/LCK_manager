package com.choonham.lck_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

public class LeagueRanking extends Fragment {
    String[] teamList = {"T1", "DRX", "DK", "BRO", "Gen", "KDF", "NS", "LSB", "KT", "HLE", "KMH"};
    int[] winList = {4, 4, 3, 3, 2, 2, 1, 1, 1, 0, 0};

    String[] pogList = {"Doran", "Faker", "Chovy", "Keria", "DoryDory"};
    int[] positionIcons = {
        R.drawable.position_top_icon,
        R.drawable.position_mid_icon,
        R.drawable.position_mid_icon,
        R.drawable.position_support_icon,
        R.drawable.position_support_icon
    };

    int[] pogPointList = {400, 400, 300, 200, 100};

    ListView leagueRankingListView;
    ListView pogPointListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.league_ranking, container, false);

        leagueRankingListView = view.findViewById(R.id.league_ranking_list_view);
        pogPointListView = view.findViewById(R.id.pog_point_list_view);

        LeagueRankingAdapter leagueRankingAdapter = new LeagueRankingAdapter(teamList, winList, getContext());
        POGPointAdapter pogPointAdapter = new POGPointAdapter(pogList, positionIcons, pogPointList, getContext());

        leagueRankingListView.setAdapter(leagueRankingAdapter);
        pogPointListView.setAdapter(pogPointAdapter);

        return view;
    }

}
