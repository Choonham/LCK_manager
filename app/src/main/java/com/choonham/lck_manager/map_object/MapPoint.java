package com.choonham.lck_manager.map_object;

public class MapPoint {
    private int x;
    private int y;
    private String lane;
    private int num;

    public MapPoint(int x, int y, String lane, int num) {
        this.x = x;
        this.y = y;
        this.lane = lane;
        this.num = num;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
