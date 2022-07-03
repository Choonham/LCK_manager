package com.choonham.lck_manager.entity;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player_champion_play_data")
public class PlayerChampionPlayDataEntity implements Parcelable {

    public PlayerChampionPlayDataEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "play_data_code")
    private int playDataCode;

    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "champion_code")
    private int championCode;

    private int win;
    private int lose;
    private double kda;

    protected PlayerChampionPlayDataEntity(Parcel in) {
        playDataCode = in.readInt();
        playerCode = in.readInt();
        championCode = in.readInt();
        win = in.readInt();
        lose = in.readInt();
        kda = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(playDataCode);
        dest.writeInt(playerCode);
        dest.writeInt(championCode);
        dest.writeInt(win);
        dest.writeInt(lose);
        dest.writeDouble(kda);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlayerChampionPlayDataEntity> CREATOR = new Creator<PlayerChampionPlayDataEntity>() {
        @Override
        public PlayerChampionPlayDataEntity createFromParcel(Parcel in) {
            return new PlayerChampionPlayDataEntity(in);
        }

        @Override
        public PlayerChampionPlayDataEntity[] newArray(int size) {
            return new PlayerChampionPlayDataEntity[size];
        }
    };

    public int getPlayDataCode() {
        return playDataCode;
    }

    public void setPlayDataCode(int playDataCode) {
        this.playDataCode = playDataCode;
    }

    public int getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(int playerCode) {
        this.playerCode = playerCode;
    }

    public int getChampionCode() {
        return championCode;
    }

    public void setChampionCode(int championCode) {
        this.championCode = championCode;
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

    public double getKda() {
        return kda;
    }

    public void setKda(double kda) {
        this.kda = kda;
    }
}
