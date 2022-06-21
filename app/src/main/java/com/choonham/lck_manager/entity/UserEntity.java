package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_code")
    private int userCode;

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo(name = "user_email")
    private String userEmail;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "user_nick_name")
    private String userNickName;

    @ColumnInfo(name = "user_fame_lv")
    private int userFameLv;

    @ColumnInfo(name = "user_money")
    private int userMoney;

    @ColumnInfo(name = "season_code")
    private int seasonCode;

    @ColumnInfo(name = "match_num")
    private int matchNum;

    @ColumnInfo(name = "apier")
    private int apiVer;

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public int getUserFameLv() {
        return userFameLv;
    }

    public void setUserFameLv(int userFameLv) {
        this.userFameLv = userFameLv;
    }

    public int getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(int userMoney) {
        this.userMoney = userMoney;
    }

    public int getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
    }

    public int getApiVer() {
        return apiVer;
    }

    public void setApiVer(int apiVer) {
        this.apiVer = apiVer;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }
}
