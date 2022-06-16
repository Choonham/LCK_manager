package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.MainRosterAdapter;
import com.choonham.lck_manager.entity.MainRosterEntity;
import com.choonham.lck_manager.entity.PlayerEntity;

public class JoinedMainRoster {
    @Embedded
    public MainRosterEntity mainRosterEntity;

    @Relation(
            parentColumn = "player_code",
            entityColumn = "player_code"
    )
    public PlayerEntity playerEntity;
}
