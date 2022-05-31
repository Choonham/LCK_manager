package com.choonham.lck_manager;

import android.app.Activity;
import android.content.Intent;
import android.print.PrintAttributes;
import android.view.Gravity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class NewsPopUpActivity extends Activity {

    ListView issueEffectListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.news_pop_up);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        issueEffectListView = findViewById(R.id.issue_effect_list_view);

        params.gravity = Gravity.TOP;
        params.y = 200;
         /* wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;*/
        window.setAttributes(params);

        IssueEffectAdapter issueEffectAdapter = new IssueEffectAdapter(this);
        issueEffectListView.setAdapter(issueEffectAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}