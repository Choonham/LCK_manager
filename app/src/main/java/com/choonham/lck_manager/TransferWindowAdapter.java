package com.choonham.lck_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TransferWindowAdapter extends BaseAdapter {
    Context context;
    String[] transferWindowPlayerList;
    int[] positionIconList;
    float[] transferWindowAvgList;
    float[] transferWindowStabilityList;

    LayoutInflater inflater;

    public TransferWindowAdapter(Context context, String[] transferWindowPlayerList, int[] positionIconList, float[] transferWindowAvgList, float[] transferWindowStabilityList) {
        this.context = context;
        this.transferWindowPlayerList = transferWindowPlayerList;
        this.positionIconList = positionIconList;
        this.transferWindowAvgList = transferWindowAvgList;
        this.transferWindowStabilityList = transferWindowStabilityList;

        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return transferWindowPlayerList.length;
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
            view = inflater.inflate(R.layout.player_status_for_list, viewGroup, false);
        }

        TextView mainRosterSeason = view.findViewById(R.id.player_season_for_list);
        TextView mainRosterPlayerName = view.findViewById(R.id.player_name_for_list);
        TextView mainRosterAvg = view.findViewById(R.id.player_avg_for_list);
        TextView mainRosterStability = view.findViewById(R.id.player_stability_for_list);

        ImageView positionIcon = view.findViewById(R.id.main_roster_position_icon);


        mainRosterSeason.setText("22 SPR");
        mainRosterPlayerName.setText(transferWindowPlayerList[i]);
        mainRosterAvg.setText(Float.toString(transferWindowAvgList[i]));
        mainRosterStability.setText(Float.toString(transferWindowStabilityList[i]));

        positionIcon.setImageResource(positionIconList[i]);
        positionIcon.setTag(positionIconList[i]);

        return view;
    }
}
