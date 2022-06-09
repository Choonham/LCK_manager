package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Champion {

    public Champion(long championCode, String championName, double laneStrength, double teamFight, double splitPush, double oneVsOneStrength, double initiating, double poking, int type, double toSixPotential, double toElvPotential, double toSixteenPotential) {
        this.championCode = championCode;
        this.championName = championName;
        this.laneStrength = laneStrength;
        this.teamFight = teamFight;
        this.splitPush = splitPush;
        this.oneVsOneStrength = oneVsOneStrength;
        this.initiating = initiating;
        this.poking = poking;
        this.type = type;
        this.toSixPotential = toSixPotential;
        this.toElvPotential = toElvPotential;
        this.toSixteenPotential = toSixteenPotential;
    }

    @PrimaryKey
    @ColumnInfo(name = "champion_code")
    private long championCode;

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

    public long getChampionCode() {
        return championCode;
    }

    public void setChampionCode(long championCode) {
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
