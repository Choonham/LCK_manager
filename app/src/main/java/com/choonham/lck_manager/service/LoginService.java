package com.choonham.lck_manager.service;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.common.JsonArrayRequest;
import com.choonham.lck_manager.dao.SeasonDAO;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.entity.UserEntity;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.choonham.lck_manager.room.AppDatabase;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class LoginService {

    private int rtnVal = 0;

    public void regSeasonData(Context context) {
        RequestQueue requestQueue = Common.getRequestQueueInstance(context);

        String url = Common.REST_API_URL + "getSeasonList";

        String apiKey = Common.REST_API_KEY;

        String getSeasonDataUrl = url + "?key=" + apiKey;

        AppDatabase db = AppDatabase.getInstance(context);

        SeasonDAO seasonDAO = db.seasonDAO();

        ObjectMapper mapper = new ObjectMapper();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST,
                getSeasonDataUrl,
                // Using a variable for the domain is great for testing,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("응답 ->", response.toString());

                        try {
                            for(int i = 0; i < response.length(); i++) {
                                SeasonEntity season = mapper.readValue(response.get(i).toString(), SeasonEntity.class);

                                seasonDAO.insertSeasonData(season)
                                        .subscribeOn(Schedulers.io())
                                        .doOnSuccess(insertValue -> {
                                            Log.d("Insert season: ", insertValue.toString());
                                        })
                                        .doOnError(error -> {
                                            Log.e("insert error :", error.getMessage());
                                        })
                                        .subscribe();
                            }


                        } catch (Exception e) {
                            throw new RuntimeException(e);
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

    public void regUserToServer(Context context, UserEntity userEntity, final VolleyCallBack volleyCallBack) throws JSONException {
        RequestQueue requestQueue = Common.getRequestQueueInstance(context);

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String url = Common.REST_API_URL + "regUser";

        String apiKey = Common.REST_API_KEY;

        String regUserUrl = url + "?key=" + apiKey;

        JSONObject playerJson;

        try{
            playerJson = new JSONObject(mapper.writeValueAsString(userEntity));

            com.android.volley.toolbox.JsonObjectRequest request = new com.android.volley.toolbox.JsonObjectRequest(
                    Request.Method.POST,
                    regUserUrl,
                    playerJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("유저 등록 완료: ", response.toString());

                            try {
                                rtnVal = Integer.parseInt((String)response.get("userCode"));

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            try {
                                volleyCallBack.onLoad();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("유저 등록 에러: ", error.toString());
                        }
                    }
            );

            requestQueue.add(request);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public int getRtnVal() {
        return rtnVal;
    }

    public void setRtnVal(int rtnVal) {
        this.rtnVal = rtnVal;
    }
}
