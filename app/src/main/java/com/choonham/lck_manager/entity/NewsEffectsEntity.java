package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_effects_entity")
public class NewsEffectsEntity implements Parcelable {

    public NewsEffectsEntity() {}

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

    protected NewsEffectsEntity(Parcel in) {
        effectCode = in.readInt();
        newsCode = in.readInt();
        effect = in.readInt();
        effectContent = in.readString();
        effectedStatus = in.readInt();
        effectedIndex = in.readInt();
    }

    public static final Creator<NewsEffectsEntity> CREATOR = new Creator<NewsEffectsEntity>() {
        @Override
        public NewsEffectsEntity createFromParcel(Parcel in) {
            return new NewsEffectsEntity(in);
        }

        @Override
        public NewsEffectsEntity[] newArray(int size) {
            return new NewsEffectsEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(effectCode);
        parcel.writeInt(newsCode);
        parcel.writeInt(effect);
        parcel.writeString(effectContent);
        parcel.writeInt(effectedStatus);
        parcel.writeInt(effectedIndex);
    }
}
