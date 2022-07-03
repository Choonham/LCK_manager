package com.choonham.lck_manager.entity;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sub_roster")
public class SubRosterEntity implements Parcelable {

    public SubRosterEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subRoster_Code")
    private int subRosterCode;

    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "main_order")
    private int mainOrder;

    protected SubRosterEntity(Parcel in) {
        subRosterCode = in.readInt();
        playerCode = in.readInt();
        mainOrder = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(subRosterCode);
        dest.writeInt(playerCode);
        dest.writeInt(mainOrder);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubRosterEntity> CREATOR = new Creator<SubRosterEntity>() {
        @Override
        public SubRosterEntity createFromParcel(Parcel in) {
            return new SubRosterEntity(in);
        }

        @Override
        public SubRosterEntity[] newArray(int size) {
            return new SubRosterEntity[size];
        }
    };

    public int getSubRosterCode() {
        return subRosterCode;
    }

    public void setSubRosterCode(int subRosterCode) {
        this.subRosterCode = subRosterCode;
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
}
