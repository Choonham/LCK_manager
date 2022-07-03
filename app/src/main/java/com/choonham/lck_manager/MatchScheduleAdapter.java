package com.choonham.lck_manager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.choonham.lck_manager.enums.ActivityTagEnum;

public class MatchScheduleAdapter extends BaseAdapter {

    private final ActivityTagEnum TAG = ActivityTagEnum.MATCH_SCHEDULE_ADAPTER;

    private String[] matchScheduleTeamList;
    private int[] matchScheduleResultList;
    private int[] matchScheduleRankList;

    private LayoutInflater inflater;
    private Context context;

    public MatchScheduleAdapter(String[] matchScheduleTeamList, int[] matchScheduleResultList, int[] matchScheduleRankList, Context context) {
        this.matchScheduleTeamList = matchScheduleTeamList;
        this.matchScheduleResultList = matchScheduleResultList;
        this.matchScheduleRankList = matchScheduleRankList;
        this.context = context;

        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return matchScheduleTeamList.length;
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
            view = inflater.inflate(R.layout.match_schedule_list_item, viewGroup, false);
        }

        TextView matchScheduleResult = view.findViewById(R.id.match_schedule_win_lose);
        TextView matchScheduleTeam = view.findViewById(R.id.match_schedule_team_name);
        TextView matchScheduleRank = view.findViewById(R.id.match_schedule_rank);

        if(matchScheduleResultList[i] == 0) {
            matchScheduleResult.setText("L");
            matchScheduleResult.setTextColor(Color.BLUE);
        } else if(matchScheduleResultList[i] == 1) {
            matchScheduleResult.setText("W");
            matchScheduleResult.setTextColor(Color.RED);
        } else {
            matchScheduleResult.setText(" ");
        }

        matchScheduleRank.setText(matchScheduleRankList[i] + "위");
        matchScheduleTeam.setText(matchScheduleTeamList[i]);

        if(i == 2) {
            view.findViewById(R.id.match_schedule_list_item).setBackground(ContextCompat.getDrawable(context, R.drawable.list_view_select_border));
        }

        return view;
    }
}
