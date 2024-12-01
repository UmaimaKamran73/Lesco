/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.a1_scd_22l7942;

import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
//import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.List;

/**
 *
 * @author LENOVO
 */
public class FileCustomerDataAccess //implements CustomerDataAccess
{
    public static final String FILE_PATH= "CustomerInfo.txt";

    public FileCustomerDataAccess() 
    {
        
    }
    
    
    //@Override
    public ArrayList<Customer> loadAllCustomers()
    {
        ArrayList<Customer> customerList=new ArrayList<>();
        DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try(BufferedReader br=new BufferedReader(new FileReader(FILE_PATH)))
        {
            
            try
            {
                String line;
                while((line=br.readLine())!=null)
                {
                    String[] fields=line.split(",");
                    if(fields.length<10)
                    {
                        //skipping line if it is not properly storing ifany
                        continue; 
                    }

                    Customer customer=new Customer();
                    customer.setCustID(fields[0]);
                    customer.setCNIC(fields[1]);
                    customer.setCustName(fields[2]);
                    customer.setCustAddress(fields[3]);
                    customer.setPhoneNumber(fields[4]);
                    try
                    {
                        customer.setCustType(CustomerType.valueOf(fields[5].toUpperCase()));   //see if checks are needed here just in case
                    }
                    catch(IllegalArgumentException e)
                    {
                        System.out.println("Invalid Customer Type for customer ID: " + fields[0]);
                        continue;
                    }

                    try
                    {
                        customer.setMeterType(MeterType.valueOf(fields[6])); 
                    }
                    catch(IllegalArgumentException e)
                    {
                        System.out.println("Invalid Meter Type for customer ID: " + fields[0]);
                        continue;
                    }
                     //same for this one yk enum

                     try
                     {
                         customer.setConnectionDate(LocalDate.parse(fields[7],dateFormatter));
                     }
                     catch(IllegalArgumentException e)
                     {
                           System.out.println("Invalid date format for customer ID: " + fields[0]);
                           continue;
                     }

                     try
                     {
                        customer.setRegularUnitsConsumed(Integer.parseInt(fields[8]));
                        customer.setPeakHourUnitsConsumed(Integer.parseInt(fields[9]));
                     }
                     catch(NumberFormatException e)
                     {
                         System.out.println("Invalid meter reading for customer ID: "+fields[0]);
                         continue;
                     }

                    customerList.add(customer);   
                }
            }
            catch(IOException e)
            {
                System.out.println("Error while reading the file"+e.getMessage());
            }
        }
        catch(IOException e)
        {
            System.out.println("Error Loading Customer Data"+e.getMessage());
        }
        return customerList;
}
    
    //@Override
    public void saveAllCustomers(ArrayList<Customer> customerList) 
    {
    BufferedWriter bw = null;
    FileWriter fw = null;

    try {
        fw = new FileWriter(FILE_PATH);
        bw = new BufferedWriter(fw);

        for (Customer customer : customerList) {
            String line = customer.getCustID() + "," +
                          customer.getCNIC() + "," +
                          customer.getCustName() + "," +
                          customer.getCustAddress() + "," +
                          customer.getPhoneNumber() + "," +
                          customer.getCustType() + "," +
                          customer.getMeterType() + "," +
                          customer.getConnectionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "," +
                          customer.getRegularUnitsConsumed() + "," +
                          customer.getPeakHourUnitsConsumed();
            bw.write(line);
            bw.newLine(); // move to the next line after writing one customer's data
        }
        bw.flush(); // ensures all data is written to the file
    } catch (IOException e) 
    {
        System.out.println("Error while saving data to file\n" + e.getMessage());
    } 
    finally 
    {
        try 
        {
            if (bw != null) 
            {
                bw.close();
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error while closing the file writer\n" + e.getMessage());
        }
    }
}

}
