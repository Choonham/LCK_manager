package com.choonham.lck_manager.entity;

public class TeamRank {

    private int team_code;

    private String team_name;

    private int rank;

    private int total_wins;

    private int total_loses;

    private double win_rate;

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public int getTeam_code() {
        return team_code;
    }

    public void setTeam_code(int team_code) {
        this.team_code = team_code;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTotal_wins() {
        return total_wins;
    }

    public void setTotal_wins(int total_wins) {
        this.total_wins = total_wins;
    }

    public int getTotal_loses() {
        return total_loses;
    }

    public void setTotal_loses(int total_loses) {
        this.total_loses = total_loses;
    }

    public double getWin_rate() {
        return win_rate;
    }

    public void setWin_rate(double win_rate) {
        this.win_rate = win_rate;
    }
}
