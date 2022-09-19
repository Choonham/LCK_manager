package com.choonham.lck_manager.service;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.choonham.lck_manager.MainRosterAdapter;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.common.JsonArrayRequest;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RosterService {

    private List<JoinedPlayer> playerEntityList;

    private int rtnVal = 0;
    /**
     * 선택한 시즌에 맞는 첫 로스터 후보를 불러오는 메서드
     */
    public List<JoinedPlayer> getFirstTransferList(RequestQueue requestQueue, int seasonCode) {
        JSONObject jsonParams = new JSONObject();

        String url = Common.REST_API_URL + "getFirstPlayerList";

        String apiKey = Common.REST_API_KEY;

        String getFirstRosterUrl = url + "?key=" + apiKey;

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

        return playerEntityList;
    }

    /**
     * 선택한 스타트 팀 로스터 등록
     */
    public int regFirstRoster(RequestQueue requestQueue, List<JoinedPlayer> playerEntityList) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        ObjectMapper mapper = new ObjectMapper();

        String url = Common.REST_API_URL + "regFirstRoster";

        String apiKey = Common.REST_API_KEY;

        String regFirstRosterUrl = url + "?key=" + apiKey;

        for(JoinedPlayer player : playerEntityList) {

            JSONObject playerJson = new JSONObject();
            JSONObject seasonJson = new JSONObject();

            playerJson = mapper.convertValue(player.playerEntity, JSONObject.class);
            seasonJson = mapper.convertValue(player.seasonEntity, JSONObject.class);

            playerJson.put("seasonCode", seasonJson);
            //jsonParams = mapper.convertValue(player, JSONObject.class);

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
                        rtnVal = Integer.parseInt(response.toString());
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

        return rtnVal;
    }
}

