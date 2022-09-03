package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserEntity implements Parcelable{

    public UserEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_code")
    private int userCode;

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo(name = "user_email")
    private String userEmail;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "user_nick_name")
    private String userNickName;

    @ColumnInfo(name = "user_fame_lv")
    private int userFameLv;

    @ColumnInfo(name = "user_money")
    private int userMoney;

    @ColumnInfo(name = "season_code")
    private int seasonCode;

    @ColumnInfo(name = "match_num")
    private int matchNum;

    @ColumnInfo(name = "apiver")
    private int apiVer;

    protected UserEntity(Parcel in) {
        userCode = in.readInt();
        userId = in.readString();
        userEmail = in.readString();
        userName = in.readString();
        userNickName = in.readString();
        userFameLv = in.readInt();
        userMoney = in.readInt();
        seasonCode = in.readInt();
        matchNum = in.readInt();
        apiVer = in.readInt();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel in) {
            return new UserEntity(in);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public int getUserFameLv() {
        return userFameLv;
    }

    public void setUserFameLv(int userFameLv) {
        this.userFameLv = userFameLv;
    }

    public int getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(int userMoney) {
        this.userMoney = userMoney;
    }

    public int getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
    }

    public int getApiVer() {
        return apiVer;
    }

    public void setApiVer(int apiVer) {
        this.apiVer = apiVer;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userCode);
        parcel.writeString(userId);
        parcel.writeString(userEmail);
        parcel.writeString(userName);
        parcel.writeString(userNickName);
        parcel.writeInt(userFameLv);
        parcel.writeInt(userMoney);
        parcel.writeInt(seasonCode);
        parcel.writeInt(matchNum);
        parcel.writeInt(apiVer);
    }
}
