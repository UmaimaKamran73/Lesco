/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.a1_scd_22l7942;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BillingDataAccess 
{
    private static final String BILLING_FILE_PATH="BillingInfo.txt";
    private static final DateTimeFormatter DATE_FORMAT=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public ArrayList<Billing> loadFileData()
    {
        ArrayList<Billing> billingList=new ArrayList<>();
        try(BufferedReader reader=new BufferedReader(new FileReader(BILLING_FILE_PATH)))
        {
            String line;
            
            while((line=reader.readLine())!=null)
            {
                String[] fields=line.split(",");
                if(fields.length<11)
                    continue;
                
                String custID=fields[0];
                Month billingMonth=Month.valueOf(fields[1].toUpperCase());
                int currentRegularMeterReading=Integer.parseInt(fields[2]);
                int currentPeakMeterReading=Integer.parseInt(fields[3]);
                
                LocalDate readingEntryDate=LocalDate.parse(fields[4], DATE_FORMAT);
                
                BigDecimal electricityCost=new BigDecimal(fields[5]);
                BigDecimal salesTaxAmount=new BigDecimal(fields[6]);
                BigDecimal fixedCharges=new BigDecimal(fields[7]);
                BigDecimal totalBillingAmount=new BigDecimal(fields[8]);
                
                LocalDate dueDate=LocalDate.parse(fields[9],DATE_FORMAT);
                
                BillStatus billPaidStatus=BillStatus.valueOf(fields[10].toUpperCase());
                
                LocalDate billPaymentDate;
                
                Billing billing;
                if(!"-".equals(fields[11]))
                {
                    billPaymentDate=LocalDate.parse(fields[11], DATE_FORMAT);
                    
                    billing=new Billing(custID,billingMonth,currentRegularMeterReading,currentPeakMeterReading,readingEntryDate,electricityCost,salesTaxAmount,fixedCharges,totalBillingAmount,dueDate,billPaidStatus,billPaymentDate);
                }
                else
                {
                    billing=new Billing(custID,billingMonth,currentRegularMeterReading,currentPeakMeterReading,readingEntryDate,electricityCost,salesTaxAmount,fixedCharges,totalBillingAmount,dueDate,billPaidStatus);
                }
                billingList.add(billing);
            }
                
        }
        catch(IOException e)
        {
            System.out.println("Error loading Billing File Data: "+e.getMessage());   
        }
        catch(Exception e)
        {
            System.out.println("Error parsing Billing File Data: "+e.getMessage());
        }
            return billingList;
    }
    
    public static void addBillingToCustomer(String custID,ArrayList<Billing> billingList,ArrayList<Customer> customerList)
    {
        Customer matchedCustomer=null;
        
        matchedCustomer=findCustomer(customerList,custID);
       
        if(matchedCustomer!=null)
        {
            for(Billing billing:billingList)
            {
                if(billing.getCustID().equals(custID))
                {
                    matchedCustomer.addBilling(billing);
                }
            }
        }
        else
        {
            System.out.println("Cust with ID: "+custID+" not found");
        }
    }
      public static Customer findCustomer(ArrayList<Customer> customerList,String CustID)
    {
        for(Customer customer: customerList)
        {
            if(customer.getCustID().equals(CustID))
            {
                return customer;
            }
        }
        return null;
    }
    
    
    public void saveFileData(ArrayList<Billing> billingList)
    {
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(BILLING_FILE_PATH)))
        {
            for(Billing billing: billingList)
            {
                String custID=billing.getCustID();
                String billingMonth=billing.getBillingMonth().name();
                String currentRegularMeterReading=String.valueOf(billing.getCurrentRegularMeterReading());
                String currentPeakMeterReading=String.valueOf(billing.getCurrentPeakMeterReading());
                
                String readingEntryDate=billing.getReadingEntryDate().format(DATE_FORMAT);
                String dueDate=billing.getDueDate().format(DATE_FORMAT);
                
                // If the bill is unpaid, write "-" for bill payment date
                String billPaymentDate = (billing.getBillPaidStatus() == BillStatus.UNPAID) ? "-" :
                (billing.getBillPaymentDate() != null) ? billing.getBillPaymentDate().format(DATE_FORMAT) : "";                
                String electricityCost=billing.getElectricityCost().toString();
                String salesTaxAmount=billing.getSalesTaxAmount().toString();
                String fixedCharges=billing.getFixedCharges().toString();
                String totalBillingAmount=billing.getTotalBillingAmount().toString();
                
                String billPaidStatus=billing.getBillPaidStatus().name();
                
                String line=String.join(",", custID,billingMonth,currentRegularMeterReading,currentPeakMeterReading,readingEntryDate,electricityCost,salesTaxAmount,fixedCharges,totalBillingAmount,dueDate,billPaidStatus,billPaymentDate);
                
                writer.write(line);
                writer.newLine();
            }
        }
        catch(IOException e)
        {
            System.out.println("Error writing in Billing File: " + e.getMessage());
        }
    }
}
