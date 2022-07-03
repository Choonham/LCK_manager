package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pog_point_rank")
public class PogPointRankEntity implements Parcelable {

    public PogPointRankEntity() {
    }

    @PrimaryKey
    @ColumnInfo(name="pog_point_code")
    private int pogPointCode;

    private int rank;

    @ColumnInfo(name="player_code")
    private int playerCode;

    @ColumnInfo(name="pog_point")
    private int pogPoint;

    protected PogPointRankEntity(Parcel in) {
        pogPointCode = in.readInt();
        rank = in.readInt();
        playerCode = in.readInt();
        pogPoint = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pogPointCode);
        dest.writeInt(rank);
        dest.writeInt(playerCode);
        dest.writeInt(pogPoint);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PogPointRankEntity> CREATOR = new Creator<PogPointRankEntity>() {
        @Override
        public PogPointRankEntity createFromParcel(Parcel in) {
            return new PogPointRankEntity(in);
        }

        @Override
        public PogPointRankEntity[] newArray(int size) {
            return new PogPointRankEntity[size];
        }
    };

    public int getPogPointCode() {
        return pogPointCode;
    }

    public void setPogPointCode(int pogPointCode) {
        this.pogPointCode = pogPointCode;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(int playerCode) {
        this.playerCode = playerCode;
    }

    public int getPogPoint() {
        return pogPoint;
    }

    public void setPogPoint(int pogPoint) {
        this.pogPoint = pogPoint;
    }
}
