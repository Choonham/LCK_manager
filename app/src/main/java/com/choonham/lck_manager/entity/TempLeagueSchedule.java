package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class TempLeagueSchedule implements Parcelable {

    public TempLeagueSchedule() {
    }

    private Date date;
    private int matchNum;
    private String teamA;
    private String teamB;
    private int scoreA = 0;
    private int scoreB = 0;

    protected TempLeagueSchedule(Parcel in) {
        matchNum = in.readInt();
        teamA = in.readString();
        teamB = in.readString();
        scoreA = in.readInt();
        scoreB = in.readInt();
    }

    public static final Creator<TempLeagueSchedule> CREATOR = new Creator<TempLeagueSchedule>() {
        @Override
        public TempLeagueSchedule createFromParcel(Parcel in) {
            return new TempLeagueSchedule(in);
        }

        @Override
        public TempLeagueSchedule[] newArray(int size) {
            return new TempLeagueSchedule[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public int getScoreA() {
        return scoreA;
    }

    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public void setScoreB(int scoreB) {
        this.scoreB = scoreB;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(matchNum);
        parcel.writeString(teamA);
        parcel.writeString(teamB);
        parcel.writeInt(scoreA);
        parcel.writeInt(scoreB);
    }
}
