package com.choonham.lck_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

public class TeamInfoPopUpActivity extends Activity {

    ListView teamInfoListView;

    String[] tempMainRosterList = {"Doran", "Peanut", "Chovy", "Ruler", "Lehands"};
    float[] tempMainRosterAvgList = {112.5f, 115.5f, 120.2f, 117.2f, 120.8f};
    float[] tempMainRosterStabilityList = {5.1f, 6.8f, 9.3f, 8.2f, 1.3f};

    int[] positionIcons = {R.drawable.position_top_icon, R.drawable.position_jungle_icon, R.drawable.position_mid_icon, R.drawable.position_ad_icon, R.drawable.position_support_icon};

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

        MainRosterAdapter mainRosterAdapter = new MainRosterAdapter(this, tempMainRosterList, positionIcons, tempMainRosterAvgList, tempMainRosterStabilityList);

        teamInfoListView = findViewById(R.id.team_info_list_view);
        teamInfoListView.setAdapter(mainRosterAdapter);

        TextView teamName = findViewById(R.id.team_info_team_name);
        teamName.setText(name);

        TextView teamRank = findViewById(R.id.team_info_rank);
        teamRank.setText(rank);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}