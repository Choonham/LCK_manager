package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "league_season_team")
public class LeagueSeasonTeamEntity implements Parcelable {

    public LeagueSeasonTeamEntity() {
    };

    @PrimaryKey
    @ColumnInfo(name="league_season_team_code")
    private int leagueSeasonTeamCode;

    private int rank;

    @ColumnInfo(name="season_code")
    private int seasonCode;

    @ColumnInfo(name="season_id")
    private String seasonId;

    @ColumnInfo(name="team_code")
    private int teamCode;

    private int win;

    private int lose;

    private int wd;

    protected LeagueSeasonTeamEntity(Parcel in) {
        leagueSeasonTeamCode = in.readInt();
        rank = in.readInt();
        seasonCode = in.readInt();
        seasonId = in.readString();
        teamCode = in.readInt();
        win = in.readInt();
        lose = in.readInt();
        wd = in.readInt();
    }

    public static final Creator<LeagueSeasonTeamEntity> CREATOR = new Creator<LeagueSeasonTeamEntity>() {
        @Override
        public LeagueSeasonTeamEntity createFromParcel(Parcel in) {
            return new LeagueSeasonTeamEntity(in);
        }

        @Override
        public LeagueSeasonTeamEntity[] newArray(int size) {
            return new LeagueSeasonTeamEntity[size];
        }
    };

    public int getLeagueSeasonTeamCode() {
        return leagueSeasonTeamCode;
    }

    public void setLeagueSeasonTeamCode(int leagueSeasonTeamCode) {
        this.leagueSeasonTeamCode = leagueSeasonTeamCode;
    }

    public int getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(int teamCode) {
        this.teamCode = teamCode;
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

    public int getWd() {
        return wd;
    }

    public void setWd(int wd) {
        this.wd = wd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(leagueSeasonTeamCode);
        parcel.writeInt(rank);
        parcel.writeInt(seasonCode);
        parcel.writeString(seasonId);
        parcel.writeInt(teamCode);
        parcel.writeInt(win);
        parcel.writeInt(lose);
        parcel.writeInt(wd);
    }
}
