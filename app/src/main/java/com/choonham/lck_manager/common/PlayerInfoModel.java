package com.choonham.lck_manager.common;

public class PlayerInfoModel {
    PlayerInfoListener playerInfoListener;

    private static PlayerInfoModel mInstance;

    public static PlayerInfoModel createInstance(PlayerInfoListener playerInfoListener) {
        if(mInstance == null) {
            mInstance = new PlayerInfoModel(playerInfoListener);
        }
        return mInstance;
    }

    public static PlayerInfoModel getInstance() {
        return mInstance;
    }

    public PlayerInfoModel(PlayerInfoListener playerInfoListener) {
        this.playerInfoListener = playerInfoListener;
    }

    public void close() {
        mInstance = null;
    }

    public void onConfirm(double OfferTransferFee) {
        playerInfoListener.onConfirm(OfferTransferFee);
    }
}
