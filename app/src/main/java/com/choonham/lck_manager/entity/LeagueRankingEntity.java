package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "league_ranking")
public class LeagueRankingEntity implements Parcelable {

    public LeagueRankingEntity() {
    }

    protected LeagueRankingEntity(Parcel in) {
        teamCode = in.readInt();
        rank = in.readInt();
    }

    @PrimaryKey
    @ColumnInfo(name="team_code")
    private int teamCode;

    @ColumnInfo(name="rank")
    private int rank;

    public static final Creator<LeagueRankingEntity> CREATOR = new Creator<LeagueRankingEntity>() {
        @Override
        public LeagueRankingEntity createFromParcel(Parcel in) {
            return new LeagueRankingEntity(in);
        }

        @Override
        public LeagueRankingEntity[] newArray(int size) {
            return new LeagueRankingEntity[size];
        }
    };

    public int getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(int teamCode) {
        this.teamCode = teamCode;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(teamCode);
        parcel.writeInt(rank);
    }
}
