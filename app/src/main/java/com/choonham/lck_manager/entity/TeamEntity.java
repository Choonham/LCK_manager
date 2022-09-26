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

    @ColumnInfo(name = "api_team_code")
    private int apiTeamCode;

    @ColumnInfo(name = "user_code")
    private int userCode;

    @ColumnInfo(name = "user_type")
    private int userType;

    @ColumnInfo(name = "team_name")
    private String teamName;

    @ColumnInfo(name = "season_code")
    private int seasonCode;

    @ColumnInfo(name = "win_rate", defaultValue = "0")
    private double winRate;

    @ColumnInfo(name = "total_wins", defaultValue = "0")
    private int totalWins;

    @ColumnInfo(name = "total_loses", defaultValue = "0")
    private int totalLoses;

    protected TeamEntity(Parcel in) {
        teamCode = in.readInt();
        userCode = in.readInt();
        userType = in.readInt();
        teamName = in.readString();
        seasonCode = in.readInt();
        winRate = in.readDouble();
        totalWins = in.readInt();
        totalLoses = in.readInt();
        apiTeamCode = in.readInt();
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

    public int getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getTotalLoses() {
        return totalLoses;
    }

    public void setTotalLoses(int totalLoses) {
        this.totalLoses = totalLoses;
    }

    public int getApiTeamCode() {
        return apiTeamCode;
    }

    public void setApiTeamCode(int apiTeamCode) {
        this.apiTeamCode = apiTeamCode;
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
        parcel.writeInt(seasonCode);
        parcel.writeDouble(winRate);
        parcel.writeInt(totalWins);
        parcel.writeInt(totalLoses);
        parcel.writeInt(apiTeamCode);
    }
}
