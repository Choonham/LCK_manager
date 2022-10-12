package com.choonham.lck_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.choonham.lck_manager.entity.TeamRank;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedLeagueRanking;

import java.util.List;

public class LeagueRankingAdapter extends BaseAdapter {

    private final ActivityTagEnum TAG = ActivityTagEnum.LEAGUE_RANKING_ADAPTER;

    private LayoutInflater inflater;
    private Context context;

    private List<JoinedLeagueRanking> teamRankList;

    public LeagueRankingAdapter(List<JoinedLeagueRanking> teamRankList, Context context) {
        this.teamRankList = teamRankList;
        this.context = context;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return teamRankList.size();
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
        teamName.setText(teamRankList.get(i).teamEntity.getTeamName());
        teamWins.setText(String.valueOf(teamRankList.get(i).teamEntity.getTotalWins()));
        teamLoses.setText(String.valueOf(teamRankList.get(i).teamEntity.getTotalLoses()));
        teamWR.setText(String.valueOf(teamRankList.get(i).teamEntity.getWinRate()));
        teamWD.setText(String.valueOf(teamRankList.get(i).teamEntity.getTotalWins() - teamRankList.get(i).teamEntity.getTotalLoses()));

        return view;
    }
}
