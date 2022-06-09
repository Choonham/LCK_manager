package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class ChampionCounter {

    public ChampionCounter(int championCode, int counterChampionCode, int laneWinRate) {
        this.championCode = championCode;
        this.counterChampionCode = counterChampionCode;
        this.laneWinRate = laneWinRate;
    }

    @ColumnInfo(name = "champion_code")
    private int championCode;

    @ColumnInfo(name = "counter_champion_code")
    private int counterChampionCode;

    @ColumnInfo(name = "lane_win_rate")
    private int laneWinRate;

    public int getChampionCode() {
        return championCode;
    }

    public void setChampionCode(int championCode) {
        this.championCode = championCode;
    }

    public int getCounterChampionCode() {
        return counterChampionCode;
    }

    public void setCounterChampionCode(int counterChampionCode) {
        this.counterChampionCode = counterChampionCode;
    }

    public int getLaneWinRate() {
        return laneWinRate;
    }

    public void setLaneWinRate(int laneWinRate) {
        this.laneWinRate = laneWinRate;
    }
}
