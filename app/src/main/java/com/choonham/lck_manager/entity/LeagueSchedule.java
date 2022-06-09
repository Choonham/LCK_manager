package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity
public class LeagueSchedule {

    public LeagueSchedule(long leagueScheduleCode, Date date, int matchNum, long teamCodeA, long teamCodeB, String teamA, String teamB, int scoreA, int scoreB, int playFlag) {
        this.leagueScheduleCode = leagueScheduleCode;
        this.date = date;
        this.matchNum = matchNum;
        this.teamCodeA = teamCodeA;
        this.teamCodeB = teamCodeB;
        this.teamA = teamA;
        this.teamB = teamB;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.playFlag = playFlag;
    }

    @PrimaryKey
    @ColumnInfo(name = "league_schedule_code")
    private long leagueScheduleCode;
    private Date date;

    @ColumnInfo(name = "match_num")
    private int matchNum;

    @ColumnInfo(name = "team_code_a")
    private long teamCodeA;

    @ColumnInfo(name = "team_code_b")
    private long teamCodeB;

    @ColumnInfo(name = "team_a")
    private String teamA;

    @ColumnInfo(name = "team_b")
    private String teamB;

    @ColumnInfo(name = "score_a")
    private int scoreA = 0;

    @ColumnInfo(name = "score_b")
    private int scoreB = 0;

    @ColumnInfo(name = "play_flag")
    private int playFlag;

    public long getLeagueScheduleCode() {
        return leagueScheduleCode;
    }

    public void setLeagueScheduleCode(long leagueScheduleCode) {
        this.leagueScheduleCode = leagueScheduleCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public int getScoreA() {
        return scoreA;
    }

    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public void setScoreB(int scoreB) {
        this.scoreB = scoreB;
    }

    public long getTeamCodeA() {
        return teamCodeA;
    }

    public void setTeamCodeA(long teamCodeA) {
        this.teamCodeA = teamCodeA;
    }

    public long getTeamCodeB() {
        return teamCodeB;
    }

    public void setTeamCodeB(long teamCodeB) {
        this.teamCodeB = teamCodeB;
    }

    public int getPlayFlag() {
        return playFlag;
    }

    public void setPlayFlag(int playFlag) {
        this.playFlag = playFlag;
    }
}
