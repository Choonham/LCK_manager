package com.choonham.lck_manager.entity;

public class ChampionCounter {
    private int championCode;
    private int counterChampionCode;
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
