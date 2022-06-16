package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "champion_counter")
public class ChampionCounterEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "champion_counter_seq_code")
    private int championCounterSeqCode;

    @ColumnInfo(name = "champion_code")
    private int championCode;

    @ColumnInfo(name = "counter_champion_code")
    private int counterChampionCode;

    @ColumnInfo(name = "lane_win_rate")
    private int laneWinRate;

    public int getChampionCounterSeqCode() {
        return championCounterSeqCode;
    }

    public void setChampionCounterSeqCode(int championCounterSeqCode) {
        this.championCounterSeqCode = championCounterSeqCode;
    }

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
