package com.choonham.lck_manager;

import android.util.Log;
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
                .findFragmentById(R.id.SetNicknameAndSeasonFragment);
        setFirstTeamFragment = new SetFirstTeamFragment();
    }

    public void onFragmentChanged(int index) {
        Log.d("TAG", Integer.toString(index));
        if(index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, setNicknameAndSeasonFragment).commit();
        } else if(index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, setFirstTeamFragment).commit();
        }
    }
}