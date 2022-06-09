package com.choonham.lck_manager.entity;

import androidx.room.*;
import com.choonham.lck_manager.room.DateConverter;

import java.util.Date;
@Entity(tableName = "league_schedule")
public class LeagueScheduleEntity {

    public LeagueScheduleEntity(int leagueScheduleCode, Date date, int matchNum, int teamCodeA, int teamCodeB, String teamA, String teamB, int scoreA, int scoreB, int playFlag) {
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
    private int leagueScheduleCode;

    @ColumnInfo(name = "match_date")
    @TypeConverters({DateConverter.class})
    private Date date;

    @ColumnInfo(name = "match_num")
    private int matchNum;

    @ColumnInfo(name = "team_code_a")
    private int teamCodeA;

    @ColumnInfo(name = "team_code_b")
    private int teamCodeB;

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

    public int getLeagueScheduleCode() {
        return leagueScheduleCode;
    }

    public void setLeagueScheduleCode(int leagueScheduleCode) {
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

    public int getTeamCodeA() {
        return teamCodeA;
    }

    public void setTeamCodeA(int teamCodeA) {
        this.teamCodeA = teamCodeA;
    }

    public int getTeamCodeB() {
        return teamCodeB;
    }

    public void setTeamCodeB(int teamCodeB) {
        this.teamCodeB = teamCodeB;
    }

    public int getPlayFlag() {
        return playFlag;
    }

    public void setPlayFlag(int playFlag) {
        this.playFlag = playFlag;
    }
}
