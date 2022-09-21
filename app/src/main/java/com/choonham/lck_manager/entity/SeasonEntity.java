package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.android.material.textfield.TextInputLayout;

@Entity(tableName = "season")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeasonEntity implements Parcelable {
    public SeasonEntity() {
    }

    @PrimaryKey
    @ColumnInfo(name="season_code")
    private int seasonCode;

    @ColumnInfo(name="season_name")
    private String seasonName;

    @ColumnInfo(name="season_for_short")
    private String seasonForShort;

    protected SeasonEntity(Parcel in) {
        seasonCode = in.readInt();
        seasonName = in.readString();
        seasonForShort = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(seasonCode);
        dest.writeString(seasonName);
        dest.writeString(seasonForShort);
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

    public String getSeasonForShort() {
        return seasonForShort;
    }

    public void setSeasonForShort(String seasonForShort) {
        this.seasonForShort = seasonForShort;
    }
}
