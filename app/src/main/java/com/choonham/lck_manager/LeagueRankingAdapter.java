package com.choonham.lck_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeagueRankingAdapter extends BaseAdapter {

    String[] teamList;
    int[] winList;
    private LayoutInflater inflater;
    private Context context;

    public LeagueRankingAdapter(String[] teamList, int[] winList, Context context) {
        this.teamList = teamList;
        this.winList = winList;
        this.context = context;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return teamList.length;
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
            view = inflater.inflate(R.layout.league_ranking_list_item, viewGroup, false);
        }

        TextView teamRank = view.findViewById(R.id.team_rank_league_ranking);
        TextView teamName = view.findViewById(R.id.team_name_league_ranking);
        TextView teamWins = view.findViewById(R.id.team_win_league_ranking);
        TextView teamLoses = view.findViewById(R.id.team_lose_league_ranking);
        TextView teamWR = view.findViewById(R.id.team_win_ratio_league_ranking);
        TextView teamWD = view.findViewById(R.id.team_win_diff_league_ranking);

        teamRank.setText(Integer.toString(i+1));
        teamName.setText(teamList[i]);
        teamWins.setText(Integer.toString(winList[i]));
        teamLoses.setText(Integer.toString(4- winList[i]));
        teamWR.setText(String.format("%.1f", (float)winList[i]/4));
        teamWD.setText(Integer.toString((int) (winList[i] * ((Math.random() *2) + 1))));

        return view;
    }
}
