package com.choonham.lck_manager.map_object;

public class Nexus extends MapObject{
    private int playerSide;

    public Nexus(String objectName, int x, int y, int visionDistance, int teamSide, int playerSide) {
        super(objectName, x, y, visionDistance, teamSide);

        this.playerSide = playerSide;

        if(playerSide != teamSide) {
            super.visionDistance = 0;
        }
    }
}
