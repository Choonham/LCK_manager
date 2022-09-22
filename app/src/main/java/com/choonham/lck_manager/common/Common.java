package com.choonham.lck_manager.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.choonham.lck_manager.BuildConfig;
import com.choonham.lck_manager.PlayerInfoPopUpActivity;
import com.choonham.lck_manager.R;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Common {

    public static String REST_API_KEY = BuildConfig.REST_API_KEY;
    public static String REST_API_URL = BuildConfig.REST_API_URL;

    String[] tempMainRosterList = {"Doran", "Peanut", "Chovy", "Ruler", "Lehands"};
    double[] tempMainRosterAvgList = {112.5, 115.5, 120.2, 117.2, 120.8};
    double[] tempMainRosterStabilityList = {5.1, 6.8, 9.3, 8.2, 1.3};

    String[] tempMainRosterList2 = {"Doran", "Pyosik", "Chovy", "Deft", "Keria", "Faker", "Gumayusi", "Geus", "Kiin"};
    double[] tempMainRosterAvgList2 = {112.5, 115.5, 120.2, 117.2, 120.8, 115.5, 120.2, 117.2, 120.8};
    double[] tempMainRosterStabilityList2 = {5.1, 6.8, 9.3, 8.2, 1.3, 6.8, 9.3, 8.2, 1.3};

    // HTTP 요청을 위한 RequestQueue 를 static 으로 선언
    private static RequestQueue requestQueue;

    public static double startMoney = 50000;

    public static int[] positionIcons = {R.drawable.position_top_icon, R.drawable.position_jungle_icon, R.drawable.position_mid_icon, R.drawable.position_ad_icon, R.drawable.position_support_icon};

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
    private static Context contextForChecking;

    private Common() {};

    /**
     * get RequestQueue Instance
     *
     * @param context
     * @return requestQueue
     */
    public static RequestQueue getRequestQueueInstance(Context context) {
        // RequestQueue 객체 생성
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
            contextForChecking = context;
        } else {
            if(!contextForChecking.equals(context)) {
                requestQueue = Volley.newRequestQueue(context);
            }
        }
        return requestQueue;
    }

    public Intent getPlayerInfoPopUpIntent(List<JoinedPlayer> playerList, int index, View selectedView, ActivityTagEnum tag, Context context, int popupFlag){

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

        intent.putExtra("playerEntity", playerList.get(index).playerEntity);

        intent.putExtra("seasonEntity", playerList.get(index).seasonEntity);

        return intent;
    }

    public List<JoinedPlayer> getTempPlayerList(int teamCode) {
        List<JoinedPlayer> returnList = new ArrayList<>();

        switch (teamCode) {
            case 0:
                int i = 0;

                for(String tempPlayerName : tempMainRosterList) {
                    JoinedPlayer tempPlayer = new JoinedPlayer();

                    PlayerEntity playerEntity = new PlayerEntity();
                    SeasonEntity seasonEntity = new SeasonEntity();

                    seasonEntity.setSeasonCode(15);
                    seasonEntity.setSeasonForShort("22 SPR");
                    seasonEntity.setSeasonName("22 SPRING");

                    playerEntity.setPlayerName(tempPlayerName);
                    playerEntity.setSeasonCode(1);
                    playerEntity.setPosition(i);
                    playerEntity.setPhysical(tempMainRosterAvgList[i]);
                    playerEntity.setStability(tempMainRosterStabilityList[i]);
                    playerEntity.setPositionIcon(positionIcons[i]);

                    tempPlayer.playerEntity = playerEntity;
                    tempPlayer.seasonEntity = seasonEntity;

                    returnList.add(tempPlayer);
                    i++;
                }

                break;

            case 1:
                int j = 0;

                for(String tempPlayerName : tempMainRosterList2) {
                    JoinedPlayer tempPlayer = new JoinedPlayer();

                    PlayerEntity playerEntity = new PlayerEntity();
                    SeasonEntity seasonEntity = new SeasonEntity();

                    playerEntity.setPlayerName(tempPlayerName);
                    playerEntity.setSeasonCode(1);
                    playerEntity.setPosition(j);
                    playerEntity.setPhysical(tempMainRosterAvgList2[j]);
                    playerEntity.setStability(tempMainRosterStabilityList2[j]);
                    playerEntity.setPositionIcon(positionIcons2[j]);

                    seasonEntity.setSeasonCode(15);
                    seasonEntity.setSeasonForShort("22 SPR");
                    seasonEntity.setSeasonName("22 SPRING");

                    tempPlayer.seasonEntity = seasonEntity;

                    tempPlayer.playerEntity = playerEntity;

                    returnList.add(tempPlayer);
                    j++;
                }

                break;

            case 2:
                int k = 0;

                for(String tempPlayerName : tempMainRosterList2) {
                    JoinedPlayer tempPlayer = new JoinedPlayer();

                    PlayerEntity playerEntity = new PlayerEntity();
                    SeasonEntity seasonEntity = new SeasonEntity();

                    playerEntity.setPlayerName(tempPlayerName);
                    playerEntity.setSeasonCode(1);
                    playerEntity.setPosition(k);
                    playerEntity.setPositionIcon(positionIcons2[k]);
                    playerEntity.setPogPoint(pogPoints[k]);

                    seasonEntity.setSeasonCode(15);
                    seasonEntity.setSeasonForShort("22 SPR");
                    seasonEntity.setSeasonName("22 SPRING");

                    tempPlayer.seasonEntity = seasonEntity;

                    tempPlayer.playerEntity = playerEntity;

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
