package com.choonham.lck_manager.map_object;

public class Champion extends MapObject{

    private int playerSide;

    public Champion(String objectName, float x, float y, int visionDistance, int teamSide, int playerSide) {
        super(objectName, x, y, visionDistance, teamSide);

        this.playerSide = playerSide;

        if(playerSide != teamSide) {
            super.visionDistance = 0;
        }
    }
}
