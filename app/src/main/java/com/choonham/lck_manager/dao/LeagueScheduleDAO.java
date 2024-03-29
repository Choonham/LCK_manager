package com.choonham.lck_manager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.choonham.lck_manager.entity.LeagueScheduleEntity;
import com.choonham.lck_manager.entity.MatchData;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

@Dao
public interface LeagueScheduleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertLeagueSchedule(LeagueScheduleEntity leagueScheduleEntity);

    @Query("SELECT * FROM league_schedule ORDER BY match_num")
    LiveData<List<LeagueScheduleEntity>> loadLeagueSchedule();

    @Query("SELECT " +
            "ls.match_num, " +
            "CASE WHEN ls.team_code_a = :teamCode THEN ls.team_code_b WHEN ls.team_code_b = :teamCode THEN ls.team_code_a ELSE '' END AS against_team, " +
            "CASE WHEN ls.team_code_a = :teamCode THEN ls.team_b WHEN ls.team_code_b = :teamCode THEN ls.team_a ELSE '' END AS against_team_name, " +
            "CASE WHEN ls.team_code_a = :teamCode THEN (SELECT t.rank FROM (SELECT tt.api_team_code as team_code, " +
            "(SELECT COUNT(*) + 1 FROM team WHERE (total_wins > tt.total_wins " +
            "OR (total_wins = tt.total_wins AND total_loses < tt.total_loses)" +
            "OR (total_wins = tt.total_wins AND total_loses = tt.total_loses AND api_team_code > tt.api_team_code)) AND season_code = :seasonCode) as rank FROM team tt " +
            "WHERE tt.season_code = :seasonCode) t WHERE t.team_code = team_code_b) " +
            "WHEN ls.team_code_b = :teamCode THEN (SELECT ta.rank FROM (SELECT tt.api_team_code as team_code," +
            "(SELECT COUNT(*) + 1 FROM team WHERE (total_wins > tt.total_wins " +
            "OR (total_wins = tt.total_wins AND total_loses < tt.total_loses)" +
            "OR (total_wins = tt.total_wins AND total_loses = tt.total_loses AND api_team_code > tt.api_team_code)) AND season_code = :seasonCode) as rank FROM team tt " +
            "WHERE tt.season_code = :seasonCode) ta WHERE ta.team_code = team_code_a) " +
            "ELSE '1' " +
            "END AS team_rank," +
            "ls.play_flag," +
            "ls.curr_match " +
            "FROM league_schedule ls " +
            "WHERE (ls.team_code_a = :teamCode OR ls.team_code_b = :teamCode) " +
            "ORDER BY ls.match_num;")
    LiveData<List<MatchData>> loadScheduleAgainstTeam(int seasonCode, int teamCode);
}
