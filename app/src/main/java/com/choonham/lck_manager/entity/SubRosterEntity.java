package com.choonham.lck_manager.entity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sub_roster")
public class SubRosterEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subRoster_Code")
    private int subRosterCode;

    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "main_order")
    private int mainOrder;

    public int getSubRosterCode() {
        return subRosterCode;
    }

    public void setSubRosterCode(int subRosterCode) {
        this.subRosterCode = subRosterCode;
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
