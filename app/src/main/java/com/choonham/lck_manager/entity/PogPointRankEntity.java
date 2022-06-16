package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pog_point_rank")
public class PogPointRankEntity {
    @PrimaryKey
    @ColumnInfo(name="pog_point_code")
    private int pogPointCode;

    private int rank;

    @ColumnInfo(name="player_code")
    private int playerCode;

    @ColumnInfo(name="pog_point")
    private int pogPoint;

    public int getPogPointCode() {
        return pogPointCode;
    }

    public void setPogPointCode(int pogPointCode) {
        this.pogPointCode = pogPointCode;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(int playerCode) {
        this.playerCode = playerCode;
    }

    public int getPogPoint() {
        return pogPoint;
    }

    public void setPogPoint(int pogPoint) {
        this.pogPoint = pogPoint;
    }
}
