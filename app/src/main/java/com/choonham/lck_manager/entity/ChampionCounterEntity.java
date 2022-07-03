package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "champion_counter")
public class ChampionCounterEntity implements Parcelable {

    public ChampionCounterEntity() {}

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "champion_counter_seq_code")
    private int championCounterSeqCode;

    @ColumnInfo(name = "champion_code")
    private int championCode;

    @ColumnInfo(name = "counter_champion_code")
    private int counterChampionCode;

    @ColumnInfo(name = "lane_win_rate")
    private int laneWinRate;

    protected ChampionCounterEntity(Parcel in) {
        championCounterSeqCode = in.readInt();
        championCode = in.readInt();
        counterChampionCode = in.readInt();
        laneWinRate = in.readInt();
    }

    public static final Creator<ChampionCounterEntity> CREATOR = new Creator<ChampionCounterEntity>() {
        @Override
        public ChampionCounterEntity createFromParcel(Parcel in) {
            return new ChampionCounterEntity(in);
        }

        @Override
        public ChampionCounterEntity[] newArray(int size) {
            return new ChampionCounterEntity[size];
        }
    };

    public int getChampionCounterSeqCode() {
        return championCounterSeqCode;
    }

    public void setChampionCounterSeqCode(int championCounterSeqCode) {
        this.championCounterSeqCode = championCounterSeqCode;
    }

    public int getChampionCode() {
        return championCode;
    }

    public void setChampionCode(int championCode) {
        this.championCode = championCode;
    }

    public int getCounterChampionCode() {
        return counterChampionCode;
    }

    public void setCounterChampionCode(int counterChampionCode) {
        this.counterChampionCode = counterChampionCode;
    }

    public int getLaneWinRate() {
        return laneWinRate;
    }

    public void setLaneWinRate(int laneWinRate) {
        this.laneWinRate = laneWinRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(championCounterSeqCode);
        parcel.writeInt(championCode);
        parcel.writeInt(counterChampionCode);
        parcel.writeInt(laneWinRate);
    }
}
