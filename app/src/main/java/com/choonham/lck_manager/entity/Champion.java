package com.choonham.lck_manager.entity;

public class Champion {
    private long championCode;
    private String championName;
    private double laneStrength;
    private double teamFight;
    private double splitPush;
    private double oneVsOneStrength;
    private double initiating;
    private double poking;
    private int type;
    private double toSixPotential;
    private double toElvPotential;
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
