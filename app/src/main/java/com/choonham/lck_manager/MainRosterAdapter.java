package com.choonham.lck_manager;

import android.annotation.SuppressLint;
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

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.main_roster_list_view, viewGroup, false);

        TextView mainRosterSeason = view.findViewById(R.id.main_roster_season);
        TextView mainRosterPlayerName = view.findViewById(R.id.main_roster_player_name);
        TextView mainRosterAvg = view.findViewById(R.id.main_roster_avg);
        TextView mainRosterStability = view.findViewById(R.id.main_roster_stability);

        ImageView positionIcon = view.findViewById(R.id.main_roster_position_icon);

        mainRosterSeason.setText("22 SPR");
        mainRosterPlayerName.setText(mainRosterList[i]);
        mainRosterAvg.setText((int) mainRosterAvgList[i]);
        mainRosterStability.setText((int) mainRosterStabilityList[i]);

        positionIcon.setImageResource(positionIconList[i]);


        return view;
    }

}
