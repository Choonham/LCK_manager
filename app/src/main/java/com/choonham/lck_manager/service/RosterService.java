package com.choonham.lck_manager.service;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.choonham.lck_manager.MainRosterAdapter;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.common.JsonArrayRequest;
import com.choonham.lck_manager.entity.*;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RosterService {

    private List<JoinedPlayer> playerEntityList;

    private List<RosterEntity> rosterList;

    private int rtnVal = 0;
    /**
     * 선택한 시즌에 맞는 첫 로스터 후보를 불러오는 메서드
     */
    public void getFirstTransferList(Context context, int seasonCode, final VolleyCallBack volleyCallBack) {
        JSONObject jsonParams = new JSONObject();

        RequestQueue requestQueue = Common.getRequestQueueInstance(context);

        String url = Common.REST_API_URL + "getFirstPlayerList";

        String apiKey = Common.REST_API_KEY;

        String getFirstRosterUrl = url + "?key=" + apiKey;

        playerEntityList = new ArrayList<>();

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
                        playerEntityList.clear();
                        // Handle the response
                        try {
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

                                volleyCallBack.onLoad();
                            }

                        } catch (Exception e) {
                            Log.e("getFirstTransferList JSON parsing Error: ", e.getMessage());
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
    }

    /**
     * 선택한 스타트 팀 로스터 등록
     */
    public void regFirstRoster(Context context, List<JoinedPlayer> playerEntityList, int teamCode, UserEntity userEntity, final VolleyCallBack volleyCallBack) throws JSONException {
        RequestQueue requestQueue = Common.getRequestQueueInstance(context);

        JSONArray jsonArray = new JSONArray();

        String url = Common.REST_API_URL + "regFirstRoster";

        String apiKey = Common.REST_API_KEY;

        String regFirstRosterUrl = url + "?key=" + apiKey;

        for(JoinedPlayer player : playerEntityList) {

            JSONObject playerJson = new JSONObject();
            //JSONObject seasonJson = new JSONObject();

            //playerJson = mapper.convertValue(player.playerEntity, JSONObject.class);
            //seasonJson = mapper.convertValue(player.seasonEntity, JSONObject.class);

            playerJson.put("teamCode", teamCode);
            //playerJson.put("userCode", player.playerEntity.getPlayerCode());
            playerJson.put("playerCode", player.playerEntity.getPlayerCode());
            playerJson.put("mainEntry", 1);
            jsonArray.put(playerJson);
        }

        com.android.volley.toolbox.JsonArrayRequest request = new com.android.volley.toolbox.JsonArrayRequest(
                Request.Method.POST,
                regFirstRosterUrl,
                jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("팀 로스터 등록 완료: ", response.toString());

                        try {
                            volleyCallBack.onLoad();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("팀 로스터 등록 에러: ", error.toString());
                    }
                }
        );

        requestQueue.add(request);
    }

    public void getRosterListBySeason(Context context, int seasonCode, final VolleyCallBack volleyCallBack) {
        JSONObject jsonParams = new JSONObject();

        RequestQueue requestQueue = Common.getRequestQueueInstance(context);

        String url = Common.REST_API_URL + "getTeamRosterBySeasonCode";

        String apiKey = Common.REST_API_KEY;

        String getFirstRosterUrl = url + "?key=" + apiKey;

        rosterList = new ArrayList<>();

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
                        Log.d("getRosterListBySeason 응답 ->", response.toString());
                        // Handle the response
                        try {

                            //PlayerEntity[] playerEntityArray = mapper.readValue((DataInput) response, PlayerEntity[].class);
                            for(int i = 0; i < response.length(); i++) {
                                JSONObject json = (JSONObject) response.get(i);

                                RosterEntity roster = new RosterEntity();

                                roster.setApiRosterCode((Integer) json.get("mainRosterCode"));
                                Log.e("Roster Code", Integer.toString((Integer) json.get("mainRosterCode")));
                                roster.setTeamCode((Integer) json.getJSONObject("team").get("teamCode"));

                                if(json.isNull("player")) {
                                    roster.setPlayerCode(99999);
                                } else {
                                    roster.setPlayerCode((Integer) json.getJSONObject("player").get("playerCode"));
                                }

                                roster.setMainOrder((Integer) json.get("mainOrder"));
                                roster.setMainEntry((Integer) json.get("mainEntry"));

                                rosterList.add(roster);

                            }
                            volleyCallBack.onLoad();

                        } catch (Exception e) {
                            Log.e("getRosterListBySeason JSON parsing Error: ", e.getMessage());
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
    }

    public List<JoinedPlayer> getPlayerEntityList() {
        return playerEntityList;
    }

    public void setPlayerEntityList(List<JoinedPlayer> playerEntityList) {
        this.playerEntityList = playerEntityList;
    }

    public int getRtnVal() {
        return rtnVal;
    }

    public void setRtnVal(int rtnVal) {
        this.rtnVal = rtnVal;
    }

    public List<RosterEntity> getRosterList() {
        return rosterList;
    }

    public void setRosterList(List<RosterEntity> rosterList) {
        this.rosterList = rosterList;
    }
}

