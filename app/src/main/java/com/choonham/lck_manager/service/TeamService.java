package com.choonham.lck_manager.service;

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
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeamService{
    private int rtnVal = 0;

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

        return rtnVal;
    }
}
