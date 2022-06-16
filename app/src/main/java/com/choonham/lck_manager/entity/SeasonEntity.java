package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.android.material.textfield.TextInputLayout;

@Entity(tableName = "season")
public class SeasonEntity {
    @PrimaryKey
    @ColumnInfo(name="season_code")
    private int seasonCode;

    @ColumnInfo(name="season_name")
    private String seasonName;

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
