package com.choonham.lck_manager;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;

import java.util.List;

public class LeagueRanking extends Fragment {
    private final ActivityTagEnum TAG = ActivityTagEnum.LEAGUE_RANKING;

    private List<JoinedPlayer> pogPlayerList;

    String[] teamList = {"T1", "DRX", "DK", "BRO", "Gen", "KDF", "NS", "LSB", "KT", "HLE", "KMH"};
    int[] winList = {4, 4, 3, 3, 2, 2, 1, 1, 1, 0, 0};

    String[] pogList = {"Doran", "Faker", "Chovy", "Keria", "DoryDory"};
    int[] positionIcons = {
        R.drawable.position_top_icon,
        R.drawable.position_mid_icon,
        R.drawable.position_mid_icon,
        R.drawable.position_support_icon,
        R.drawable.position_support_icon
    };

    int[] pogPointList = {400, 400, 300, 200, 100};

    ListView leagueRankingListView;
    ListView pogPointListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.league_ranking, container, false);

        leagueRankingListView = view.findViewById(R.id.league_ranking_list_view);
        pogPointListView = view.findViewById(R.id.pog_point_list_view);

        Common common = Common.getInstance();
        pogPlayerList = common.getTempPlayerList(2);

        LeagueRankingAdapter leagueRankingAdapter = new LeagueRankingAdapter(teamList, winList, getContext());
        POGPointAdapter pogPointAdapter = new POGPointAdapter(getContext(), pogPlayerList);

        leagueRankingListView.setAdapter(leagueRankingAdapter);
        pogPointListView.setAdapter(pogPointAdapter);

        leagueRankingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                Intent intent = new Intent(getContext(), TeamInfoPopUpActivity.class);
                TextView teamName = selectedView.findViewById(R.id.team_name_league_ranking);
                TextView teamRank = selectedView.findViewById(R.id.team_rank_league_ranking);
                intent.putExtra("teamName", teamName.getText());
                intent.putExtra("teamRank", teamRank.getText());

                startActivity(intent);
            }
        });

        pogPointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                Intent intent = new Intent(getContext(), PlayerInfoPopUpActivity.class);

                TextView season = selectedView.findViewById(R.id.player_season_pog_point);
                TextView name = selectedView.findViewById(R.id.player_name_pog_point);
                ImageView positionIcon = selectedView.findViewById(R.id.pog_point_position_icon);
                int drawableRef = (int) positionIcon.getTag();

                intent.putExtra("playerSeason", season.getText());
                intent.putExtra("playerName", name.getText());
                intent.putExtra("positionIcon", drawableRef);

                intent.putExtra("playerAvg", "112.5");
                intent.putExtra("playerStability", "112.5");

                intent.putExtra("playerEntity", pogPlayerList.get(i).playerEntity);

                intent.putExtra("tag", TAG);

                /*
                Common common = Common.getInstance();
                Intent intent = common.getPlayerInfoPopUpIntent(selectedView, TAG, getContext(), 0);
                */
                startActivity(intent);
            }
        });


        return view;
    }

}
