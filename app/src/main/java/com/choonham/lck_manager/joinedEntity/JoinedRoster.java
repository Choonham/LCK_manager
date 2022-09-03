package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.RosterEntity;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.TeamEntity;

public class JoinedRoster {
    @Embedded
    public RosterEntity rosterEntity;

    @Relation(
            parentColumn = "player_code",
            entityColumn = "player_code"
    )
    public PlayerEntity playerEntity;

    @Relation(
            parentColumn = "team_code",
            entityColumn = "team_code"
    )
    public TeamEntity teamEntity;
}
