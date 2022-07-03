package com.choonham.lck_manager.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.choonham.lck_manager.PlayerInfoPopUpActivity;
import com.choonham.lck_manager.R;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;

import java.util.ArrayList;
import java.util.List;

public class Common {

    String[] tempMainRosterList = {"Doran", "Peanut", "Chovy", "Ruler", "Lehands"};
    double[] tempMainRosterAvgList = {112.5, 115.5, 120.2, 117.2, 120.8};
    double[] tempMainRosterStabilityList = {5.1, 6.8, 9.3, 8.2, 1.3};

    String[] tempMainRosterList2 = {"Doran", "Pyosik", "Chovy", "Deft", "Keria", "Faker", "Gumayusi", "Geus", "Kiin"};
    double[] tempMainRosterAvgList2 = {112.5, 115.5, 120.2, 117.2, 120.8, 115.5, 120.2, 117.2, 120.8};
    double[] tempMainRosterStabilityList2 = {5.1, 6.8, 9.3, 8.2, 1.3, 6.8, 9.3, 8.2, 1.3};

    int[] positionIcons = {R.drawable.position_top_icon, R.drawable.position_jungle_icon, R.drawable.position_mid_icon, R.drawable.position_ad_icon, R.drawable.position_support_icon};

    int[] positionIcons2 = {
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

    int[] pogPoints = {400, 400, 300, 300, 200, 200, 100, 100, 100};

    static Common instance;

    private Common() {};

    public Intent getPlayerInfoPopUpIntent(List<PlayerEntity> playerList, int index, View selectedView, ActivityTagEnum tag, Context context, int popupFlag){
        Intent intent = new Intent(context, PlayerInfoPopUpActivity.class);

        TextView season;
        TextView name;
        ImageView positionIcon;
        TextView avg;
        TextView stability;

        if(popupFlag == 1) {
            season = selectedView.findViewById(R.id.player_season_for_list_popup);
            name = selectedView.findViewById(R.id.player_name_for_list_popup);
            positionIcon = selectedView.findViewById(R.id.main_roster_position_icon_popup);
            avg = selectedView.findViewById(R.id.player_avg_for_list_popup);
            stability = selectedView.findViewById(R.id.player_stability_for_list_popup);
        } else {
            season = selectedView.findViewById(R.id.player_season_for_list);
            name = selectedView.findViewById(R.id.player_name_for_list);
            positionIcon = selectedView.findViewById(R.id.main_roster_position_icon);
            avg = selectedView.findViewById(R.id.player_avg_for_list);
            stability = selectedView.findViewById(R.id.player_stability_for_list);
        }

        int drawableRef = (int) positionIcon.getTag();

        intent.putExtra("playerSeason", season.getText());
        intent.putExtra("playerName", name.getText());
        intent.putExtra("positionIcon", drawableRef);
        intent.putExtra("playerAvg", avg.getText());
        intent.putExtra("playerStability", stability.getText());
        intent.putExtra("tag", tag);

        intent.putExtra("playerEntity", playerList.get(index));

        return intent;
    }

    public List<PlayerEntity> getTempPlayerList(int teamCode) {
        List<PlayerEntity> returnList = new ArrayList<>();

        switch (teamCode) {
            case 0:
                int i = 0;

                for(String tempPlayerName : tempMainRosterList) {
                    PlayerEntity tempPlayer = new PlayerEntity();
                    tempPlayer.setPlayerName(tempPlayerName);
                    tempPlayer.setSeasonCode(1);
                    tempPlayer.setPosition(i);
                    tempPlayer.setPhysical(tempMainRosterAvgList[i]);
                    tempPlayer.setStability(tempMainRosterStabilityList[i]);
                    tempPlayer.setPositionIcon(positionIcons[i]);

                    returnList.add(tempPlayer);
                    i++;
                }

                break;

            case 1:
                int j = 0;

                for(String tempPlayerName : tempMainRosterList2) {
                    PlayerEntity tempPlayer = new PlayerEntity();
                    tempPlayer.setPlayerName(tempPlayerName);
                    tempPlayer.setSeasonCode(1);
                    tempPlayer.setPosition(j);
                    tempPlayer.setPhysical(tempMainRosterAvgList2[j]);
                    tempPlayer.setStability(tempMainRosterStabilityList2[j]);
                    tempPlayer.setPositionIcon(positionIcons2[j]);

                    returnList.add(tempPlayer);
                    j++;
                }

                break;

            case 2:
                int k = 0;

                for(String tempPlayerName : tempMainRosterList2) {
                    PlayerEntity tempPlayer = new PlayerEntity();
                    tempPlayer.setPlayerName(tempPlayerName);
                    tempPlayer.setSeasonCode(1);
                    tempPlayer.setPosition(k);
                    tempPlayer.setPositionIcon(positionIcons2[k]);
                    tempPlayer.setPogPoint(pogPoints[k]);
                    returnList.add(tempPlayer);
                    k++;
                }

                break;

        }

        return returnList;
    }

    public static Common getInstance() {
        if(instance == null) {
            instance = new Common();
        }

        return instance;
    }
}
