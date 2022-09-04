package com.choonham.lck_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;

import java.util.List;

public class TeamInfoAdapter extends BaseAdapter {

    private final ActivityTagEnum TAG = ActivityTagEnum.TEAM_INFO_ADAPTER;

    Context context;

    List<JoinedPlayer> playerEntityList;

    LayoutInflater inflater;

    public TeamInfoAdapter(Context applicationContext, List<JoinedPlayer> playerEntityList) {
        this.context = applicationContext;
        this.playerEntityList = playerEntityList;

        inflater = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return playerEntityList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.player_status_for_list_popup, viewGroup, false);

        TextView mainRosterSeason = view.findViewById(R.id.player_season_for_list_popup);
        TextView mainRosterPlayerName = view.findViewById(R.id.player_name_for_list_popup);
        TextView mainRosterAvg = view.findViewById(R.id.player_avg_for_list_popup);
        TextView mainRosterStability = view.findViewById(R.id.player_stability_for_list_popup);

        ImageView positionIcon = view.findViewById(R.id.main_roster_position_icon_popup);

        PlayerEntity playerEntity = playerEntityList.get(i).playerEntity;

        //SeasonEntity seasonEntity = playerEntityList.get(i).seasonEntity;

        mainRosterSeason.setText("22 SPR");
        mainRosterPlayerName.setText(playerEntity.getPlayerName());
        mainRosterAvg.setText(Double.toString(playerEntity.getPhysical()));
        mainRosterStability.setText(Double.toString(playerEntity.getStability()));

        positionIcon.setImageResource(playerEntity.getPositionIcon());
        positionIcon.setTag(playerEntity.getPositionIcon());

        return view;
    }

}
