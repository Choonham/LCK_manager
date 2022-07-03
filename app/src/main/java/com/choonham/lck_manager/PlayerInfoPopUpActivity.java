package com.choonham.lck_manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.choonham.lck_manager.entity.ChampionCounterEntity;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;

public class PlayerInfoPopUpActivity extends Activity {
    private final ActivityTagEnum TAG = ActivityTagEnum.PLAYER_INFO_POPUP_ACTIVITY;

    private float avg;
    private float stability;

    ImageButton offerBtn;

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

        /*String season = intent.getStringExtra("playerSeason");
        String name = intent.getStringExtra("playerName");
        int positionIconID = intent.getIntExtra("positionIcon", 0);*/

        PlayerEntity playerEntity = intent.getParcelableExtra("playerEntity");

        String season = (playerEntity.getSeasonCode() == 1 ? "22 SPR" : "NONE");
        String name = playerEntity.getPlayerName();

        ActivityTagEnum tag = (ActivityTagEnum)intent.getSerializableExtra("tag");

        if(tag.equals(ActivityTagEnum.TRANSFER_WINDOW) || tag.equals(ActivityTagEnum.SET_FIRST_TEAM_FRAGMENT)) {
            LinearLayout parentLayout = findViewById(R.id.player_info_popup_parent_layout);
            ViewGroup.LayoutParams layoutParams = parentLayout.getLayoutParams();
            parentLayout.post(new Runnable()
            {
                @Override
                public void run()
                {
                    layoutParams.height = (int)(parentLayout.getHeight() * 1.3);
                    ConstraintLayout constraintLayout = new ConstraintLayout(getApplicationContext());
                    constraintLayout.setId(ConstraintLayout.generateViewId());

                    offerBtn = new ImageButton(getApplicationContext());
                    offerBtn.setImageResource(R.drawable.offer_button);
                    offerBtn.setBackgroundColor(Color.TRANSPARENT);

                    offerBtn.setPadding(0,0,0,0);

                    offerBtn.setId(Button.generateViewId());

                    constraintLayout.addView(offerBtn, 0);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);

                    constraintSet.connect(offerBtn.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 100);
                    constraintSet.connect(offerBtn.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM, 0);
                    constraintSet.connect(offerBtn.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 0);
                    constraintSet.connect(offerBtn.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, 0);

                    constraintSet.applyTo(constraintLayout);

                    parentLayout.setLayoutParams(layoutParams);
                    parentLayout.addView(constraintLayout);


                }
            });

            /*parentLayout.setLayoutParams(layoutParams);*/
        }

        avg = Float.parseFloat(intent.getStringExtra("playerAvg"));
        stability = Float.parseFloat(intent.getStringExtra("playerStability"));

        TextView playerName = findViewById(R.id.player_info_name);
        TextView playerSeason = findViewById(R.id.player_info_season);
        ImageView playerPositionIcon = findViewById(R.id.player_info_position_icon);

        playerPositionIcon.setImageResource(playerEntity.getPositionIcon());

        playerName.setText(name);
        playerSeason.setText(season);

        intent.putExtra("result", "result");

        setResult(Activity.RESULT_OK, intent);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        View view = findViewById(R.id.status_pentagon_layout);

        StatusPentagonView statusPentagonView = new StatusPentagonView(this, view.getWidth(), view.getHeight(), avg);
        ConstraintLayout status_pentagon_layout = findViewById(R.id.status_pentagon_layout);
        status_pentagon_layout.addView(statusPentagonView);
    }
}