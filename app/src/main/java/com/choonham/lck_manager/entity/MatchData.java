package com.choonham.lck_manager.entity;

public class MatchData {
    private int match_num;
    private int against_team;
    private int team_rank;
    private int play_flag;
    private String against_team_name;
    private int curr_match;

    public int getMatch_num() {
        return match_num;
    }

    public void setMatch_num(int match_num) {
        this.match_num = match_num;
    }

    public int getAgainst_team() {
        return against_team;
    }

    public void setAgainst_team(int against_team) {
        this.against_team = against_team;
    }

    public int getTeam_rank() {
        return team_rank;
    }

    public void setTeam_rank(int team_rank) {
        this.team_rank = team_rank;
    }

    public int getPlay_flag() {
        return play_flag;
    }

    public void setPlay_flag(int play_flag) {
        this.play_flag = play_flag;
    }

    public String getAgainst_team_name() {
        return against_team_name;
    }

    public void setAgainst_team_name(String against_team_name) {
        this.against_team_name = against_team_name;
    }

    public int getCurr_match() {
        return curr_match;
    }

    public void setCurr_match(int curr_match) {
        this.curr_match = curr_match;
    }
}
