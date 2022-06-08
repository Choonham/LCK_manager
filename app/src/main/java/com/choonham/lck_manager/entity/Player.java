package com.choonham.lck_manager.entity;

public class Player {
    private long playerCode;
    private long seasonCode;
    private String playerName;
    private int position;
    private double physical;
    private double teamFight;
    private double outSmart;
    private double laneStrength;
    private double stability;
    private int fameLv;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(long playerCode) {
        this.playerCode = playerCode;
    }

    public long getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(long seasonCode) {
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
}
