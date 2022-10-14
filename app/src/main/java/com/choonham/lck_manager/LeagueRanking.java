package com.choonham.lck_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.dao.LeagueRankingDAO;
import com.choonham.lck_manager.dao.TeamDAO;
import com.choonham.lck_manager.entity.*;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedLeagueRanking;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.choonham.lck_manager.room.AppDatabase;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class LeagueRanking extends Fragment {
    private final ActivityTagEnum TAG = ActivityTagEnum.LEAGUE_RANKING;

    private List<JoinedPlayer> pogPlayerList;

    Boolean isLeagueRankingLoad = false;

    private List<JoinedLeagueRanking> leagueRanking;

    ListView leagueRankingListView;
    ListView pogPointListView;

    AppDatabase db;

    SharedPreferences userPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.league_ranking, container, false);

        leagueRanking = new ArrayList<>();

        userPreferences = Common.getPreferences(getContext());

        db = AppDatabase.getInstance(getContext());

        loadLeagueRanking();

        while(!isLeagueRankingLoad) {}

        leagueRankingListView = view.findViewById(R.id.league_ranking_list_view);
        pogPointListView = view.findViewById(R.id.pog_point_list_view);

        Common common = Common.getInstance();
        pogPlayerList = common.getTempPlayerList(2);

        LeagueRankingAdapter leagueRankingAdapter = new LeagueRankingAdapter(leagueRanking, getContext());
        POGPointAdapter pogPointAdapter = new POGPointAdapter(getContext(), pogPlayerList);

        leagueRankingListView.setAdapter(leagueRankingAdapter);
        pogPointListView.setAdapter(pogPointAdapter);

        leagueRankingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                Intent intent = new Intent(getContext(), TeamInfoPopUpActivity.class);

                TeamEntity teamEntity = leagueRanking.get(i).teamEntity;
                LeagueRankingEntity rankEntity = leagueRanking.get(i).leagueRankingEntity;
                intent.putExtra("teamName", teamEntity.getTeamName());
                intent.putExtra("teamRank", rankEntity.getRank());
                intent.putExtra("teamCode", teamEntity.getTeamCode());

                startActivity(intent);
            }
        });

        pogPointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);

                TextView season = selectedView.findViewById(R.id.player_season_pog_point);
                TextView name = selectedView.findViewById(R.id.player_name_pog_point);
                ImageView positionIcon = selectedView.findViewById(R.id.pog_point_position_icon);
                int drawableRef = (int) positionIcon.getTag();

                intent.putExtra("playerSeason", season.getText());
                intent.putExtra("playerName", name.getText());
                intent.putExtra("positionIcon", drawableRef);

                intent.putExtra("playerAvg", "112.5");
                intent.putExtra("playerStability", "112.5");

                intent.putExtra("playerEntity", pogPlayerList.get(i).playerEntity);

                intent.putExtra("seasonEntity", pogPlayerList.get(i).seasonEntity);

                intent.putExtra("tag", TAG);

                /*
                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(selectedView, TAG, getContext(), 0);
                */
                startActivity(intent);
            }
        });


        return view;
    }

    private void loadLeagueRanking() {
        LeagueRankingDAO leagueRankingDAO = db.leagueRankingDAO();

        leagueRankingDAO.loadLeagueRanking()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(value -> {
                    for(JoinedLeagueRanking entity : value) {
                        leagueRanking.add(entity);
                        Log.e("loadLeagueRanking :", entity.teamEntity.getTeamName());
                    }

                    isLeagueRankingLoad = true;
                })
                .doOnError(error -> {
                    Log.e("updateLeagueRankingList error 1:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }





}
