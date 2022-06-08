package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserRecord {
    @PrimaryKey
    @ColumnInfo(name = "user_record_code")
    private long userRecordCode;

    @ColumnInfo(name = "user_code")
    private long userCode;

    @ColumnInfo(name = "season_code")
    private int seasonCode;

    private int rank;

    private int win;

    private int lose;

    @ColumnInfo(name = "win_diff")
    private int winDiff;

    @ColumnInfo(name = "fame_diff")
    private int fameDiff;

    @ColumnInfo(name = "top_player_code")
    private long topPlayerCode;

    @ColumnInfo(name = "jungle_player_code")
    private long junglePlayerCode;

    @ColumnInfo(name = "mid_player_code")
    private long midPlayerCode;

    @ColumnInfo(name = "ad_player_code")
    private long adPlayerCode;

    @ColumnInfo(name = "support_player_code")
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
