
package Controller;

import Model.EmployeeModel;
import com.mycompany.a1_scd_22l7942.Billing;
import com.mycompany.a1_scd_22l7942.Customer;
import com.mycompany.a1_scd_22l7942.Nadra;
import com.mycompany.a1_scd_22l7942.TarrifTax;
import java.time.Month;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.util.List;


public class EmployeeController 
{
   // private EmployeeModel employeeModel;
    private String hostName;
    private int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public EmployeeController() 
    {
     //  employeeModel=new EmployeeModel();
        hostName = "172.20.10.4";
        port = 54321;

        try 
        {
            this.socket = new Socket(hostName, port);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        }
         catch (Exception ex) 
        {
            System.out.println("Error: "+ ex.getMessage());
        }
    
    }

    public EmployeeController(EmployeeModel employeeModel) 
    {
       // this.employeeModel = employeeModel;
    }
    public void setEmployee() 
    {
        try
        {
            String message = "employee";
            out.writeObject(message);
            out.flush();
        
            // Optionally, read the server's response
            //Object response = in.readObject();
            //System.out.println("Server response: " + response);
        } 
        catch (Exception ex) 
        {
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    public boolean login(String username, String password)
    {
        try
        {
            String message_request="login,"+username+","+password;
            out.writeObject(message_request);
            out.flush();
            Object server_response=in.readObject();
            return Boolean.TRUE.equals(server_response);
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
              //  return employeeModel.authenticateEmployee(username, password);
    }
    
    public List<Integer> viewBillsReport() 
    {
        try
        {
           String message_request="viewBillsReport";
           out.writeObject(message_request);
           out.flush();
           Object server_response=in.readObject();
           if(server_response instanceof List){
            return (List<Integer>)server_response;
           }
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ ex.getMessage());
        }
        return null;
     //   return employeeModel.viewBillsReport();
    }

    public String addBill(String custID, String currentRegularMeterReading, String currentPeakMeterReading)
    {
        try
        {
            String message_request="addBill,"+custID+","+currentRegularMeterReading+","+currentPeakMeterReading;
            out.writeObject(message_request);
            out.flush();
            Object server_response= in.readObject();
            if(server_response instanceof String)
            {
            return (String) server_response;
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ ex.getMessage());
        }
        return null;
      //  return employeeModel.addBill(custID, currentRegularMeterReading, currentPeakMeterReading);
    }

    public boolean isValidCNIC(String cnic) 
    {
        try
        {
            String message_request="isValidCNIC,"+cnic;
            out.writeObject(message_request);
            out.flush();
            Object server_response=in.readObject();
            return Boolean.TRUE.equals(server_response);
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
       
      //  return employeeModel.isValidCNIC(cnic);
    }

    public String installNewMeter(String cnic, String name, String address, String phoneNumber, String custType, String meterType) 
    {
        try
        {
          String message_request = "installNewMeter,"+cnic+","+name+","+address+","+phoneNumber+","+custType+","+meterType;
          out.writeObject(message_request);
          out.flush();
          Object server_response=in.readObject();
          if(server_response instanceof String){
            return (String) server_response;
        }
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ ex.getMessage());
        }
    return null;
     //   return employeeModel.installNewMeter(cnic, name, address,phoneNumber, custType,meterType);
    }

    public String getUsername() 
    {
        try
        {
            String message_request ="getUsername";
            out.writeObject(message_request);
            out.flush();
            Object server_response=in.readObject();
            if(server_response instanceof String)
            {
              return (String) server_response;
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ ex.getMessage());
        }
      return null;
        //return employeeModel.getUsername();
    }

    public void changePassword(String currentPassword, String newPassword) 
    {
        try{
        String message_request="changePassword,"+currentPassword+","+newPassword;
        out.writeObject(message_request);
        out.flush();
        //Object server_response=in.readObject();
        //if(server_response instanceof String)
        //{
           // return (String) server_response; //return a string response like a confirmation typo thing
        //}
        }
        catch(Exception ex){
            System.out.println("Error: "+ ex.getMessage());
        }
      //  employeeModel.changePassword(newPassword);
    }
    
    public String getPassword()
    {
        try{
            String message_request="getPassword";
            out.writeObject(message_request);
            out.flush();
            Object server_response=in.readObject();
            if(server_response instanceof String){
                return (String) server_response; 
            }
            }
            catch(Exception ex){
                System.out.println("Error: "+ ex.getMessage());
            }
            return null;
       // return employeeModel.getPassword();
    }

    public List<TarrifTax> getTarrifList() 
    {
        try{
            String message_request="getTarrifList";
            out.writeObject(message_request);
            out.flush();
            Object server_response=in.readObject();
            if(server_response instanceof List){
             return (List<TarrifTax>)server_response;
            }
         }
         catch(Exception ex){
            System.out.println("Error: "+ ex.getMessage());
         }
         return null;
      //  return employeeModel.getTarrifList();
    }

    public void updateTarrif(TarrifTax tarrif) 
    {
        try{
            String message_request="updateTarrif,"+tarrif;
            out.writeObject(message_request);
            out.flush();
            out.writeObject(tarrif);
            out.flush();
            Object server_response=in.readObject();
            if(server_response instanceof String){
                //return (String) server_response; //return a string response like a confirmation typo thing
            }
            }
            catch(Exception ex){
                System.out.println("Error: "+ ex.getMessage());
            }
      //  employeeModel.updateTarrif(tarrif);
    }

    public List<Customer> getCustomerList() 
    {
        try{
            String message_request="getCustomerList";
            out.writeObject(message_request);
            out.flush();
            Object server_response=in.readObject();
            if(server_response instanceof List){
             return (List<Customer>)server_response;
            }
         }
         catch(Exception ex){
            System.out.println("Error: "+ ex.getMessage());
         }
         return null;
       // return employeeModel.getCustomerList();
    }

    public List<Nadra> getExpiringCNICList() 
    {
        
        try{
            String message_request="getExpiringCNICList";
            out.writeObject(message_request);
            out.flush();
            Object server_response=in.readObject();
            if(server_response instanceof List){
             return (List<Nadra>)server_response;
            }
         }
         catch(Exception ex){
            System.out.println("Error: "+ ex.getMessage());
         }
         return null;
     //   return employeeModel.getExpiringCNICList();
    }

    public List<Billing> getBillsList() 
    {
        
        try{
            String message_request="getBillsList";
            out.writeObject(message_request);
            out.flush();
            Object server_response=in.readObject();
            if(server_response instanceof List){
             return (List<Billing>)server_response;
            }
         }
         catch(Exception ex){
            System.out.println("Error: "+ ex.getMessage());
         }
         return null;
        
       // return employeeModel.getBillsWithCustomerInfo();
    }

   //view bills
    public boolean isMostRecentOrUnpaid(Billing bill) 
    {
        try{
            String message_request="isMostRecentOrUnpaid,"+bill;
            out.writeObject(message_request);
             out.writeObject(bill);
            out.flush();
            Object server_response=in.readObject();
            return Boolean.TRUE.equals(server_response);
        }
        catch(Exception ex){
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
      //  return employeeModel.isMostRecentOrUnpaid(bill);
    }

    public boolean isMostRecent(Billing bill) 
    {
        try{
            String message_request="isMostRecentOrUnpaid,"+bill;
            out.writeObject(message_request);
             out.writeObject(bill);
            out.flush();
            Object server_response=in.readObject();
            return Boolean.TRUE.equals(server_response);
        }
        catch(Exception ex){
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
      //  return employeeModel.isMostRecentOrUnpaid(bill);
    }

    public boolean updateBillStatus(String custID, Month billingMonth) 
    {
        try{
            String message_request="updateBillStatus,"+custID+","+billingMonth;
            out.writeObject(message_request);
            out.flush();
            Object server_response=in.readObject();
            return Boolean.TRUE.equals(server_response);
        }
        catch(Exception ex){
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
     //   return employeeModel.updateBillStatus(custID,billingMonth);
    }

    public boolean deleteBillRecord(String custID, Month billingMonth) 
    {
        try{
            String message_request="deleteBillRecord,"+custID+","+billingMonth;
            out.writeObject(message_request);
            out.flush();
            Object server_response=in.readObject();
            return Boolean.TRUE.equals(server_response);
        }
        catch(Exception ex){
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
       // return employeeModel.deleteBillRecord(custID,billingMonth);
    }

    public TarrifTax getTarrifAt(int row) 
    {
        try{
        String message_request="getTarrifAt,"+row;
        out.writeObject(message_request);
        out.flush();
        Object server_response=in.readObject();
        if(server_response instanceof TarrifTax){
            return (TarrifTax) server_response;
        }
    }
    catch(Exception ex){
        System.out.println("Error: "+ ex.getMessage());
    }
      //  return employeeModel.getTarrifAt(row);
      return null;
    }

    public void editCustomer(Customer customer)
    {
        try{
            String message_request="editCustomer,"+customer;
            out.writeObject(message_request);
            out.writeObject(customer);
            out.flush();
            Object server_response=in.readObject();
            if(server_response instanceof String){
               // return (String) server_response; //return a string response like a confirmation typo thing
            }
            }
            catch(Exception ex){
                System.out.println("Error: "+ ex.getMessage());
            }

     //   employeeModel.editCustomer(customer);
    }

    public boolean deleteCustomer(String custID) 
    {
        //ye kya ha???? //idk myself
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
