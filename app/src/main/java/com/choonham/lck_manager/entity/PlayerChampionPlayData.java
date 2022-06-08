package com.choonham.lck_manager.entity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class PlayerChampionPlayData {
    @ColumnInfo(name = "player_code")
    private long playerCode;

    @ColumnInfo(name = "champion_code")
    private long championCode;

    private int win;
    private int lose;
    private double kda;

    public long getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(long playerCode) {
        this.playerCode = playerCode;
    }

    public long getChampionCode() {
        return championCode;
    }

    public void setChampionCode(long championCode) {
        this.championCode = championCode;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public double getKda() {
        return kda;
    }

    public void setKda(double kda) {
        this.kda = kda;
    }
}
