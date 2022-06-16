package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SubRosterEntity;

public class JoinedSubRoster {
    @Embedded
    public SubRosterEntity subRosterEntity;

    @Relation(
            parentColumn = "player_code",
            entityColumn = "player_code"
    )
    public PlayerEntity playerEntity;
}
