package com.choonham.lck_manager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.entity.UserEntity;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface SeasonDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertSeasonData(SeasonEntity seasonEntity);

    @Query("SELECT * FROM season s WHERE s.season_code = :seasonCode")
    LiveData<SeasonEntity> loadSeasonEntityByCode(Long seasonCode);
}
