package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.entity.UserEntity;
import com.choonham.lck_manager.entity.UserRecordEntity;

public class JoinedUserRecord {
    @Embedded
    public UserRecordEntity userRecordEntity;

    @Relation(
            parentColumn = "user_code",
            entityColumn = "user_code"
    )
    public UserEntity userEntity;

    @Relation(
            parentColumn = "season_code",
            entityColumn = "season_code"
    )
    public SeasonEntity seasonEntity;
}
