package com.choonham.lck_manager.common;

public class TeamRosterModel {
    TeamRosterListener teamRosterListener;

    private static TeamRosterModel mInstance;

    public static TeamRosterModel createInstance(TeamRosterListener teamRosterListener) {
        if(mInstance == null) {
            mInstance = new TeamRosterModel(teamRosterListener);
        }

        return mInstance;
    }

    public static TeamRosterModel getInstance() {
        return mInstance;
    }

    public TeamRosterModel(TeamRosterListener teamRosterListener) {
        this.teamRosterListener = teamRosterListener;
    }

    public void close() {
        mInstance = null;
    }

    public void onEntryChange() {
        teamRosterListener.onEntryChange();
    }

    public void onRelease() {
        teamRosterListener.onRelease();
    }

    public void toFA() {
        teamRosterListener.toFA();
    }
}
