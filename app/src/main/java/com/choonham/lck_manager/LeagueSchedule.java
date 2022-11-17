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
import com.choonham.lck_manager.dao.*;
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

    int teamAvgA;
    int teamAvgB;

    TextView teamA;
    TextView teamB;

    TextView teamARank;
    TextView teamBRank;

    TextView teamAWinInfo;
    TextView teamBWinInfo;

    TextView teamAAvgView;
    TextView teamBAvgView;

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

        teamAvgA = 0;
        teamAvgB = 0;

        db = AppDatabase.getInstance(getContext());

        leagueRankingDAO = db.leagueRankingDAO();

        getMatchDateArray(28);

        //getSeasonCode();

        //getTeamList();

        //getLeagueSchedule();

        //while(!isLeagueScheduleLoad) {}

        //leagueScheduleList = setLeagueSchedule(teamList);

        /*try{
            setLeagueScheduleMap();
        } catch (Exception e) {
            Log.e("error" , e.getMessage());
        }*/

        leagueScheduleListView = view.findViewById(R.id.league_schedule_list_view);

        db.leagueScheduleDAO().loadLeagueSchedule().observe(this, value -> {
            leagueScheduleEntityList.addAll(value);

            LeagueScheduleAdapter leagueScheduleAdapter2 = new LeagueScheduleAdapter(leagueScheduleEntityList, matchDateList, getContext());
            leagueScheduleListView.setAdapter(leagueScheduleAdapter2);

            db.leagueRankingDAO().loadTeamRanking(leagueScheduleEntityList.get(0).getTeamCodeA()).observe(getViewLifecycleOwner(), value2 -> {
                teamA.setText(value2.teamEntity.getTeamName());

                teamAEntityWithRank = value2;

                TextView teamA = view.findViewById(R.id.match_detail_team_a);

                //TextView selectedTeamA = selectedView.findViewById(R.id.league_schedule_team_a);
                teamA.setText(teamAEntityWithRank.teamEntity.getTeamName());


                teamARank.setText(teamAEntityWithRank.leagueRankingEntity.getRank()+"위");

                int teamAwin = teamAEntityWithRank.teamEntity.getTotalWins();
                int teamALose = teamAEntityWithRank.teamEntity.getTotalLoses();

                String teamAInfoStr = teamAwin + "W " + teamALose + "L " + (teamAwin - teamALose);

                teamAWinInfo.setText(teamAInfoStr);
            });

            db.leagueRankingDAO().loadTeamRanking(leagueScheduleEntityList.get(0).getTeamCodeB()).observe(getViewLifecycleOwner(), value3 -> {
                teamB.setText(value3.teamEntity.getTeamName());

                teamBEntityWithRank = value3;

                TextView teamB = view.findViewById(R.id.match_detail_team_b);

                //TextView selectedTeamB = selectedView.findViewById(R.id.league_schedule_team_b);
                teamB.setText(teamBEntityWithRank.teamEntity.getTeamName());

                teamBRank.setText(teamBEntityWithRank.leagueRankingEntity.getRank()+"위");

                int teamBwin = teamBEntityWithRank.teamEntity.getTotalWins();
                int teamBLose = teamBEntityWithRank.teamEntity.getTotalLoses();

                String teamBInfoStr = teamBwin + "W " + teamBLose + "L " + (teamBwin - teamBLose);

                teamBWinInfo.setText(teamBInfoStr);
            });

        });

        //getTeamRankEntity(leagueScheduleEntityList.get(0).getTeamCodeA(), leagueScheduleEntityList.get(0).getTeamCodeB());

        //while(!isTeamARankLoad || !isTeamBRankLoad) {}

        teamA = view.findViewById(R.id.match_detail_team_a);
        teamB = view.findViewById(R.id.match_detail_team_b);

        teamARank = view.findViewById(R.id.match_detail_team_a_rank);
        teamBRank = view.findViewById(R.id.match_detail_team_b_rank);

        teamAWinInfo = view.findViewById(R.id.match_detail_team_a_record);
        teamBWinInfo = view.findViewById(R.id.match_detail_team_b_record);

        teamAAvgView = view.findViewById(R.id.match_detail_team_a_avg);
        teamBAvgView = view.findViewById(R.id.match_detail_team_b_avg);

        leagueScheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                int selectedIndex = (i-1) - (i/3);

                int teamACode = leagueScheduleEntityList.get(selectedIndex).getTeamCodeA();
                int teamBCode = leagueScheduleEntityList.get(selectedIndex).getTeamCodeB();

                //getTeamRankEntity(teamACode, teamBCode);

                db.leagueRankingDAO().loadTeamRanking(teamACode).observe(getViewLifecycleOwner(), value -> {
                    teamAEntityWithRank = value;

                    TextView teamA = view.findViewById(R.id.match_detail_team_a);

                    //TextView selectedTeamA = selectedView.findViewById(R.id.league_schedule_team_a);
                    teamA.setText(teamAEntityWithRank.teamEntity.getTeamName());


                    teamARank.setText(teamAEntityWithRank.leagueRankingEntity.getRank()+"위");

                    int teamAwin = teamAEntityWithRank.teamEntity.getTotalWins();
                    int teamALose = teamAEntityWithRank.teamEntity.getTotalLoses();

                    String teamAInfoStr = teamAwin + "W " + teamALose + "L " + (teamAwin - teamALose);

                    teamAWinInfo.setText(teamAInfoStr);
                });

                db.leagueRankingDAO().loadTeamRanking(teamBCode).observe(getViewLifecycleOwner(), value -> {
                    teamBEntityWithRank = value;

                    TextView teamB = view.findViewById(R.id.match_detail_team_b);

                    //TextView selectedTeamB = selectedView.findViewById(R.id.league_schedule_team_b);
                    teamB.setText(teamBEntityWithRank.teamEntity.getTeamName());

                    teamBRank.setText(teamBEntityWithRank.leagueRankingEntity.getRank()+"위");

                    int teamBwin = teamBEntityWithRank.teamEntity.getTotalWins();
                    int teamBLose = teamBEntityWithRank.teamEntity.getTotalLoses();

                    String teamBInfoStr = teamBwin + "W " + teamBLose + "L " + (teamBwin - teamBLose);

                    teamBWinInfo.setText(teamBInfoStr);
                });

                //getTeamMainRosterAvg(teamACode, teamBCode);

                teamAvgA = 0;
                teamAvgB = 0;

                teamAAvgView.setText(String.valueOf(teamAvgA));
                teamBAvgView.setText(String.valueOf(teamAvgB));

                db.rosterDAO().getTeamMainRosterAvg(teamACode, 1).observe(getViewLifecycleOwner(), value -> {
                    teamAAvgView.setText(String.valueOf(teamAvgA));
                    teamAvgA = value;
                });

                db.rosterDAO().getTeamMainRosterAvg(teamBCode, 1).observe(getViewLifecycleOwner(), value -> {
                    teamAvgB = value;
                    teamBAvgView.setText(String.valueOf(teamAvgB));
                });

                TextView matchInfo = view.findViewById(R.id.league_schedule_match_detail);
                TextView selectedMatch = selectedView.findViewById(R.id.league_schedule_match_num);
                matchInfo.setText(selectedMatch.getText());

            }
        });

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

    /*public void getSeasonCode() {
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
*/
    /*public void getLeagueSchedule() {
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
                    isLeagueScheduleLoad = true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/

    /*public void getTeamRankEntity(int teamACode, int teamBCode) {
        isTeamARankLoad = false;
        isTeamBRankLoad = false;

        leagueRankingDAO.loadTeamRanking(teamACode)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(value -> {
                    teamAEntityWithRank = value;

                    isTeamARankLoad = true;
                })
                .doOnError(error -> {
                    Log.e("getTeamARankEntity error", error.getMessage());
                    isTeamARankLoad = true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        leagueRankingDAO.loadTeamRanking(teamBCode)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(value -> {
                    teamBEntityWithRank = value;

                    isTeamBRankLoad = true;
                })
                .doOnError(error -> {
                    Log.e("getTeamARankEntity error", error.getMessage());
                    isTeamBRankLoad = true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/

    /*public void getTeamMainRosterAvg(int teamACode, int teamBCode) {
        teamAvgA = 0;
        teamAvgB = 0;

        RosterDAO rosterDAO = db.rosterDAO();

        rosterDAO.getTeamMainRosterAvg(teamACode, 1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(value -> {
                    teamAvgA = value;
                    Log.e("getTeamMainRosterAvg A:", String.valueOf(value));
                })
                .doOnError(error -> {
                    Log.e("getTeamList error 2:", error.getMessage());
                    teamAvgA = 999;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        rosterDAO.getTeamMainRosterAvg(teamBCode, 1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(value -> {
                    teamAvgB = value;
                    Log.e("getTeamMainRosterAvg B:", String.valueOf(value));
                })
                .doOnError(error -> {
                    Log.e("getTeamList error 2:", error.getMessage());
                    teamAvgB = 999;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/





}
