package com.choonham.lck_manager.map_object;

public class Champion extends MapObject{

    private int playerSide;
    private int drawableId;

    public Champion(String objectName, int x, int y, int visionDistance, int teamSide, int playerSide, int drawableId) {
        super(objectName, x, y, visionDistance, teamSide);

        this.playerSide = playerSide;
        this.drawableId = drawableId;

        if(playerSide != teamSide) {
            super.visionDistance = 0;
        }
    }
}
