package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "league_season_team")
public class LeagueSeasonTeamEntity {
    @PrimaryKey
    @ColumnInfo(name="league_season_team_code")
    private int leagueSeasonTeamCode;

    private int rank;

    @ColumnInfo(name="season_code")
    private int seasonCode;

    @ColumnInfo(name="season_id")
    private String seasonId;

    @ColumnInfo(name="team_code")
    private int teamCode;

    private int win;

    private int lose;

    private int wd;

    public int getLeagueSeasonTeamCode() {
        return leagueSeasonTeamCode;
    }

    public void setLeagueSeasonTeamCode(int leagueSeasonTeamCode) {
        this.leagueSeasonTeamCode = leagueSeasonTeamCode;
    }

    public int getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(int teamCode) {
        this.teamCode = teamCode;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getWd() {
        return wd;
    }

    public void setWd(int wd) {
        this.wd = wd;
    }
}
