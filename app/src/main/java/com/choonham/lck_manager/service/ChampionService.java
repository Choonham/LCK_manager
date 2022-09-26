package com.choonham.lck_manager.service;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.common.JsonArrayRequest;
import com.choonham.lck_manager.entity.ChampionEntity;
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

public class ChampionService {
    private List<ChampionEntity> championList;

    public List<ChampionEntity> getChampionList() {
        return championList;
    }

    public void setChampionList(List<ChampionEntity> championList) {
        this.championList = championList;
    }

    public void getChampionList(Context context, final VolleyCallBack volleyCallBack) {

        RequestQueue requestQueue = Common.getRequestQueueInstance(context);

        String url = Common.REST_API_URL + "getChampionList";

        String apiKey = Common.REST_API_KEY;

        String getFirstRosterUrl = url + "?key=" + apiKey;

        championList = new ArrayList<>();

        // Building a request
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST,
                getFirstRosterUrl,
                // Using a variable for the domain is great for testing,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("전체 챔피언 리스트 응답 ->", response.toString());
                        try {
                            championList.clear();

                            /*ObjectMapper mapper = new ObjectMapper();
                            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);*/

                            for(int i = 0; i < response.length(); i++) {
                                JSONObject json = (JSONObject) response.get(i);

                                ChampionEntity champion = new ChampionEntity();

                                champion.setChampionCode((Integer)json.get("championCode"));
                                champion.setChampionName((String) json.get("championName"));
                                champion.setLaneStrength((Integer) json.get("laneStrength"));
                                champion.setTeamFight((Integer) json.get("teamFight"));
                                champion.setSplitPush((Integer) json.get("splitPush"));
                                champion.setInitiating((Integer) json.get("initiating"));
                                champion.setPoking((Integer) json.get("poking"));
                                champion.setNuking((Integer) json.get("nuking"));
                                champion.setUtil((Integer) json.get("util"));
                                champion.setCc((Integer) json.get("cc"));
                                champion.setGank((Integer) json.get("gank"));
                                champion.setTank((Integer) json.get("tank"));
                                champion.setDps((Integer) json.get("dps"));
                                champion.setPotential1((Double) json.get("potential1"));
                                champion.setPotential2((Double) json.get("potential2"));
                                champion.setPotential3((Double) json.get("potential3"));
                                champion.setType((Integer) json.get("type"));
                                champion.setWinRate((Double) json.get("winRate"));

                                //champion = mapper.readValue(mapper.writeValueAsString(json), ChampionEntity.class);

                                championList.add(champion);

                            }

                            volleyCallBack.onLoad();

                        } catch (Exception e) {
                            Log.e("전체 챔피언 JSON parsing Error: ", e.getMessage());
                        }
                    }
                },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("전체 챔피언 리스트 에러 ->", error.getMessage());
                        // Handle the error
                    }
                });

        // RequestQueue 의 add()메서드를 사용하여 요청 보냄
        requestQueue.add(request);
    }
}
