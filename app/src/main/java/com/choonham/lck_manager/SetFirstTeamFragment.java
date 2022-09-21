package com.choonham.lck_manager;

import android.content.Context;
import android.content.Intent;
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
import com.choonham.lck_manager.dao.UserDAO;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.entity.UserEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.choonham.lck_manager.room.AppDatabase;
import com.choonham.lck_manager.service.LoginService;
import com.choonham.lck_manager.service.RosterService;
import com.choonham.lck_manager.service.VolleyCallBack;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.snackbar.Snackbar;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SetFirstTeamFragment extends Fragment implements SetFirstTeamListener {

    private final ActivityTagEnum TAG = ActivityTagEnum.SET_FIRST_TEAM_FRAGMENT;
    private List<JoinedPlayer> playerEntityList;
    private List<JoinedPlayer> myTeamList;

    ListView faPlayerListView;

    ListView myTeamListView;

    UserDAO userDAO;

    RequestQueue requestQueue;

    String url = Common.REST_API_URL + "getFirstPlayerList";

    String apiKey = Common.REST_API_KEY;

    String getFirstRosterUrl = url + "?key=" + apiKey;

    UserEntity userEntity = null;

    AppDatabase db;

    ProgressDialog customProgressDialog;

    TextView moneyView = null;

    int selectedIndex_fa = 0;

    int selectedIndex_myTeam = 0;

    LoginService loginService;

    RosterService rosterService;

    int apiUserCode = 0;

    Context context;

    public JSONObject getJsonObject(){
        HashMap<String, String> params = new HashMap<String, String>();
        return new JSONObject(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_set_first_team, container, false);

        Button button = rootView.findViewById(R.id.set_first_team_button);

        moneyView = rootView.findViewById(R.id.first_team_transfer_window_money);
        moneyView.setText(Double.toString(Common.startMoney));

        loginService = new LoginService();
        rosterService = new RosterService();

        myTeamList = new ArrayList<>();

        // 로딩창 객체 생성
        customProgressDialog = new ProgressDialog(getContext());

        // 로딩창 배경 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        customProgressDialog.show();

        int seasonCode = getArguments().getInt("seasonCode");
        String teamName = getArguments().getString("teamCode");

        userEntity = getArguments().getParcelable("userEntity");

        //requestQueue = Common.getRequestQueueInstance(getContext());

        faPlayerListView = rootView.findViewById(R.id.FA_player_list_view);
        myTeamListView = rootView.findViewById(R.id.selected_player_list_view);

        SetFirstTeamModel.createInstance(this);

        context = getContext();

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

                                    rosterService.regTeamCode(context, userEntity, teamName,
                                            new VolleyCallBack() {
                                                @Override
                                                public void onLoad() throws JSONException {
                                                    int teamCode = rosterService.getRtnVal();
                                                    rosterService.regFirstRoster(context, myTeamList, teamCode, userEntity,
                                                            new VolleyCallBack() {
                                                                @Override
                                                                public void onLoad() throws JSONException {
                                                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                                                    db = AppDatabase.getInstance(getContext());

                                                                    userDAO = db.userDAO();

                                                                    insertUserIDInfo(userEntity);

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
                } catch (JSONException e) {
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
                })
                .doOnError(error -> {
                    Log.e("insert error :", error.getMessage());
                })
                .subscribe();
    }


    private void checkInsertYN(Long insertCode) {
        AtomicReference<UserEntity> value = new AtomicReference<>();

        userDAO.loadUserEntityById(insertCode)
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
}