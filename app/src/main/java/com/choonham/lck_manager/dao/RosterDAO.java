package com.choonham.lck_manager.dao;

import androidx.room.*;
import com.choonham.lck_manager.entity.RosterEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
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

    @Query("SELECT p.*, s.* FROM player p " +
            "INNER JOIN season s ON p.season_code = s.season_code " +
            "INNER JOIN roster r ON r.player_code = p.player_code " +
            "INNER JOIN team t ON t.api_team_code = r.team_code " +
            "WHERE t.api_team_code = :teamCode  AND r.main_entry = :mainEntry")
    Single<List<JoinedPlayer>> loadPlayerListByTeamCode(int teamCode, int mainEntry);

}
