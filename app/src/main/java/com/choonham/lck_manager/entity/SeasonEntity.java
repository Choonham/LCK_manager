package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.android.material.textfield.TextInputLayout;

@Entity(tableName = "season")
public class SeasonEntity implements Parcelable {
    public SeasonEntity() {
    }

    @PrimaryKey
    @ColumnInfo(name="season_code")
    private int seasonCode;

    @ColumnInfo(name="season_name")
    private String seasonName;

    protected SeasonEntity(Parcel in) {
        seasonCode = in.readInt();
        seasonName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(seasonCode);
        dest.writeString(seasonName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SeasonEntity> CREATOR = new Creator<SeasonEntity>() {
        @Override
        public SeasonEntity createFromParcel(Parcel in) {
            return new SeasonEntity(in);
        }

        @Override
        public SeasonEntity[] newArray(int size) {
            return new SeasonEntity[size];
        }
    };

    public int getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }
}
