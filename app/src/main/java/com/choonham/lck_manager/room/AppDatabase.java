package com.choonham.lck_manager.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.choonham.lck_manager.dao.*;
import com.choonham.lck_manager.entity.*;

@Database(entities = {
        ChampionEntity.class,
        ChampionCounterEntity.class,
        LeagueSeasonTeamEntity.class,
        LeagueScheduleEntity.class,
        RosterEntity.class,
        MatchDataEntity.class,
        PlayerChampionPlayDataEntity.class,
        PlayerEntity.class,
        PogPointRankEntity.class,
        TeamEntity.class,
        TransferWindowEntity.class,
        UserEntity.class,
        UserRecordEntity.class,
        NewsAndIssueEntity.class,
        NewsEffectsEntity.class,
        SeasonEntity.class
}, version = 8, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase database;

    private static String DATABASE_NAME = "lck";

    public synchronized static AppDatabase getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract TestDAO textDao();

    public abstract NewsAndIssueDAO newsAndIssueDAO();

    public abstract UserDAO userDAO();

    public abstract SeasonDAO seasonDAO();

    public abstract PlayerDAO playerDAO();

    public abstract TeamDAO teamDAO();

    public abstract RosterDAO rosterDAO();
}
