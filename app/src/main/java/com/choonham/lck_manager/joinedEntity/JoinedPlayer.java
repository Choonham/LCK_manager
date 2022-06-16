package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;

public class JoinedPlayer {
    @Embedded
    public PlayerEntity playerEntity;

    @Relation(
            parentColumn = "season_code",
            entityColumn = "season_code"
    )
    public SeasonEntity seasonEntity;
}
