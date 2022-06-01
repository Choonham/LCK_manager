package com.choonham.lck_manager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainRosterAdapter extends BaseAdapter {

    Context context;
    String[] mainRosterList;
    int[] positionIconList;
    float[] mainRosterAvgList;
    float[] mainRosterStabilityList;

    LayoutInflater inflater;

    public MainRosterAdapter(Context applicationContext, String[] mainRosterList, int[] positionIconList, float[] mainRosterAvgList, float[] mainRosterStabilityList) {
        this.context = applicationContext;
        this.mainRosterList = mainRosterList;
        this.positionIconList = positionIconList;
        this.mainRosterAvgList = mainRosterAvgList;
        this.mainRosterStabilityList = mainRosterStabilityList;

        inflater = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return mainRosterList.length;
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

        mainRosterSeason.setText("22 SPR");
        mainRosterPlayerName.setText(mainRosterList[i]);
        mainRosterAvg.setText(Float.toString(mainRosterAvgList[i]));
        mainRosterStability.setText(Float.toString(mainRosterStabilityList[i]));

        positionIcon.setImageResource(positionIconList[i]);
        positionIcon.setTag(positionIconList[i]);

        return view;
    }

}
