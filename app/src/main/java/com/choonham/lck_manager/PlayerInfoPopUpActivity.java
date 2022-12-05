package com.choonham.lck_manager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.choonham.lck_manager.common.*;
import com.choonham.lck_manager.dao.RosterDAO;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.entity.TransferWindowEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.room.AppDatabase;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.Set;

public class PlayerInfoPopUpActivity extends Activity implements PlayerInfoListener {
    private final ActivityTagEnum TAG = ActivityTagEnum.PLAYER_INFO_POPUP_ACTIVITY;

    private double avg;
    private double stability;

    ImageButton offerBtn;
    ImageButton cancelBtn;

    ImageButton toSubBtn;
    ImageButton toMainBtn;

    ImageButton offerToFABtn;
    ImageButton releaseBtn;

    SetFirstTeamModel setFirstTeamModel;
    TransferWindowModel transferWindowModel;
    TeamRosterModel teamRosterModel;

    TextView stabilityView;
    TextView physicalView;
    TextView outSmartView;
    TextView laneStrengthView;
    TextView teamFightView;

    PlayerEntity playerEntity;
    SeasonEntity seasonEntity;

    AppDatabase db;

    int tagFrom;

    TransferWindowEntity transferEntity = null;

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

        Bundle b = intent.getBundleExtra("bundle");

        playerEntity = b.getParcelable("playerEntity");
        seasonEntity = b.getParcelable("seasonEntity");

        String season = (seasonEntity.getSeasonForShort());
        String name = playerEntity.getPlayerName();

       /* Log.e("태그 테스트123", season);
        Log.e("태그 테스트124", name);*/

        stabilityView = findViewById(R.id.player_stat_stability_index);
        physicalView = findViewById(R.id.player_stat_physical_index);
        outSmartView = findViewById(R.id.player_stat_operation_index);
        laneStrengthView = findViewById(R.id.player_stat_lane_index);
        teamFightView = findViewById(R.id.player_stat_team_fight_index);

        stabilityView.setText("[" + Double.toString(playerEntity.getStability()) + "]");
        physicalView.setText("[" + Double.toString(playerEntity.getPhysical()) + "]");
        outSmartView.setText("[" + Double.toString(playerEntity.getOutSmart()) + "]");
        laneStrengthView.setText("[" + Double.toString(playerEntity.getLaneStrength()) + "]");
        teamFightView.setText("[" + Double.toString(playerEntity.getTeamFight()) + "]");

        Log.e("번들", b.toString());

        ActivityTagEnum tag = ActivityTagEnum.INIT_TAG;

        tag = (ActivityTagEnum) b.getSerializable("tag");

        int tagInt = 0;

        if(tag == null) {
            tagInt = b.getInt("tagInt");
            tag = ActivityTagEnum.INIT_TAG;
        }

        LinearLayout parentLayout = findViewById(R.id.player_info_popup_parent_layout);
        ViewGroup.LayoutParams layoutParams = parentLayout.getLayoutParams();

        LinearLayout mostFiveLinearLayout = findViewById(R.id.most_five_linear_layout);

        //setFirstTeamModel = SetFirstTeamModel.getInstance();

        if(tagInt == 1122 || tag.equals(ActivityTagEnum.SET_FIRST_TEAM_FRAGMENT)) {

            if(tagInt == 1122) {
                tagFrom = 0;
                transferWindowModel = TransferWindowModel.getInstance();
                //transferEntity = intent.getParcelableExtra("transferWindowEntity");
            } else {
                tagFrom = 1;
                setFirstTeamModel = SetFirstTeamModel.getInstance();
            }

            int temp = tagInt;

            PlayerInfoModel.createInstance(this);
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
                    offerBtn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view)
                        {
                            Intent intent2 = new Intent(getApplicationContext(),PlayerProposalActivity.class);
                            intent2.putExtra("playerEntity", playerEntity);
                            intent2.putExtra("seasonEntity", seasonEntity);

                            /*if(temp == 1122) {
                                intent2.putExtra("transferWindowEntity", transferEntity);
                            }*/

                            intent2.putExtra("tagInt", temp);

                            startActivity(intent2);

                            finish();
                        }
                    });

                    cancelBtn = new ImageButton(getApplicationContext());
                    cancelBtn.setImageResource(R.drawable.release_btn);
                    cancelBtn.setBackgroundColor(Color.TRANSPARENT);

                    cancelBtn.setPadding(0,0,0,0);

                    cancelBtn.setId(Button.generateViewId());
                    cancelBtn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view)
                        {
                            double feeIndex1 = playerEntity.getOutSmart() + playerEntity.getPhysical();
                            double feeIndex2 = playerEntity.getLaneStrength() + playerEntity.getTeamFight();

                            Common.startMoney +=
                                    feeIndex1 * feeIndex2 * playerEntity.getStability() * playerEntity.getFameLv() * 10;

                            setFirstTeamModel.onRelease();
                            finish();
                        }
                    });

                    ImageButton imageButton = null;
                    boolean isMyTeam = intent.getBooleanExtra("isMyTeam", false);

                    if(!isMyTeam) {
                        imageButton = offerBtn;
                    } else {
                        imageButton = cancelBtn;
                    }


                    constraintLayout.addView(imageButton, 0);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);

                    constraintSet.connect(imageButton.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 100);
                    constraintSet.connect(imageButton.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM, 0);
                    constraintSet.connect(imageButton.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 0);
                    constraintSet.connect(imageButton.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, 0);

                    constraintSet.applyTo(constraintLayout);

                    parentLayout.setLayoutParams(layoutParams);
                    parentLayout.addView(constraintLayout);
                }
            });

            /*parentLayout.setLayoutParams(layoutParams);*/
        } else if(tag.equals(ActivityTagEnum.TEAM_ROSTER_MAIN)){

            db = AppDatabase.getInstance(getApplicationContext());

            teamRosterModel = TeamRosterModel.getInstance();

            LinearLayout.LayoutParams paramsTemp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsTemp.setMargins(0, 0, 0, 0);

            paramsTemp.gravity = Gravity.LEFT;

            toSubBtn = new ImageButton(getApplicationContext());
            toSubBtn.setImageResource(R.drawable.to_sub_button);
            toSubBtn.setBackgroundColor(Color.TRANSPARENT);

            toSubBtn.setPadding(0,0,50,0);

            toSubBtn.setLayoutParams(paramsTemp);

            int teamCode = b.getInt("teamCode");

            toSubBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RosterDAO rosterDAO = db.rosterDAO();

                    rosterDAO.updateRosterData(0, playerEntity.getPlayerCode(), teamCode)
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(insertValue -> {
                                Log.d("Roster Update!: ", String.valueOf(insertValue));
                            })
                            .doOnError(error -> {
                                Log.e("Roster Update error :", error.getMessage());
                            })
                            .subscribe();

                    teamRosterModel.toSub();

                    finish();
                }
            });

            mostFiveLinearLayout.addView(toSubBtn);

        } else if(tag.equals(ActivityTagEnum.TEAM_ROSTER_SUB)){
            teamRosterModel = TeamRosterModel.getInstance();

            PlayerInfoModel.createInstance(this);
            tagFrom = 2;

            Log.e("여긴", String.valueOf(tagFrom));

            db = AppDatabase.getInstance(getApplicationContext());

            LinearLayout.LayoutParams paramsTemp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsTemp.setMargins(0, 0, 0, 0);

            paramsTemp.gravity = Gravity.LEFT;

            toMainBtn = new ImageButton(getApplicationContext());
            toMainBtn.setImageResource(R.drawable.to_main_button);
            toMainBtn.setBackgroundColor(Color.TRANSPARENT);

            toMainBtn.setPadding(0,0,50,0);

            toMainBtn.setLayoutParams(paramsTemp);

            int teamCode = b.getInt("teamCode");

            toMainBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log.e("뭐지", String.valueOf(playerEntity.getPlayerCode()));
                    Log.e("뭐지2", String.valueOf(teamCode));
                    RosterDAO rosterDAO = db.rosterDAO();

                    rosterDAO.updateRosterData(1, playerEntity.getPlayerCode(), teamCode)
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(insertValue -> {
                                Log.d("Roster Update!: ", String.valueOf(insertValue));
                            })
                            .doOnError(error -> {
                                Log.e("Roster Update error :", error.getMessage());
                            })
                            .subscribe();

                    teamRosterModel.toMain();

                    finish();
                }
            });

            mostFiveLinearLayout.addView(toMainBtn);

            parentLayout.post(new Runnable()
            {
                @Override
                public void run()
                {
                    layoutParams.height = (int)(parentLayout.getHeight() * 1.3);
                    ConstraintLayout constraintLayout2 = new ConstraintLayout(getApplicationContext());
                    constraintLayout2.setId(ConstraintLayout.generateViewId());

                    offerToFABtn = new ImageButton(getApplicationContext());
                    offerToFABtn.setImageResource(R.drawable.offer_to_fa_btn);
                    offerToFABtn.setBackgroundColor(Color.TRANSPARENT);

                    offerToFABtn.setPadding(0,0,0,0);

                    offerToFABtn.setId(Button.generateViewId());

                    releaseBtn = new ImageButton(getApplicationContext());
                    releaseBtn.setImageResource(R.drawable.release_btn);
                    releaseBtn.setBackgroundColor(Color.TRANSPARENT);

                    releaseBtn.setPadding(0,0,0,0);

                    releaseBtn.setId(Button.generateViewId());

                    constraintLayout2.addView(offerToFABtn, 0);
                    constraintLayout2.addView(releaseBtn, 1);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout2);

                    constraintSet.connect(releaseBtn.getId(), ConstraintSet.TOP, constraintLayout2.getId(), ConstraintSet.TOP, 100);
                    constraintSet.connect(releaseBtn.getId(), ConstraintSet.BOTTOM, constraintLayout2.getId(), ConstraintSet.BOTTOM, 0);
                    constraintSet.connect(releaseBtn.getId(), ConstraintSet.LEFT, constraintLayout2.getId(), ConstraintSet.LEFT, 0);
                    constraintSet.connect(releaseBtn.getId(), ConstraintSet.RIGHT, offerToFABtn.getId(), ConstraintSet.LEFT, 0);

                    constraintSet.connect(offerToFABtn.getId(), ConstraintSet.TOP, constraintLayout2.getId(), ConstraintSet.TOP, 100);
                    constraintSet.connect(offerToFABtn.getId(), ConstraintSet.BOTTOM, constraintLayout2.getId(), ConstraintSet.BOTTOM, 0);
                    constraintSet.connect(offerToFABtn.getId(), ConstraintSet.LEFT, releaseBtn.getId(), ConstraintSet.RIGHT, 0);
                    constraintSet.connect(offerToFABtn.getId(), ConstraintSet.RIGHT, constraintLayout2.getId(), ConstraintSet.RIGHT, 0);

                    constraintSet.applyTo(constraintLayout2);

                    parentLayout.setLayoutParams(layoutParams);
                    parentLayout.addView(constraintLayout2);

                    offerToFABtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent2 = new Intent(getApplicationContext(),PlayerProposalActivity.class);
                            intent2.putExtra("playerEntity", playerEntity);
                            intent2.putExtra("seasonEntity", seasonEntity);

                            intent2.putExtra("teamRosterEntity", 1);
                            startActivity(intent2);

                            finish();
                        }
                    });

                    releaseBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PlayerInfoPopUpActivity.this, R.style.ReleaseDialogTheme);

                            View view = LayoutInflater.from(PlayerInfoPopUpActivity.this).inflate(
                                    R.layout.release_confirm_dialog, (LinearLayout) findViewById(R.id.release_confirm_layout)
                            );

                            builder.setView(view);
                            ((TextView)view.findViewById(R.id.release_confirm_msg)).setText("정말 " + playerEntity.getPlayerName() + " 선수를 방출하시겠습니까? \n (방출된 선수는 다시 되돌릴 수 없습니다.)");

                            AlertDialog alertDialog = builder.create();

                            view.findViewById(R.id.release_yes_btn).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    teamRosterModel.onRelease();
                                    alertDialog.dismiss();

                                    finish();
                                }
                            });

                            view.findViewById(R.id.release_no_btn).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });

                            if(alertDialog.getWindow() != null) {
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }

                            alertDialog.show();
                        }
                    });
                }
            });

        }

        avg = (playerEntity.getStability() + playerEntity.getTeamFight() + playerEntity.getOutSmart() + playerEntity.getPhysical())/4;
        stability = playerEntity.getStability();
 
        TextView playerName = findViewById(R.id.player_info_name);
        TextView playerSeason = findViewById(R.id.player_info_season);
        ImageView playerPositionIcon = findViewById(R.id.player_info_position_icon);

        playerPositionIcon.setImageResource(Common.positionIcons[playerEntity.getPosition()]);

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

        StatusPentagonView statusPentagonView = new StatusPentagonView(this,
                view.getWidth(),
                view.getHeight(),
                (float) playerEntity.getStability(),
                (float) playerEntity.getPhysical(),
                (float) playerEntity.getOutSmart(),
                (float) playerEntity.getLaneStrength(),
                (float) playerEntity.getTeamFight()
        );
        ConstraintLayout status_pentagon_layout = findViewById(R.id.status_pentagon_layout);
        status_pentagon_layout.addView(statusPentagonView);
    }

    @Override
    public void onConfirm(double offerTransferFee) {
        if(tagFrom == 0) {
            transferWindowModel.onConfirm(offerTransferFee);
        } else if(tagFrom ==1) {
            setFirstTeamModel.onConfirm(offerTransferFee);
        } else if(tagFrom == 2) {
            teamRosterModel.onConfirm(offerTransferFee);
        }

        finish();
    }

}