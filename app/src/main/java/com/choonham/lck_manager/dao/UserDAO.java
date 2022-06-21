package com.choonham.lck_manager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.choonham.lck_manager.entity.UserEntity;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertUserEntity(UserEntity userEntity);

    @Query("SELECT * FROM user u WHERE u.user_id = :userID")
    Single<UserEntity> loadUserEntityById(String userID);

}
