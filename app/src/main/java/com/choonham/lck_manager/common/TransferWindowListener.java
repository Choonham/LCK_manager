package com.choonham.lck_manager.common;

public interface TransferWindowListener {
    void onConfirm(double offeredTransferFee);

    void onRelease();
}
