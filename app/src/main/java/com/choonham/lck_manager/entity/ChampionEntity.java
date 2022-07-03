package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "champion")
public class ChampionEntity implements Parcelable {

    public ChampionEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "champion_code")
    private int championCode;

    @ColumnInfo(name = "champion_name")
    private String championName;

    @ColumnInfo(name = "lane_strength")
    private double laneStrength;

    @ColumnInfo(name = "team_fight")
    private double teamFight;

    @ColumnInfo(name = "split_push")
    private double splitPush;

    @ColumnInfo(name = "one_vs_one_strength")
    private double oneVsOneStrength;
    private double initiating;
    private double poking;
    private int type;

    @ColumnInfo(name = "to_six_potential")
    private double toSixPotential;

    @ColumnInfo(name = "to_elv_potential")
    private double toElvPotential;

    @ColumnInfo(name = "to_sixteen_potential")
    private double toSixteenPotential;

    protected ChampionEntity(Parcel in) {
        championCode = in.readInt();
        championName = in.readString();
        laneStrength = in.readDouble();
        teamFight = in.readDouble();
        splitPush = in.readDouble();
        oneVsOneStrength = in.readDouble();
        initiating = in.readDouble();
        poking = in.readDouble();
        type = in.readInt();
        toSixPotential = in.readDouble();
        toElvPotential = in.readDouble();
        toSixteenPotential = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(championCode);
        dest.writeString(championName);
        dest.writeDouble(laneStrength);
        dest.writeDouble(teamFight);
        dest.writeDouble(splitPush);
        dest.writeDouble(oneVsOneStrength);
        dest.writeDouble(initiating);
        dest.writeDouble(poking);
        dest.writeInt(type);
        dest.writeDouble(toSixPotential);
        dest.writeDouble(toElvPotential);
        dest.writeDouble(toSixteenPotential);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChampionEntity> CREATOR = new Creator<ChampionEntity>() {
        @Override
        public ChampionEntity createFromParcel(Parcel in) {
            return new ChampionEntity(in);
        }

        @Override
        public ChampionEntity[] newArray(int size) {
            return new ChampionEntity[size];
        }
    };

    public int getChampionCode() {
        return championCode;
    }

    public void setChampionCode(int championCode) {
        this.championCode = championCode;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public double getLaneStrength() {
        return laneStrength;
    }

    public void setLaneStrength(double laneStrength) {
        this.laneStrength = laneStrength;
    }

    public double getTeamFight() {
        return teamFight;
    }

    public void setTeamFight(double teamFight) {
        this.teamFight = teamFight;
    }

    public double getSplitPush() {
        return splitPush;
    }

    public void setSplitPush(double splitPush) {
        this.splitPush = splitPush;
    }

    public double getOneVsOneStrength() {
        return oneVsOneStrength;
    }

    public void setOneVsOneStrength(double oneVsOneStrength) {
        this.oneVsOneStrength = oneVsOneStrength;
    }

    public double getInitiating() {
        return initiating;
    }

    public void setInitiating(double initiating) {
        this.initiating = initiating;
    }

    public double getPoking() {
        return poking;
    }

    public void setPoking(double poking) {
        this.poking = poking;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getToSixPotential() {
        return toSixPotential;
    }

    public void setToSixPotential(double toSixPotential) {
        this.toSixPotential = toSixPotential;
    }

    public double getToElvPotential() {
        return toElvPotential;
    }

    public void setToElvPotential(double toElvPotential) {
        this.toElvPotential = toElvPotential;
    }

    public double getToSixteenPotential() {
        return toSixteenPotential;
    }

    public void setToSixteenPotential(double toSixteenPotential) {
        this.toSixteenPotential = toSixteenPotential;
    }
}
