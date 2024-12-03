/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.mycompany.a1_scd_22l7942.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerModel 
{
    private FileCustomerDataAccess customerFile;
    private NadraDataAccess nadraFile;
    private BillingDataAccess billingFile;
    private TarrifTaxDataAccess tariffFile;

    private List<Customer> customerList;
    private List<Nadra> nadraList;
    private List<Billing> billingList;
    private List<TarrifTax> tariffList;
    private Customer currentCustomer;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CustomerModel() 
    {
        customerFile = new FileCustomerDataAccess();
        nadraFile = new NadraDataAccess();
        billingFile = new BillingDataAccess();
        tariffFile = new TarrifTaxDataAccess();
        
        customerList = customerFile.loadAllCustomers();
        nadraList = nadraFile.loadNadraData();
        billingList = billingFile.loadFileData();
        tariffList = tariffFile.loadData();

        // Match billing records with customers
        A1_SCD_22l7942.matchBillingRecords(customerList, billingList);
    }

    public CustomerModel(FileCustomerDataAccess customerFile, NadraDataAccess nadraFile, BillingDataAccess billingFile, TarrifTaxDataAccess tariffFile, List<Customer> customerList, List<Nadra> nadraList, List<Billing> billingList, List<TarrifTax> tariffList) 
    {
         this.customerFile= customerFile;
        this.nadraFile = nadraFile ;
        this.billingFile = billingFile;
        this.tariffFile = tariffFile;
        
        this.customerList = customerList;
        this.nadraList = nadraList;
        this.billingList = billingList;
        this.tariffList = tariffList;
    }

    public boolean authenticateCustomer(String custID, String CNIC) 
    {
        for (Customer customer : customerList)
        {
            if (customer.getCustID().equals(custID) && customer.getCNIC().equals(CNIC))
            {
                currentCustomer = customer;
                return true; // Login successful
            }
        }
        return false; // Invalid credentials
    }

    public String getCurrentCustomerCNIC() 
    {
        return (currentCustomer != null) ? currentCustomer.getCNIC() : null;
    }

    public LocalDate getExpiryDate() 
    {
        if (currentCustomer != null)
        {
            String customerCNIC = currentCustomer.getCNIC();
            for (Nadra nadra : nadraList) 
            {
                if (nadra.getCNIC().equals(customerCNIC)) 
                {
                    return nadra.getExpiryDate();
                }
            }
        }
        return null;
    }

    public boolean updateCNICExpiryDate(String newExpiryDate)
    {
        if (currentCustomer != null) 
        {
            LocalDate expiryDate = parseDate(newExpiryDate);
            if (expiryDate != null && expiryDate.isAfter(LocalDate.now())) 
            {
                boolean isUpdated = nadraFile.updateCNICExpiryDate(nadraList, currentCustomer.getCNIC(), expiryDate);
                if (isUpdated) 
                {
                    customerFile.saveAllCustomers(customerList);
                    return true;
                }
            }
            //else
            //{
              //  return "Error: The date should be in the future";
            //}
        }
        return false;
    }

    private static LocalDate parseDate(String dateString)
    {
        try 
        {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } 
        catch (DateTimeParseException e)
        {
            return null;
        }
    }

    public Map<String, String> viewCurrentBill(int meterReading, MeterType meterType)
    {
        Map<String, String> billDetails = new ConcurrentHashMap<>();
        
        if (currentCustomer == null)
        {
            billDetails.put("error", "Customer not found.");
            return billDetails;
        }
        if(currentCustomer.getMeterType()!=meterType)
        {
            billDetails.put("error", "Meter Type mismatch");
            return billDetails;
        }

        Billing latestBill = null;
        for (Billing bill : billingList)
        {
            if (bill.getCustID().equals(currentCustomer.getCustID()))
            {
                latestBill = bill;
                break;
            }
        }

        int regularUnitsConsumed = meterReading - currentCustomer.getRegularUnitsConsumed();
        if (regularUnitsConsumed < 0) 
        {
            billDetails.put("error", "Current regular meter reading cannot be less than the previous reading.");
            return billDetails;
        }

        int peakHourUnitsConsumed = 0;
        if (meterType.equals(MeterType.THREE_PHASE))
        {
            peakHourUnitsConsumed = currentCustomer.getPeakHourUnitsConsumed();
        }

        TarrifTax tariff = getTarrif(currentCustomer.getMeterType(), currentCustomer.getCustType());
        if (tariff == null) 
        {
            billDetails.put("error", "No tariff information found.");
            return billDetails;
        }

        BigDecimal regularUnitCost = tariff.getRegularUnitPrice().multiply(BigDecimal.valueOf(regularUnitsConsumed));
        BigDecimal peakUnitCost = (meterType.equals(MeterType.THREE_PHASE)) ? tariff.getPeakHourUnitPrice().multiply(BigDecimal.valueOf(peakHourUnitsConsumed)) : BigDecimal.ZERO;
        BigDecimal electricityCost = regularUnitCost.add(peakUnitCost);
        BigDecimal taxAmount = electricityCost.multiply(BigDecimal.valueOf(tariff.getPercentageOfTax() / 100));
        BigDecimal fixedCharges = BigDecimal.valueOf(tariff.getFixedCharges());
        BigDecimal totalAmountDue = electricityCost.add(taxAmount).add(fixedCharges);
        totalAmountDue = totalAmountDue.setScale(2, RoundingMode.HALF_UP);  // Round to 2 decimal places
        taxAmount = taxAmount.setScale(2, RoundingMode.HALF_UP);  // Round to 2 decimal places

        
        billDetails.put("CustomerID", currentCustomer.getCustID());
        billDetails.put("CustomerName", currentCustomer.getCustName());
        billDetails.put("Address", currentCustomer.getCustAddress());
        billDetails.put("PhoneNumber", currentCustomer.getPhoneNumber());
        billDetails.put("ElectricityCost", electricityCost.toString());
        billDetails.put("Tax", taxAmount.toString());
        billDetails.put("TotalAmountDue", totalAmountDue.toString());
        billDetails.put("DueDate", latestBill.getDueDate().toString());
        billDetails.put("PaymentStatus", latestBill.getBillPaidStatus().toString());
        billDetails.put("RegularUnitsConsumed", String.valueOf(regularUnitsConsumed));
        billDetails.put("PeakHourUnitsConsumed", String.valueOf(peakHourUnitsConsumed));
        billDetails.put("FixedCharges", fixedCharges.toString());
        billDetails.put("MeterType", currentCustomer.getMeterType().toString());
        billDetails.put("CustomerType", currentCustomer.getCustType().toString());

        
        return billDetails;
    }

    private TarrifTax getTarrif(MeterType meterType, CustomerType customerType) 
    {
        for (TarrifTax tariff : tariffList) 
        {
            if (tariff.getMeterType().equals(meterType) && tariff.getCustType().equals(customerType)) 
            {
                return tariff;
            }
        }
        return null;
    }

    public List<TarrifTax> getTarrifList() 
    {
        return tariffList;
    }

   
}
