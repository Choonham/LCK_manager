package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_and_issue")
public class NewsAndIssueEntity implements Parcelable {
    public NewsAndIssueEntity() {}

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="news_code")
    private int newsCode;

    @ColumnInfo(name="team_code")
    private int teamCode;

    @ColumnInfo(name="news_title")
    private String newsTitle;

    @ColumnInfo(name="news_content")
    private String newsContent;

    @ColumnInfo(name="effected_player")
    private int effectedPlayer;

    @ColumnInfo(name="remaining")
    private int remaining;

    protected NewsAndIssueEntity(Parcel in) {
        newsCode = in.readInt();
        teamCode = in.readInt();
        newsTitle = in.readString();
        newsContent = in.readString();
        effectedPlayer = in.readInt();
        remaining = in.readInt();
    }

    public static final Creator<NewsAndIssueEntity> CREATOR = new Creator<NewsAndIssueEntity>() {
        @Override
        public NewsAndIssueEntity createFromParcel(Parcel in) {
            return new NewsAndIssueEntity(in);
        }

        @Override
        public NewsAndIssueEntity[] newArray(int size) {
            return new NewsAndIssueEntity[size];
        }
    };

    public int getNewsCode() {
        return newsCode;
    }

    public void setNewsCode(int newsCode) {
        this.newsCode = newsCode;
    }

    public int getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(int teamCode) {
        this.teamCode = teamCode;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public int getEffectedPlayer() {
        return effectedPlayer;
    }

    public void setEffectedPlayer(int effectedPlayer) {
        this.effectedPlayer = effectedPlayer;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(newsCode);
        parcel.writeInt(teamCode);
        parcel.writeString(newsTitle);
        parcel.writeString(newsContent);
        parcel.writeInt(effectedPlayer);
        parcel.writeInt(remaining);
    }
}
