package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "match_data")
public class MatchDataEntity {

    public MatchDataEntity(int matchDataCode, int leagueScheduleCode, int teamCode, int playerCode, int championCode, double kda) {
        this.matchDataCode = matchDataCode;
        this.leagueScheduleCode = leagueScheduleCode;
        this.teamCode = teamCode;
        this.playerCode = playerCode;
        this.championCode = championCode;
        this.kda = kda;
    }

    @PrimaryKey
    @ColumnInfo(name = "match_data_code")
    private int matchDataCode;

    @ColumnInfo(name = "league_schedule_code")
    private int leagueScheduleCode;

    @ColumnInfo(name = "team_code")
    private int teamCode;

    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "champion_code")
    private int championCode;

    private double kda;

    public int getMatchDataCode() {
        return matchDataCode;
    }

    public void setMatchDataCode(int matchDataCode) {
        this.matchDataCode = matchDataCode;
    }

    public int getLeagueScheduleCode() {
        return leagueScheduleCode;
    }

    public void setLeagueScheduleCode(int leagueScheduleCode) {
        this.leagueScheduleCode = leagueScheduleCode;
    }

    public int getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(int teamCode) {
        this.teamCode = teamCode;
    }

    public int getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(int playerCode) {
        this.playerCode = playerCode;
    }

    public int getChampionCode() {
        return championCode;
    }

    public void setChampionCode(int championCode) {
        this.championCode = championCode;
    }

    public double getKda() {
        return kda;
    }

    public void setKda(double kda) {
        this.kda = kda;
    }
}
