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
//import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
//import java.util.List;

/**
 *
 * @author LENOVO
 */
public class NadraDataAccess 
{
    private static final String NADRA_DB_PATH="NADRADB.txt";
    private static final DateTimeFormatter DATE_FORMATTER=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public ArrayList<Nadra> loadNadraData() //throws IOException
    {
        ArrayList<Nadra> nadraList=new ArrayList<>();
        
        try(BufferedReader reader=new BufferedReader(new FileReader(NADRA_DB_PATH)))
        {
            String line;
            while((line=reader.readLine())!=null)
            {
                String[] fields=line.split(",");
                if(fields.length==3)
                {
                    String custID =fields[0];
                    String CNIC=fields[1];
                   
                    LocalDate expiryDate =LocalDate.parse(fields[2],DATE_FORMATTER);
                    
                    Nadra nadra = new Nadra(custID,CNIC, expiryDate);
                    nadraList.add(nadra);
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Error loading NADRADB file:"+e.getMessage());
        }
        catch (DateTimeParseException e)
        {
        System.out.println("Date parsing error: " + e.getMessage());
        }
        finally
        {
            
        }
        return nadraList;
    }
    
    public boolean updateCNICExpiryDate(ArrayList<Nadra> nadraData,String cnic,LocalDate newExpiryDate)
    {
        boolean updated= false;
        
        for(Nadra record: nadraData)
        {
            if(record.getCNIC().equals(cnic))
            {
                record.setExpiryDate(newExpiryDate);
                updated=true;
                break;
            }
        }
        if(updated)
        {
            saveNadraData(nadraData);
        }
        return updated;
    }
    
    
    public void checkCNICExpiring(ArrayList<Nadra> nad)
    {
       
        System.out.println("\tThe folloing are CNICs expiring within the next 30 days: ");
        for(Nadra n:nad)
        {
            //checking cnic expiry date
            if(n.isCNICExpiringIn30Days())
            {
                System.out.println("CustID: "+n.getCustID()+"\tCNIC: "+n.getCNIC()+"\tExpiryDate: "+n.getExpiryDate());
            }
        }
        System.out.println("----------------------------------------");
    }
    
    public void saveNadraData(ArrayList<Nadra> nadraData)
    {
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(NADRA_DB_PATH)))
        {
            for(Nadra record:nadraData)
            {
                String formattedDate=record.getExpiryDate().format(DATE_FORMATTER);
                writer.write(record.getCustID()+","+record.getCNIC()+","+formattedDate);
                writer.newLine();
            }
        }
        catch(IOException e)
        {
            System.out.println("Error writing nadra file: "+ e.getMessage());   
        }
            
    }
}
