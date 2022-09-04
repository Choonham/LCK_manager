package com.choonham.lck_manager;

import android.content.Context;
import android.util.Log;
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

public class MainRosterAdapter extends BaseAdapter {

    private final ActivityTagEnum TAG = ActivityTagEnum.MAIN_ROSTER_ADAPTER;

    Context context;
    List<JoinedPlayer> playerEntityList;

    LayoutInflater inflater;

    public MainRosterAdapter(Context applicationContext, List<JoinedPlayer> playerEntityList) {
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
        view = inflater.inflate(R.layout.player_status_for_list, viewGroup, false);

        TextView mainRosterSeason = view.findViewById(R.id.player_season_for_list);
        TextView mainRosterPlayerName = view.findViewById(R.id.player_name_for_list);
        TextView mainRosterAvg = view.findViewById(R.id.player_avg_for_list);
        TextView mainRosterStability = view.findViewById(R.id.player_stability_for_list);

        ImageView positionIcon = view.findViewById(R.id.main_roster_position_icon);

        PlayerEntity playerEntity = playerEntityList.get(i).playerEntity;
        SeasonEntity seasonEntity = playerEntityList.get(i).seasonEntity;

        mainRosterSeason.setText(seasonEntity.getSeasonForShort());
        mainRosterPlayerName.setText(playerEntity.getPlayerName());
        mainRosterAvg.setText(Double.toString(playerEntity.getPhysical()));
        mainRosterStability.setText(Double.toString(playerEntity.getStability()));

        positionIcon.setImageResource(playerEntity.getPositionIcon());
        positionIcon.setTag(playerEntity.getPositionIcon());

        return view;
    }

}
