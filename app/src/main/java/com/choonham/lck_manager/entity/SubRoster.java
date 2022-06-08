package com.choonham.lck_manager.entity;

public class SubRoster {
    private long subRosterCode;
    private long playerCode;
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
