package com.choonham.lck_manager.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.choonham.lck_manager.entity.MatchData;
import com.choonham.lck_manager.entity.TeamRank;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

@Dao
public interface LeagueRankingDAO {
    @Query("SELECT tt.team_name, tt.win_rate, tt.api_team_code as team_code, tt.total_wins, tt.total_loses, " +
                    "(SELECT COUNT(*) + 1 FROM team WHERE (total_wins > tt.total_wins " +
                    "OR (total_wins = tt.total_wins AND total_loses < tt.total_loses)" +
                    "OR (total_wins = tt.total_wins AND total_loses = tt.total_loses AND api_team_code > tt.api_team_code)) AND season_code = :seasonCode) as rank FROM team tt " +
                    "WHERE tt.season_code = :seasonCode")
    Single<List<TeamRank>> loadLeagueRanking(int seasonCode);
}
