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
import androidx.appcompat.app.AppCompatActivity;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;

import java.util.List;

public class TeamInfoPopUpActivity extends Activity {

    private final ActivityTagEnum TAG = ActivityTagEnum.TEAM_INFO_POPUP_ACTIVITY;
    private List<PlayerEntity> playerEntityList;
    ListView teamInfoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.team_info_pop_up);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.TOP;
        params.y = 200;
         /* wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;*/
        window.setAttributes(params);

        Intent intent = getIntent();

        String name = intent.getStringExtra("teamName");
        String rank = intent.getStringExtra("teamRank");

        intent.putExtra("result", "result");

        setResult(Activity.RESULT_OK, intent);

        Common common = Common.getInstance();
        playerEntityList = common.getTempPlayerList(0);
        TeamInfoAdapter teamInfoAdapter = new TeamInfoAdapter(this, playerEntityList);

        teamInfoListView = findViewById(R.id.team_info_list_view);
        teamInfoListView.setAdapter(teamInfoAdapter);

        TextView teamName = findViewById(R.id.team_info_team_name);
        teamName.setText(name);

        TextView teamRank = findViewById(R.id.team_info_rank);
        teamRank.setText(rank);

        teamInfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                /*Intent intent = new Intent(getApplicationContext(), PlayerInfoPopUpActivity.class);
                TextView playerSeason = selectedView.findViewById(R.id.player_season_for_list_popup);
                TextView playerName = selectedView.findViewById(R.id.player_name_for_list_popup);
                ImageView positionIcon = selectedView.findViewById(R.id.main_roster_position_icon_popup);

                intent.putExtra("playerSeason", playerSeason.getText());
                intent.putExtra("playerName", playerName.getText());

                TextView avg = selectedView.findViewById(R.id.player_avg_for_list_popup);
                TextView stability = selectedView.findViewById(R.id.player_stability_for_list_popup);
                intent.putExtra("playerAvg", avg.getText());
                intent.putExtra("playerStability", stability.getText());

                int drawableRef = (int) positionIcon.getTag();
                intent.putExtra("positionIcon", drawableRef);

                intent.putExtra("tag", TAG);*/

                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(playerEntityList, i, selectedView, TAG, getApplicationContext(), 1);

                startActivity(intent);
            }
        });

    }



    @Override
    public void onBackPressed() {
        finish();
    }

}