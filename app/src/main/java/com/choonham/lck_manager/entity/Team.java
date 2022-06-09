package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Team {

    public Team(long teamCode, long userCode, int userType, String teamName, long mainRosterCode, long subRosterCode) {
        this.teamCode = teamCode;
        this.userCode = userCode;
        this.userType = userType;
        this.teamName = teamName;
        this.mainRosterCode = mainRosterCode;
        this.subRosterCode = subRosterCode;
    }

    @PrimaryKey
    @ColumnInfo(name = "team_code")
    private long teamCode;

    @ColumnInfo(name = "user_code")
    private long userCode;

    @ColumnInfo(name = "user_type")
    private int userType;

    @ColumnInfo(name = "team_name")
    private String teamName;

    @ColumnInfo(name = "main_roster_code")
    private long mainRosterCode;

    @ColumnInfo(name = "sub_roster_code")
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
