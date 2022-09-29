package com.choonham.lck_manager;

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
import com.choonham.lck_manager.dao.PlayerDAO;
import com.choonham.lck_manager.dao.RosterDAO;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.RosterEntity;
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

public class TeamRoster extends Fragment {

    private ActivityTagEnum tag;

    private List<JoinedPlayer> mainPlayerList;
    private List<JoinedPlayer> subPlayerList;

    ListView teamMainRosterListView;
    ListView teamSubRosterListView;

    ProgressDialog customProgressDialog;

    AppDatabase db;

    boolean isMainRosterLoad = false;
    boolean isSubRosterLoad = false;

    TextView playerName;
    TextView playerSeason;

    MainRosterAdapter mainRosterAdapter;
    MainRosterAdapter subRosterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.team_roster, container, false);

        db = AppDatabase.getInstance(getContext());

        Common common = Common.getInstance();

        mainPlayerList = new ArrayList<>();
        subPlayerList = new ArrayList<>();

        loadMainRoster();
        loadSubRoster();

        // 로딩창 객체 생성
        customProgressDialog = new ProgressDialog(getContext());

        // 로딩창 배경 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        customProgressDialog.show();

        while(!isMainRosterLoad || !isSubRosterLoad) {

        }

        customProgressDialog.dismiss();

        MainRosterAdapter mainRosterAdapter = new MainRosterAdapter(getContext(), mainPlayerList);
        MainRosterAdapter subRosterAdapter = new MainRosterAdapter(getContext(), subPlayerList);

       //MainRosterAdapter mainRosterAdapter = new MainRosterAdapter(getContext(), playerEntityList);
        teamMainRosterListView = view.findViewById(R.id.main_roster_list);
        /*ViewGroup header = (ViewGroup) inflater.inflate(R.layout.main_roster_header_view, teamMainRosterListView, false);
        header.setPadding(0, 20, 0, 0);*/

        teamSubRosterListView = view.findViewById(R.id.sub_roster_list);
        /*teamSubRosterListView.addHeaderView(header, null, false);*/

        teamMainRosterListView.setAdapter((ListAdapter) mainRosterAdapter);
        teamSubRosterListView.setAdapter((ListAdapter) subRosterAdapter);

        teamMainRosterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {

                tag = ActivityTagEnum.TEAM_ROSTER_MAIN;

                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(mainPlayerList, i, selectedView, tag, getContext(), 0);

                startActivity(intent);
            }
        });

        teamSubRosterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                /*Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);
                TextView season = selectedView.findViewById(R.id.player_season_for_list);
                TextView name = selectedView.findViewById(R.id.player_name_for_list);
                ImageView positionIcon = selectedView.findViewById(R.id.main_roster_position_icon);

                int drawableRef = (int) positionIcon.getTag();

                intent.putExtra("playerSeason", season.getText());
                intent.putExtra("playerName", name.getText());
                intent.putExtra("positionIcon", drawableRef);

                TextView avg = selectedView.findViewById(R.id.player_avg_for_list);
                TextView stability = selectedView.findViewById(R.id.player_stability_for_list);
                intent.putExtra("playerAvg", avg.getText());
                intent.putExtra("playerStability", stability.getText());
                intent.putExtra("tag", TAG);

                startActivity(intent);*/

                tag = ActivityTagEnum.TEAM_ROSTER_SUB;

                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(subPlayerList, i, selectedView, tag, getContext(), 0);

                startActivity(intent);
            }
        });

        return view;
    }

    public void loadMainRoster() {
        RosterDAO rosterDAO = db.rosterDAO();
        PlayerDAO playerDAO = db.playerDAO();
        //AtomicReference<RosterEntity> value = new AtomicReference<>();

        rosterDAO.loadRosterListByTeamCode(0, 1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    for(RosterEntity rosterEntity : loadValue) {
                        /*JoinedPlayer player = playerDAO.loadPlayerEntityByCode(rosterEntity.getPlayerCode());*/
                        playerDAO.loadPlayerEntityByCode(rosterEntity.getPlayerCode())
                                .subscribeOn(Schedulers.io())
                                .doOnSuccess(player -> {
                                    mainPlayerList.add(player);
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

        rosterDAO.loadRosterListByTeamCode(0, 0)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    for(RosterEntity rosterEntity : loadValue) {
                        /*JoinedPlayer player = playerDAO.loadPlayerEntityByCode(rosterEntity.getPlayerCode());*/
                        playerDAO.loadPlayerEntityByCode(rosterEntity.getPlayerCode())
                                .subscribeOn(Schedulers.io())
                                .doOnSuccess(player -> {
                                    subPlayerList.add(player);
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
    }

}
