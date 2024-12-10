/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.a1_scd_22l7942;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.List;

/**
 *
 * @author LENOVO
 */
public class Customer implements Serializable
{
    private static final long serialVersionUID = 3175390045694102459L;

    String custID; //unique 4 digit(search for limit of digits thingy
    String CNIC;  //same search for 3 numbers without dashes
    String custName;  //comma check for every function almost. only letters and spaces
    String custAddress; //checks
    String phoneNumber; //checks and give a format to user too
    CustomerType custType; //2 options(commercial or domestic) so search for that. 1,0 input or the enumeration thingy (dbms)
    MeterType meterType; //same thing 2 options(Single Phase or Three Phase)
    LocalDate connectionDate;
    int regularUnitsConsumed;    //meter reading for which customer HAS PAID THE BILL (and paid at a given time)
    int peakHourUnitsConsumed;  
    //in a particular month thingy.read 2nd bullet in req again
 
    //AGREGATION
    ArrayList <Billing> billingHistory;
    
    private static final DateTimeFormatter DATE_FORMATTER=DateTimeFormatter.ofPattern("dd/MM/yyy");

    public Customer(String custID, String CNIC, String custName, String custAddress, String phoneNumber, CustomerType custType, MeterType meterType, LocalDate connectionDate, int regularUnitsConsumed, int peakHourUnitsConsumed) 
    {
        this.custID = custID;
        this.CNIC = CNIC;
        this.custName = custName;
        this.custAddress = custAddress;
        this.phoneNumber = phoneNumber;
        this.custType = custType;
        this.meterType = meterType;
        this.connectionDate = connectionDate;
        this.regularUnitsConsumed = regularUnitsConsumed;
        this.peakHourUnitsConsumed = peakHourUnitsConsumed;
        this.billingHistory=new ArrayList<>();
    }

    public Customer()
    {
        
    }

    public void addBilling(Billing bill)
    {
        if (this.billingHistory == null) {
            this.billingHistory = new ArrayList<>();
        }
        this.billingHistory.add(bill);
    }
    
    public void viewCurrentBill()
    {
        //basically bill with that meter type and current readings
        //gotta edit it rn
        if(billingHistory.isEmpty())
        {
            System.out.println("No Billing History Available");
        }
        
         Billing latestBill=billingHistory.get(billingHistory.size()-1);
         
    }
    
    public String getCustID()
    {
        return custID;
    }

    public void setCustID(String custID)
    {
        this.custID = custID;
    }

    public String getCNIC()
    {
        return CNIC;
    }

    public void setCNIC(String CNIC)
    {
        this.CNIC = CNIC;
    }

    public String getCustName() 
    {
        return custName;
    }

    public void setCustName(String custName)
    {
        this.custName = custName;
    }

    public String getCustAddress() 
    {
        return custAddress;
    }

    public void setCustAddress(String custaAddress) 
    {
        this.custAddress = custaAddress;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    public LocalDate getConnectionDate() 
    {
        return connectionDate;
    }

    public void setConnectionDate(LocalDate connectionDate)
    {
        this.connectionDate = connectionDate;
    }
    public CustomerType getCustType()
    {
        return custType;
    }

    public void setCustType(CustomerType custType)
    {
        this.custType = custType;
    }

    public MeterType getMeterType() 
    {
        return meterType;
    }

    public void setMeterType(MeterType meterType)
    {
        this.meterType = meterType;
    }

    public int getRegularUnitsConsumed() 
    {
        return regularUnitsConsumed;
    }

    public void setRegularUnitsConsumed(int regularUnitsConsumed)
    {
        this.regularUnitsConsumed = regularUnitsConsumed;
    }

    public int getPeakHourUnitsConsumed() 
    {
        return peakHourUnitsConsumed;
    }

    //2nd point of req sus. read regular units thingy again
    public void setPeakHourUnitsConsumed(int peakHourUnitsConsumed) 
    {
        this.peakHourUnitsConsumed = peakHourUnitsConsumed;
    }
   
}
