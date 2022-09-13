package com.choonham.lck_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.common.PlayerInfoModel;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.google.android.material.snackbar.Snackbar;

public class PlayerProposalActivity extends Activity {
    private final ActivityTagEnum TAG = ActivityTagEnum.PLAYER_PROPOSAL;

    ImageButton submitButton;

    TextView offeredTransferFeeView;
    TextView offeredSalaryView;

    //double offeredSalary;
    double offeredTransferFee;

    EditText offerSalaryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Common common = Common.getInstance();

        PlayerInfoModel playerInfoModel = PlayerInfoModel.getInstance();

        requestWindowFeature(Window.FEATURE_NO_TITLE); // no headLine
        setContentView(R.layout.player_proposal);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        submitButton = findViewById(R.id.offer_to_roster_btn);

        Intent intent = getIntent();

        PlayerEntity playerEntity = intent.getParcelableExtra("playerEntity");
        SeasonEntity seasonEntity = intent.getParcelableExtra("seasonEntity");

        String name = playerEntity.getPlayerName();

        TextView playerName = findViewById(R.id.player_info_name);
        TextView playerSeason = findViewById(R.id.player_info_season);
        ImageView playerPositionIcon = findViewById(R.id.player_info_position_icon);

        playerSeason.setText(seasonEntity.getSeasonForShort());
        playerPositionIcon.setImageResource(Common.positionIcons[playerEntity.getPosition()]);
        playerName.setText(name);

        double feeIndex1 = playerEntity.getOutSmart() + playerEntity.getPhysical();
        double feeIndex2 = playerEntity.getLaneStrength() + playerEntity.getTeamFight();

        offeredTransferFee =
                feeIndex1 * feeIndex2 * playerEntity.getStability() * playerEntity.getFameLv() * 10;

        offeredTransferFeeView = findViewById(R.id.transfer_fee_proposal);
        offeredTransferFeeView.setText(Double.toString(offeredTransferFee));
        //transfer_fee_total_salary_proposal

        offeredSalaryView = findViewById(R.id.transfer_fee_salary_proposal);
        offeredSalaryView.setText(Double.toString(offeredTransferFee/10));

        offerSalaryView = findViewById(R.id.transfer_fee_total_salary_proposal);
        offerSalaryView.setText(Double.toString(offeredTransferFee/10));

        offerSalaryView.setFocusable(false);
        offerSalaryView.setEnabled(false);
        offerSalaryView.setCursorVisible(false);
        offerSalaryView.setKeyListener(null);
        offerSalaryView.setBackgroundColor(Color.TRANSPARENT);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.startMoney < Double.parseDouble(offeredTransferFeeView.getText().toString())) {
                    Snackbar.make(v, "자본금이 부족합니다.", Snackbar.LENGTH_LONG).show();
                } else {
                    Common.startMoney -= Double.parseDouble(offeredTransferFeeView.getText().toString());

                    playerInfoModel.onConfirm();
                    finish();
                }
                //String offeredBox = offeredTransferFeeView.getText().toString();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public Intent getPlayerProposalPopUp(Context context){
        Intent intent = new Intent(context, PlayerInfoPopUpActivity.class);
        
        return intent;
    }


}
