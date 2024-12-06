/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.a1_scd_22l7942;

import Controller.CustomerController;
import Controller.EmployeeController;
import Server.Server;
import View.Welcome;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.logging.Level;
//import java.util.logging.Logger;

public class A1_SCD_22l7942 
{
    public static void displayCustomerMenu()
    {
        System.out.println("--------CUSTOMER MENU----------------");
        System.out.println("1. View Current Bill \t2. Update CNIC Expiry Date \t3. Logout \t");
    }

    public static void displayEmployeeMenu()
    {
        System.out.println("--------EMPLOYEE MENU----------------");
        System.out.println("1. View Report of Paid/Unpaid Bills \t2. View Report of Expiring CNICs \t3. Update Bill Paid Status ");
        System.out.println("4. View Customers \t5. Install New Meter \t6. Update Tarrif Information ");   
        System.out.println("7. View Bill \t8. Change Password \t9. Add Bill");
        System.out.println("10. LogOut");
    }
    
    //checking from customer arraylist
    public static Customer findCustomer(List<Customer> customerList,String CustID,String CNIC)
    {
        for(Customer customer: customerList)
        {
            if(customer.getCustID().equals(CustID)&&customer.getCNIC().equals(CNIC))
            {
                return customer;
            }
        }
        return null;
    }
    
    public static Customer findCustomer(List<Customer> customerList,String custID)
    {
        for(Customer customer: customerList)
        {
            if(customer.getCustID().equals(custID))
            {
                return customer;
            }
        }
        return null;
    }
     
    public static void main(String[] args) 
    {
        Scanner obj=new Scanner(System.in);
        
        Server server=new Server();

        CustomerController custController=new CustomerController();
        EmployeeController empController=new EmployeeController();
        new Welcome(custController,empController);
        
        //all the other code is of previous A1
        //currently the code is running with both CLI and GUI
        //loading all the files data into 3 arrays
         FileCustomerDataAccess dataAccess=new FileCustomerDataAccess();
        EmployeeDataAccess empData=new EmployeeDataAccess();
        NadraDataAccess nadra=new NadraDataAccess();
        BillingDataAccess billing=new BillingDataAccess();
        TarrifTaxDataAccess tarrif=new TarrifTaxDataAccess();
        //NadraFunc nardaFunc=new NadraFunc();
        List<Nadra> nadraList= nadra.loadNadraData();
        List<Customer> customerList=dataAccess.loadAllCustomers();
        List<Billing> billingList=billing.loadFileData();
        matchBillingRecords(customerList,billingList);
        List<TarrifTax> tarrifList=tarrif.loadData();
        
            int choice;
            int menuChoice;
            OUTER:
            while (true) 
            {
                System.out.println("1. Customer\t2. Employee\t3. Exit");
                System.out.println("Your choice(1/2/3): ");
                choice=obj.nextInt();
            OUTER_3: 
            switch (choice) 
            {
                case 1 ->
                {

                        String custID;
                        String CNIC;
                        obj.nextLine();
                        System.out.println("Enter Username(CustID): ");
                        custID=obj.nextLine();
                        System.out.println("Enter Password(CNIC): ");
                        CNIC=obj.nextLine();
                        if(!isValidCNIC(CNIC)||!isValidCustID(custID))
                        {
                            System.out.println("Error: Invalid CNIC or CustID");
                            break;
                        }
                        Customer currCustomer=findCustomer(customerList,custID,CNIC);
                    
                        if (currCustomer==null) 
                        {
                            System.out.println("Error: Customer do not exist");
                            break;
                        }
                        else 
                        {
                            while(true)
                            {
                            //customer logged in
                            displayCustomerMenu();
                            System.out.println("Enter you choice: ");
                            menuChoice=obj.nextInt();
                            OUTER_2:
                            switch (menuChoice) 
                            {
                                case 1 -> 
                                {
                                    int meterType;
                                    MeterType mType;
                                    int currReadings;
                                    System.out.println("\nProvide the following details to generate the Bill: ");
                                    System.out.println("Meter Type(Press: 1 for One-Phase, 2 for Three-Phase)");
                                    meterType=obj.nextInt();
                                    switch (meterType) 
                                    {
                                        case 1 -> mType=MeterType.SINGLE_PHASE;
                                        case 2 -> mType=MeterType.THREE_PHASE;
                                        default -> 
                                        {
                                            System.out.println("Invalid Input");
                                            break OUTER_2;
                                        }
                                    }
                                   
                                    
                                    System.out.println("Current Meter Readings");
                                    currReadings=obj.nextInt();
                                    if(!isPositive(currReadings))
                                    {
                                        System.out.println("Error: Meter Readings cannot be negative");
                                        break;
                                    }
                                    if (currCustomer.getMeterType().equals(mType))
                                    {
                                         //checking the customer type from custID
                                        //currCustomer.viewCurrentBill(currReadings);
                                        viewCurrentBill(currReadings,currCustomer,customerList,billingList,tarrifList);
                                        displayTarrifTax(tarrifList);
                                    }
                                    else 
                                    {
                                        System.out.println("Error: Meter type mismatch");
                                    }
                                }
                                case 2 -> 
                                {
                                    //Update CNIC Expiry Date
                                    String expDateStr;
                                    LocalDate expDate;
                                    obj.nextLine();
                                    //System.out.println("ExpiryDate: ");
                                    //since customer is already logged in we will gonna ask justfor the new expiry date for now
                                    System.out.println("Enter new Expiry Date(dd/MM/yyyy): ");
                                    expDateStr=obj.nextLine();
                                    //chcek if it is in the format of dd/MM/yyyy
                                    if(isValidDateFormat(expDateStr))
                                    {
                                        expDate=parseDate(expDateStr);
                                        if(expDate!=null&&expDate.isAfter(LocalDate.now()))
                                        {
                                            //callthe updateCNICExpiryDate function
                                            boolean success=false;
                                           // expDate=DateTimeFormater.Parse()
                                            success = nadra.updateCNICExpiryDate(nadraList,currCustomer.getCNIC(),expDate);
                                            if(success)
                                            {
                                                System.out.println("CNIC expiry date updated Successfully");
                                                //break;
                                            }
                                            else
                                            {
                                                System.out.println("Error: Could not Update CNIC Expiry Date");
                                                break;
                                            }
                                        }
                                        else
                                        {
                                            System.out.println("Error: The date should be in the future");
                                            break;
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Error: Incorrect Date Formate(dd/mm/yyyy)");
                                        break;
                                    }
                                  
                                }
                                default -> 
                                {
                                    break OUTER_3;
                                }
                            }
                        }
                    }
                    
                }
                case 2 ->
                { 
                    String username;
                    String password;
                    obj.nextLine();
                    System.out.println("Enter Username: ");
                    username=obj.nextLine();
                    System.out.println("Enter Password: ");
                    password=obj.nextLine();
                    if(checkCommas(username)||checkCommas(password))
                    {
                        System.out.println("Error: You cannot use comma in your username or password");
                        break;
                    }
                    Employee employee= empData.authenticateEmployee(username,password);
                    if (employee==null) 
                    {
                        System.out.println("Error: Incorrect username or password");
                        break;
                    } 
                    else
                    {
                        while(true)
                        {
                            displayEmployeeMenu();
                            System.out.println("Enter you choice: ");
                            menuChoice=obj.nextInt();
                            obj.nextLine();
                            switch (menuChoice) 
                            {
                                case 1 -> //View Report of Paid Unpaid Bills
                                {
                                    showBillsReport(billingList);
                                }
                                case 2 -> //View Report of CNIC Expiring
                                {

                                    nadra.checkCNICExpiring(nadraList);
                                }
                                case 3 -> //Update Bill Paid Status
                                {
                                      String custID;  
                                      System.out.println("Unpaid Bills: ");
                                      displayUnpaidBills(billingList); //(whose billstatus is unpaid
                                      System.out.println("Enter CustomerID to update status to Paid: ");
                                      custID=obj.nextLine();
                                      
                                      Customer matchedCustomer=findCustomer(customerList,custID);
                                      
                                      if(matchedCustomer!=null)
                                      {
                                          Billing billingRecord=findBillingRecord(matchedCustomer.getCustID(),billingList);
                                          if(billingRecord!=null&&billingRecord.getBillPaidStatus().equals(BillStatus.UNPAID))
                                          {
                                              billingRecord.setBillPaidStatus(BillStatus.PAID);
                                              billingRecord.setBillPaymentDate(LocalDate.now());  // Update the payment date to current date
                                             // Check meter type and update units consumed accordingly
                                            if (matchedCustomer.getMeterType() == MeterType.SINGLE_PHASE) // Single-phase meter
                                            {
                                                matchedCustomer.setRegularUnitsConsumed(matchedCustomer.getRegularUnitsConsumed() + billingRecord.getCurrentRegularMeterReading());
                                            }
                                            else if (matchedCustomer.getMeterType() == MeterType.THREE_PHASE) // Three-phase meter
                                            {
                                                matchedCustomer.setRegularUnitsConsumed(matchedCustomer.getRegularUnitsConsumed() + billingRecord.getCurrentRegularMeterReading());
                                                matchedCustomer.setPeakHourUnitsConsumed(matchedCustomer.getPeakHourUnitsConsumed() + billingRecord.getCurrentPeakMeterReading());
                                            }

                                              billing.saveFileData(billingList);
                                              dataAccess.saveAllCustomers(customerList);
                                              
                                              System.out.println("Billing status updated to 'Paid' and total units consumed updated.");
                                          }
                                          else
                                          {
                                              System.out.println("No unpaid billing record found for the given Customer ID");
                                          }
                                      }
                                      else
                                      {
                                        System.out.println("Customer not found!");

                                      }
                                      //change statustopaid and save billing info in file too
                                      //also update the customer total units consumed in customer file(also the arraylist) to current meter reading in the billing file
                                }
                                 case 4 -> //View Customers
                                {
                                    displayAllCustomers(customerList);
                                }
                                 case 5 ->  //Update Customer Info(add new meter)
                                {
                                    String CNIC;
                                    System.out.println("Enter CNIC for which the meter is to be installed : ");
                                    CNIC=obj.nextLine();
                                    if(!isValidCNIC(CNIC))
                                    {
                                        System.out.println("Invalid CNIC!");
                                        break;
                                    }
                                    else
                                    {
                                        //if the cnic is valid
                                        if(!canInstallMeter(CNIC,customerList))
                                        {
                                            System.out.println("\tNot Allowed! Maximum 3 meters allowed per CNIC");
                                            break;
                                        }
                                        else
                                        {
                                            /*//check if cust already exists
                                            Customer existingCustomer = findCustomer(customerList,CNIC);
                                            Customer newCustomer;
                                            if(existingCustomer!=null)
                                            {
                                                System.out.println("Customer Already exists. Displaying current meter details: ");
                                              
                                                
                                                newCustomer=createNewCustomer(existingCustomer,customerList);
                                            }
                                            else 
                                            {
                                               
                                            } */
                                            //first also check if the customer already exists.
                                            //if it alreadyexists then just simply take the meter type customer type and address as input and get the other inputs from the previous data already included. also first display the current meters already installed by user before too. if cust is new then simply just take input for all values
                                            //checking if the cnic already exists and have installed 3 meters already
                                            //if true then no meter can be added break;
                                            
                                            Customer newCustomer=createNewCustomer(CNIC,customerList);
                                            if(newCustomer!=null)
                                            {
                                                customerList.add(newCustomer);
                                                dataAccess.saveAllCustomers(customerList);
                                                System.out.println("New meter Installed Successfully");
                                            }
                                            else
                                            {
                                                System.out.println("Error: Unable to install new Meter");
                                                break;
                                            }
                                            
                                           
                                            //else install a new meter and take all the details about the user etc
                                            //make a function which takes all the information and return the Customer objectback and then the customer object is added to the customers arraylist
                                        }
                                       
                                        
                                    }
                                        
                                }
                                 case 6 -> //Update Tarrif Tax File
                                {
                                    displayTarrifTax(tarrifList);
                                    updateTarrifTax(tarrifList);
                                    //saving data back in file
                                    tarrif.saveData(tarrifList);
                                    break;
                                }
                                 case 7 -> //View Bill
                                {
                                    System.out.println("Enter Customer ID: ");
                                    String custID = obj.nextLine();

                                    // Find the customer with the given Customer ID
                                    Customer matchedCustomer = findCustomer(customerList, custID);

                                    if(matchedCustomer != null)
                                    {
                                        // Find the billing record associated with the customer
                                        Billing billingRecord = findBillingRecord(matchedCustomer.getCustID(), billingList);

                                        if(billingRecord != null)
                                        {
                                            // Display the billing information
                                            System.out.println("Billing Information for Customer ID: " + matchedCustomer.getCustID());
                                            System.out.println("Customer Name: " + matchedCustomer.getCustName());
                                            System.out.println("Billing Month: " + billingRecord.getBillingMonth());
                                            System.out.println("Current Regular Meter Reading: " + billingRecord.getCurrentRegularMeterReading());

                                            if (matchedCustomer.getMeterType() == MeterType.THREE_PHASE) // Check if meter is three-phase
                                            {
                                                System.out.println("Current Peak Meter Reading: " + billingRecord.getCurrentPeakMeterReading());
                                            }

                                            System.out.println("Total Amount: " + billingRecord.getTotalBillingAmount());
                                            System.out.println("Due Date: " + billingRecord.getDueDate());
                                            System.out.println("Bill Status: " + billingRecord.getBillPaidStatus());
                                            if (billingRecord.getBillPaidStatus().equals(BillStatus.PAID))
                                            {
                                                System.out.println("Payment Date: " + billingRecord.getBillPaymentDate());
                                            }
                                        }
                                        else
                                        {
                                            System.out.println("No billing record found for the given Customer ID.");
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Customer not found!");
                                    }
                                    
                                }
                                 case 8 -> //change Password
                                {
                                    System.out.println("------Change Password------------");
                                    //obj.nextLine();
                                    System.out.println("Enter Username: ");
                                    username=obj.nextLine();
                                    System.out.println("Enter Current Password: ");
                                    password=obj.nextLine();
                                    System.out.println("Enter New Password: ");
                                    String newPassword=obj.nextLine();

                                    if(username.equals(employee.getUsername())&&password.equals(employee.getPassword()))
                                    {
                                        //changing password
                                        employee.setPassword(newPassword);
                                        if(empData.updatePassword(username, newPassword))
                                        {
                                            System.out.println("\tPassword Updated Successfully");
                                        }
                                        else
                                        {
                                            System.out.println("Error: Unable to Update the password");
                                        }


                                    }
                                    else
                                    {
                                        System.out.println("Error: Username or Password is incorrect");
                                    }

                                }
                                 case 9 ->  //Add in Billing Info
                                {
                                    addNewBillingRecord(billingList,customerList,tarrifList);
                                    billing.saveFileData(billingList);
                                }
                                default -> 
                                {
                                    break OUTER_3;
                                }
                           }
                        }
                    }
                }
                case 3 ->
                {
                    break OUTER;
                }
                default -> System.out.println("Error: Invalid entry(You can only choose 1/2/3)");
            }

            }
            //System.out.println("");
            //return 0;
    }

    private static final DateTimeFormatter DATE_FORMATTER= DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static LocalDate parseDate(String dateString) 
    {
        try
        {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        }
        catch(DateTimeParseException e)
        {
            throw new IllegalArgumentException("Date format should be DD/MM/YYYY");
        }
    }
    
     public static void matchBillingRecords(List<Customer> customers,List<Billing> billingRecords)
    {
        for(Customer customer:customers)
        {
            for(Billing billing: billingRecords)
            {
                if(customer.getCustID().equals(billing.getCustID()))
                {
                    customer.addBilling(billing);
                }
            }
        }
    }
     
    public static void displayUnpaidBills(List<Billing> billingList)
    {
        for(Billing billing:billingList)
        {
            if(billing.getBillPaidStatus().equals(BillStatus.UNPAID))
            {
                System.out.println("CustomerID: "+billing.getCustID()+" Billing Month: "+billing.getBillingMonth()+" Total Amount: "+ billing.getTotalBillingAmount());
            }
        }
    }
    
    public static void displayAllCustomers(List<Customer> customerList)
    {
        if (customerList.isEmpty()) 
        {
            System.out.println("No customers found.");
            return;
        }

        System.out.println("Customers and their meters:");
        for (Customer customer : customerList) 
        {
            System.out.println("Customer ID: " + customer.getCustID());
            System.out.println("Name: " + customer.getCustName());
            System.out.println("Address: " + customer.getCustAddress());
            System.out.println("Phone Number: " + customer.getPhoneNumber());
            System.out.println("Customer Type: " + customer.getCustType());
            System.out.println("Meter Type: " + customer.getMeterType());
            System.out.println("Connection Date: " + customer.getConnectionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Regular Units Consumed: " + customer.getRegularUnitsConsumed());

            if (customer.getMeterType() == MeterType.THREE_PHASE)
            {
                System.out.println("Peak Hour Units Consumed: " + customer.getPeakHourUnitsConsumed());
            }

            System.out.println(); // Print a blank line for better readability
        }
    }
    
    public static void showBillsReport(List<Billing> billingList)
    {
        int paid=0,unpaid=0;
        for(Billing billing:billingList)
        {
            if(billing.getBillPaidStatus().equals(BillStatus.PAID))
                paid++;
            if(billing.getBillPaidStatus().equals(BillStatus.UNPAID))
                unpaid++;
        }
        
        System.out.println("Total Bills: "+billingList.size());
        System.out.println("Paid Bills: "+paid);
        System.out.println("Unpaid Bills: "+unpaid);
    }
     
     public static void viewCurrentBill(int currReadings, Customer currCustomer, List<Customer> customerList, List<Billing> billingList, List<TarrifTax> tariffList)
     {
        if (currCustomer == null) 
        {
            System.out.println("Customer not found.");
            return;
        }

        // Get the latest billing record
        Billing latestBill = null;
        for (Billing bill : billingList)
        {
            if (bill.getCustID().equals(currCustomer.getCustID())) 
            {
                latestBill = bill;
                break;
            }
        }

        // Calculate the usage (regular and peak)
        int regularUnitsConsumed = currReadings - currCustomer.getRegularUnitsConsumed();
        if (regularUnitsConsumed < 0)
        {
            System.out.println("Error: Current regular meter reading cannot be less than the previous reading.");
            return;
        }

    int peakHourUnitsConsumed = 0;
    if (currCustomer.getMeterType() == MeterType.THREE_PHASE) 
    {
        // For three-phase meters, include peak hour consumption
        peakHourUnitsConsumed = currCustomer.getPeakHourUnitsConsumed();
    }

        // Get tariff information
        TarrifTax tariff = getTarrif(currCustomer.getMeterType(), currCustomer.getCustType(), tariffList);
        if (tariff == null) 
        {
            System.out.println("No tariff information found for the given customer type and meter type.");
            return;
        }

        // Calculate costs
        BigDecimal regularUnitCost = tariff.getRegularUnitPrice().multiply(BigDecimal.valueOf(regularUnitsConsumed));
        BigDecimal peakUnitCost = BigDecimal.ZERO;
        if (currCustomer.getMeterType() == MeterType.THREE_PHASE) 
        {
            peakUnitCost = tariff.getPeakHourUnitPrice().multiply(BigDecimal.valueOf(peakHourUnitsConsumed));
        }

        BigDecimal electricityCost = regularUnitCost.add(peakUnitCost);
        BigDecimal taxAmount = electricityCost.multiply(BigDecimal.valueOf(tariff.getPercentageOfTax() / 100));
        BigDecimal fixedCharges = BigDecimal.valueOf(tariff.getFixedCharges());
        BigDecimal totalAmountDue = electricityCost.add(taxAmount).add(fixedCharges);

        // Create the billing view
        System.out.println("\n--- CURRENT BILL ---");
        System.out.println("Customer ID: " + currCustomer.getCustID());
        System.out.println("Customer Name: " + currCustomer.getCustName());
        System.out.println("Address: " + currCustomer.getCustAddress());
        System.out.println("Phone Number: " + currCustomer.getPhoneNumber());
        System.out.println("Customer Type: " + currCustomer.getCustType());
        System.out.println("Meter Type: " + currCustomer.getMeterType());
        System.out.println("Tariff: Regular - " + tariff.getRegularUnitPrice() + ", Peak Hour - " + tariff.getPeakHourUnitPrice());
        System.out.println("Regular Units Consumed: " + regularUnitsConsumed);
        System.out.println("Peak Hour Units Consumed: " + peakHourUnitsConsumed);
        System.out.println("Cost of Electricity: " + electricityCost);
        System.out.println("Tax: " + taxAmount);
        System.out.println("Fixed Charges: " + fixedCharges);
        System.out.println("Total Amount Due: " + totalAmountDue);
        System.out.println("Due Date: " + LocalDate.now().plusDays(14).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("Payment Status: " + (latestBill != null ? latestBill.getBillPaidStatus() : "Unpaid"));
    }
     

    // Sample function to get the tariff data
    private static TarrifTax getTarrif(MeterType meterType, CustomerType customerType, List<TarrifTax> tariffList) 
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

    // Sample isPositive method

    /**
     *
     * @param value
     * @return
     */
    private static boolean isPositive(int value)
    {
        return value > 0;
    }
    
    
    public static void addNewBillingRecord(List<Billing> billingList,List<Customer> customerList,List<TarrifTax> tarrifList)
     {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer ID: ");
        String custID = scanner.nextLine(); 

        //just take the current billing month instead
        Month billingMonth = LocalDate.now().getMonth();

        //also check if customerid exists then the billing month already exists (custID+month)=key
        Customer customer = findCustomer(customerList, custID);
        if (customer == null)
        {
            System.out.println("Error: Customer with ID " + custID + " does not exist.");
            return;
        }
        
        // Check if there's already a bill for this customer in the same month
        for (Billing billing : billingList) 
        {
            if (billing.getCustID().equals(custID) && billing.getBillingMonth().equals(billingMonth)) 
            {
                System.out.println("Error: Customer ID " + custID + " already has a bill for this month " + billingMonth);
                return;
            }
        }
        
        // Get the matching TarrifTax based on customer meter type and customer type
        TarrifTax applicableTarrif = null;
        for (TarrifTax tarrif : tarrifList)
        {
             if (tarrif.getMeterType() == customer.getMeterType() && tarrif.getCustType() == customer.getCustType()) 
             {
                applicableTarrif = tarrif;
                break;
            }
        }
        
        //not gonna happen but just in case
        if (applicableTarrif == null) 
        {
            System.out.println("Error: No tariff found for this customer type and meter type.");
            return;
        }
        
        //type the reading
        System.out.print("Enter Current Regular Meter Reading: ");
        int currentRegularMeterReading = Integer.parseInt(scanner.nextLine());
        if (currentRegularMeterReading < 0)
        {
            System.out.println("Error: Meter reading cannot be negative.");
            return;
        }
        
        LocalDate readingEntryDate = LocalDate.now();
        int regularUnitsConsumed = currentRegularMeterReading - customer.getRegularUnitsConsumed();
        BigDecimal regularUnitCost = applicableTarrif.getRegularUnitPrice().multiply(BigDecimal.valueOf(regularUnitsConsumed));
        
        if (regularUnitsConsumed < 0) 
        {
            System.out.println("Error: Current regular meter reading cannot be less than the previous meter reading.");
            return;
        }
        //only if the meter type is 3 phase then peak meter reading will be taken else it will not be considered ie first check the meter type from the customers ArrayList
       int currentPeakMeterReading = 0;
       BigDecimal peakUnitCost = BigDecimal.ZERO;
        if (customer.getMeterType() == MeterType.THREE_PHASE) 
        {
            System.out.print("Enter Current Peak Meter Reading: ");
            currentPeakMeterReading = Integer.parseInt(scanner.nextLine());
            if (currentPeakMeterReading < 0) 
            {
                System.out.println("Error: Meter reading cannot be negative.");
                return;
            }
            int peakUnitsConsumed = currentPeakMeterReading - customer.getPeakHourUnitsConsumed();
            peakUnitCost = applicableTarrif.getPeakHourUnitPrice().multiply(BigDecimal.valueOf(peakUnitsConsumed));
            if (peakUnitsConsumed < 0) 
            {
                System.out.println("Error: Current peak meter reading cannot be less than the previous peak meter reading.");
                return;
            }
        }
        
        BigDecimal electricityCost = regularUnitCost.add(peakUnitCost);
        
         // Calculate tax and total billing amount
        BigDecimal taxAmount = electricityCost.multiply(BigDecimal.valueOf(applicableTarrif.getPercentageOfTax() / 100));
BigDecimal totalBillingAmount = electricityCost.add(taxAmount)
    .add(BigDecimal.valueOf(applicableTarrif.getFixedCharges()));        //taking values of fixed price, sales tax amount and unit price from tarriftax file list
        
BigDecimal fixedCharges = BigDecimal.valueOf(applicableTarrif.getFixedCharges());
        
        LocalDate dueDate = readingEntryDate.plusDays(14);
        //System.out.println("Due Date: " + dueDate.format(DATE_FORMATTER));

        System.out.println("Due Date: " + dueDate.format(DATE_FORMATTER));
        Billing newBilling = new Billing(
            custID, 
            billingMonth, 
            currentRegularMeterReading, 
            currentPeakMeterReading, 
            readingEntryDate, 
            electricityCost, 
            taxAmount, 
            fixedCharges, 
            totalBillingAmount, 
            dueDate, 
            BillStatus.UNPAID
        );

        customer.addBilling(newBilling);
        billingList.add(newBilling);
        //saving data in file
        //saveFileData(billingList);
        System.out.println("Billing record added successfully.");
    }
    
    public static Billing findBillingRecord(String custID,List<Billing> billingList)
    {
        for(Billing billing:billingList)
        {
            if(billing.getCustID().equals(custID))
            {
                return billing;
            }
        }
        return null;
    }
    
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
        if (peakUnitPrice.compareTo(BigDecimal.ZERO) > 0)
        {
            for (TarrifTax tarrif : tarrifList) {
                if (isMatchingTarrif(meter, cust, tarrif))
                {
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
    
     public static Customer createNewCustomer(String CNIC,List<Customer> customerList)
     {
        Scanner obj=new Scanner(System.in);
        
        System.out.println("Enter Customer Name: ");
        String name = obj.nextLine();

        
        String address =getValidAddress();

        System.out.println("Enter Phone Number: ");
        String phoneNumber = obj.nextLine();
        if(!isValidPhoneNumber(phoneNumber))
        {
            System.out.println("Invalid Phone Number");
            return null;
        }
        
         System.out.println("Enter Customer Type (Domestic/Commercial): ");
        CustomerType customerType;
        try 
        {
            customerType = CustomerType.valueOf(obj.nextLine().toUpperCase());
        } 
        catch (IllegalArgumentException e) 
        {
            System.out.println("Invalid Customer Type. Please enter either 'Domestic' or 'Commercial'.");
            return null;
        }

        System.out.println("Enter Meter Type (SINGLE_PHASE/THREE_PHASE): ");
        MeterType meterType;
        try 
        {
            meterType = MeterType.valueOf(obj.nextLine().toUpperCase());
        } 
        catch (IllegalArgumentException e) 
        {
            System.out.println("Invalid Meter Type. Please enter either 'SINGLE_PHASE' or 'THREE_PHASE'.");
            return null;
        }

        LocalDate connectionDate = LocalDate.now();  // Assuming you have a method to get the current date in DD/MM/YYYY format

        // Create and return the new Customer object
        return new Customer(generateCustomerId(customerList), CNIC, name, address, phoneNumber, customerType, meterType, connectionDate, 0, 0);
     }
    
     public static String generateCustomerId(List<Customer> customerList)
     {
         int maxId = 1000;  // Start with the lowest possible 4-digit ID

        // Find the highest existing customer ID
        for (Customer customer : customerList) 
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
     
    public static String getValidAddress()
    {
        Scanner scanner=new Scanner(System.in);
        String address;
        while(true)
        {
            System.out.println("Enter Address(commas are not allowed): ");
            address=scanner.nextLine();
            if(!checkCommas(address))
            {
                return address;
            }
            System.out.println("Address contains commas. Please enter a valid address without commas");
        }
    }
     

    //validation checks    
    //public static boolean isPositive(int num)
    //{
      //  return num>=0;
    //}
    
    public static boolean isPositive(float num)
    {
        return num>=0;
    }
    
    public static boolean checkCommas(String val)   //checking if the input has delimeter in it
    {
        return val.contains(",");
    }
    
    public static boolean isValidCNIC(String input)
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

     public static boolean isValidCustID(String input)
    {
        if(input.length()!=4)
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
     public static boolean isValidPhoneNumber(String input)
     {
         if(input.length()!=11)
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
     
    public static boolean isValidDateFormat(String dateStr)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try 
        {
            LocalDate.parse(dateStr, formatter);
            return true;
        } 
        catch (DateTimeParseException e)
        {
            return false;
        }
    }
    
    public static boolean canInstallMeter(String CNIC,List<Customer> customers)
    {
        int meterCount=0;
        
        for(Customer customer: customers)
        {
            if(customer.getCNIC().equals(CNIC))
            {
                meterCount++;
            }
        }
        return meterCount<3;
    }
    
}
