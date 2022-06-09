package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "team")
public class TeamEntity {

    public TeamEntity(int teamCode, int userCode, int userType, String teamName, int mainRosterCode, int subRosterCode) {
        this.teamCode = teamCode;
        this.userCode = userCode;
        this.userType = userType;
        this.teamName = teamName;
        this.mainRosterCode = mainRosterCode;
        this.subRosterCode = subRosterCode;
    }

    @PrimaryKey
    @ColumnInfo(name = "team_code")
    private int teamCode;

    @ColumnInfo(name = "user_code")
    private int userCode;

    @ColumnInfo(name = "user_type")
    private int userType;

    @ColumnInfo(name = "team_name")
    private String teamName;

    @ColumnInfo(name = "main_roster_code")
    private int mainRosterCode;

    @ColumnInfo(name = "sub_roster_code")
    private int subRosterCode;

    public int getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(int teamCode) {
        this.teamCode = teamCode;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
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

    public int getMainRosterCode() {
        return mainRosterCode;
    }

    public void setMainRosterCode(int mainRosterCode) {
        this.mainRosterCode = mainRosterCode;
    }

    public int getSubRosterCode() {
        return subRosterCode;
    }

    public void setSubRosterCode(int subRosterCode) {
        this.subRosterCode = subRosterCode;
    }
}
