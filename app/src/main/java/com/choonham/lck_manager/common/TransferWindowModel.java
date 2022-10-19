package com.choonham.lck_manager.common;

public class TransferWindowModel {
    TransferWindowListener transferWindowListener;

    private static TransferWindowModel mInstance;

    public static TransferWindowModel createInstance(TransferWindowListener transferWindowListener) {
        if(mInstance == null) {
            mInstance = new TransferWindowModel(transferWindowListener);
        }

        return mInstance;
    }

    public static TransferWindowModel getInstance() {
        return mInstance;
    }

    public TransferWindowModel(TransferWindowListener transferWindowListener) {
        this.transferWindowListener = transferWindowListener;
    }

    public void close() {
        mInstance = null;
    }

    public void onConfirm(double offeredTransferFee) {
        transferWindowListener.onConfirm(offeredTransferFee);
    }

    public void onRelease() {
        transferWindowListener.onRelease();
    }
}
