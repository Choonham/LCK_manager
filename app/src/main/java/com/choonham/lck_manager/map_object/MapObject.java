package com.choonham.lck_manager.map_object;

public class MapObject {

    public String objectName;

    public int x;
    public int y;

    public int visionDistance;

    // 0: red, 1: blue
    public int teamSide;

    public MapObject(String objectName, int x, int y, int visionDistance, int teamSide) {
        this.objectName = objectName;
        this.visionDistance = visionDistance;
        this.teamSide = teamSide;
        this.x = x;
        this.y = y;
    }
}
