package com.choonham.lck_manager.dao;

import androidx.room.*;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PlayerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertPlayerEntity(PlayerEntity playerEntity);

    /*@Query("SELECT * FROM player s WHERE s.player_code = :playerCode")
    Single<PlayerEntity> loadPlayerEntityByCode(int playerCode);*/

    @Query("SELECT s.*, p.* FROM player p INNER JOIN season s ON s.season_code = p.season_code WHERE p.player_code = :playerCode")
    Single<JoinedPlayer> loadPlayerEntityByCode(int playerCode);
}
