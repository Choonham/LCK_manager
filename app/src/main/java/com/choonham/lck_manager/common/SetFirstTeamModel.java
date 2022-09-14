package com.choonham.lck_manager.common;

public class SetFirstTeamModel {
    SetFirstTeamListener setFirstTeamListener;

    private static SetFirstTeamModel mInstance;

    public static SetFirstTeamModel createInstance(SetFirstTeamListener setFirstTeamListener) {
        if(mInstance == null) {
            mInstance = new SetFirstTeamModel(setFirstTeamListener);
        }

        return mInstance;
    }

    public static SetFirstTeamModel getInstance() {
        return mInstance;
    }

    public SetFirstTeamModel(SetFirstTeamListener setFirstTeamListener) {
        this.setFirstTeamListener = setFirstTeamListener;
    }

    public void close() {
        mInstance = null;
    }

    public void onConfirm() {
        setFirstTeamListener.onConfirm();
    }

    public void onRelease() {
        setFirstTeamListener.onRelease();
    }

}
