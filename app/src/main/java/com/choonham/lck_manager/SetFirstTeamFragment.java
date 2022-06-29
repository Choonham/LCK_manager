package com.choonham.lck_manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SetFirstTeamFragment extends Fragment {

    ListView faPlayerListView;

    String[] tempFAPlayerList = {"Doran", "Pyosik", "Chovy", "Deft", "Keria", "Faker", "Gumayusi", "Geus", "Kiin"};
    float[] tempFAPlayerAvgList = {112.5f, 115.5f, 120.2f, 117.2f, 120.8f, 115.5f, 120.2f, 117.2f, 120.8f};
    float[] tempFAPlayerStabilityList = {5.1f, 6.8f, 9.3f, 8.2f, 1.3f, 6.8f, 9.3f, 8.2f, 1.3f};

    int[] positionIcons = {
            R.drawable.position_top_icon,
            R.drawable.position_jungle_icon,
            R.drawable.position_mid_icon,
            R.drawable.position_ad_icon,
            R.drawable.position_support_icon,
            R.drawable.position_mid_icon,
            R.drawable.position_ad_icon,
            R.drawable.position_top_icon,
            R.drawable.position_top_icon
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_set_first_team, container, false);

        Button button = rootView.findViewById(R.id.set_first_team_button);

        MainRosterAdapter faPlayerListAdapter = new MainRosterAdapter(getContext(), tempFAPlayerList, positionIcons, tempFAPlayerAvgList, tempFAPlayerStabilityList);

        faPlayerListView = rootView.findViewById(R.id.FA_player_list_view);
        faPlayerListView.setAdapter(faPlayerListAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });

        faPlayerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);
                TextView season = selectedView.findViewById(R.id.player_season_for_list);
                TextView name = selectedView.findViewById(R.id.player_name_for_list);
                ImageView positionIcon = selectedView.findViewById(R.id.main_roster_position_icon);
                int drawableRef = (int) positionIcon.getTag();

                intent.putExtra("playerSeason", season.getText());
                intent.putExtra("playerName", name.getText());
                intent.putExtra("positionIcon", drawableRef);

                TextView avg = selectedView.findViewById(R.id.player_avg_for_list);
                TextView stability = selectedView.findViewById(R.id.player_stability_for_list);
                intent.putExtra("playerAvg", avg.getText());
                intent.putExtra("playerStability", stability.getText());


                startActivity(intent);
            }
        });

        return rootView;
    }
}