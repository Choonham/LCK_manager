package com.choonham.lck_manager.service;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.entity.UserEntity;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginService {

    private int rtnVal = 0;

    public void regUserToServer(Context context, UserEntity userEntity, final VolleyCallBack volleyCallBack) throws JSONException {
        RequestQueue requestQueue = Common.getRequestQueueInstance(context);

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String url = Common.REST_API_URL + "regUser";

        String apiKey = Common.REST_API_KEY;

        String regUserUrl = url + "?key=" + apiKey;

        JSONObject playerJson = new JSONObject();

        playerJson = mapper.convertValue(userEntity, JSONObject.class);

        com.android.volley.toolbox.JsonObjectRequest request = new com.android.volley.toolbox.JsonObjectRequest(
                Request.Method.POST,
                regUserUrl,
                playerJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("유저 등록 완료: ", response.toString());
                        rtnVal = Integer.parseInt(response.toString());

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
                        Log.d("유저 등록 에러: ", error.toString());
                    }
                }
        );

        requestQueue.add(request);
    }

    public int getRtnVal() {
        return rtnVal;
    }

    public void setRtnVal(int rtnVal) {
        this.rtnVal = rtnVal;
    }
}
