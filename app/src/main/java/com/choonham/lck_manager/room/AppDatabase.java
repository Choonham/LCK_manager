package com.choonham.lck_manager.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
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

    private static AppDatabase database;

    private static String DATABASE_NAME = "database";

    public synchronized static AppDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract TestDAO textDao();
}