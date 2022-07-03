package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "match_data")
public class MatchDataEntity implements Parcelable {

    public MatchDataEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "match_data_code")
    private int matchDataCode;

    @ColumnInfo(name = "league_schedule_code")
    private int leagueScheduleCode;

    @ColumnInfo(name = "team_code")
    private int teamCode;

    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "champion_code")
    private int championCode;

    private double kda;

    protected MatchDataEntity(Parcel in) {
        matchDataCode = in.readInt();
        leagueScheduleCode = in.readInt();
        teamCode = in.readInt();
        playerCode = in.readInt();
        championCode = in.readInt();
        kda = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(matchDataCode);
        dest.writeInt(leagueScheduleCode);
        dest.writeInt(teamCode);
        dest.writeInt(playerCode);
        dest.writeInt(championCode);
        dest.writeDouble(kda);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MatchDataEntity> CREATOR = new Creator<MatchDataEntity>() {
        @Override
        public MatchDataEntity createFromParcel(Parcel in) {
            return new MatchDataEntity(in);
        }

        @Override
        public MatchDataEntity[] newArray(int size) {
            return new MatchDataEntity[size];
        }
    };

    public int getMatchDataCode() {
        return matchDataCode;
    }

    public void setMatchDataCode(int matchDataCode) {
        this.matchDataCode = matchDataCode;
    }

    public int getLeagueScheduleCode() {
        return leagueScheduleCode;
    }

    public void setLeagueScheduleCode(int leagueScheduleCode) {
        this.leagueScheduleCode = leagueScheduleCode;
    }

    public int getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(int teamCode) {
        this.teamCode = teamCode;
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

    public double getKda() {
        return kda;
    }

    public void setKda(double kda) {
        this.kda = kda;
    }
}
