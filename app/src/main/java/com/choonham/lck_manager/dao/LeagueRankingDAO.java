package com.choonham.lck_manager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.choonham.lck_manager.LeagueRanking;
import com.choonham.lck_manager.entity.LeagueRankingEntity;
import com.choonham.lck_manager.entity.MatchData;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.TeamRank;
import com.choonham.lck_manager.joinedEntity.JoinedLeagueRanking;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

@Dao
public interface LeagueRankingDAO {
    @Query("SELECT a.* FROM (SELECT tt.team_name, tt.win_rate, tt.api_team_code as team_code, tt.total_wins, tt.total_loses, " +
                    "(SELECT COUNT(*) + 1 FROM team WHERE (total_wins > tt.total_wins " +
                    "OR (total_wins = tt.total_wins AND total_loses < tt.total_loses)" +
                    "OR (total_wins = tt.total_wins AND total_loses = tt.total_loses AND api_team_code > tt.api_team_code)) AND season_code = :seasonCode) as rank FROM team tt " +
                    "WHERE tt.season_code = :seasonCode ) a " +
                    "ORDER BY a.rank ")
    Single<List<TeamRank>> loadLeagueRankingList(int seasonCode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertLeagueRanking(LeagueRankingEntity LeagueRankingEntity);

    @Query("SELECT r.*, t.* FROM  league_ranking r INNER JOIN team t ON t.api_team_code = r.team_code ORDER BY r.rank")
    Single<List<JoinedLeagueRanking>> loadLeagueRanking();

    @Query("SELECT r.*, t.* FROM  league_ranking r INNER JOIN team t ON t.api_team_code = r.team_code WHERE team_code = :teamCode ORDER BY r.rank")
    Single<JoinedLeagueRanking> loadTeamRanking(int teamCode);

}
