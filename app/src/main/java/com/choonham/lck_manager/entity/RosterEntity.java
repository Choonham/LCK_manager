package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "roster")
public class RosterEntity implements Parcelable {

    public RosterEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "roster_code")
    private int rosterCode;

    @ColumnInfo(name = "api_roster_code")
    private int apiRosterCode;

    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "team_code")
    private int teamCode;

    @ColumnInfo(name = "main_entry")
    private int mainEntry;

    @ColumnInfo(name = "main_order")
    private int mainOrder;

    protected RosterEntity(Parcel in) {
        rosterCode = in.readInt();
        playerCode = in.readInt();
        mainOrder = in.readInt();
        teamCode = in.readInt();
        mainEntry = in.readInt();
        apiRosterCode = in.readInt();
    }

    public static final Creator<RosterEntity> CREATOR = new Creator<RosterEntity>() {
        @Override
        public RosterEntity createFromParcel(Parcel in) {
            return new RosterEntity(in);
        }

        @Override
        public RosterEntity[] newArray(int size) {
            return new RosterEntity[size];
        }
    };

    public int getRosterCode() {
        return rosterCode;
    }

    public void setRosterCode(int rosterCode) {
        this.rosterCode = rosterCode;
    }

    public int getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(int playerCode) {
        this.playerCode = playerCode;
    }

    public int getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(int mainOrder) {
        this.mainOrder = mainOrder;
    }

    public int getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(int teamCode) {
        this.teamCode = teamCode;
    }

    public int getMainEntry() {
        return mainEntry;
    }

    public void setMainEntry(int mainEntry) {
        this.mainEntry = mainEntry;
    }

    public int getApiRosterCode() {
        return apiRosterCode;
    }

    public void setApiRosterCode(int apiRosterCode) {
        this.apiRosterCode = apiRosterCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(rosterCode);
        parcel.writeInt(playerCode);
        parcel.writeInt(mainOrder);
        parcel.writeInt(teamCode);
        parcel.writeInt(mainEntry);
        parcel.writeInt(apiRosterCode);
    }
}
