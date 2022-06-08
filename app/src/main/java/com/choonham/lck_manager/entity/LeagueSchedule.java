package com.choonham.lck_manager.entity;

import java.util.Date;

public class LeagueSchedule {
    private Date date;
    private int matchNum;
    private long teamCodeA;
    private long teamCodeB;
    private String teamA;
    private String teamB;
    private int scoreA = 0;
    private int scoreB = 0;
    private int playFlag;

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
