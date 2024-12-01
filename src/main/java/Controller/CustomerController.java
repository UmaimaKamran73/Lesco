/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.CustomerModel;
import com.mycompany.a1_scd_22l7942.MeterType;
import com.mycompany.a1_scd_22l7942.TarrifTax;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class CustomerController 
{
    private CustomerModel customerModel;

    public CustomerController() 
    {
        customerModel = new CustomerModel();
    }

    public boolean login(String custID, String CNIC)
    {
        return customerModel.authenticateCustomer(custID, CNIC);
    }

    public String getCNIC() 
    {
        return customerModel.getCurrentCustomerCNIC();
    }

    public LocalDate getExpiryDate() 
    {
        return customerModel.getExpiryDate();
    }

    public boolean updateCNICExpiryDate(String newExpiryDate) 
    {
        return customerModel.updateCNICExpiryDate(newExpiryDate);
    }

    public Map<String, String> viewCurrBill(int meterReading, MeterType meterType) 
    {
        return customerModel.viewCurrentBill(meterReading, meterType);
    }

    public ArrayList<TarrifTax> getTarrifList()
    {
        return customerModel.getTarrifList();
    }
    
    
}
