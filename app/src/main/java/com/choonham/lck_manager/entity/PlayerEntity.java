package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player")
public class PlayerEntity {

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
}