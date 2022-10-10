package com.choonham.lck_manager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transfer_window")
public class TransferWindowEntity implements Parcelable {

    public TransferWindowEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transfer_window_code")
    private int transferWindowCode;

    private int weeks;

    @ColumnInfo(name = "player_code")
    private int playerCode;

    @ColumnInfo(name = "transfer_fee")
    private double transferFee;

    @ColumnInfo(name = "salary_wants")
    private double salaryWants;

    @ColumnInfo(name = "salary_offer")
    private double salaryOffer;

    @ColumnInfo(name = "min_salary")
    private double minSalary;

    protected TransferWindowEntity(Parcel in) {
        transferWindowCode = in.readInt();
        weeks = in.readInt();
        playerCode = in.readInt();
        transferFee = in.readInt();
        salaryWants = in.readInt();
        salaryOffer = in.readInt();
        minSalary = in.readInt();
    }

    public static final Creator<TransferWindowEntity> CREATOR = new Creator<TransferWindowEntity>() {
        @Override
        public TransferWindowEntity createFromParcel(Parcel in) {
            return new TransferWindowEntity(in);
        }

        @Override
        public TransferWindowEntity[] newArray(int size) {
            return new TransferWindowEntity[size];
        }
    };

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

    public double getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(double transferFee) {
        this.transferFee = transferFee;
    }

    public double getSalaryWants() {
        return salaryWants;
    }

    public void setSalaryWants(double salaryWants) {
        this.salaryWants = salaryWants;
    }

    public double getSalaryOffer() {
        return salaryOffer;
    }

    public void setSalaryOffer(double salaryOffer) {
        this.salaryOffer = salaryOffer;
    }

    public double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(transferWindowCode);
        parcel.writeInt(weeks);
        parcel.writeInt(playerCode);
        parcel.writeDouble(transferFee);
        parcel.writeDouble(salaryWants);
        parcel.writeDouble(salaryOffer);
        parcel.writeDouble(minSalary);
    }
}
