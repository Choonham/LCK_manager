package com.choonham.lck_manager;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
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

        issueEffectListView = findViewById(R.id.issue_effect_list_view);

        IssueEffectAdapter issueEffectAdapter = new IssueEffectAdapter(this);
        issueEffectListView.setAdapter(issueEffectAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}