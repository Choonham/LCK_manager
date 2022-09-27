package com.choonham.lck_manager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.choonham.lck_manager.entity.ChampionEntity;
import com.choonham.lck_manager.entity.PlayerEntity;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ChampionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertChampionEntity(ChampionEntity championEntity);

    @Query("SELECT * FROM champion c WHERE c.champion_code = :championCode")
    Single<ChampionEntity> loadChampionEntityByCode(Long championCode);
}
