package com.choonham.lck_manager.entity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player_champion_play_data")
public class PlayerChampionPlayDataEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "play_data_code")
    private int playDataCode;

    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "champion_code")
    private int championCode;

    private int win;
    private int lose;
    private double kda;

    public int getPlayDataCode() {
        return playDataCode;
    }

    public void setPlayDataCode(int playDataCode) {
        this.playDataCode = playDataCode;
    }

    public int getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(int playerCode) {
        this.playerCode = playerCode;
    }

    public int getChampionCode() {
        return championCode;
    }

    public void setChampionCode(int championCode) {
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
