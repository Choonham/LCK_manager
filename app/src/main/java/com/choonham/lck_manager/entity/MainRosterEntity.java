package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "main_roster")
public class MainRosterEntity implements Parcelable {

    public MainRosterEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "main_roster_code")
    private int mainRosterCode;

    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "main_order")
    private int mainOrder;

    protected MainRosterEntity(Parcel in) {
        mainRosterCode = in.readInt();
        playerCode = in.readInt();
        mainOrder = in.readInt();
    }

    public static final Creator<MainRosterEntity> CREATOR = new Creator<MainRosterEntity>() {
        @Override
        public MainRosterEntity createFromParcel(Parcel in) {
            return new MainRosterEntity(in);
        }

        @Override
        public MainRosterEntity[] newArray(int size) {
            return new MainRosterEntity[size];
        }
    };

    public int getMainRosterCode() {
        return mainRosterCode;
    }

    public void setMainRosterCode(int mainRosterCode) {
        this.mainRosterCode = mainRosterCode;
    }

    public int getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(int playerCode) {
        this.playerCode = playerCode;
    }

    public int getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(int mainOrder) {
        this.mainOrder = mainOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mainRosterCode);
        parcel.writeInt(playerCode);
        parcel.writeInt(mainOrder);
    }
}
