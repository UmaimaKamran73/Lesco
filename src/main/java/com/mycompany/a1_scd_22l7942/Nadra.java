/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.a1_scd_22l7942;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author LENOVO
 */
public class Nadra 
{
    String custID;
    
    String CNIC;
    LocalDate expiryDate;

    public Nadra() 
    {
        
    }

    public Nadra(String custID, String CNIC, LocalDate expiryDate) 
    {
        this.custID = custID;
        this.CNIC = CNIC;
        this.expiryDate = expiryDate;
    }
    
    public boolean isCNICExpired()
    {
        return this.expiryDate.isBefore(LocalDate.now());
    }
    
    public boolean isCNICExpiringIn30Days()
    {
        LocalDate now=LocalDate.now();
        long daysUntilExpiry=ChronoUnit.DAYS.between(now, expiryDate);
  
        return daysUntilExpiry>0&&daysUntilExpiry<=30;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }
    
    
}
