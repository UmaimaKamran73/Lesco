/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.mycompany.a1_scd_22l7942.A1_SCD_22l7942;
import static com.mycompany.a1_scd_22l7942.A1_SCD_22l7942.generateCustomerId;
import com.mycompany.a1_scd_22l7942.BillStatus;
import com.mycompany.a1_scd_22l7942.Billing;
import com.mycompany.a1_scd_22l7942.BillingDataAccess;
import com.mycompany.a1_scd_22l7942.Customer;
import com.mycompany.a1_scd_22l7942.CustomerType;
import com.mycompany.a1_scd_22l7942.Employee;
import com.mycompany.a1_scd_22l7942.EmployeeDataAccess;
import com.mycompany.a1_scd_22l7942.FileCustomerDataAccess;
import com.mycompany.a1_scd_22l7942.MeterType;
import com.mycompany.a1_scd_22l7942.Nadra;
import com.mycompany.a1_scd_22l7942.NadraDataAccess;
import com.mycompany.a1_scd_22l7942.TarrifTax;
import com.mycompany.a1_scd_22l7942.TarrifTaxDataAccess;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author LENOVO
 */
public class EmployeeModel 
{
     private FileCustomerDataAccess customerFile;
    private NadraDataAccess nadraFile;
    private BillingDataAccess billingFile;
    private TarrifTaxDataAccess tariffFile;
    private EmployeeDataAccess employeeFile;
    
    private List<Customer> customerList;
    private List<Nadra> nadraList;
    private List<Billing> billingList;
    private List<TarrifTax> tariffList;
    private List<Employee> employeeList;
    private Employee currentEmployee;

    public EmployeeModel() 
    {
        customerFile = new FileCustomerDataAccess();
        nadraFile = new NadraDataAccess();
        billingFile = new BillingDataAccess();
        tariffFile = new TarrifTaxDataAccess();
        employeeFile=new EmployeeDataAccess();
        
        customerList = customerFile.loadAllCustomers();
        nadraList = nadraFile.loadNadraData();
        billingList = billingFile.loadFileData();
        tariffList = tariffFile.loadData();
        
        // Match billing records with customers
        A1_SCD_22l7942.matchBillingRecords(customerList, billingList);
    }
    
    
    public boolean authenticateEmployee(String username, String password) 
    {
       currentEmployee= employeeFile.authenticateEmployee(username, password);//THIS
       if(currentEmployee==null)
       {
           return false;
       }
       return true;
    }
    
    //view Bills Report
    public List<Integer> viewBillsReport() 
    {
        int paid = 0, unpaid = 0;
        // Iterate through the billing list to count paid and unpaid bills
        for (Billing billing : billingList) //THIS
        {
            if (billing.getBillPaidStatus().equals(BillStatus.PAID)) 
            {
                paid++;
            } 
            else if (billing.getBillPaidStatus().equals(BillStatus.UNPAID))
            {
                unpaid++;
            }
        }

        // Create a report list: [total bills, paid bills, unpaid bills]
        List<Integer> report = new CopyOnWriteArrayList<>();
        report.add(billingList.size()); 
        report.add(paid);               
        report.add(unpaid);             

        return report;
    }

    public String addBill(String custID, String currentRegularMeterReading, String currentPeakMeterReading)
    {
        // Just take the current billing month instead
        Month billingMonth = LocalDate.now().getMonth();

        // Check if customer ID exists
        Customer customer = findCustomer(custID);
        if (customer == null)
        {
            return "Error: Customer with ID " + custID + " does not exist.";
        }
        
        // Check if there's already a bill for this customer in the same month
        for (Billing billing : billingList) 
        {
            if (billing.getCustID().equals(custID) && billing.getBillingMonth().equals(billingMonth)) 
            {
                return "Error: Customer ID " + custID + " already has a bill for this month " + billingMonth;
            }
        }
        
        // Get the matching TarrifTax based on customer meter type and customer type
        TarrifTax applicableTarrif = null;
        for (TarrifTax tarrif : tariffList)
        {
            if (tarrif.getMeterType() == customer.getMeterType() && tarrif.getCustType() == customer.getCustType()) 
            {
                applicableTarrif = tarrif;
                break;
            }
        }

        if (applicableTarrif == null) 
        {
            return "Error: No tariff found for this customer type and meter type.";
        }
        
        if(Integer.parseInt(currentRegularMeterReading)<0)
        {
            return "Error: Meter reading cannot be negative.";
        }
        
        LocalDate readingEntryDate = LocalDate.now();
        int regularUnitsConsumed =   Integer.parseInt(currentRegularMeterReading)-customer.getRegularUnitsConsumed();
        BigDecimal regularUnitCost = applicableTarrif.getRegularUnitPrice().multiply(BigDecimal.valueOf(regularUnitsConsumed));
        
        if (regularUnitsConsumed < 0) 
        {
             return "Error: Current regular meter reading cannot be less than the previous meter reading.";
        }

        // Only if the meter type is 3 phase then peak meter reading will be taken else it will not be considered
        BigDecimal peakUnitCost = BigDecimal.ZERO;
        if (customer.getMeterType() == MeterType.THREE_PHASE) 
        {
            int peakUnitsConsumed = Integer.parseInt(currentPeakMeterReading) - customer.getPeakHourUnitsConsumed();
            peakUnitCost = applicableTarrif.getPeakHourUnitPrice().multiply(BigDecimal.valueOf(peakUnitsConsumed));
            if (peakUnitsConsumed < 0) 
            {
                return "Error: Current peak meter reading cannot be less than the previous peak meter reading.";
            }
        }

        BigDecimal electricityCost = regularUnitCost.add(peakUnitCost);
        
        // Calculate tax and total billing amount
        BigDecimal taxAmount = electricityCost.multiply(BigDecimal.valueOf(applicableTarrif.getPercentageOfTax() / 100));
        BigDecimal totalBillingAmount = electricityCost.add(taxAmount)
                .add(BigDecimal.valueOf(applicableTarrif.getFixedCharges()));

        BigDecimal fixedCharges = BigDecimal.valueOf(applicableTarrif.getFixedCharges());
        LocalDate dueDate = readingEntryDate.plusDays(14);

        Billing newBilling = new Billing(
            custID, 
            billingMonth, 
            Integer.parseInt(currentRegularMeterReading), 
            Integer.parseInt(currentPeakMeterReading), 
            readingEntryDate, 
            electricityCost, 
            taxAmount, 
            fixedCharges, 
            totalBillingAmount, 
            dueDate, 
            BillStatus.UNPAID
        );

        customer.addBilling(newBilling); //THIS
        billingList.add(newBilling); //THIS --it iz fine now
        // Save data in file
        billingFile.saveFileData(billingList); //THIS // Make sure you have a save function in BillingDataAccess 
        System.out.println("Billing record added successfully.");
        return "Billing record added successfully.";
    }

    // Find a customer by ID
    public Customer findCustomer(String custID) 
    {
        for (Customer customer : customerList) //THIS
        {
            if (customer.getCustID().equals(custID)) 
            {
                return customer;
            }
        }
        return null;
    }
    //Install New Meter
    public String installNewMeter(String cnic, String name, String address, String phoneNumber, String custType, String meterType) {
        // Validate the CNIC
        if (!isValidCNIC(cnic)) {
            return "Error: Invalid CNIC format! CNIC must be 13 digits.";
        }

        // Check if the customer can install more meters
        if (!canInstallMeter(cnic, customerList)) {
            return "Error: Not allowed! Maximum 3 meters allowed per CNIC.";
        }

        // Create a new customer if the customer does not exist
        Customer newCustomer = createNewCustomer(cnic, name, address, phoneNumber, custType, meterType);
        if (newCustomer != null) {
            customerList.add(newCustomer); //THIS
            customerFile.saveAllCustomers(customerList); //THIS
            return "New meter installed successfully.";
        } 
        else
        {
            return "Error: Unable to install new meter.";
        }
    }
    
    public boolean isValidCNIC(String input)
    {
        if(input.length()!=13)
        {
            return false;
        }
        
        for(char c: input.toCharArray())
        {
            if(!Character.isDigit(c))
            {
                return false;
            }
        }
        return true;
    }
    
    public static boolean canInstallMeter(String CNIC,List<Customer> customers)
    {
        int meterCount=0;
        
        for(Customer customer: customers) //THIS
        {
            if(customer.getCNIC().equals(CNIC))
            {
                meterCount++;
            }
        }
        return meterCount<3;
    }
    
    
    public Customer createNewCustomer(String cnic, String name, String address, String phoneNumber, String custType, String meterType) {
        
        /*// Check if a customer with this CNIC already exists
        for (Customer customer : customerList) 
        {
            if (customer.getCNIC().equals(cnic)) 
            {
                return null; // Customer already exists
            }
        } */

        // Create a new customer
        //Customer newCustomer = new Customer();
        //newCustomer.setCNIC(cnic);
        //newCustomer.setCustID(generateCustomerId(customerList)); // Method to generate a unique customer ID
        //newCustomer.setCustName(name);
        //newCustomer.setCustAddress(address);
        //newCustomer.setPhoneNumber(phoneNumber);
        //newCustomer.setCustType(CustomerType.valueOf(custType.toUpperCase()));
        MeterType mType;
        if(meterType.equals("Single Phase"))
        {
            mType=MeterType.SINGLE_PHASE;
        }
        else
        {
            mType=MeterType.THREE_PHASE;
        }
            
        //newCustomer.setMeterType(mType);
        //newCustomer.setConnectionDate(LocalDate.now());
        LocalDate connectionDate = LocalDate.now();  // Assuming you have a method to get the current date in DD/MM/YYYY format

        //setting them to zero initially
        //newCustomer.setPeakHourUnitsConsumed(0);
        //newCustomer.setRegularUnitsConsumed(0);
        return new Customer(generateCustomerId(customerList), cnic, name, address, phoneNumber, CustomerType.valueOf(custType.toUpperCase()), mType, connectionDate, 0, 0);
        //return newCustomer;
    }
    
    public static String generateCustomerId(List<Customer> customerList)
     {
         int maxId = 1000;  // Start with the lowest possible 4-digit ID

        // Find the highest existing customer ID
        for (Customer customer : customerList) //THIS
        {
            int custId = Integer.parseInt(customer.getCustID());
            if (custId > maxId)
            {
                maxId = custId;
            }
        }

        // Generate the next ID in sequence
        int nextId = maxId + 1;

        // Ensure it's a 4-digit number and not exceeding 9999
        if (nextId > 9999)
        {
            throw new IllegalArgumentException("Customer ID limit reached.");
        }

        // Return the next ID as a String
        return String.format("%04d", nextId);
    }

    //change password
    public String getUsername() 
    {
        return currentEmployee.getUsername();
    }
    public String getPassword()
    {
        return currentEmployee.getPassword();
    }
    public void changePassword(String newPassword)
    {
        currentEmployee.setPassword(newPassword);
       employeeFile.updatePassword(currentEmployee.getUsername(), newPassword); //THIS
    }
    
    //View Tarrif Info

    public List<TarrifTax> getTarrifList() 
    {
        return tariffList;
    }

    
    //edit Tarrif Info
    public void updateTarrif(TarrifTax updatedTarrif) 
    {
        for (int i = 0; i < tariffList.size(); i++)
        {
            TarrifTax tarrif = tariffList.get(i);
            if (tarrif.getMeterType().equals(updatedTarrif.getMeterType()) &&
                tarrif.getCustType().equals(updatedTarrif.getCustType())) 
            {
                tariffList.set(i, updatedTarrif);
                tariffFile.saveData(tariffList);  // Save updated list back to the file //THIS
                return;
            }
        }
    }

    
    //view Customers
    public List<Customer> getCustomerList() 
    {
        return customerList;
    }

    //report of expiring cnics
    public List<Nadra> getExpiringCNICList() 
    {
        List<Nadra> expiringCNICList=new CopyOnWriteArrayList<>();
        //"\tThe folloing are CNICs expiring within the next 30 days: ");
        for(Nadra n:nadraList) //THIS
        {
            //checking cnic expiry date
            if(n.isCNICExpiringIn30Days())
            {
                expiringCNICList.add(n); 
            }
        }
        return expiringCNICList;
    }

     public List<Billing> getBillsWithCustomerInfo()
     {
        List<Billing> billsWithCustomerInfo = new CopyOnWriteArrayList<>();
    
        for (Billing bill : billingList)  //THIS
        {
            for (Customer customer : customerList)  //THIS
            {
                if (customer != null && customer.getCustID().equals(bill.getCustID())) 
                {
                    bill.setCustomerName(customer.getCustName()); //THIS
                    billsWithCustomerInfo.add(bill);
                    break; // Found the correct customer, exit inner loop
                }
            }
        }
        return billsWithCustomerInfo;
        
     }
    // Check if the bill is the most recent or unpaid
    public boolean isMostRecentOrUnpaid(Billing bill) 
    {
        return isMostRecent(bill) || bill.getBillPaidStatus().equals(BillStatus.UNPAID);
    }

    // Check if the bill is the most recent
    public boolean isMostRecent(Billing bill)
    {
        for (Billing b : billingList)  //THIS
        {
            if (b.getCustID().equals(bill.getCustID()))
            {
                if (b.getBillingMonth().compareTo(bill.getBillingMonth()) < 0)//!b.getBillingMonth().isAfter(bill.getBillingMonth()))
                {
                    continue;
                } 
                else 
                {
                    return false;
                }
            }
        }
        return true;
    }

    // Update the bill status to 'Paid'
    public boolean updateBillStatus(String custID, Month billingMonth)
    {
        Billing billingRecord = findBillingRecord(custID, billingMonth); //THIS
        if (billingRecord != null && billingRecord.getBillPaidStatus().equals(BillStatus.UNPAID))
        {
            billingRecord.setBillPaidStatus(BillStatus.PAID); //THIS
            billingRecord.setBillPaymentDate(LocalDate.now()); 

            // Save updated billing list to file
            billingFile.saveFileData(billingList); //THIS
            return true;
        }
        return false;
    }

    // Delete a bill record
    public boolean deleteBillRecord(String custID, Month billingMonth)
    {
        Billing billingRecord = findBillingRecord(custID, billingMonth);
        if (billingRecord != null)// && isMostRecent(billingRecord)) 
        {
            billingList.remove(billingRecord); //THIS
            billingFile.saveFileData(billingList); //THIS
            return true;
        }
        return false;
    }

    // Helper function to find the billing record
    public Billing findBillingRecord(String custID, Month billingMonth) 
    {
        for (Billing bill : billingList) //THIS
        {
            if (bill.getCustID().equals(custID) && bill.getBillingMonth().equals(billingMonth)) 
            {
                return bill;
            }
        }
        return null;
    }
    
    public TarrifTax getTarrifAt(int index) 
    {
    if (index >= 0 && index < tariffList.size()) {
        return tariffList.get(index); // Assuming tarrifList is your ArrayList<TarrifTax>
    }
    return null; // Handle out-of-bounds cases
}

    public void editCustomer(Customer updatedCustomer)
    {
        //go through the arraylist. edit the customer with the customer ID (parameter one)
        //save the customers back into the file
        // Find the customer with the matching ID and update it
        for (int i = 0; i < customerList.size(); i++) 
        {
            Customer currentCustomer = customerList.get(i); //THIS
            if (currentCustomer.getCustID().equals(updatedCustomer.getCustID())) 
            {
                // Replace the old customer with the updated one
                customerList.set(i, updatedCustomer);
                break; // Stop the loop once the customer is found and updated
            }
        }

        // Save the updated customer list back to the file
        customerFile.saveAllCustomers(customerList); //THIS
    }

}
