package com.choonham.lck_manager.entity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SubRoster {

    public SubRoster(long subRosterCode, long playerCode, int mainOrder) {
        this.subRosterCode = subRosterCode;
        this.playerCode = playerCode;
        this.mainOrder = mainOrder;
    }

    @PrimaryKey
    @ColumnInfo(name = "subRoster_Code")
    private long subRosterCode;

    @ColumnInfo(name = "player_code")
    private long playerCode;

    @ColumnInfo(name = "main_order")
    private int mainOrder;

    public long getSubRosterCode() {
        return subRosterCode;
    }

    public void setSubRosterCode(long subRosterCode) {
        this.subRosterCode = subRosterCode;
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
