package com.choonham.lck_manager.entity;

public class Team {
    private long teamCode;
    private long userCode;
    private int userType;
    private String teamName;
    private long mainRosterCode;
    private long subRosterCode;

    public long getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(long teamCode) {
        this.teamCode = teamCode;
    }

    public long getUserCode() {
        return userCode;
    }

    public void setUserCode(long userCode) {
        this.userCode = userCode;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getMainRosterCode() {
        return mainRosterCode;
    }

    public void setMainRosterCode(long mainRosterCode) {
        this.mainRosterCode = mainRosterCode;
    }

    public long getSubRosterCode() {
        return subRosterCode;
    }

    public void setSubRosterCode(long subRosterCode) {
        this.subRosterCode = subRosterCode;
    }
}
