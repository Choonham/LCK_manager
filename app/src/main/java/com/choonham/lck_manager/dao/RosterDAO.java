package com.choonham.lck_manager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
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

}
