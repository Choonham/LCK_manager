package com.choonham.lck_manager;

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

        myTeamList = new ArrayList<>();

        // 로딩창 객체 생성
        customProgressDialog = new ProgressDialog(getContext());

        // 로딩창 배경 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        customProgressDialog.show();

        int seasonCode = getArguments().getInt("seasonCode");

        userEntity = getArguments().getParcelable("userEntity");

        requestQueue = Common.getRequestQueueInstance(getContext());

        faPlayerListView = rootView.findViewById(R.id.FA_player_list_view);
        myTeamListView = rootView.findViewById(R.id.selected_player_list_view);

        ObjectMapper mapper = new ObjectMapper();

        JSONObject jsonParams = new JSONObject();

        SetFirstTeamModel.createInstance(this);

        try {
            jsonParams.put("seasonCode", seasonCode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Building a request
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST,
                getFirstRosterUrl,
                // Using a variable for the domain is great for testing,
                jsonParams,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("응답 ->", response.toString());

                        // Handle the response
                        try {
                            playerEntityList = new ArrayList<>();
                            //PlayerEntity[] playerEntityArray = mapper.readValue((DataInput) response, PlayerEntity[].class);
                            for(int i = 0; i < response.length(); i++) {
                                JSONObject json = (JSONObject) response.get(i);

                                JoinedPlayer joinedPlayer = new JoinedPlayer();

                                PlayerEntity player = new PlayerEntity();
                                SeasonEntity seasonEntity = new SeasonEntity();

                                player.setPlayerName((String) json.get("playerName"));
                                player.setPlayerCode((Integer) json.get("playerCode"));

                                JSONObject season = (JSONObject) json.get("seasonCode");
                                seasonEntity.setSeasonCode((Integer) season.get("seasonCode"));
                                seasonEntity.setSeasonName((String) season.get("seasonName"));
                                seasonEntity.setSeasonForShort((String) season.get("seasonForShort"));

                                player.setPosition((Integer) json.get("position"));
                                player.setPhysical((Double) json.get("physical"));
                                player.setLaneStrength((Double) json.get("laneStrength"));
                                player.setStability((Double) json.get("stability"));
                                player.setOutSmart((Double) json.get("outSmart"));
                                player.setTeamFight((Double) json.get("teamFight"));
                                player.setFameLv((Integer) json.get("fameLv"));

                                joinedPlayer.playerEntity = player;
                                joinedPlayer.seasonEntity = seasonEntity;

                                playerEntityList.add(joinedPlayer);
                            }

                            MainRosterAdapter faPlayerListAdapter = new MainRosterAdapter(getContext(), playerEntityList);

                            faPlayerListView.setAdapter(faPlayerListAdapter);

                            customProgressDialog.dismiss();

                        } catch (Exception e) {
                            Log.e("JSON parsing Error: ", e.getMessage());
                        }
                    }
                },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("에러 ->", error.getMessage());
                        // Handle the error
                    }
                });

        // RequestQueue 의 add()메서드를 사용하여 요청 보냄
        requestQueue.add(request);
        //playerEntityList = common.getTempPlayerList(1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                db = AppDatabase.getInstance(getContext());

                userDAO = db.userDAO();

                insertUserIDInfo(userEntity);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
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
}