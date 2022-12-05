package com.choonham.lck_manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.common.TeamRosterListener;
import com.choonham.lck_manager.common.TeamRosterModel;
import com.choonham.lck_manager.dao.PlayerDAO;
import com.choonham.lck_manager.dao.RosterDAO;
import com.choonham.lck_manager.dao.TeamDAO;
import com.choonham.lck_manager.dao.UserDAO;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.RosterEntity;
import com.choonham.lck_manager.entity.TransferWindowEntity;
import com.choonham.lck_manager.entity.UserEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.choonham.lck_manager.room.AppDatabase;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TeamRoster extends Fragment implements TeamRosterListener {

    private ActivityTagEnum tag;

    private int selectedIndex = 0;

    private List<JoinedPlayer> mainPlayerList;
    private List<JoinedPlayer> subPlayerList;

    ListView teamMainRosterListView;
    ListView teamSubRosterListView;

    ProgressDialog customProgressDialog;

    AppDatabase db;

    boolean isMainRosterLoad = false;
    boolean isSubRosterLoad = false;

    boolean isTeamCodeLoad = false;

    int teamCode = 0;

    TeamRosterModel teamRosterModel;

    TextView playerName;
    TextView playerSeason;

    MainRosterAdapter mainRosterAdapter;
    MainRosterAdapter subRosterAdapter;

    int userApiCode;

    PlayerEntity selectedPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.team_roster, container, false);

        db = AppDatabase.getInstance(getContext());

        mainPlayerList = new ArrayList<>();
        subPlayerList = new ArrayList<>();

        TeamRosterModel.createInstance(this);

        // 로딩창 객체 생성
        customProgressDialog = new ProgressDialog(getContext());

        // 로딩창 배경 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        customProgressDialog.show();

        //loadTeamCodeByUserCode();

        //while(!isTeamCodeLoad) {}

        teamMainRosterListView = view.findViewById(R.id.main_roster_list);

        teamSubRosterListView = view.findViewById(R.id.sub_roster_list);

        db.userDAO().loadUserEntityById(1).observe(this, userEntity -> {
            userApiCode = userEntity.getApiUserCode();
            db.teamDAO().loadTeamDataByUserCode(userApiCode).observe(this, teamEntity -> {
                teamCode = teamEntity.getApiTeamCode();
                db.rosterDAO().loadRosterListByTeamCode(teamCode, 1).observe(this, loadValue -> {
                    mainPlayerList.clear();
                    for(RosterEntity rosterEntity : loadValue) {
                        db.playerDAO().loadPlayerEntityByCode(rosterEntity.getPlayerCode()).observe(this, player -> {
                            mainPlayerList.add(player);

                            MainRosterAdapter mainRosterAdapter2 = new MainRosterAdapter(getContext(), mainPlayerList);
                            teamMainRosterListView.setAdapter((ListAdapter) mainRosterAdapter2);
                        });
                    }
                });

                db.rosterDAO().loadRosterListByTeamCode(teamCode, 0).observe(this, loadValue -> {
                    subPlayerList.clear();
                    for(RosterEntity rosterEntity : loadValue) {
                        db.playerDAO().loadPlayerEntityByCode(rosterEntity.getPlayerCode()).observe(this, player -> {
                            Log.e("홀리", "홀리");
                            subPlayerList.add(player);

                            MainRosterAdapter subRosterAdapter2 = new MainRosterAdapter(getContext(), subPlayerList);
                            teamSubRosterListView.setAdapter((ListAdapter) subRosterAdapter2);
                            Log.e("홀리2", "홀리2");
                        });
                    }
                });

            });
        });

        //loadRosters();

        //while(!isMainRosterLoad || !isSubRosterLoad) {}

        customProgressDialog.dismiss();

        /*MainRosterAdapter mainRosterAdapter = new MainRosterAdapter(getContext(), mainPlayerList);
        MainRosterAdapter subRosterAdapter = new MainRosterAdapter(getContext(), subPlayerList);*/

       //MainRosterAdapter mainRosterAdapter = new MainRosterAdapter(getContext(), playerEntityList);
        /*teamSubRosterListView.addHeaderView(header, null, false);*/

        /*teamMainRosterListView.setAdapter((ListAdapter) mainRosterAdapter);
        teamSubRosterListView.setAdapter((ListAdapter) subRosterAdapter);*/

        teamMainRosterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                tag = ActivityTagEnum.TEAM_ROSTER_MAIN;

                selectedIndex = i;

                Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);

                Bundle b = new Bundle();

                b.putParcelable("playerEntity", mainPlayerList.get(i).playerEntity);

                b.putParcelable("seasonEntity", mainPlayerList.get(i).seasonEntity);

                b.putSerializable("tag", tag);

                b.putInt("teamCode", teamCode);

                intent.putExtra("bundle", b);

                startActivity(intent);

                selectedPlayer = mainPlayerList.get(i).playerEntity;
            }
        });

        teamSubRosterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {

                tag = ActivityTagEnum.TEAM_ROSTER_SUB;

                selectedIndex = i;

                Log.e("짜증나", String.valueOf(teamCode));

                selectedPlayer = subPlayerList.get(i).playerEntity;

                Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);

                Bundle b = new Bundle();

                b.putParcelable("playerEntity", subPlayerList.get(i).playerEntity);

                b.putParcelable("seasonEntity", subPlayerList.get(i).seasonEntity);

                b.putSerializable("tag", tag);

                b.putInt("teamCode", teamCode);

                intent.putExtra("bundle", b);

                startActivity(intent);
            }
        });

        return view;
    }

    public void loadRosters() {
        mainPlayerList.clear();
        subPlayerList.clear();
    }

    /*public void loadTeamCodeByUserCode() {
        UserDAO userDAO = db.userDAO();

        userDAO.loadUserEntityById(1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(userEntity -> {

                    isTeamCodeLoad = false;

                    TeamDAO teamDAO = db.teamDAO();
                    Log.e("유저 코드", String.valueOf(userEntity.getApiUserCode()));
                    teamDAO.loadTeamDataByUserCode(userEntity.getApiUserCode())
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(loadValue -> {

                                teamCode = loadValue.getApiTeamCode();
                                Log.e("팀 코드", String.valueOf(teamCode));
                                isTeamCodeLoad = true;
                            })
                            .doOnError(error -> {
                                Log.e("loadMainRoster error 2:", error.getMessage());
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                })
                .doOnError(error -> {
                    Log.e("loadMainRoster error 2:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/

    /*public void loadMainRoster() {
        RosterDAO rosterDAO = db.rosterDAO();
        PlayerDAO playerDAO = db.playerDAO();
        //AtomicReference<RosterEntity> value = new AtomicReference<>();
        Log.e("팀 코드2", String.valueOf(teamCode));
        rosterDAO.loadRosterListByTeamCode(teamCode, 1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    isMainRosterLoad = false;

                    Log.e("사이즈", String.valueOf(loadValue.size()));
                    for(RosterEntity rosterEntity : loadValue) {
                        *//*JoinedPlayer player = playerDAO.loadPlayerEntityByCode(rosterEntity.getPlayerCode());*//*
                        playerDAO.loadPlayerEntityByCode(rosterEntity.getPlayerCode())
                                .subscribeOn(Schedulers.io())
                                .doOnSuccess(player -> {
                                    Log.e("선수 코드", String.valueOf(player.playerEntity.getPlayerCode()));
                                    mainPlayerList.add(player);
                                    //if(mainPlayerList.size() == loadValue.size())  isMainRosterLoad = true;
                                })
                                .doOnError(error -> {
                                    Log.e("loadMainRoster error 1:", error.getMessage());
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe();
                    }

                    isMainRosterLoad = true;

                    //Log.d("insertedID", loadValue.getUserId());
                    //value.set(loadValue.get(0));
                })
                .doOnError(error -> {
                    Log.e("loadMainRoster error 2:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void loadSubRoster() {
        RosterDAO rosterDAO = db.rosterDAO();
        PlayerDAO playerDAO = db.playerDAO();
        //AtomicReference<RosterEntity> value = new AtomicReference<>();

        rosterDAO.loadRosterListByTeamCode(teamCode, 0)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {

                    isSubRosterLoad = false;
                    Log.e("사이즈2", String.valueOf(loadValue.size()));
                    for(RosterEntity rosterEntity : loadValue) {
                        *//*JoinedPlayer player = playerDAO.loadPlayerEntityByCode(rosterEntity.getPlayerCode());*//*
                        playerDAO.loadPlayerEntityByCode(rosterEntity.getPlayerCode())
                                .subscribeOn(Schedulers.io())
                                .doOnSuccess(player -> {
                                    subPlayerList.add(player);

                                    //if(subPlayerList.size() == loadValue.size())
                                })
                                .doOnError(error -> {
                                    Log.e("loadSubRoster error 1:", error.getMessage());
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe();
                    }

                    isSubRosterLoad = true;

                    //Log.d("insertedID", loadValue.getUserId());
                    //value.set(loadValue.get(0));
                })
                .doOnError(error -> {
                    Log.e("loadSubRoster error 2:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/

    @Override
    public void toSub() {
        subPlayerList.add(mainPlayerList.remove(selectedIndex));

        /*MainRosterAdapter main = new MainRosterAdapter(getContext(), mainPlayerList);
        MainRosterAdapter sub = new MainRosterAdapter(getContext(), subPlayerList);

        teamMainRosterListView.setAdapter(main);
        teamSubRosterListView.setAdapter(sub);*/
    }

    @Override
    public void toMain() {
        mainPlayerList.add(subPlayerList.remove(selectedIndex));

        /*MainRosterAdapter main = new MainRosterAdapter(getContext(), mainPlayerList);
        MainRosterAdapter sub = new MainRosterAdapter(getContext(), subPlayerList);

        teamMainRosterListView.setAdapter(main);
        teamSubRosterListView.setAdapter(sub);*/
    }

    @Override
    public void onRelease() {
        //JoinedPlayer releasePlayer = subPlayerList.remove(selectedIndex);
        db.rosterDAO().deleteRosterData(subPlayerList.get(selectedIndex).playerEntity.getPlayerCode(), teamCode)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(value -> {
                    Log.d("release player:", "done");
                })
                .doOnError(error -> {
                    Log.e("release error :", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        //subPlayerList.remove(selectedIndex);
    }

    @Override
    public void toFA() {

    }

    @Override
    public void onOffer() {
        /*MainRosterAdapter mainRosterAdapter = new MainRosterAdapter(getContext(), mainPlayerList);
        MainRosterAdapter subRosterAdapter = new MainRosterAdapter(getContext(), subPlayerList);

        teamMainRosterListView.setAdapter((ListAdapter) mainRosterAdapter);
        teamSubRosterListView.setAdapter((ListAdapter) subRosterAdapter);*/
    }

    @Override
    public void onConfirm(double offeredTransferFee) {
        TransferWindowEntity transferWindowEntity = new TransferWindowEntity();
        transferWindowEntity.setPlayerCode(selectedPlayer.getPlayerCode());
        transferWindowEntity.setWeeks(1);
        transferWindowEntity.setMinSalary(selectedPlayer.getFameLv() * 1000);
        transferWindowEntity.setSalaryWants((int) (selectedPlayer.getStability() * 1000));
        transferWindowEntity.setTransferFee(offeredTransferFee);

        db.transferWindowDAO().insertTransferList(transferWindowEntity)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(value -> {
                    Log.d("insert transfer window entity:", "done");
                })
                .doOnError(error -> {
                    Log.e("insert transfer window entity error :", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        db.rosterDAO().deleteRosterData(subPlayerList.get(selectedIndex).playerEntity.getPlayerCode(), teamCode)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(value -> {
                    Log.d("release player:", "done");
                })
                .doOnError(error -> {
                    Log.e("release error :", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
