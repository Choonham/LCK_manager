package com.choonham.lck_manager;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.enums.ActivityTagEnum;

import javax.annotation.Nullable;

public class InitialSettingActivity extends AppCompatActivity {
    private final ActivityTagEnum TAG = ActivityTagEnum.INITIAL_SETTING_ACTIVITY;

    SetFirstTeamFragment setFirstTeamFragment;
    SetNicknameAndSeasonFragment setNicknameAndSeasonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_setting);

        setNicknameAndSeasonFragment = (SetNicknameAndSeasonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.SetNicknameAndSeasonFragment);
        setFirstTeamFragment = new SetFirstTeamFragment();
    }

    public void onFragmentChanged(int index, @Nullable Bundle data) {
        Log.d("TAG", Integer.toString(index));
        if(index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, setNicknameAndSeasonFragment).commit();
        } else if(index == 1) {
            setFirstTeamFragment.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, setFirstTeamFragment).commit();
        }
    }
}