package com.choonham.lck_manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.entity.MatchData;
import com.choonham.lck_manager.enums.ActivityTagEnum;

import java.util.List;

public class MatchScheduleAdapter extends BaseAdapter {

    private final ActivityTagEnum TAG = ActivityTagEnum.MATCH_SCHEDULE_ADAPTER;

    private List<MatchData> matchDataList;

    private LayoutInflater inflater;
    private Context context;

    private int currMatchIndex = 0;

    public MatchScheduleAdapter(List<MatchData> matchDataList, Context context) {
        this.matchDataList = matchDataList;
        this.context = context;

        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return matchDataList.size();
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

        if(matchDataList.get(i).getPlay_flag() == 1) {
            matchScheduleResult.setText("L");
            matchScheduleResult.setTextColor(Color.BLUE);
        } else if(matchDataList.get(i).getPlay_flag() == 2) {
            matchScheduleResult.setText("W");
            matchScheduleResult.setTextColor(Color.RED);
        } else {
            matchScheduleResult.setText(" ");
        }

        matchScheduleRank.setText(matchDataList.get(i).getTeam_rank() + "위");
        matchScheduleTeam.setText(matchDataList.get(i).getAgainst_team_name());

        if(matchDataList.get(i).getCurr_match() == 1) {
            view.findViewById(R.id.match_schedule_list_item).setBackground(ContextCompat.getDrawable(context, R.drawable.list_view_select_border));

            SharedPreferences userPreferences = Common.getPreferences(inflater.getContext());
            SharedPreferences.Editor editor = userPreferences.edit();

            editor.putInt("curr_match_index", i);
            editor.apply();
        }

        return view;
    }
}
