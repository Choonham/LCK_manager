package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MainRoster {

    public MainRoster(long mainRosterCode, long playerCode, int mainOrder) {
        this.mainRosterCode = mainRosterCode;
        this.playerCode = playerCode;
        this.mainOrder = mainOrder;
    }

    @PrimaryKey
    @ColumnInfo(name = "main_roster_code")
    private long mainRosterCode;

    @ColumnInfo(name = "player_code")
    private long playerCode;

    @ColumnInfo(name = "main_order")
    private int mainOrder;

    public long getMainRosterCode() {
        return mainRosterCode;
    }

    public void setMainRosterCode(long mainRosterCode) {
        this.mainRosterCode = mainRosterCode;
    }

    public long getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(long playerCode) {
        this.playerCode = playerCode;
    }

    public int getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(int mainOrder) {
        this.mainOrder = mainOrder;
    }
}
