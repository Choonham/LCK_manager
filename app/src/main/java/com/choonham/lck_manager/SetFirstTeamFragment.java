package com.choonham.lck_manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.*;
import com.choonham.lck_manager.common.*;
import com.choonham.lck_manager.dao.*;
import com.choonham.lck_manager.entity.*;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.choonham.lck_manager.room.AppDatabase;
import com.choonham.lck_manager.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.snackbar.Snackbar;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class SetFirstTeamFragment extends Fragment implements SetFirstTeamListener {

    private final ActivityTagEnum TAG = ActivityTagEnum.SET_FIRST_TEAM_FRAGMENT;
    private List<JoinedPlayer> playerEntityList;
    private List<JoinedPlayer> myTeamList;

    ListView faPlayerListView;

    ListView myTeamListView;

    UserDAO userDAO;

    RosterDAO rosterDAO;

    ChampionDAO championDAO;

    PlayerDAO playerDAO;

    UserEntity userEntity = null;

    AppDatabase db;

    ProgressDialog customProgressDialog;

    TextView moneyView = null;

    int selectedIndex_fa = 0;

    int selectedIndex_myTeam = 0;

    LoginService loginService;
    RosterService rosterService;
    TeamService teamService;
    PlayerService playerService;
    ChampionService championService;

    int apiUserCode = 0;

    Context context;

    SharedPreferences userPreferences;

    boolean loadAllDone = false;

    boolean isInsertDone = false;

    boolean isLeagueRankingSet = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_set_first_team, container, false);

        Button button = rootView.findViewById(R.id.set_first_team_button);

        userPreferences = Common.getPreferences(getContext());

        moneyView = rootView.findViewById(R.id.first_team_transfer_window_money);
        moneyView.setText(Double.toString(Common.startMoney));

        loginService = new LoginService();
        rosterService = new RosterService();
        playerService = new PlayerService();
        championService = new ChampionService();
        teamService = new TeamService();

        myTeamList = new ArrayList<>();

        db = AppDatabase.getInstance(getContext());

        // 로딩창 객체 생성
        customProgressDialog = new ProgressDialog(getContext());

        // 로딩창 배경 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        customProgressDialog.show();

        int seasonCode = getArguments().getInt("seasonCode");
        String teamName = getArguments().getString("teamName");

        userEntity = getArguments().getParcelable("userEntity");

        //requestQueue = Common.getRequestQueueInstance(getContext());

        faPlayerListView = rootView.findViewById(R.id.FA_player_list_view);
        myTeamListView = rootView.findViewById(R.id.selected_player_list_view);

        SetFirstTeamModel.createInstance(this);

        context = getContext();

        loginService.regSeasonData(getContext());

        rosterService.getFirstTransferList(context, seasonCode,
                new VolleyCallBack() {
                    @Override
                    public void onLoad() {
                        playerEntityList = rosterService.getPlayerEntityList();

                        MainRosterAdapter faPlayerListAdapter = new MainRosterAdapter(getContext(), playerEntityList);

                        faPlayerListView.setAdapter(faPlayerListAdapter);

                        customProgressDialog.dismiss();
                    }
                });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customProgressDialog.show();

                try {
                    loginService.regUserToServer(context, userEntity,
                    new VolleyCallBack() {
                        @Override
                        public void onLoad() throws JSONException {
                            apiUserCode = loginService.getRtnVal();

                            userEntity.setApiUserCode(apiUserCode);
                            userEntity.setSeasonCode(seasonCode);


                            teamService.regTeamCode(context, userEntity, teamName,
                            new VolleyCallBack() {
                                @Override
                                public void onLoad() throws JSONException {
                                    int teamCode = teamService.getRtnVal();

                                    rosterService.regFirstRoster(context, myTeamList, teamCode, userEntity,
                                    new VolleyCallBack() {
                                        @Override
                                        public void onLoad() throws JSONException {

                                            playerService.getSeasonPlayerList(context, seasonCode, new VolleyCallBack() {
                                                        @Override
                                                        public void onLoad() throws JSONException {
                                                            playerDAO = db.playerDAO();

                                                            for(JoinedPlayer joinedPlayer : playerService.getPlayerEntityList()) {
                                                                insertPlayerEntity(joinedPlayer.playerEntity);
                                                            }

                                                        }
                                                    });

                                            championService.getChampionList(context, new VolleyCallBack() {
                                                @Override
                                                public void onLoad() throws JSONException {
                                                    championDAO = db.championDAO();

                                                    for(ChampionEntity champion : championService.getChampionList()) {
                                                        insertChampionEntity(champion);
                                                    }

                                                }
                                            });

                                            teamService.getTeamListBySeason(context, seasonCode, userEntity.getApiUserCode(),
                                                new VolleyCallBack() {
                                                    @Override
                                                    public void onLoad() throws JSONException {
                                                        List<TeamEntity> teamList = teamService.getTeamList();
                                                        for(TeamEntity team : teamList) {
                                                            insertTeamInfo(team);
                                                        }

                                                        rosterService.getRosterListBySeason(context, seasonCode,
                                                                new VolleyCallBack() {
                                                                    @Override
                                                                    public void onLoad() throws Exception {
                                                                        List<RosterEntity> rosterList = rosterService.getRosterList();

                                                                        rosterDAO = db.rosterDAO();

                                                                        for(RosterEntity roster : rosterList) {
                                                                            insertRosterData(roster);
                                                                        }

                                                                        Intent intent = new Intent(getContext(), MainActivity.class);

                                                                        userDAO = db.userDAO();

                                                                        userEntity.setMatchNum(1);
                                                                        userEntity.setUserFameLv(1);
                                                                        userEntity.setUserMoney((int) Common.startMoney);

                                                                        insertUserIDInfo(userEntity);

                                                                        while(!isInsertDone) {}

                                                                        SharedPreferences.Editor editor = userPreferences.edit();

                                                                        editor.putInt("api_user_code", userEntity.getApiUserCode());
                                                                        editor.putInt("user_code", userEntity.getUserCode());
                                                                        editor.putInt("match_num", userEntity.getMatchNum());
                                                                        editor.putInt("fame_lv", userEntity.getUserFameLv());
                                                                        editor.putInt("user_money", userEntity.getUserMoney());
                                                                        editor.putInt("user_season", userEntity.getSeasonCode());

                                                                        editor.commit();

                                                                        setLeagueScheduleMap(setLeagueSchedule(teamList));

                                                                        insertFirstTransferWindow(teamCode, seasonCode);

                                                                        while(!loadAllDone) {}

                                                                        updateLeagueRankingList(seasonCode);

                                                                        while(!isLeagueRankingSet) {}

                                                                        customProgressDialog.dismiss();

                                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                                                        startActivity(intent);
                                                                    }
                                                                });
                                                    }
                                                });

                                        }
                                    });
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });

        faPlayerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {

                selectedIndex_fa = i;

                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(playerEntityList, i, selectedView, TAG, getContext(), 0);

                startActivity(intent);
            }
        });

        myTeamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                selectedIndex_myTeam = i;

                Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);

                intent.putExtra("tag", TAG);
                intent.putExtra("playerEntity", myTeamList.get(selectedIndex_myTeam).playerEntity);
                intent.putExtra("seasonEntity", myTeamList.get(selectedIndex_myTeam).seasonEntity);
                intent.putExtra("isMyTeam", true);

                startActivity(intent);

            }
        });


        return rootView;
    }

    private void insertUserIDInfo(UserEntity userEntity) {

        userDAO.insertUserEntity(userEntity)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(insertValue -> {
                    Log.d("Insert data: ", insertValue.toString());
                    checkInsertYN(insertValue);

                    isInsertDone = true;
                })
                .doOnError(error -> {
                    Log.e("insert error :", error.getMessage());
                })
                .subscribe();
    }

    private void insertChampionEntity(ChampionEntity championEntity) {
        championDAO.insertChampionEntity(championEntity)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(insertValue -> {
                    Log.d("Insert champ data: ", insertValue.toString());
                })
                .doOnError(error -> {
                    Log.e("insert champ error :", error.getMessage());
                })
                .subscribe();
    }

    private void insertPlayerEntity(PlayerEntity playerEntity) {
        playerDAO.insertPlayerEntity(playerEntity)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(insertValue -> {
                    Log.d("Insert player data: ", insertValue.toString());
                })
                .doOnError(error -> {
                    Log.e("insert player error :", error.getMessage());
                })
                .subscribe();
    }

    private void insertRosterData(RosterEntity rosterEntity) {

        rosterDAO.insertRosterData(rosterEntity)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(insertValue -> {
                    Log.d("Insert data: ", String.valueOf(insertValue));
                })
                .doOnError(error -> {
                    Log.e("insert error :", error.getMessage());
                })
                .subscribe();
    }



    private void checkInsertYN(Long insertCode) {
        AtomicReference<UserEntity> value = new AtomicReference<>();

        userDAO.loadUserEntityById(insertCode.intValue())
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    Log.d("insertedID", loadValue.getUserId());
                    value.set(loadValue);
                })
                .doOnError(error -> {
                    Log.e("check error :", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void insertTeamInfo(TeamEntity team) {
        TeamDAO teamDAO = db.teamDAO();

        teamDAO.insertTeamData(team)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    Log.d("inserted Team", String.valueOf(loadValue));
                })
                .doOnError(error -> {
                    Log.e("insert error :", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void onConfirm() {
        JoinedPlayer selectPlayer = playerEntityList.get(selectedIndex_fa);

        for(JoinedPlayer player : myTeamList) {
            if(selectPlayer.playerEntity.getPosition() == player.playerEntity.getPosition()) {
                Toast.makeText(getContext(), "동일한 포지션의 선수가 이미 존재합니다.", Toast.LENGTH_LONG).show();

                return;
            }
        }

        myTeamList.add(playerEntityList.remove(selectedIndex_fa));

        MainRosterAdapter faPlayerListAdapter = new MainRosterAdapter(getContext(), playerEntityList);
        MainRosterAdapter myTeamListAdapter = new MainRosterAdapter(getContext(), myTeamList);

        faPlayerListView.setAdapter(faPlayerListAdapter);
        myTeamListView.setAdapter(myTeamListAdapter);

        moneyView.setText(Double.toString(Common.startMoney));
    }

    @Override
    public void onRelease() {
        playerEntityList.add(myTeamList.remove(selectedIndex_myTeam));

        MainRosterAdapter faPlayerListAdapter = new MainRosterAdapter(getContext(), playerEntityList);
        MainRosterAdapter myTeamListAdapter = new MainRosterAdapter(getContext(), myTeamList);

        faPlayerListView.setAdapter(faPlayerListAdapter);
        myTeamListView.setAdapter(myTeamListAdapter);

        moneyView.setText(Double.toString(Common.startMoney));
    }

    public void setLeagueScheduleMap(List<TeamEntity[]> leagueScheduleList) throws Exception{

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

        boolean tempBol = false;

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

                        if(tempMatch[0].getApiTeamCode() == userPreferences.getInt("user_team_code", 1) && !tempBol ) {
                            leagueScheduleEntity.setCurrMatch(1);
                            tempBol = true;
                        } else {
                            leagueScheduleEntity.setCurrMatch(0);
                        }

                        leagueScheduleEntity.setTeamCodeA(tempMatch[0].getApiTeamCode());
                        leagueScheduleEntity.setTeamCodeB(tempMatch[1].getApiTeamCode());

                        leagueScheduleEntity.setTeamA(tempMatch[0].getTeamName());
                        leagueScheduleEntity.setTeamB(tempMatch[1].getTeamName());
                        leagueScheduleEntity.setMatchNum(matchNum);
                        leagueScheduleEntity.setDate(date);

                        //leagueScheduleEntityList.add(leagueScheduleEntity);

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
                //matchDateList.add(Integer.toString(matchDay));
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

    private void insertFirstTransferWindow(int teamCode, int seasonCode) {
        TransferWindowDAO transferWindowDAO = db.transferWindowDAO();

        transferWindowDAO.loadPlayerListNotUserTeam(teamCode, seasonCode)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    for(JoinedPlayer player : loadValue) {
                        TransferWindowEntity transferWindowEntity = new TransferWindowEntity();
                        transferWindowEntity.setPlayerCode(player.playerEntity.getPlayerCode());
                        transferWindowEntity.setWeeks(1);
                        transferWindowEntity.setMinSalary(player.playerEntity.getFameLv() * 1000);
                        transferWindowEntity.setSalaryWants((int) (player.playerEntity.getStability() * 1000));

                        transferWindowDAO.insertTransferList(transferWindowEntity)
                                .subscribeOn(Schedulers.io())
                                .doOnSuccess(value -> {
                                    Log.d("insert transfer window entity:", "done");
                                })
                                .doOnError(error -> {
                                    Log.e("insert transfer window entity error :", error.getMessage());
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe();
                    }

                    loadAllDone = true;
                })
                .doOnError(error -> {
                    Log.e("load transfer player list error :", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void updateLeagueRankingList(int season) {
        LeagueRankingDAO leagueRankingDAO = db.leagueRankingDAO();

        leagueRankingDAO.loadLeagueRankingList(season)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    for(TeamRank teamEntity : loadValue) {
                        LeagueRankingEntity leagueRankingEntity = new LeagueRankingEntity();
                        leagueRankingEntity.setTeamCode(teamEntity.getTeam_code());
                        leagueRankingEntity.setRank(teamEntity.getRank());

                        leagueRankingDAO.insertLeagueRanking(leagueRankingEntity)
                                .subscribeOn(Schedulers.io())
                                .doOnSuccess(value -> {
                                    Log.d("updateLeagueRankingEntity",  value.toString());
                                })
                                .doOnError(error -> {
                                    Log.e("updateLeagueRankingList error 1:", error.getMessage());
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe();
                    }

                    isLeagueRankingSet = true;
                })
                .doOnError(error -> {
                    Log.e("updateLeagueRankingList error 2:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}