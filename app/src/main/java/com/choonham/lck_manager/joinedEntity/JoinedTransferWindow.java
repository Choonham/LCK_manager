package com.choonham.lck_manager.joinedEntity;

import android.graphics.Paint;
import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.PlayerEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.entity.TransferWindowEntity;
import com.google.android.material.badge.ExperimentalBadgeUtils;

public class JoinedTransferWindow {
    @Embedded
    public TransferWindowEntity transferWindowEntity;

    @Relation(
            parentColumn = "player_code",
            entityColumn = "player_code"
    )
    public PlayerEntity playerEntity;

    @Embedded
    public SeasonEntity seasonEntity;
}
