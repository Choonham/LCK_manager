package com.choonham.lck_manager;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.entity.UserEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class SetNicknameAndSeasonFragment extends Fragment {

    private final ActivityTagEnum TAG = ActivityTagEnum.SET_NICKNAME_AND_SEASON_FRAGMENT;

    String[] items = {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"};

    int springSeasonCode;

    static RequestQueue requestQueue;

    UserEntity userEntity;

    EditText teamNameBox = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_set_nickname_and_season, container, false);
        Spinner spinner = rootView.findViewById(R.id.season_spinner);

        InitialSettingActivity initialSettingActivity = (InitialSettingActivity) getActivity();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, items);

        spinner.setAdapter(adapter);

        if(getArguments() != null) {
            userEntity = getArguments().getParcelable("userEntity");
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                springSeasonCode = i*2 + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button button = rootView.findViewById(R.id.set_nickname_season_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamNameBox = getActivity().findViewById(R.id.team_name_input);
                String teamName = teamNameBox.getText().toString();

                if(teamName.equals("")) {
                    Snackbar.make(view, "팀명을 입력해주세요.", Snackbar.LENGTH_LONG).show();
                } else if(springSeasonCode == 1) {
                    Snackbar.make(view, "현재 2015시즌은 선택할 수 없습니다.", Snackbar.LENGTH_LONG).show();
                } else {
                    Bundle result = new Bundle();
                    result.putInt("seasonCode", springSeasonCode);
                    result.putString("teamName", teamName);
                    result.putParcelable("userEntity", userEntity);

                    initialSettingActivity.onFragmentChanged(1, result);
                }
            }
        });



        return rootView;
    }

    /**
     * @param {String} userName
     * @param {String} userId
     * @param {String} userNickName
     * @param {String} seasonCode
     */
    public void makeLoginRequest(String userName, String userId, String userNickName, String seasonCode) {

        String firstUrl = Common.REST_API_URL + "regUser";

        String apiKey = Common.REST_API_KEY;

        String url = firstUrl + "?key=" + apiKey;

        //String url = firstUrl + "http://59.17.192.100:8100/apiDataServer/regUser?key=this00is00lck00manager00api00key";

        // 요청을 보내기 위한 StringRequest 객체 생성
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        println("응답 ->" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 ->" + error);
                    }
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> resultSet = new HashMap<String, String>();
                resultSet.put("userName", userName);
                resultSet.put("userId", userId);
                resultSet.put("userNickName", userNickName);
                resultSet.put("season_code", seasonCode);
                return resultSet;
            }
        };
        //cache
        request.setShouldCache(false);

        // RequestQueue 의 add()메서드를 사용하여 요청 보냄
        requestQueue.add(request);
        println("요청 보냄");
    }

    public void println(String data) {
        Log.v("tag", data);
    }
}