package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_record")
public class UserRecordEntity implements Parcelable {

    public UserRecordEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_record_code")
    private int userRecordCode;

    @ColumnInfo(name = "user_code")
    private int userCode;

    @ColumnInfo(name = "season_code")
    private int seasonCode;

    private int rank;

    private int win;

    private int lose;

    @ColumnInfo(name = "win_diff")
    private int winDiff;

    @ColumnInfo(name = "fame_diff")
    private int fameDiff;

    protected UserRecordEntity(Parcel in) {
        userRecordCode = in.readInt();
        userCode = in.readInt();
        seasonCode = in.readInt();
        rank = in.readInt();
        win = in.readInt();
        lose = in.readInt();
        winDiff = in.readInt();
        fameDiff = in.readInt();
    }

    public static final Creator<UserRecordEntity> CREATOR = new Creator<UserRecordEntity>() {
        @Override
        public UserRecordEntity createFromParcel(Parcel in) {
            return new UserRecordEntity(in);
        }

        @Override
        public UserRecordEntity[] newArray(int size) {
            return new UserRecordEntity[size];
        }
    };

    public int getUserRecordCode() {
        return userRecordCode;
    }

    public void setUserRecordCode(int userRecordCode) {
        this.userRecordCode = userRecordCode;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getWinDiff() {
        return winDiff;
    }

    public void setWinDiff(int winDiff) {
        this.winDiff = winDiff;
    }

    public int getFameDiff() {
        return fameDiff;
    }

    public void setFameDiff(int fameDiff) {
        this.fameDiff = fameDiff;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userRecordCode);
        parcel.writeInt(userCode);
        parcel.writeInt(seasonCode);
        parcel.writeInt(rank);
        parcel.writeInt(win);
        parcel.writeInt(lose);
        parcel.writeInt(winDiff);
        parcel.writeInt(fameDiff);
    }
}
