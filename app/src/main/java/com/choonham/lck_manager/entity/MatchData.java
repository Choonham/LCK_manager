package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MatchData {

    public MatchData(long matchDataCode, long leagueScheduleCode, long teamCode, long playerCode, long championCode, double kda) {
        this.matchDataCode = matchDataCode;
        this.leagueScheduleCode = leagueScheduleCode;
        this.teamCode = teamCode;
        this.playerCode = playerCode;
        this.championCode = championCode;
        this.kda = kda;
    }

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
