package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.ChampionEntity;
import com.choonham.lck_manager.entity.PlayerChampionPlayDataEntity;
import com.choonham.lck_manager.entity.PlayerEntity;

public class JoinedPlayerChampionData {
    @Embedded
    public PlayerChampionPlayDataEntity playerChampionPlayDataEntity;

    @Relation(
            parentColumn = "player_code",
            entityColumn = "player_code"
    )
    public PlayerEntity playerEntity;

    @Relation(
            parentColumn = "champion_code",
            entityColumn = "champion_code"
    )
    public ChampionEntity championEntity;
}
