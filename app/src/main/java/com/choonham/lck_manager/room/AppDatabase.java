package com.choonham.lck_manager.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.choonham.lck_manager.dao.TestDAO;
import com.choonham.lck_manager.entity.*;

@Database(entities = {
        ChampionEntity.class,
        ChampionCounterEntity.class,
        LeagueRankEntity.class,
        LeagueScheduleEntity.class,
        MainRosterEntity.class,
        MatchDataEntity.class,
        PlayerChampionPlayDataEntity.class,
        PlayerEntity.class,
        PogPointRankEntity.class,
        SubRosterEntity.class,
        TeamEntity.class,
        TransferWindowEntity.class,
        UserEntity.class,
        UserRecordEntity.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TestDAO textDao();
}
