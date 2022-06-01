package com.choonham.lck_manager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

public class MainView extends Fragment {

    ListView matchScheduleListView;

    String[] matchScheduleTeamList = {"T1", "DRX", "Gen.G", "KT", "DWG"};
    int[] matchScheduleResultList = {1, 0, 2, 2, 2};
    int[] matchScheduleRankList = {1, 4, 2, 6, 2};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_view, container, false);

        MatchScheduleAdapter matchScheduleAdapter = new MatchScheduleAdapter(matchScheduleTeamList, matchScheduleResultList, matchScheduleRankList, getContext());
        matchScheduleListView = view.findViewById(R.id.match_schedule_list_view);

        matchScheduleListView.setAdapter(matchScheduleAdapter);

        LinearLayout newsLayout = view.findViewById(R.id.news);
        newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewsPopUpActivity.class);
                startActivity(intent);
            }
        });

        matchScheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                Intent intent = new Intent(getContext(), TeamInfoPopUpActivity.class);
                TextView teamName = selectedView.findViewById(R.id.match_schedule_team_name);
                TextView teamRank = selectedView.findViewById(R.id.match_schedule_rank);
                intent.putExtra("teamName", teamName.getText());
                intent.putExtra("teamRank", teamRank.getText());

                mGetContent.launch(intent);
                /*startActivity(intent);*/
            }
        });

        return view;
    }

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                }
            }
    });
}
