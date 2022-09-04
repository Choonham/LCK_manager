package com.choonham.lck_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;

import java.util.List;

public class POGPointAdapter extends BaseAdapter {
    private final ActivityTagEnum TAG = ActivityTagEnum.POG_POINT_ADAPTER;

    private List<JoinedPlayer> playerEntityList;

    private LayoutInflater inflater;
    private Context context;

    public POGPointAdapter(Context context, List<JoinedPlayer> playerEntityList) {
        this.playerEntityList = playerEntityList;
        this.context = context;
        inflater = (LayoutInflater.from(context));
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
        if(context != null) {
            view = inflater.inflate(R.layout.pog_point_list_item, viewGroup, false);
        }

        TextView pogRank = view.findViewById(R.id.player_rank_pog_point);
        ImageView pogPositionIcon = view.findViewById(R.id.pog_point_position_icon);

        TextView pogSeason = view.findViewById(R.id.player_season_pog_point);
        TextView pogName = view.findViewById(R.id.player_name_pog_point);
        TextView pogPoint = view.findViewById(R.id.pog_point);

        pogRank.setText(Integer.toString(i + 1));

        PlayerEntity playerEntity = playerEntityList.get(i).playerEntity;
        //PlayerEntity seasonEntity = playerEntityList.get(i).playerEntity;

        pogPositionIcon.setImageResource(playerEntity.getPositionIcon());
        pogPositionIcon.setTag(playerEntity.getPositionIcon());

        pogSeason.setText("22 SPR");
        pogName.setText(playerEntity.getPlayerName());
        pogPoint.setText(Integer.toString(playerEntity.getPogPoint()));
        return view;
    }
}
