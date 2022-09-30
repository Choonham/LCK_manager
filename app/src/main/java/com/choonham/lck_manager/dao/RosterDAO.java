package com.choonham.lck_manager.dao;

import androidx.room.*;
import com.choonham.lck_manager.entity.RosterEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

@Dao
public interface RosterDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertRosterData(RosterEntity roster);

    @Query("SELECT * FROM roster r WHERE r.team_code = :teamCode AND r.main_entry = :mainEntry")
    Single<List<RosterEntity>> loadRosterListByTeamCode(int teamCode, int mainEntry);

    @Query("UPDATE roster SET main_entry = :entryCode WHERE  player_code = :playerCode AND team_code = :teamCode")
    Maybe<Integer> updateRosterData(int entryCode, int playerCode, int teamCode);

}
