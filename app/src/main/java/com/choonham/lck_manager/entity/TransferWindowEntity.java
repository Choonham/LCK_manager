package com.choonham.lck_manager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transfer_window")
public class TransferWindowEntity {
    @PrimaryKey
    @ColumnInfo(name = "transfer_window_code")
    private int transferWindowCode;

    private int weeks;

    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "transfer_fee")
    private int transferFee;

    @ColumnInfo(name = "salary_wants")
    private int salaryWants;

    @ColumnInfo(name = "salary_offer")
    private int salaryOffer;

    @ColumnInfo(name = "min_salary")
    private int minSalary;

    public int getTransferWindowCode() {
        return transferWindowCode;
    }

    public void setTransferWindowCode(int transferWindowCode) {
        this.transferWindowCode = transferWindowCode;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public int getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(int playerCode) {
        this.playerCode = playerCode;
    }

    public int getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(int transferFee) {
        this.transferFee = transferFee;
    }

    public int getSalaryWants() {
        return salaryWants;
    }

    public void setSalaryWants(int salaryWants) {
        this.salaryWants = salaryWants;
    }

    public int getSalaryOffer() {
        return salaryOffer;
    }

    public void setSalaryOffer(int salaryOffer) {
        this.salaryOffer = salaryOffer;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }
}
