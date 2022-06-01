package com.choonham.lck_manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerInfoPopUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.player_info_pop_up);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.TOP;
        params.y = 200;
         /* wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;*/
        window.setAttributes(params);

        Intent intent = getIntent();

        String season = intent.getStringExtra("playerSeason");
        String name = intent.getStringExtra("playerName");
        int positionIconID = intent.getIntExtra("positionIcon", 0);

        TextView playerName = findViewById(R.id.player_info_name);
        TextView playerSeason = findViewById(R.id.player_info_season);
        ImageView playerPositionIcon = findViewById(R.id.player_info_position_icon);

        playerPositionIcon.setImageResource(positionIconID);

        playerName.setText(name);
        playerSeason.setText(season);

        intent.putExtra("result", "result");

        setResult(Activity.RESULT_OK, intent);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}