package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_effects_entity")
public class NewsEffectsEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="effect_code")
    private int effectCode;

    @ColumnInfo(name="news_code")
    private int newsCode;

    @ColumnInfo(name="effect")
    private int effect;

    @ColumnInfo(name="effect_content")
    private String effectContent;

    @ColumnInfo(name="effected_status")
    private int effectedStatus;

    @ColumnInfo(name="effected_index")
    private int effectedIndex;

    public int getNewsCode() {
        return newsCode;
    }

    public void setNewsCode(int newsCode) {
        this.newsCode = newsCode;
    }

    public int getEffectCode() {
        return effectCode;
    }

    public void setEffectCode(int effectCode) {
        this.effectCode = effectCode;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public int getEffectedStatus() {
        return effectedStatus;
    }

    public void setEffectedStatus(int effectedStatus) {
        this.effectedStatus = effectedStatus;
    }

    public int getEffectedIndex() {
        return effectedIndex;
    }

    public void setEffectedIndex(int effectedIndex) {
        this.effectedIndex = effectedIndex;
    }

    public String getEffectContent() {
        return effectContent;
    }

    public void setEffectContent(String effectContent) {
        this.effectContent = effectContent;
    }

}
