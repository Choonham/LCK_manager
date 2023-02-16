package com.choonham.lck_manager.map_object;

public class Inhibitor extends MapObject{

    private int playerSide;

    public Inhibitor(String objectName, int x, int y, int visionDistance, int teamSide, int playerSide) {
        super(objectName, x, y, visionDistance, teamSide);

        this.playerSide = playerSide;

        if(playerSide != teamSide) {
            super.visionDistance = 0;
        }
    }
}
