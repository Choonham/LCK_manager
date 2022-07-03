package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "team")
public class TeamEntity implements Parcelable {

    public TeamEntity() {
    }

    @PrimaryKey(autoGenerate = true)
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

    protected TeamEntity(Parcel in) {
        teamCode = in.readInt();
        userCode = in.readInt();
        userType = in.readInt();
        teamName = in.readString();
        mainRosterCode = in.readInt();
        subRosterCode = in.readInt();
    }

    public static final Creator<TeamEntity> CREATOR = new Creator<TeamEntity>() {
        @Override
        public TeamEntity createFromParcel(Parcel in) {
            return new TeamEntity(in);
        }

        @Override
        public TeamEntity[] newArray(int size) {
            return new TeamEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(teamCode);
        parcel.writeInt(userCode);
        parcel.writeInt(userType);
        parcel.writeString(teamName);
        parcel.writeInt(mainRosterCode);
        parcel.writeInt(subRosterCode);
    }
}
