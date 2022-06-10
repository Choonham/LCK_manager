package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_record")
public class UserRecordEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_record_code")
    private int userRecordCode;

    @ColumnInfo(name = "user_code")
    private int userCode;

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
    private int topPlayerCode;

    @ColumnInfo(name = "jungle_player_code")
    private int junglePlayerCode;

    @ColumnInfo(name = "mid_player_code")
    private int midPlayerCode;

    @ColumnInfo(name = "ad_player_code")
    private int adPlayerCode;

    @ColumnInfo(name = "support_player_code")
    private int supportPlayerCode;

    public int getUserRecordCode() {
        return userRecordCode;
    }

    public void setUserRecordCode(int userRecordCode) {
        this.userRecordCode = userRecordCode;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
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

    public int getTopPlayerCode() {
        return topPlayerCode;
    }

    public void setTopPlayerCode(int topPlayerCode) {
        this.topPlayerCode = topPlayerCode;
    }

    public int getJunglePlayerCode() {
        return junglePlayerCode;
    }

    public void setJunglePlayerCode(int junglePlayerCode) {
        this.junglePlayerCode = junglePlayerCode;
    }

    public int getMidPlayerCode() {
        return midPlayerCode;
    }

    public void setMidPlayerCode(int midPlayerCode) {
        this.midPlayerCode = midPlayerCode;
    }

    public int getAdPlayerCode() {
        return adPlayerCode;
    }

    public void setAdPlayerCode(int adPlayerCode) {
        this.adPlayerCode = adPlayerCode;
    }

    public int getSupportPlayerCode() {
        return supportPlayerCode;
    }

    public void setSupportPlayerCode(int supportPlayerCode) {
        this.supportPlayerCode = supportPlayerCode;
    }
}
