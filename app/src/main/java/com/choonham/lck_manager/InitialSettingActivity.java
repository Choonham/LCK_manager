package com.choonham.lck_manager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class InitialSettingActivity extends AppCompatActivity {

    SetFirstTeamFragment setFirstTeamFragment;
    SetNicknameAndSeasonFragment setNicknameAndSeasonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_setting);

        setNicknameAndSeasonFragment = (SetNicknameAndSeasonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
        setFirstTeamFragment = new SetFirstTeamFragment();
    }
}