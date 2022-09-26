package com.choonham.lck_manager.service;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.common.JsonArrayRequest;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.entity.TeamEntity;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TeamService{
    private int rtnVal = 0;

    private List<TeamEntity> teamList;

    public List<TeamEntity> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<TeamEntity> teamList) {
        this.teamList = teamList;
    }

    /**
     * User Code, 팀 정보 등록
     */
    public int regTeam(RequestQueue requestQueue, int userCode, String teamName, int seasonCode) {
        JSONObject jsonParams = new JSONObject();

        String url = Common.REST_API_URL + "regTeam";

        String apiKey = Common.REST_API_KEY;

        String regTeamUrl = url + "?key=" + apiKey;

        try {
            jsonParams.put("seasonCode", seasonCode);
            jsonParams.put("teamName", teamName);
            jsonParams.put("userCode", userCode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                regTeamUrl,
                // Using a variable for the domain is great for testing,
                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        try {
                            Log.d("응답 ->", response.toString());
                            rtnVal = Integer.parseInt(response.toString());
                        } catch (Exception e) {
                            Log.e("regTeam JSON parsing Error: ", e.getMessage());
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

        return rtnVal;
    }

    public void getTeamListBySeason(Context context, int seasonCode, final VolleyCallBack volleyCallBack){
        JSONObject jsonParams = new JSONObject();

        RequestQueue requestQueue = Common.getRequestQueueInstance(context);

        String url = Common.REST_API_URL + "getTeamList";

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
                        //ObjectMapper mapper = new ObjectMapper();
                        //mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                        teamList = new ArrayList<>();
                        // Handle the response
                        try {
                            //PlayerEntity[] playerEntityArray = mapper.readValue((DataInput) response, PlayerEntity[].class);
                            for(int i = 0; i < response.length(); i++) {
                                JSONObject json = (JSONObject) response.get(i);

                                JSONObject seasonJson = (JSONObject)json.get("season");
                                JSONObject userJson = (JSONObject)json.get("userCode");

                                TeamEntity team = new TeamEntity();

                                team.setApiTeamCode((Integer) json.get("teamCode"));
                                team.setSeasonCode((Integer) seasonJson.get("seasonCode"));
                                team.setWinRate((Double) json.get("winRate"));
                                team.setTotalWins((Integer) json.get("totalWins"));
                                team.setTotalLoses((Integer) json.get("totalLoses"));
                                team.setUserType((Integer) json.get("userType"));
                                team.setUserCode((Integer) userJson.get("userCode"));
                                team.setTeamName((String) json.get("teamName"));


                                //TeamEntity team = mapper.readValue(mapper.writeValueAsString(json), TeamEntity.class);

                                teamList.add(team);

                            }

                            volleyCallBack.onLoad();

                        } catch (Exception e) {
                            Log.e("getTeamListBySeason JSON parsing Error: ", e.getMessage());
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
}
