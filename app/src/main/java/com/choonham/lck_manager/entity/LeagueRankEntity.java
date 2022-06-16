package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "league_rank")
public class LeagueRankEntity {
    @PrimaryKey
    @ColumnInfo(name="league_rank_code")
    private int leagueRankCode;

    private int rank;

    @ColumnInfo(name="team_code")
    private int teamCode;

    private int win;

    private int lose;

    private int wd;

    public int getLeagueRankCode() {
        return leagueRankCode;
    }

    public void setLeagueRankCode(int leagueRankCode) {
        this.leagueRankCode = leagueRankCode;
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
