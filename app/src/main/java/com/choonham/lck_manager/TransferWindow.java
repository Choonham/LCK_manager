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
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.entity.ChampionCounterEntity;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TransferWindow extends Fragment {
    final ActivityTagEnum TAG = ActivityTagEnum.TRANSFER_WINDOW;

    ListView transferWindowListView;

    private List<JoinedPlayer> playerEntityList;

    private ImageButton refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transfer_window, container, false);

        Common common = Common.getInstance();
        playerEntityList =  common.getTempPlayerList(1);

        MainRosterAdapter mainRosterAdapter = new MainRosterAdapter(getContext(), playerEntityList);
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
                /*Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);
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
                intent.putExtra("Tag", TAG);*/

                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(playerEntityList, i, selectedView, TAG, getContext(), 0);

                startActivity(intent);
            }
        });

        return view;
    }

}
