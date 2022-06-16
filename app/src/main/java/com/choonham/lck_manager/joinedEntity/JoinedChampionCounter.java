package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.ChampionCounterEntity;
import com.choonham.lck_manager.entity.ChampionEntity;

import java.util.List;

public class JoinedChampionCounter {
    @Embedded
    public ChampionEntity championEntity;

    @Relation(
            parentColumn = "champion_code",
            entityColumn = "champion_code"
    )
    public List<ChampionCounterEntity> championCounterEntities;

}
