package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MatchData {
    @PrimaryKey
    @ColumnInfo(name = "match_data_code")
    private long matchDataCode;

    @ColumnInfo(name = "league_schedule_code")
    private long leagueScheduleCode;

    @ColumnInfo(name = "team_code")
    private long teamCode;

    @ColumnInfo(name = "player_code")
    private long playerCode;

    @ColumnInfo(name = "champion_code")
    private long championCode;

    private double kda;
}
