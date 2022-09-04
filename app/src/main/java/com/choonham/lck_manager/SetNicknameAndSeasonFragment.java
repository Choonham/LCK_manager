package com.choonham.lck_manager;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.choonham.lck_manager.enums.ActivityTagEnum;

public class SetNicknameAndSeasonFragment extends Fragment {

    private final ActivityTagEnum TAG = ActivityTagEnum.SET_NICKNAME_AND_SEASON_FRAGMENT;

    String[] items = {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"};

    int springSeasonCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_set_nickname_and_season, container, false);
        Spinner spinner = rootView.findViewById(R.id.season_spinner);

        InitialSettingActivity initialSettingActivity = (InitialSettingActivity) getActivity();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, items);

        spinner.setAdapter(adapter);

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
                Bundle result = new Bundle();
                result.putInt("seasonCode", springSeasonCode);
                result.putString("teamName", "teamName");

                initialSettingActivity.onFragmentChanged(1, result);
            }
        });

        return rootView;
    }
}