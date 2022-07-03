package com.choonham.lck_manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import org.w3c.dom.Text;

import java.util.List;

public class TeamRoster extends Fragment {

    private final ActivityTagEnum TAG = ActivityTagEnum.TEAM_ROSTER;

    private List<PlayerEntity> playerEntityList;

    ListView teamMainRosterListView;
    ListView teamSubRosterListView;

    TextView playerName;
    TextView playerSeason;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.team_roster, container, false);

        Common common = Common.getInstance();
        playerEntityList = common.getTempPlayerList(0);
        MainRosterAdapter mainRosterAdapter = new MainRosterAdapter(getContext(), playerEntityList);
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
                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(playerEntityList, i, selectedView, TAG, getContext(), 0);

                startActivity(intent);
            }
        });

        teamSubRosterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                /*Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);
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
                intent.putExtra("tag", TAG);

                startActivity(intent);*/

                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(playerEntityList, i, selectedView, TAG, getContext(), 0);

                startActivity(intent);
            }
        });

        return view;
    }
}
