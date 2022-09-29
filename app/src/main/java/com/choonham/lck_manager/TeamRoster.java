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

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TeamRoster extends Fragment {

    private ActivityTagEnum tag;

    private List<JoinedPlayer> playerEntityList;

    ListView teamMainRosterListView;
    ListView teamSubRosterListView;

    ProgressDialog customProgressDialog;

    AppDatabase db;

    boolean temp;

    TextView playerName;
    TextView playerSeason;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.team_roster, container, false);

        temp = false;

        db = AppDatabase.getInstance(getContext());

        Common common = Common.getInstance();
        playerEntityList = common.getTempPlayerList(0);
        MainRosterAdapter mainRosterAdapter = new MainRosterAdapter(getContext(), playerEntityList);
        teamMainRosterListView = view.findViewById(R.id.main_roster_list);
        /*ViewGroup header = (ViewGroup) inflater.inflate(R.layout.main_roster_header_view, teamMainRosterListView, false);
        header.setPadding(0, 20, 0, 0);*/

        teamSubRosterListView = view.findViewById(R.id.sub_roster_list);
        /*teamSubRosterListView.addHeaderView(header, null, false);*/

        loadMainRoster();

        // 로딩창 객체 생성
        customProgressDialog = new ProgressDialog(getContext());

        // 로딩창 배경 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        customProgressDialog.show();

        while(!temp) {
            Log.d("temp22", String.valueOf(temp));
        }

        customProgressDialog.dismiss();

        teamMainRosterListView.setAdapter((ListAdapter) mainRosterAdapter);
        teamSubRosterListView.setAdapter((ListAdapter) mainRosterAdapter);

        teamMainRosterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {

                tag = ActivityTagEnum.TEAM_ROSTER_MAIN;

                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(playerEntityList, i, selectedView, tag, getContext(), 0);

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
                Intent intent = common.getPlayerInfoPopUpIntent(playerEntityList, i, selectedView, tag, getContext(), 0);

                startActivity(intent);
            }
        });

        return view;
    }

    public List<JoinedPlayer> loadMainRoster() {
        RosterDAO rosterDAO = db.rosterDAO();
        AtomicReference<RosterEntity> value = new AtomicReference<>();

        rosterDAO.loadRosterListByTeamCode(0, 1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    Log.d("temp", String.valueOf(temp));
                    temp = true;
                    Log.d("temp", String.valueOf(temp));
                    //Log.d("insertedID", loadValue.getUserId());
                    //value.set(loadValue.get(0));
                })
                .doOnError(error -> {
                    Log.e("check error :", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        return null;
    }
}
