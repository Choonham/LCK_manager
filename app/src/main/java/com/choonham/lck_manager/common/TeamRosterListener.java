package com.choonham.lck_manager.common;

public interface TeamRosterListener {
    void toSub();

    void toMain();

    void onRelease();

    void toFA();

    void onOffer();

    void onConfirm(double offeredTransferFee);
}
