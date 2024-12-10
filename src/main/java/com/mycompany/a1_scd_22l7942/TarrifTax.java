/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.a1_scd_22l7942;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author LENOVO
 */
public class TarrifTax implements Serializable
{
   final MeterType meterType;
   final CustomerType custType;
    BigDecimal regularUnitPrice;
    BigDecimal peakHourUnitPrice;
    float percentageOfTax;
    int fixedCharges;

    public TarrifTax(MeterType meterType, CustomerType custType, BigDecimal regularUnitPrice, float percentageOfTax, int fixedCharges)
    {
        this.meterType = meterType;
        this.custType = custType;
        this.regularUnitPrice = regularUnitPrice;
        this.percentageOfTax = percentageOfTax;
        this.fixedCharges = fixedCharges;
    }

    
    
    public TarrifTax(MeterType meterType, CustomerType custType, BigDecimal regularUnitPrice, BigDecimal peakHourUnitPrice, float percentageOfTax, int fixedCharges) 
    {
        this.meterType = meterType;
        this.custType = custType;
        this.regularUnitPrice = regularUnitPrice;
        this.peakHourUnitPrice = peakHourUnitPrice;
        this.percentageOfTax = percentageOfTax;
        this.fixedCharges = fixedCharges;
    }

    public MeterType getMeterType() 
    {
        return meterType;
    }

    public CustomerType getCustType() 
    {
        return custType;
    }

    public BigDecimal getRegularUnitPrice() 
    {
        return regularUnitPrice;
    }

    public void setRegularUnitPrice(BigDecimal regularUnitPrice) 
    {
        this.regularUnitPrice = regularUnitPrice;
    }

    public BigDecimal getPeakHourUnitPrice() 
    {
        return peakHourUnitPrice;
    }

    public void setPeakHourUnitPrice(BigDecimal peakHourUnitPrice) 
    {
        this.peakHourUnitPrice = peakHourUnitPrice;
    }

    public float getPercentageOfTax() 
    {
        return percentageOfTax;
    }

    public void setPercentageOfTax(float percentageOfTax) 
    {
        this.percentageOfTax = percentageOfTax;
    }

    public int getFixedCharges() 
    {
        return fixedCharges;
    }

    public void setFixedCharges(int fixedCharges) 
    {
        this.fixedCharges = fixedCharges;
    }
   
    
    
}
