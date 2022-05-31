package com.choonham.lck_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.choonham.lck_manager.vo.MatchScheduleVo;

import java.util.ArrayList;

public class LeagueScheduleAdapter extends BaseAdapter {

    int type;
    private static final int TYPE_MATCH_INFO = 0;
    private static final int TYPE_DATE_INFO = 1;

    ArrayList<MatchScheduleVo> matchScheduleList;
    ArrayList<String> matchDateList;

    private LayoutInflater inflater;
    private Context context;

    private int matchDateSubNum = 1;

    public LeagueScheduleAdapter(ArrayList<MatchScheduleVo> matchScheduleList, ArrayList<String> matchDateList, Context context) {
        this.matchScheduleList = matchScheduleList;
        this.context = context;
        this.matchDateList = matchDateList;

        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getItemViewType(int position) {
        int positionPlus = position;
        if(positionPlus % 3 == 0) {
            type = TYPE_DATE_INFO;
        } else {
            type = TYPE_MATCH_INFO;
        }
        return type;
    }

    @Override
    public boolean isEnabled(int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_MATCH_INFO:
                return true;
            case TYPE_DATE_INFO:
                return false;
        }
        return true;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return matchScheduleList.size() + matchDateList.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        int type = getItemViewType(position);


        if(context != null) {

            matchDateSubNum = (position)/3 + 1;

            switch (type) {
                case TYPE_MATCH_INFO:

                    view = inflater.inflate(R.layout.league_schedule_list_item_match_info, viewGroup, false);
                    TextView matchNum = view.findViewById(R.id.league_schedule_match_num);
                    TextView scoreA = view.findViewById(R.id.league_schedule_score_a);
                    TextView teamA = view.findViewById(R.id.league_schedule_team_a);
                    TextView teamB = view.findViewById(R.id.league_schedule_team_b);
                    TextView scoreB = view.findViewById(R.id.league_schedule_score_b);
                    matchNum.setText("Match " + Integer.toString(matchScheduleList.get(position - matchDateSubNum).getMatchNum()));
                    scoreA.setText(Integer.toString(matchScheduleList.get(position - matchDateSubNum).getScoreA()));
                    teamA.setText(matchScheduleList.get(position - matchDateSubNum).getTeamA());
                    teamB.setText(matchScheduleList.get(position - matchDateSubNum).getTeamB());
                    scoreB.setText(Integer.toString(matchScheduleList.get(position - matchDateSubNum).getScoreB()));

                    break;

                case TYPE_DATE_INFO:
                    view = inflater.inflate(R.layout.league_schedule_list_item_date, viewGroup, false);
                    TextView matchDate = view.findViewById(R.id.league_schedule_match_date);
                    if(position == 0) {
                        matchDate.setText("Day " + matchDateList.get(0));
                    } else {
                        matchDate.setText("Day " + matchDateList.get((position)/3));
                    }
                    break;
            }

        }
        return view;
    }
}
