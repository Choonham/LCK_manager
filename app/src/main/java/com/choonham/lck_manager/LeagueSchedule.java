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
import com.choonham.lck_manager.dao.LeagueScheduleDAO;
import com.choonham.lck_manager.dao.TeamDAO;
import com.choonham.lck_manager.dao.UserDAO;
import com.choonham.lck_manager.entity.LeagueScheduleEntity;
import com.choonham.lck_manager.entity.TeamEntity;
import com.choonham.lck_manager.entity.MatchData;
import com.choonham.lck_manager.entity.TempLeagueSchedule;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.room.AppDatabase;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.text.SimpleDateFormat;
import java.util.*;

public class LeagueSchedule extends Fragment {
    private final ActivityTagEnum TAG = ActivityTagEnum.LEAGUE_SCHEDULE;

    private MaterialCalendarView calendarView;

    //String[] teamList = {"T1", "DRX", "DK", "BRO", "Gen", "KDF", "NS", "LSB", "KT", "HLE", "KMH"};
    List<TeamEntity> teamList;

    ArrayList<TeamEntity[]> leagueScheduleList;

    boolean isTeamListLoad = false;
    boolean isLeagueScheduleLoad = false;

    TextView teamA;
    TextView teamB;
    String teamName;
    String teamRank;

    TextView teamARank;
    TextView teamBRank;

    ArrayList<TempLeagueSchedule> tempLeagueScheduleList = new ArrayList<>();
    ArrayList<String> matchDateList = new ArrayList<>();

    ArrayList<LeagueScheduleEntity> leagueScheduleEntityList = new ArrayList<>();

    ListView leagueScheduleListView;

    AppDatabase db;

    UserDAO userDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.league_schedule, container, false);

        teamList = new ArrayList<>();

        getMatchDateArray(28);

        db = AppDatabase.getInstance(getContext());

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

        leagueScheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                TextView teamA = view.findViewById(R.id.match_detail_team_a);
                TextView teamB = view.findViewById(R.id.match_detail_team_b);
                TextView matchInfo = view.findViewById(R.id.league_schedule_match_detail);

                TextView selectedTeamA = selectedView.findViewById(R.id.league_schedule_team_a);
                teamA.setText(selectedTeamA.getText());

                TextView selectedTeamB = selectedView.findViewById(R.id.league_schedule_team_b);
                teamB.setText(selectedTeamB.getText());

                TextView selectedMatch = selectedView.findViewById(R.id.league_schedule_match_num);
                matchInfo.setText(selectedMatch.getText());

            }
        });

        teamA = view.findViewById(R.id.match_detail_team_a);
        teamB = view.findViewById(R.id.match_detail_team_b);

        teamARank = view.findViewById(R.id.match_detail_team_a_rank);
        teamBRank = view.findViewById(R.id.match_detail_team_b_rank);
        teamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamName = (String) teamA.getText();
                teamRank = (String) teamARank.getText();

                Intent intent = new Intent(getContext(), TeamInfoPopUpActivity.class);

                intent.putExtra("teamName", teamName);
                intent.putExtra("teanRank", teamRank);

                startActivity(intent);
            }
        });

        teamB.setOnClickListener(view1 -> {
            teamName = (String) teamB.getText();
            teamRank = (String) teamBRank.getText();

            Intent intent = new Intent(getContext(), TeamInfoPopUpActivity.class);

            intent.putExtra("teamName", teamName);
            intent.putExtra("teanRank", teamRank);

            startActivity(intent);
        });

        return view;
    }

    public void getMatchDateArray(int i) {
        for(int j = 1; j <= i; j++) {
            matchDateList.add(Integer.toString(j));
        }
    }

    public void setLeagueScheduleMap() throws Exception{

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse("2022-01-12");
     /*   Date endDate = formatter.parse("2022-02-18");*/

        LeagueScheduleDAO leagueScheduleDAO = db.leagueScheduleDAO();

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        /*Calendar end = Calendar.getInstance();
        end.setTime(endDate);*/

        Date date = start.getTime();
        int matchNum = 1;
        int matchDay = 1;

        for(int i = 1; i <= 55;) {

            Calendar temp = Calendar.getInstance();
            temp.setTime(date);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd E요일");
            String strNowDate = simpleDateFormat.format(date);

            int day = temp.get(Calendar.DAY_OF_WEEK);

            if(day >= 4 || day == 1) {

                for(int j = 0; j <= 1; j++) {
                    if(leagueScheduleList.size() > 0) {
                        TeamEntity[] tempMatch = leagueScheduleList.remove(0);

                        LeagueScheduleEntity leagueScheduleEntity = new LeagueScheduleEntity();

                        leagueScheduleEntity.setTeamA(tempMatch[0].getTeamName());
                        leagueScheduleEntity.setTeamB(tempMatch[1].getTeamName());
                        leagueScheduleEntity.setMatchNum(matchNum);
                        leagueScheduleEntity.setDate(date);

                        leagueScheduleEntityList.add(leagueScheduleEntity);

                        leagueScheduleDAO.insertLeagueSchedule(leagueScheduleEntity)
                                .subscribeOn(Schedulers.io())
                                .doOnSuccess(loadValue -> {
                                    Log.d("insertAllLeagueSchedule: ", String.valueOf(loadValue));
                                })
                                .doOnError(error -> {
                                    Log.e("insertAllLeagueSchedule error:", error.getMessage());
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe();

                        matchNum = matchNum + 1;
                    }
                }

                /*matchDateList.add(strNowDate);*/
                matchDateList.add(Integer.toString(matchDay));
                /*Log.d("dd", matchScheduleList.size() + "||" + matchDateList.size());*/
                i = i + 2;
                matchDay ++;

            }

            start.add(Calendar.DATE, 1);
            date = start.getTime();
        }

    }

    public ArrayList<TeamEntity[]> setLeagueSchedule(List<TeamEntity> teamList) {
        List<TeamEntity> tempTeamList = teamList;
        int teamNum = tempTeamList.size();
        int[] teamIdArray = new int[teamNum];

        for(int i = 1; i <= teamNum; i++) {
            teamIdArray[i-1] = i;
        }

        ArrayList<Integer> groupA = new ArrayList<>();
        ArrayList<Integer> groupB = new ArrayList<>();

        for(int i = 0; i <= teamIdArray.length-1; i++) {
            groupA.add(teamIdArray[i]);
            groupB.add(teamIdArray[i]);
        }

        groupB.remove(0);
        groupB.add(0);

        ArrayList<TeamEntity[]> leagueScheduleResult = new ArrayList<TeamEntity[]>();

        while (groupB.contains(11)) {
            for (int j = 0; j < teamIdArray.length; j++) {
                /*if(!Objects.equals(groupA.get(j), groupB.get(j))) {*/
                    /*Log.d("DD", groupA.get(j)+ "||" + groupB.get(j));*/
                    int a = groupA.get(j) - 1;
                    int b = groupB.get(j) - 1;
                    if(b >= 0) {
                        TeamEntity[] aMatch = {tempTeamList.get(a), tempTeamList.get(b)};
                        /*Log.d("dd", aMatch[0] + "||" +aMatch[1]);*/
                        leagueScheduleResult.add(aMatch);
                    }

                }
            /*}*/
            /*Collections.rotate(groupB, -1);*/
            groupB.remove(0);
            groupB.add(0);
        }

        return leagueScheduleResult;
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

    public void getTeamList() {
        TeamDAO teamDAO = db.teamDAO();
        teamDAO.loadAllTeamBySeasonCode(Common.CURR_SEASON_CODE)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    for(TeamEntity teamEntity : loadValue) {
                        teamList.add(teamEntity);
                    }

                    isTeamListLoad = true;
                })
                .doOnError(error -> {
                    Log.e("getTeamList error 2:", error.getMessage());
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



}
