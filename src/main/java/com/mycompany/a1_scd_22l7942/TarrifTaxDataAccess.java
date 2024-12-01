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
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class TarrifTaxDataAccess 
{
    public final String TARRIF_TAX_FILE_PATH="TarrifTaxInfo.txt";
    public ArrayList<TarrifTax> loadData()
    {
        ArrayList<TarrifTax> tarrifList =new ArrayList<>();
        
        //opening file andloading the 4 rows into the arraylist
        try(BufferedReader br=new BufferedReader(new FileReader(TARRIF_TAX_FILE_PATH)))
        {
            String line;
            
            boolean flag=false;
            //System.out.println("line");
            while((line=br.readLine())!=null)
            {
                String[] fields=line.split(",");  
                //System.out.println("Readingline: "+line);
                MeterType meterType=MeterType.valueOf(fields[0].toUpperCase());
                CustomerType custType = CustomerType.valueOf(fields[1].toUpperCase());
                
                BigDecimal regularUnitPrice=new BigDecimal(fields[2]);
                
                BigDecimal peakHourUnitPrice=null;
                if(!fields[3].isEmpty())
                {
                    peakHourUnitPrice=new BigDecimal(fields[3]);
                }
                float percentageOfTax=Float.parseFloat(fields[4]);
                int fixedCharges=Integer.parseInt(fields[5]);
                    
                TarrifTax tarrifTax=new TarrifTax(meterType,custType,regularUnitPrice,peakHourUnitPrice,percentageOfTax,fixedCharges);
                
                 flag=!flag;   
                 tarrifList.add(tarrifTax);
            }
        }
        catch(IOException e)
        {
            System.out.println("Error Loading the TarrifTax File: "+ e.getMessage());
        }
        
        return tarrifList;
    }
    
    
    public void saveData(ArrayList<TarrifTax> tarrifList)
    {
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(TARRIF_TAX_FILE_PATH)))
        {
            for(TarrifTax tarrif:tarrifList)
            {
                //converting everything back to string and saving it again
                bw.write(tarrif.getMeterType()+","+tarrif.getCustType()+","+tarrif.getRegularUnitPrice()+","+(tarrif.getPeakHourUnitPrice()!=null?tarrif.getPeakHourUnitPrice():"")+","+tarrif.getPercentageOfTax()+","+tarrif.getFixedCharges());
                bw.newLine();
            }
        }
        catch(IOException e)
        {
            System.out.println("Error Saving data in TarrifTaxFile"+e.getMessage());
        }
        
    }
}
