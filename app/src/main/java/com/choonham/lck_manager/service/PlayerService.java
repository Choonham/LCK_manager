package com.choonham.lck_manager.service;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.common.JsonArrayRequest;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlayerService {

    private List<JoinedPlayer> playerEntityList;

    public List<JoinedPlayer> getPlayerEntityList() {
        return playerEntityList;
    }

    public void setPlayerEntityList(List<JoinedPlayer> playerEntityList) {
        this.playerEntityList = playerEntityList;
    }

    public void getSeasonPlayerList(Context context, int seasonCode, final VolleyCallBack volleyCallBack) {
        JSONObject params = new JSONObject();

        RequestQueue requestQueue = Common.getRequestQueueInstance(context);

        String url = Common.REST_API_URL + "getSeasonPlayerList";

        String apiKey = Common.REST_API_KEY;

        String getFirstRosterUrl = url + "?key=" + apiKey;

        try {
            params.put("seasonCode", seasonCode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Building a request
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST,
                getFirstRosterUrl,
                // Using a variable for the domain is great for testing,
                params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("시즌 전체 선수 리스트 응답 ->", response.toString());
                        try {

                            playerEntityList = new ArrayList<>();

                            ObjectMapper mapper = new ObjectMapper();
                            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

                            for(int i = 0; i < response.length(); i++) {
                                JSONObject json = (JSONObject) response.get(i);

                                JoinedPlayer joinedPlayer = new JoinedPlayer();

                                PlayerEntity player = new PlayerEntity();
                                SeasonEntity seasonEntity = new SeasonEntity();

                                player.setPlayerName((String) json.get("playerName"));
                                player.setPlayerCode((Integer) json.get("playerCode"));

                                JSONObject season = (JSONObject) json.get("seasonCode");

                                //seasonEntity = mapper.readValue(mapper.writeValueAsString(season), SeasonEntity.class);

                                seasonEntity.setSeasonCode((Integer) season.get("seasonCode"));
                                seasonEntity.setSeasonName((String) season.get("seasonName"));
                                seasonEntity.setSeasonForShort((String) season.get("seasonForShort"));

                                //player = mapper.readValue(mapper.writeValueAsString(player), PlayerEntity.class);

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
                            volleyCallBack.onLoad();

                        } catch (Exception e) {
                            Log.e("시즌 전체 선수 JSON parsing Error: ", e.getMessage());
                        }
                    }
                },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("시즌 전체 선수 리스트 에러 ->", error.getMessage());
                        // Handle the error
                    }
                });

        // RequestQueue 의 add()메서드를 사용하여 요청 보냄
        requestQueue.add(request);
    }
}
