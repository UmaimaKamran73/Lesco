/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.a1_scd_22l7942;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author LENOVO
 */
public class Billing {
    public String CustID; // from customers (do aggregation for it?
    public Month billingMonth; // using builtin enum
    public int currentRegularMeterReading;
    public int currentPeakMeterReading;
    public LocalDate readingEntryDate; // can also be called as issue date. CANNOT BE IN FUTURE. formate dd/mm/yyyy
    public BigDecimal electricityCost;
    public BigDecimal salesTaxAmount;
    public BigDecimal fixedCharges;
    public BigDecimal totalBillingAmount;
    public LocalDate dueDate; // 7 dates after readingEntryDate. CANNOT BE BEFORE READING DATE format
                              // dd/mm/yyyy
    public BillStatus billPaidStatus; // enum again. (paid or unpaid)
    public LocalDate billPaymentDate; // dd//mm/yyyycannot be b4 reading entry date

    public String custName;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Billing() {

    }

    public Billing(String CustID, Month billingMonth, int currentRegularMeterReading, int currentPeakMeterReading,
            LocalDate readingEntryDate, BigDecimal electricityCost, BigDecimal salesTaxAmount, BigDecimal fixedCharges,
            BigDecimal totalBillingAmount, LocalDate dueDate, BillStatus billPaidStatus, LocalDate billPaymentDate) {
        this.CustID = CustID;
        this.billingMonth = billingMonth;
        this.currentRegularMeterReading = currentRegularMeterReading;
        this.currentPeakMeterReading = currentPeakMeterReading;
        this.setReadingEntryDate(readingEntryDate);
        this.electricityCost = electricityCost;
        this.salesTaxAmount = salesTaxAmount;
        this.fixedCharges = fixedCharges;
        this.totalBillingAmount = totalBillingAmount;
        this.setDueDate(dueDate);
        this.billPaidStatus = billPaidStatus;
        this.setBillPaymentDate(billPaymentDate);
    }

    public Billing(String CustID, Month billingMonth, int currentRegularMeterReading, int currentPeakMeterReading,
            LocalDate readingEntryDate, BigDecimal electricityCost, BigDecimal salesTaxAmount, BigDecimal fixedCharges,
            BigDecimal totalBillingAmount, LocalDate dueDate, BillStatus billPaidStatus) {
        this.CustID = CustID;
        this.billingMonth = billingMonth;
        this.currentRegularMeterReading = currentRegularMeterReading;
        this.currentPeakMeterReading = currentPeakMeterReading;
        this.readingEntryDate = readingEntryDate;
        this.electricityCost = electricityCost;
        this.salesTaxAmount = salesTaxAmount;
        this.fixedCharges = fixedCharges;
        this.totalBillingAmount = totalBillingAmount;
        this.dueDate = dueDate;
        this.billPaidStatus = billPaidStatus;
    }

    public void viewCurrentBill() {

    }

    public final void setReadingEntryDate(LocalDate readingEntryDate) {
        if (readingEntryDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Reading entryDate cannot be in the future");
        }
        this.readingEntryDate = readingEntryDate;
    }

    public LocalDate getReadingEntryDate() {
        return readingEntryDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public final void setDueDate(LocalDate dueDate) {

        if (dueDate.isBefore(readingEntryDate.plusDays(14))) {
            throw new IllegalArgumentException("Due Date must be atleast 14 days after the Reading Entry Date");
        }

        this.dueDate = dueDate;
    }

    public LocalDate getBillPaymentDate() {
        return billPaymentDate;
    }

    public final void setBillPaymentDate(LocalDate billPaymentDate) {

        if (billPaymentDate.isBefore(readingEntryDate)) {
            throw new IllegalArgumentException("Bill Payment Date cannot be before the Reading Entry Date");
        }

        this.billPaymentDate = billPaymentDate;
    }

    public String getCustID() {
        return CustID;
    }

    public void setCustID(String CustID) {
        this.CustID = CustID;
    }

    public Month getBillingMonth() {
        return billingMonth;
    }

    public void setBillingMonth(Month billingMonth) {
        this.billingMonth = billingMonth;
    }

    public int getCurrentRegularMeterReading() {
        return currentRegularMeterReading;
    }

    public void setCurrentRegularMeterReading(int currentRegularMeterReading) {
        this.currentRegularMeterReading = currentRegularMeterReading;
    }

    public int getCurrentPeakMeterReading() {
        return currentPeakMeterReading;
    }

    public void setCurrentPeakMeterReading(int currentPeakMeterReading) {
        this.currentPeakMeterReading = currentPeakMeterReading;
    }

    public BigDecimal getElectricityCost() {
        return electricityCost;
    }

    public void setElectricityCost(BigDecimal electricityCost) {
        this.electricityCost = electricityCost;
    }

    public BigDecimal getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(BigDecimal salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
    }

    public BigDecimal getFixedCharges() {
        return fixedCharges;
    }

    public void setFixedCharges(BigDecimal fixedCharges) {
        this.fixedCharges = fixedCharges;
    }

    public BigDecimal getTotalBillingAmount() {
        return totalBillingAmount;
    }

    public void setTotalBillingAmount(BigDecimal totalBillingAmount) {
        this.totalBillingAmount = totalBillingAmount;
    }

    public BillStatus getBillPaidStatus() {
        return billPaidStatus;
    }

    public void setBillPaidStatus(BillStatus billPaidStatus) {
        this.billPaidStatus = billPaidStatus;
    }

    public void displayInfo() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "CustID= " + CustID + " Billing Month=" + billingMonth + " Current Regular Meter Reading= "
                + currentRegularMeterReading + "\nCurrent Peak Meter Reading= " + currentPeakMeterReading
                + " Reading Entry Date= " + readingEntryDate + "\nElectricity Cost= " + electricityCost
                + " SalesTaxAmount= " + salesTaxAmount + " Fixed Charges= " + fixedCharges + "\nTotal Billing Amount= "
                + totalBillingAmount + " Due Date= " + dueDate + " Bill Paid Status= " + billPaidStatus
                + " Bill Payment Date= " + billPaymentDate;
    }

    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format shoudl be DD/MM/YYYY");
        }
    }

    public void setCustomerName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

}
