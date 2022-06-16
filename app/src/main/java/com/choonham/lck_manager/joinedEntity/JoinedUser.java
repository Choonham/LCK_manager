package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.entity.UserEntity;
import com.google.android.material.badge.ExperimentalBadgeUtils;

public class JoinedUser {
    @Embedded
    public UserEntity userEntity;

    @Relation(
            parentColumn = "season_code",
            entityColumn = "season_code"
    )
    public SeasonEntity seasonEntity;
}
