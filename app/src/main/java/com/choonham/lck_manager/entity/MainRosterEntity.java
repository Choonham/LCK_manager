package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "main_roster")
public class MainRosterEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "main_roster_code")
    private int mainRosterCode;

    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "main_order")
    private int mainOrder;

    public int getMainRosterCode() {
        return mainRosterCode;
    }

    public void setMainRosterCode(int mainRosterCode) {
        this.mainRosterCode = mainRosterCode;
    }

    public int getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(int playerCode) {
        this.playerCode = playerCode;
    }

    public int getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(int mainOrder) {
        this.mainOrder = mainOrder;
    }
}
