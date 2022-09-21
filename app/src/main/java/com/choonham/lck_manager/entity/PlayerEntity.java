package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(tableName = "player")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerEntity implements Parcelable {

    public PlayerEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "season_code")
    private int seasonCode;

    @ColumnInfo(name = "player_name")
    private String playerName;

    private int position;
    private double physical;

    @ColumnInfo(name = "team_fight")
    private double teamFight;

    @ColumnInfo(name = "out_smart")
    private double outSmart;

    @ColumnInfo(name = "lane_strength")
    private double laneStrength;

    private double stability;

    @ColumnInfo(name = "fame_lv")
    private int fameLv;

    @Ignore
    private int positionIcon;

    @Ignore
    private int pogPoint;

    protected PlayerEntity(Parcel in) {
        playerCode = in.readInt();
        seasonCode = in.readInt();
        playerName = in.readString();
        position = in.readInt();
        physical = in.readDouble();
        teamFight = in.readDouble();
        outSmart = in.readDouble();
        laneStrength = in.readDouble();
        stability = in.readDouble();
        fameLv = in.readInt();
        positionIcon = in.readInt();
        pogPoint = in.readInt();
    }

    public static final Creator<PlayerEntity> CREATOR = new Creator<PlayerEntity>() {
        @Override
        public PlayerEntity createFromParcel(Parcel in) {
            return new PlayerEntity(in);
        }

        @Override
        public PlayerEntity[] newArray(int size) {
            return new PlayerEntity[size];
        }
    };

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(int playerCode) {
        this.playerCode = playerCode;
    }

    public int getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public double getPhysical() {
        return physical;
    }

    public void setPhysical(double physical) {
        this.physical = physical;
    }

    public double getTeamFight() {
        return teamFight;
    }

    public void setTeamFight(double teamFight) {
        this.teamFight = teamFight;
    }

    public double getOutSmart() {
        return outSmart;
    }

    public void setOutSmart(double outSmart) {
        this.outSmart = outSmart;
    }

    public double getLaneStrength() {
        return laneStrength;
    }

    public void setLaneStrength(double laneStrength) {
        this.laneStrength = laneStrength;
    }

    public double getStability() {
        return stability;
    }

    public void setStability(double stability) {
        this.stability = stability;
    }

    public int getFameLv() {
        return fameLv;
    }

    public void setFameLv(int fameLv) {
        this.fameLv = fameLv;
    }

    public int getPositionIcon() {
        return positionIcon;
    }

    public void setPositionIcon(int positionIcon) {
        this.positionIcon = positionIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getPogPoint() {
        return pogPoint;
    }

    public void setPogPoint(int pogPoint) {
        this.pogPoint = pogPoint;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(playerCode);
        parcel.writeInt(seasonCode);
        parcel.writeString(playerName);
        parcel.writeInt(position);
        parcel.writeDouble(physical);
        parcel.writeDouble(teamFight);
        parcel.writeDouble(outSmart);
        parcel.writeDouble(laneStrength);
        parcel.writeDouble(stability);
        parcel.writeInt(fameLv);
        parcel.writeInt(positionIcon);
        parcel.writeInt(pogPoint);
    }
}
