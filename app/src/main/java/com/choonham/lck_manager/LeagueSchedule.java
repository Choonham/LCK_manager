package com.choonham.lck_manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.dao.LeagueRankingDAO;
import com.choonham.lck_manager.dao.LeagueScheduleDAO;
import com.choonham.lck_manager.dao.TeamDAO;
import com.choonham.lck_manager.dao.UserDAO;
import com.choonham.lck_manager.entity.LeagueScheduleEntity;
import com.choonham.lck_manager.entity.TeamEntity;
import com.choonham.lck_manager.entity.MatchData;
import com.choonham.lck_manager.entity.TempLeagueSchedule;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedLeagueRanking;
import com.choonham.lck_manager.room.AppDatabase;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.text.SimpleDateFormat;
import java.util.*;

public class LeagueSchedule extends Fragment {
    private final ActivityTagEnum TAG = ActivityTagEnum.LEAGUE_SCHEDULE;

    List<TeamEntity> teamList;

    boolean isLeagueScheduleLoad = false;

    TextView teamA;
    TextView teamB;

    TextView teamARank;
    TextView teamBRank;

    ArrayList<String> matchDateList = new ArrayList<>();

    ArrayList<LeagueScheduleEntity> leagueScheduleEntityList = new ArrayList<>();

    ListView leagueScheduleListView;

    AppDatabase db;

    UserDAO userDAO;

    LeagueRankingDAO leagueRankingDAO;

    JoinedLeagueRanking teamAEntityWithRank = null;
    JoinedLeagueRanking teamBEntityWithRank = null;

    boolean isTeamARankLoad = false;
    boolean isTeamBRankLoad = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.league_schedule, container, false);

        teamList = new ArrayList<>();

        db = AppDatabase.getInstance(getContext());

        leagueRankingDAO = db.leagueRankingDAO();

        getMatchDateArray(28);

        //getSeasonCode();

        //getTeamList();

        getLeagueSchedule();

        while(!isLeagueScheduleLoad) {}

        //leagueScheduleList = setLeagueSchedule(teamList);

        /*try{
            setLeagueScheduleMap();
        } catch (Exception e) {
            Log.e("error" , e.getMessage());
        }*/

        LeagueScheduleAdapter leagueScheduleAdapter = new LeagueScheduleAdapter(leagueScheduleEntityList, matchDateList, getContext());
        leagueScheduleListView = view.findViewById(R.id.league_schedule_list_view);
        leagueScheduleListView.setAdapter(leagueScheduleAdapter);

        getTeamARankEntity(leagueScheduleEntityList.get(0).getTeamCodeA());
        getTeamBRankEntity(leagueScheduleEntityList.get(0).getTeamCodeB());

        while(!isTeamARankLoad || !isTeamBRankLoad) {}

        leagueScheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                getTeamARankEntity(leagueScheduleEntityList.get(i).getTeamCodeA());
                getTeamBRankEntity(leagueScheduleEntityList.get(i).getTeamCodeB());

                while(!isTeamARankLoad || !isTeamBRankLoad) {}

                TextView teamA = view.findViewById(R.id.match_detail_team_a);
                TextView teamB = view.findViewById(R.id.match_detail_team_b);
                TextView matchInfo = view.findViewById(R.id.league_schedule_match_detail);

                //TextView selectedTeamA = selectedView.findViewById(R.id.league_schedule_team_a);
                teamA.setText(teamAEntityWithRank.teamEntity.getTeamName());

                //TextView selectedTeamB = selectedView.findViewById(R.id.league_schedule_team_b);
                teamB.setText(teamBEntityWithRank.teamEntity.getTeamName());

                TextView selectedMatch = selectedView.findViewById(R.id.league_schedule_match_num);
                matchInfo.setText(selectedMatch.getText());

            }
        });

        teamA = view.findViewById(R.id.match_detail_team_a);
        teamB = view.findViewById(R.id.match_detail_team_b);

        teamARank = view.findViewById(R.id.match_detail_team_a_rank);
        teamBRank = view.findViewById(R.id.match_detail_team_b_rank);

        teamA.setText(teamAEntityWithRank.teamEntity.getTeamName());
        teamB.setText(teamBEntityWithRank.teamEntity.getTeamName());

        teamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamName = teamAEntityWithRank.teamEntity.getTeamName();
                String teamRank = String.valueOf(teamAEntityWithRank.leagueRankingEntity.getRank());
                int teamCode = teamAEntityWithRank.teamEntity.getApiTeamCode();

                Intent intent = new Intent(getContext(), TeamInfoPopUpActivity.class);

                intent.putExtra("teamName", teamName);
                intent.putExtra("teamRank", teamRank);
                intent.putExtra("teamCode", teamCode);

                startActivity(intent);
            }
        });

        teamB.setOnClickListener(view1 -> {
            String teamName = teamBEntityWithRank.teamEntity.getTeamName();
            String teamRank = String.valueOf(teamBEntityWithRank.leagueRankingEntity.getRank());
            int teamCode = teamBEntityWithRank.teamEntity.getApiTeamCode();

            Intent intent = new Intent(getContext(), TeamInfoPopUpActivity.class);

            intent.putExtra("teamName", teamName);
            intent.putExtra("teamRank", teamRank);
            intent.putExtra("teamCode", teamCode);

            startActivity(intent);
        });

        return view;
    }



    public void getMatchDateArray(int i) {
        for(int j = 1; j <= i; j++) {
            matchDateList.add(Integer.toString(j));
        }
    }

    public void getSeasonCode() {
        userDAO = db.userDAO();

        userDAO.loadUserEntityById(1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    Common.CURR_SEASON_CODE = loadValue.getSeasonCode();
                })
                .doOnError(error -> {
                    Log.e("getSeasonCode error 2:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void getLeagueSchedule() {
        LeagueScheduleDAO leagueScheduleDAO = db.leagueScheduleDAO();

        leagueScheduleDAO.loadLeagueSchedule()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(value -> {
                    for(LeagueScheduleEntity leagueScheduleEntity : value) {
                        leagueScheduleEntityList.add(leagueScheduleEntity);
                    }

                    isLeagueScheduleLoad = true;
                })
                .doOnError(error -> {
                    Log.e("getTeamList error 2:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void getTeamARankEntity(int teamCode) {
        Log.e("getTeamARankEntity", String.valueOf(teamCode));
        leagueRankingDAO.loadTeamRanking(teamCode)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(value -> {
                    teamAEntityWithRank = value;

                    isTeamARankLoad = true;

                    Log.e("getTeamARankEntity 1", String.valueOf(value.leagueRankingEntity.getRank()));
                    Log.e("getTeamARankEntity 2", String.valueOf(value.leagueRankingEntity.getTeamCode()));

                    Log.e("getTeamARankEntity 3", String.valueOf(value.teamEntity.getApiTeamCode()));
                })
                .doOnError(error -> {
                    Log.e("getTeamARankEntity error", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void getTeamBRankEntity(int teamCode) {
        leagueRankingDAO.loadTeamRanking(teamCode)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(value -> {
                    teamBEntityWithRank = value;

                    isTeamBRankLoad = true;
                })
                .doOnError(error -> {
                    Log.e("getTeamARankEntity error", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }





}
