package com.choonham.lck_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import com.choonham.lck_manager.enums.ActivityTagEnum;

public class PlayerProposalActivity extends Activity {
    private final ActivityTagEnum TAG = ActivityTagEnum.PLAYER_PROPOSAL;

    ImageButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.player_proposal);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
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
