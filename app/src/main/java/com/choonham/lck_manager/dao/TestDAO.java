package com.choonham.lck_manager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.choonham.lck_manager.entity.LeagueRankEntity;

import java.util.List;
@Dao
public interface TestDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(LeagueRankEntity leagueRankEntity);

    @Query("SELECT * FROM league_rank")
    public List<LeagueRankEntity> loadAllLeagueRank();
}
