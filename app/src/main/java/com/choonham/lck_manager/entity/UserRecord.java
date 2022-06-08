package com.choonham.lck_manager.entity;

public class UserRecord {
    private long userRecordCode;
    private long userCode;
    private int seasonCode;
    private int rank;
    private int win;
    private int lose;
    private int winDiff;
    private int fameDiff;
    private long topPlayerCode;
    private long junglePlayerCode;
    private long midPlayerCode;
    private long adPlayerCode;
    private long supportPlayerCode;

    public long getUserRecordCode() {
        return userRecordCode;
    }

    public void setUserRecordCode(long userRecordCode) {
        this.userRecordCode = userRecordCode;
    }

    public long getUserCode() {
        return userCode;
    }

    public void setUserCode(long userCode) {
        this.userCode = userCode;
    }

    public int getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public int getWinDiff() {
        return winDiff;
    }

    public void setWinDiff(int winDiff) {
        this.winDiff = winDiff;
    }

    public int getFameDiff() {
        return fameDiff;
    }

    public void setFameDiff(int fameDiff) {
        this.fameDiff = fameDiff;
    }

    public long getTopPlayerCode() {
        return topPlayerCode;
    }

    public void setTopPlayerCode(long topPlayerCode) {
        this.topPlayerCode = topPlayerCode;
    }

    public long getJunglePlayerCode() {
        return junglePlayerCode;
    }

    public void setJunglePlayerCode(long junglePlayerCode) {
        this.junglePlayerCode = junglePlayerCode;
    }

    public long getMidPlayerCode() {
        return midPlayerCode;
    }

    public void setMidPlayerCode(long midPlayerCode) {
        this.midPlayerCode = midPlayerCode;
    }

    public long getAdPlayerCode() {
        return adPlayerCode;
    }

    public void setAdPlayerCode(long adPlayerCode) {
        this.adPlayerCode = adPlayerCode;
    }

    public long getSupportPlayerCode() {
        return supportPlayerCode;
    }

    public void setSupportPlayerCode(long supportPlayerCode) {
        this.supportPlayerCode = supportPlayerCode;
    }
}
