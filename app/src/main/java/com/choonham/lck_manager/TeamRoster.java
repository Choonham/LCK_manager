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

    int teamCode = 0;

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

        loadRosters();

        customProgressDialog.dismiss();

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
        db.userDAO().loadUserEntityById(1).observe(this, userEntity -> {
            userApiCode = userEntity.getApiUserCode();
            db.teamDAO().loadTeamDataByUserCode(userApiCode).observe(this, teamEntity -> {
                teamCode = teamEntity.getApiTeamCode();
                db.rosterDAO().loadRosterListByTeamCode(teamCode, 1).observe(this, loadValue -> {
                    mainPlayerList.clear();

                    if(loadValue.isEmpty()) {
                        MainRosterAdapter mainRosterAdapter2 = new MainRosterAdapter(getContext(), mainPlayerList);
                        teamMainRosterListView.setAdapter((ListAdapter) mainRosterAdapter2);
                    }

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

                    if(loadValue.isEmpty()) {
                        MainRosterAdapter subRosterAdapter2 = new MainRosterAdapter(getContext(), subPlayerList);
                        teamSubRosterListView.setAdapter((ListAdapter) subRosterAdapter2);
                    }

                    for(RosterEntity rosterEntity : loadValue) {
                        db.playerDAO().loadPlayerEntityByCode(rosterEntity.getPlayerCode()).observe(this, player -> {
                            subPlayerList.add(player);

                            MainRosterAdapter subRosterAdapter2 = new MainRosterAdapter(getContext(), subPlayerList);
                            teamSubRosterListView.setAdapter((ListAdapter) subRosterAdapter2);
                        });
                    }
                });

            });
        });
    }

    @Override
    public void toSub() {
        JoinedPlayer removed = mainPlayerList.remove(selectedIndex);

        subPlayerList.add(removed);
    }

    @Override
    public void toMain() {
        JoinedPlayer removed = subPlayerList.remove(selectedIndex);

        mainPlayerList.add(removed);
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
