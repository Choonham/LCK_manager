package com.choonham.lck_manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import org.w3c.dom.Text;

public class TeamRoster extends Fragment {

    ListView teamMainRosterListView;
    ListView teamSubRosterListView;

    String[] tempMainRosterList = {"Doran", "Peanut", "Chovy", "Ruler", "Lehands"};
    float[] tempMainRosterAvgList = {112.5f, 115.5f, 120.2f, 117.2f, 120.8f};
    float[] tempMainRosterStabilityList = {5.1f, 6.8f, 9.3f, 8.2f, 1.3f};

    int[] positionIcons = {R.drawable.position_top_icon, R.drawable.position_jungle_icon, R.drawable.position_mid_icon, R.drawable.position_ad_icon, R.drawable.position_support_icon};

    TextView playerName;
    TextView playerSeason;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.team_roster, container, false);

        MainRosterAdapter mainRosterAdapter = new MainRosterAdapter(getContext(), tempMainRosterList, positionIcons, tempMainRosterAvgList, tempMainRosterStabilityList);
        teamMainRosterListView = view.findViewById(R.id.main_roster_list);
        /*ViewGroup header = (ViewGroup) inflater.inflate(R.layout.main_roster_header_view, teamMainRosterListView, false);
        header.setPadding(0, 20, 0, 0);*/

        teamSubRosterListView = view.findViewById(R.id.sub_roster_list);
        /*teamSubRosterListView.addHeaderView(header, null, false);*/

        teamMainRosterListView.setAdapter((ListAdapter) mainRosterAdapter);
        teamSubRosterListView.setAdapter((ListAdapter) mainRosterAdapter);

        teamMainRosterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                startActivity(intent);
            }
        });

        teamSubRosterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                startActivity(intent);
            }
        });

        return view;
    }
}
