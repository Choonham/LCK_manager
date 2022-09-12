package com.choonham.lck_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;

public class PlayerProposalActivity extends Activity {
    private final ActivityTagEnum TAG = ActivityTagEnum.PLAYER_PROPOSAL;

    ImageButton submitButton;

    TextView offeredTransferFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // no headLine
        setContentView(R.layout.player_proposal);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        submitButton = findViewById(R.id.offer_to_roster_btn);

        Intent intent = getIntent();

        PlayerEntity playerEntity = intent.getParcelableExtra("playerEntity");

        String name = playerEntity.getPlayerName();

        TextView playerName = findViewById(R.id.player_info_name);

        playerName.setText(name);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offeredTransferFee = findViewById(R.id.transfer_fee_proposal);
                String offeredBox = offeredTransferFee.getText().toString();


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
