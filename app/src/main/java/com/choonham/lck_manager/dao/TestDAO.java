package com.choonham.lck_manager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.choonham.lck_manager.entity.LeagueSeasonTeamEntity;

import java.util.List;
@Dao
public interface TestDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertLeagueRankEntity(LeagueSeasonTeamEntity leagueSeasonTeamEntity);

    @Query("SELECT * FROM league_season_team")
    public List<LeagueSeasonTeamEntity> loadAllLeagueRankEntity();
}
