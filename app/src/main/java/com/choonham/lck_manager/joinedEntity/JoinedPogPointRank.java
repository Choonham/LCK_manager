package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.PlayerChampionPlayDataEntity;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.PogPointRankEntity;

public class JoinedPogPointRank {
    @Embedded
    public PogPointRankEntity pogPointRankEntity;

    @Relation(
            parentColumn = "player_code",
            entityColumn = "player_code"
    )
    public PlayerEntity playerEntity;
}
