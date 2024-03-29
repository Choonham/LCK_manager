package com.choonham.lck_manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.choonham.lck_manager.common.*;
import com.choonham.lck_manager.dao.PlayerDAO;
import com.choonham.lck_manager.dao.RosterDAO;
import com.choonham.lck_manager.dao.TeamDAO;
import com.choonham.lck_manager.dao.TransferWindowDAO;
import com.choonham.lck_manager.entity.*;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.choonham.lck_manager.joinedEntity.JoinedTransferWindow;
import com.choonham.lck_manager.room.AppDatabase;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TransferWindow extends Fragment implements TransferWindowListener {
    private final ActivityTagEnum TAG = ActivityTagEnum.TRANSFER_WINDOW;

    ListView transferWindowListView;

    private List<JoinedTransferWindow> transferWindowEntityList;

    private ImageButton refresh;

    private AppDatabase appDatabase;

    boolean isTransferListLoad = false;

    SharedPreferences userPreferences;

    TextView userFameLvView;

    TextView userMoneyView;

    int selectedIndex;

    int userTeamCode;

    TeamRosterModel teamRosterModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transfer_window, container, false);

        userPreferences = Common.getPreferences(getContext());

        appDatabase = AppDatabase.getInstance(getContext());

        int userFameLv = userPreferences.getInt("fame_lv", 1);
        int userMoney = userPreferences.getInt("user_money", 0);
        userTeamCode = userPreferences.getInt("user_team_code", 0);

        //ommon common = Common.getInstance();
        //playerEntityList =  common.getTempPlayerList(1);

        transferWindowEntityList = new ArrayList<>();

        TransferWindowModel.createInstance(this);

        //loadTransferList();

        TransferWindowDAO transferWindowDAO = appDatabase.transferWindowDAO();

        refreshUserInfo(view, userFameLv, userMoney);

        //TransferWindowListAdapter transferWindowListAdapter = new TransferWindowListAdapter(getContext(), transferWindowEntityList);
        transferWindowListView = view.findViewById(R.id.weekly_transfer_window_list_view);

        //transferWindowListView.setAdapter((ListAdapter) transferWindowListAdapter);


        transferWindowDAO.loadTransferWindowByWeek(1).observe(this, loadValue -> {
            transferWindowEntityList.clear();

            transferWindowEntityList.addAll(loadValue);

            TransferWindowListAdapter transferWindowListAdapter2 = new TransferWindowListAdapter(getContext(), transferWindowEntityList);

            transferWindowListView.setAdapter((ListAdapter) transferWindowListAdapter2);
        });

        refresh = view.findViewById(R.id.transfer_window_refresh_button);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DD", "Hey!");
            }
        });

        transferWindowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {

                selectedIndex = i;

                Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);

                Bundle b = new Bundle();

                b.putParcelable("playerEntity", transferWindowEntityList.get(i).playerEntity);

                b.putParcelable("seasonEntity", transferWindowEntityList.get(i).seasonEntity);

                b.putParcelable("transferWindowEntity", transferWindowEntityList.get(i).transferWindowEntity);

                b.putSerializable("tag", TAG);

                b.putInt("tagInt",1122);

                intent.putExtra("bundle", b);

                startActivity(intent);
            }
        });

        return view;
    }

    /*public void loadTransferList() {
        TransferWindowDAO transferWindowDAO = appDatabase.transferWindowDAO();

        transferWindowDAO.loadTransferWindowByWeek(1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    for(JoinedTransferWindow transferWindow : loadValue) {
                        transferWindowEntityList.add(transferWindow);
                    }

                    isTransferListLoad = true;
                })
                .doOnError(error -> {
                    Log.e("loadTransferList error:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/

    public void refreshUserInfo(View view, int userFameLv, int userMoney) {
        userFameLvView = view.findViewById(R.id.transfer_window_fame_lv);
        userMoneyView = view.findViewById(R.id.transfer_window_money);

        userFameLvView.setText(String.valueOf(userFameLv));
        userMoneyView.setText("$"+userMoney);
    }


    @Override
    public void onConfirm(double offeredTransferFee) {

        teamRosterModel = TeamRosterModel.getInstance();

        int userMoney = userPreferences.getInt("user_money", 0);

        if(offeredTransferFee >  userMoney) {
            Toast.makeText(getContext(), "선수 영입에 필요한 금액이 부족합니다.", Toast.LENGTH_LONG).show();

            return;
        }

        JoinedTransferWindow joinedTransferWindow = transferWindowEntityList.remove(selectedIndex);

        RosterEntity roster = new RosterEntity();
        roster.setApiRosterCode(9999);
        roster.setMainEntry(0);
        roster.setPlayerCode(joinedTransferWindow.playerEntity.getPlayerCode());
        roster.setTeamCode(userTeamCode);

        userMoney -= offeredTransferFee;

        userMoneyView.setText("$"+userMoney);

        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putInt("user_money", userMoney);

        TransferWindowListAdapter transferWindowListAdapter = new TransferWindowListAdapter(getContext(), transferWindowEntityList);
        transferWindowListView.setAdapter((ListAdapter) transferWindowListAdapter);

        insertRosterData(roster);

        deleteTransferWindowEntity(joinedTransferWindow.transferWindowEntity);

        //teamRosterModel.onOffer();
    }

    @Override
    public void onRelease() {

    }

    private void insertRosterData(RosterEntity rosterEntity) {
        RosterDAO rosterDAO = appDatabase.rosterDAO();

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

    public void deleteTransferWindowEntity(TransferWindowEntity transferWindowEntity) {
        TransferWindowDAO transferWindowDAO = appDatabase.transferWindowDAO();

        transferWindowDAO.deleteTransferWindowEntity(transferWindowEntity)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(insertValue -> {
                    Log.d("delete data: ", String.valueOf(insertValue));
                })
                .doOnError(error -> {
                    Log.e("delete error :", error.getMessage());
                })
                .subscribe();
    }
}
