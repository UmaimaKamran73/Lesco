/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.a1_scd_22l7942;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author LENOVO
 */
public class TarrifTaxFunc 
{
     public static void displayTarrifTax(List<TarrifTax> tarrif)
    {
        System.out.println(tarrif.size());
        System.out.println("-------TARRIF TAX INFO-----------");
        for(TarrifTax t:tarrif)
        {
            System.out.println("\nMeter: "+t.getMeterType()+"\tCustomer Type: "+t.getCustType());
            System.out.println("Regular Unit Price: "+t.getRegularUnitPrice()+"\tPeak Hours Unit Price: "+t.getPeakHourUnitPrice());
            System.out.println("Fixed Charges: "+t.getFixedCharges()+"\tPercentage of Tax: "+t.getPercentageOfTax());
        }
    }
    
    public static void updateTarrifTax(List<TarrifTax> tarrifList)
    {
        Scanner obj=new Scanner(System.in);
        int meter,cust;
        System.out.println("Enter metertype(1.onephase/2. three phase):");
        meter=obj.nextInt();
        System.out.println("Enter Customer Type(1. domestic/2. commercial):");
        cust=obj.nextInt();
        if(meter==1)
        {
            System.out.println("1. fixed price\t2. tax percentage\t3. regular unit price"); 
        }
        else
        {
            System.out.println("1. fixed price\t2. tax percentage\t3. regular unit price\t4. peak unit price");
        }
        
        int choice=0;
        System.out.println("What do you want to update: ");
        choice=obj.nextInt();
        if(choice==1)
        {
            updateFixedPrice(meter,cust,tarrifList);
        }
        else if(choice==2)
        {
            updateTaxPercentage(meter,cust,tarrifList);
        }
        else if(choice==3)
        {
            updateRegularUnitPrice(meter,cust,tarrifList);
        }
        
        else if(choice==4&&meter==2)
        {
            updatePeakUnitPrice(meter,cust,tarrifList);
        }
        else
        {
            System.out.println("Invalid input");
        }
        
        //saving data back in file
    }
    public static void updateFixedPrice(int meter, int cust, List<TarrifTax> tarrifList) {
        Scanner obj = new Scanner(System.in);
        System.out.println("Enter new Fixed Price: ");
        int fixedPrice = obj.nextInt();
        if (fixedPrice < 0) 
{
            System.out.println("Fixed Price cannot be negative.");
            return;
        }
            for (TarrifTax tarrif : tarrifList) {
                if (isMatchingTarrif(meter, cust, tarrif)) {
                    tarrif.setFixedCharges(fixedPrice);
                    System.out.println("Fixed Price updated successfully.");
                    return;
                }
            }
        
    }

    public static void updateTaxPercentage(int meter, int cust, List<TarrifTax> tarrifList) {
        Scanner obj = new Scanner(System.in);
        System.out.println("Enter new Tax Percentage: ");
        float taxPercentage = obj.nextFloat();
        
         if (taxPercentage < 0) 
         {
            System.out.println("Tax Percentage cannot be negative.");
            return;
        }
            for (TarrifTax tarrif : tarrifList) {
                if (isMatchingTarrif(meter, cust, tarrif)) {
                    tarrif.setPercentageOfTax(taxPercentage);
                    System.out.println("Tax Percentage updated successfully.");
                    return;
                }
            } 
    }

    public static void updateRegularUnitPrice(int meter, int cust, List<TarrifTax> tarrifList) {
        Scanner obj = new Scanner(System.in);
        System.out.println("Enter new Regular Unit Price: ");
        BigDecimal regularUnitPrice = obj.nextBigDecimal();
        if (regularUnitPrice.compareTo(BigDecimal.ZERO) > 0)
        {
            for (TarrifTax tarrif : tarrifList)
            {
                if (isMatchingTarrif(meter, cust, tarrif))
                {
                    tarrif.setRegularUnitPrice(regularUnitPrice);
                    System.out.println("Regular Unit Price updated successfully.");
                    return;
                }
            }
        } 
        else 
        {
            System.out.println("Invalid input. Regular Unit Price must be greater than 0.");
        }
    }

    public static void updatePeakUnitPrice(int meter, int cust, List<TarrifTax> tarrifList) {
        Scanner obj = new Scanner(System.in);
        System.out.println("Enter new Peak Unit Price: ");
        BigDecimal peakUnitPrice = obj.nextBigDecimal();
        if (peakUnitPrice.compareTo(BigDecimal.ZERO) > 0) {
            for (TarrifTax tarrif : tarrifList) {
                if (isMatchingTarrif(meter, cust, tarrif)) {
                    tarrif.setPeakHourUnitPrice(peakUnitPrice);
                    System.out.println("Peak Unit Price updated successfully.");
                    return;
                }
            }
        } else {
            System.out.println("Invalid input. Peak Unit Price must be greater than 0.");
        }
    }

    private static boolean isMatchingTarrif(int meter, int cust, TarrifTax tarrif) 
    {
        MeterType meterType = (meter == 1) ? MeterType.SINGLE_PHASE : MeterType.THREE_PHASE;
        CustomerType customerType = (cust == 1) ? CustomerType.DOMESTIC : CustomerType.COMMERCIAL;

        return tarrif.getMeterType() == meterType && tarrif.getCustType() == customerType;
    }
}
