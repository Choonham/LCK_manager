package com.choonham.lck_manager.entity;

public class User {
    private long userCode;
    private String userId;
    private String userPhoneNum;
    private String userNickName;
    private int userFameLv;
    private long userMoney;
    private int seasonCode;
    private int matchNum;
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
