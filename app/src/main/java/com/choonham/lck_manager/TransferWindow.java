package com.choonham.lck_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import org.jetbrains.annotations.NotNull;

public class TransferWindow extends Fragment {
    ListView transferWindowListView;

    String[] tempMainRosterList = {"Doran", "Pyosik", "Chovy", "Deft", "Keria", "Faker", "Gumayusi", "Geus", "Kiin"};
    float[] tempMainRosterAvgList = {112.5f, 115.5f, 120.2f, 117.2f, 120.8f, 115.5f, 120.2f, 117.2f, 120.8f};
    float[] tempMainRosterStabilityList = {5.1f, 6.8f, 9.3f, 8.2f, 1.3f, 6.8f, 9.3f, 8.2f, 1.3f};

    private ImageButton refresh;

    int[] positionIcons = {
            R.drawable.position_top_icon,
            R.drawable.position_jungle_icon,
            R.drawable.position_mid_icon,
            R.drawable.position_ad_icon,
            R.drawable.position_support_icon,
            R.drawable.position_mid_icon,
            R.drawable.position_ad_icon,
            R.drawable.position_top_icon,
            R.drawable.position_top_icon
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transfer_window, container, false);

        MainRosterAdapter mainRosterAdapter = new MainRosterAdapter(getContext(), tempMainRosterList, positionIcons, tempMainRosterAvgList, tempMainRosterStabilityList);
        transferWindowListView = view.findViewById(R.id.weekly_transfer_window_list_view);
        /*ViewGroup header = (ViewGroup) inflater.inflate(R.layout.main_roster_header_view, transferWindowListView, false);
        header.setPadding(0, 20, 0, 0);

        transferWindowListView.addHeaderView(header, null, false);*/
        transferWindowListView.setAdapter((ListAdapter) mainRosterAdapter);

        refresh = view.findViewById(R.id.transfer_window_refresh_button);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DD", "Hey!");
            }
        });

        transferWindowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);
                TextView season = selectedView.findViewById(R.id.player_season_for_list);
                TextView name = selectedView.findViewById(R.id.player_name_for_list);
                ImageView positionIcon = selectedView.findViewById(R.id.main_roster_position_icon);
                int drawableRef = (int) positionIcon.getTag();

                intent.putExtra("playerSeason", season.getText());
                intent.putExtra("playerName", name.getText());
                intent.putExtra("positionIcon", drawableRef);

                TextView avg = selectedView.findViewById(R.id.player_avg_for_list);
                TextView stability = selectedView.findViewById(R.id.player_stability_for_list);
                intent.putExtra("playerAvg", avg.getText());
                intent.putExtra("playerStability", stability.getText());


                startActivity(intent);
            }
        });

        return view;
    }

}
