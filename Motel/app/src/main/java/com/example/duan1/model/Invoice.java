package com.example.duan1.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = Account.class, childColumns = "username", parentColumns = "username", onDelete = ForeignKey.CASCADE),
@ForeignKey(entity = Contract.class, childColumns = "idContract", parentColumns = "idContract", onDelete = ForeignKey.CASCADE)})
public class Invoice implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int idInvoice;
    private String date;
    private int oldWaterIndex;
    private int newWaterIndex;
    private int oldPowerIndicator;
    private int newPowerIndicator;
    private String otherFeesDescribe;
    private int otherFees;
    private int status;
    private int total;
    private int idContract;
    private String username;

    public Invoice() {
    }

    public Invoice(int idInvoice, String date, int oldWaterIndex, int newWaterIndex,
                   int oldPowerIndicator, int newPowerIndicator, String otherFeesDescribe,
                   int otherFees, int status, int total, int idContract, String username) {
        this.idInvoice = idInvoice;
        this.date = date;
        this.oldWaterIndex = oldWaterIndex;
        this.newWaterIndex = newWaterIndex;
        this.oldPowerIndicator = oldPowerIndicator;
        this.newPowerIndicator = newPowerIndicator;
        this.otherFeesDescribe = otherFeesDescribe;
        this.otherFees = otherFees;
        this.status = status;
        this.total = total;
        this.idContract = idContract;
        this.username = username;
    }

    public Invoice(int oldWaterIndex, int oldPowerIndicator, int status, int idContract, String username) {
        this.oldWaterIndex = oldWaterIndex;
        this.oldPowerIndicator = oldPowerIndicator;
        this.status = status;
        this.idContract = idContract;
        this.username = username;
    }

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOldWaterIndex() {
        return oldWaterIndex;
    }

    public void setOldWaterIndex(int oldWaterIndex) {
        this.oldWaterIndex = oldWaterIndex;
    }

    public int getNewWaterIndex() {
        return newWaterIndex;
    }

    public void setNewWaterIndex(int newWaterIndex) {
        this.newWaterIndex = newWaterIndex;
    }

    public int getOldPowerIndicator() {
        return oldPowerIndicator;
    }

    public void setOldPowerIndicator(int oldPowerIndicator) {
        this.oldPowerIndicator = oldPowerIndicator;
    }

    public int getNewPowerIndicator() {
        return newPowerIndicator;
    }

    public void setNewPowerIndicator(int newPowerIndicator) {
        this.newPowerIndicator = newPowerIndicator;
    }

    public String getOtherFeesDescribe() {
        return otherFeesDescribe;
    }

    public void setOtherFeesDescribe(String otherFeesDescribe) {
        this.otherFeesDescribe = otherFeesDescribe;
    }

    public int getOtherFees() {
        return otherFees;
    }

    public void setOtherFees(int otherFees) {
        this.otherFees = otherFees;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}