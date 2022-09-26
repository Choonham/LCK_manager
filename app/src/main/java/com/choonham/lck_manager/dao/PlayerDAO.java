package com.choonham.lck_manager.dao;

import androidx.room.*;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PlayerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertPlayerEntity(PlayerEntity playerEntity);

    @Query("SELECT * FROM player s WHERE s.player_code = :playerCode")
    Single<PlayerEntity> loadPlayerEntityByCode(Long playerCode);
}
