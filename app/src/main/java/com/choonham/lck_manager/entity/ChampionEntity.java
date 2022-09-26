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

    @PrimaryKey
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

    private double nuking;
    private double util;
    private double cc;
    private double gank;
    private double tank;

    private double dps;

    private int type;

    private double potential1;

    private double potential2;

    private double potential3;

    @ColumnInfo(name = "win_rate")
    private double winRate;

    protected ChampionEntity(Parcel in) {
        championCode = in.readInt();
        championName = in.readString();
        laneStrength = in.readDouble();
        teamFight = in.readDouble();
        splitPush = in.readDouble();
        oneVsOneStrength = in.readDouble();
        initiating = in.readDouble();
        poking = in.readDouble();
        nuking = in.readDouble();
        util = in.readDouble();
        cc = in.readDouble();
        gank = in.readDouble();
        tank = in.readDouble();
        dps = in.readDouble();
        type = in.readInt();
        potential1 = in.readDouble();
        potential2 = in.readDouble();
        potential3 = in.readDouble();
        winRate = in.readDouble();
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

    public double getNuking() {
        return nuking;
    }

    public void setNuking(double nuking) {
        this.nuking = nuking;
    }

    public double getUtil() {
        return util;
    }

    public void setUtil(double util) {
        this.util = util;
    }

    public double getCc() {
        return cc;
    }

    public void setCc(double cc) {
        this.cc = cc;
    }

    public double getGank() {
        return gank;
    }

    public void setGank(double gank) {
        this.gank = gank;
    }

    public double getTank() {
        return tank;
    }

    public void setTank(double tank) {
        this.tank = tank;
    }

    public double getDps() {
        return dps;
    }

    public void setDps(double dps) {
        this.dps = dps;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPotential1() {
        return potential1;
    }

    public void setPotential1(double potential1) {
        this.potential1 = potential1;
    }

    public double getPotential2() {
        return potential2;
    }

    public void setPotential2(double potential2) {
        this.potential2 = potential2;
    }

    public double getPotential3() {
        return potential3;
    }

    public void setPotential3(double potential3) {
        this.potential3 = potential3;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    @Override
    public int describeContents() {
        return 0;
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
        dest.writeDouble(nuking);
        dest.writeDouble(util);
        dest.writeDouble(cc);
        dest.writeDouble(gank);
        dest.writeDouble(tank);
        dest.writeDouble(dps);
        dest.writeInt(type);
        dest.writeDouble(potential1);
        dest.writeDouble(potential2);
        dest.writeDouble(potential3);
        dest.writeDouble(winRate);
    }
}
