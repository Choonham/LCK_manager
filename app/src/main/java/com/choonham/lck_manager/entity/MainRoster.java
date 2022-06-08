package com.choonham.lck_manager.entity;

public class MainRoster {
    private long mainRosterCode;
    private long playerCode;
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
