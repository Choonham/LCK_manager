package com.choonham.lck_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.dao.RosterDAO;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.choonham.lck_manager.room.AppDatabase;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TeamInfoPopUpActivity extends Activity implements LifecycleOwner {

    private final ActivityTagEnum TAG = ActivityTagEnum.TEAM_INFO_POPUP_ACTIVITY;
    private List<JoinedPlayer> playerEntityList;
    ListView teamInfoListView;

    private LifecycleRegistry lifecycleRegistry;

    private List<JoinedPlayer> teamMainRosterList;

    boolean isTeamMainRosterLoad = false;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lifecycleRegistry = new LifecycleRegistry(this);

        db = AppDatabase.getInstance(this);

        isTeamMainRosterLoad = false;

        Intent intent = getIntent();

        String name = intent.getStringExtra("teamName");
        String rank = intent.getStringExtra("teamRank");

        teamMainRosterList = new ArrayList<>();
        //Log.e("읭", String.valueOf(intent.getIntExtra("teamCode", 1)));
        //getTeamMainRosterList(intent.getIntExtra("teamCode", 1));

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.team_info_pop_up);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.TOP;
        params.y = 200;
        /* wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;*/
        window.setAttributes(params);

        intent.putExtra("result", "result");

        setResult(Activity.RESULT_OK, intent);

        Common common = Common.getInstance();
        //playerEntityList = common.getTempPlayerList(0);

        teamInfoListView = findViewById(R.id.team_info_list_view);

        db.rosterDAO().loadPlayerListByTeamCode(intent.getIntExtra("teamCode", 1), 1)
            .observe(this::getLifecycle, loadValue -> {
                teamMainRosterList.addAll(loadValue);
                TeamInfoAdapter teamInfoAdapter = new TeamInfoAdapter(this, teamMainRosterList);
                teamInfoListView.setAdapter(teamInfoAdapter);
            });

        TextView teamName = findViewById(R.id.team_info_team_name);
        teamName.setText(name);

        TextView teamRank = findViewById(R.id.team_info_rank);
        teamRank.setText(rank);

        teamInfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {

                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(teamMainRosterList, i, selectedView, TAG, getApplicationContext(), 1);

                startActivity(intent);
            }
        });

    }



    @Override
    public void onBackPressed() {
        finish();
    }

    @NonNull
    @NotNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    /*public void getTeamMainRosterList(int teamCode) {

        RosterDAO rosterDAO = db.rosterDAO();
        rosterDAO.loadPlayerListByTeamCode(teamCode, 1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    for(JoinedPlayer player : loadValue) {
                        teamMainRosterList.add(player);
                    }

                    isTeamMainRosterLoad = true;
                })
                .doOnError(error -> {
                    Log.e("getTeamMainRosterList error :", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/

}