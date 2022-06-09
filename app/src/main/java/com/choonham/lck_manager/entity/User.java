package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    public User(long userCode, String userId, String userPhoneNum, String userNickName, int userFameLv, long userMoney, int seasonCode, int matchNum, int apiVer) {
        this.userCode = userCode;
        this.userId = userId;
        this.userPhoneNum = userPhoneNum;
        this.userNickName = userNickName;
        this.userFameLv = userFameLv;
        this.userMoney = userMoney;
        this.seasonCode = seasonCode;
        this.matchNum = matchNum;
        this.apiVer = apiVer;
    }

    @PrimaryKey
    @ColumnInfo(name = "user_code")
    private long userCode;

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo(name = "user_phone_num")
    private String userPhoneNum;

    @ColumnInfo(name = "user_nick_name")
    private String userNickName;

    @ColumnInfo(name = "user_fame_lv")
    private int userFameLv;

    @ColumnInfo(name = "user_money")
    private long userMoney;

    @ColumnInfo(name = "season_code")
    private int seasonCode;

    @ColumnInfo(name = "match_num")
    private int matchNum;

    @ColumnInfo(name = "apier")
    private int apiVer;

    public long getUserCode() {
        return userCode;
    }

    public void setUserCode(long userCode) {
        this.userCode = userCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
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

    public long getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(long userMoney) {
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
