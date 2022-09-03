package com.choonham.lck_manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class SetFirstTeamFragment extends Fragment {

    private final ActivityTagEnum TAG = ActivityTagEnum.SET_FIRST_TEAM_FRAGMENT;
    private List<PlayerEntity> playerEntityList;

    ListView faPlayerListView;

    RequestQueue requestQueue;

    String getFirstRosterUrl = "http://59.17.192.100:8100/apiDataServer/getFirstPlayerList";

    public JSONObject getJsonObject(){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("seasonCode", "2");
        return new JSONObject(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_set_first_team, container, false);

        Button button = rootView.findViewById(R.id.set_first_team_button);

        Common common = Common.getInstance();
        playerEntityList = common.getTempPlayerList(1);

        MainRosterAdapter faPlayerListAdapter = new MainRosterAdapter(getContext(), playerEntityList);

        faPlayerListView = rootView.findViewById(R.id.FA_player_list_view);
        faPlayerListView.setAdapter(faPlayerListAdapter);

        requestQueue = Common.getRequestQueueInstance(getContext());

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("seasonCode", "2");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Building a request
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                getFirstRosterUrl,
                // Using a variable for the domain is great for testing,
                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("응답 ->", response.toString());
                        // Handle the response
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });

        faPlayerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                /*Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);
                TextView season = selectedView.findViewById(R.id.player_season_for_list);
                TextView name = selectedView.findViewById(R.id.player_name_for_list);
                ImageView positionIcon = selectedView.findViewById(R.id.main_roster_position_icon);
                int drawableRef = (int) positionIcon.getTag();

                intent.putExtra("playerSeason", season.getText());
                intent.putExtra("playerName", name.getText());
                intent.putExtra("positionIcon", drawableRef);

                TextView avg = selectedView.findViewById(R.id.player_avg_for_list);
                TextView stability = selectedView.findViewById(R.id.player_stability_for_list);
                intent.putExtra("playerAvg", avg.getText());
                intent.putExtra("playerStability", stability.getText());
                intent.putExtra("tag", TAG);*/

                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(playerEntityList, i, selectedView, TAG, getContext(), 0);

                startActivity(intent);
            }
        });

        return rootView;
    }
}