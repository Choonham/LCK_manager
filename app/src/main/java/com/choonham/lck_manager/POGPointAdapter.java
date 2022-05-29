package com.choonham.lck_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class POGPointAdapter extends BaseAdapter {

    String[] pogList;
    int[] positionIcons;
    int[] pogPointList;
    private LayoutInflater inflater;
    private Context context;

    public POGPointAdapter(String[] pogList, int[] positionIcons, int[] pogPointList, Context context) {
        this.pogList = pogList;
        this.positionIcons = positionIcons;
        this.context = context;
        this.pogPointList = pogPointList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return pogList.length;
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
        pogPositionIcon.setImageResource(positionIcons[i]);
        pogSeason.setText("22 SPR");
        pogName.setText(pogList[i]);
        pogPoint.setText(Integer.toString(pogPointList[i]));
        return view;
    }
}
