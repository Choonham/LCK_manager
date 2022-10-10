package com.choonham.lck_manager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.choonham.lck_manager.TransferWindow;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.entity.TransferWindowEntity;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.choonham.lck_manager.joinedEntity.JoinedTransferWindow;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

@Dao
public interface TransferWindowDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertTransferList(TransferWindowEntity transferWindowEntity);

    @Query("SELECT s.*, p.* FROM player p " +
            "INNER JOIN season s ON s.season_code = p.season_code " +
            "INNER JOIN roster r ON r.player_code = p.player_code " +
            "INNER JOIN team t ON t.api_team_code  = r.team_code " +
            "WHERE t.api_team_code NOT IN (:teamCode) AND s.season_code = :seasonCode ORDER BY random() LIMIT 10;")
    Single<List<JoinedPlayer>> loadPlayerListNotUserTeam(int teamCode, int seasonCode);

    @Query("SELECT t.*, p.*, s.* FROM transfer_window t INNER JOIN player p ON p.player_code = t.player_code INNER JOIN season s ON s.season_code = p.season_code WHERE weeks = :week")
    Single<List<JoinedTransferWindow>> loadTransferWindowByWeek(int week);
}
