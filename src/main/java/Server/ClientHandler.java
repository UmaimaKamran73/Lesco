
package Server;

import Controller.CustomerController;
import Controller.EmployeeController;
import com.mycompany.a1_scd_22l7942.Billing;
import com.mycompany.a1_scd_22l7942.Customer;
import com.mycompany.a1_scd_22l7942.MeterType;
import com.mycompany.a1_scd_22l7942.Nadra;
import com.mycompany.a1_scd_22l7942.TarrifTax;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable
{

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    CustomerController customerController;
    EmployeeController employeeController;
    String client;
    
    public ClientHandler(Socket socket,CustomerController customerController,EmployeeController employeeController)
    {
        this.socket=socket;
        this.customerController=customerController;
        this.employeeController=employeeController;
        client="customer";
    }
    @Override
    public void run()
    {
        try
        {
            in=new ObjectInputStream(socket.getInputStream());
            out=new ObjectOutputStream(socket.getOutputStream());
                        
            Object msg;
            while((msg=in.readObject())!=null)
            {
                if(msg instanceof String)
                {
                    String request=(String) msg;
                    System.out.println("New Client Request: "+request);
                    //checkall the conditions that which command is client requesting for and execute them accordingly and send what client needs back
                    if("STOP".equalsIgnoreCase(request))
                    {
                        break;
                    }

                    //String response;=handleRequest(msg,new CustomerController());
                    //out.println(response);
                    if(request.equals("customer"))
                    {
                        client="customer";
                    }
                    else if(request.equals("employee"))
                    {
                        client="employee";
                    }
                    else
                    {
                        //DONE  
                        if(request.startsWith("login")) // Assume "login,username,password,employee or customer?
                        {
                            String[] credentials=request.split(",",3);
                            String username = credentials[1];
                            String password = credentials[2];
                            boolean isAuthenticated;
                            if("customer".equals(client))
                                isAuthenticated = customerController.login(username, password);
                            else
                                isAuthenticated = employeeController.login(username, password);

                            out.writeObject(isAuthenticated);
                        }
                        else if(request.startsWith("getCNIC"))//DONE  
                        {
                            String cnic=customerController.getCNIC();
                            out.writeObject(cnic);
                        }
                        else if(request.startsWith("getExpiryDate"))//DONE  
                        {
                            LocalDate date=customerController.getExpiryDate();
                            out.writeObject(date);
                        }
                        else if(request.startsWith("updateCNICExpiryDate")) //DONE  //Assume updateCNICExpiryDate,newExpiryDate
                        {
                            String[] args=request.split(",",2);
                            String req=args[0];
                            String newCNICExpiryDate=args[1];
                            boolean updated=customerController.updateCNICExpiryDate(newCNICExpiryDate);
                            out.writeObject(updated);
                        }
                        //to work on //done working on it
                        else if(request.startsWith("viewCurrBill")) // Assume "viewCurrentBill,meterreading,meterType"
                        {
                            String[] params = request.split(",", 3); 
                            int meterReading = Integer.parseInt(params[1]);
                            MeterType meterType = MeterType.valueOf(params[2]);
                            Map<String, String> billDetails = customerController.viewCurrBill(meterReading, meterType);
                            out.writeObject(billDetails);
                        } 
                        else if(request.startsWith("getTarrifList"))
                        {
                            List<TarrifTax> tarrifList;
                            if(client.equals("customer"))
                            {
                                tarrifList=customerController.getTarrifList();
                            }
                            else
                            {
                                tarrifList=employeeController.getTarrifList();
                            }
                            out.writeObject(tarrifList);
                        }
                        //now for Employee functions
                        else if(request.startsWith("viewBillsReport"))
                        {
                            List<Integer> billsReport=employeeController.viewBillsReport();
                            out.writeObject(billsReport);
                        }
                        else if(request.startsWith("addBill"))
                        {
                            String[] params=request.split(",",4);

                            String custID=params[1];
                            String currentRegularMeterReading=params[2];
                            String currentPeakMeterReading=params[3];
                            String response=employeeController.addBill(custID, currentRegularMeterReading, currentPeakMeterReading);
                            out.writeObject(response);
                        }
                        else if(request.startsWith("isValidCNIC"))
                        {
                            String[] params=request.split(",",2);
                            String cnic=params[1];
                            boolean isValid=employeeController.isValidCNIC(cnic);
                            out.writeObject(isValid);
                        }
                        else if(request.startsWith("installNewMeter"))
                        {
                            String[] params = request.split(",", 7);

                            String cnic = params[1];
                            String name = params[2];
                            String address = params[3];
                            String phoneNumber = params[4];
                            String custType = params[5];
                            String meterType = params[6];
                            String response = employeeController.installNewMeter(cnic, name, address, phoneNumber, custType, meterType);
                            out.writeObject(response);
                        }
                        else if(request.startsWith("getUsername"))
                        {
                            String username = employeeController.getUsername();
                            out.writeObject(username);  
                        }
                        else if(request.startsWith("changePassword"))
                        {
                            String[] params = request.split(",", 3);
                            String currentPassword = params[1];
                            String newPassword = params[2];
                            employeeController.changePassword(currentPassword, newPassword);
                            //out.writeObject("Password changed successfully");//justaconfirmation if not req then not sending
                        }
                        else if(request.startsWith("getPassword"))
                        {
                            String password = employeeController.getPassword();
                            out.writeObject(password);
                        }
                        else if(request.startsWith("updateTarrif"))
                        {
                             String[] params = request.split(",", 2);
                             TarrifTax tarrif = (TarrifTax) in.readObject(); // Read the TarrifTax object sent by the client
                             employeeController.updateTarrif(tarrif);
                             out.writeObject("Tarrif updated successfully");

                        }
                        else if(request.startsWith("getCustomerList"))
                        {
                            List<Customer> customerList = employeeController.getCustomerList();
                            out.writeObject(customerList);
                        }
                        else if(request.startsWith("getExpiringCNICList"))
                        {
                            List<Nadra> expiringCNICList = employeeController.getExpiringCNICList();
                            out.writeObject(expiringCNICList);
                        }
                        else if(request.startsWith("getBillsList"))
                        {
                             List<Billing> billsList = employeeController.getBillsList();
                            out.writeObject(billsList);
                        }
                        else if(request.startsWith("isMostRecentOrUnpaid"))
                        {
                           Billing bill = (Billing) in.readObject(); // Read the Billing object sent by the client
                           boolean result = employeeController.isMostRecentOrUnpaid(bill);
                           out.writeObject(result);
                        }
                        else if(request.startsWith("isMostRecent"))
                        {
                             Billing bill = (Billing) in.readObject(); // Read the Billing object sent by the client
                            boolean result = employeeController.isMostRecent(bill);
                            out.writeObject(result);
                        } 
                        else if(request.startsWith("updateBillStatus"))
                        {
                            String[] params = request.split(",", 2);
                            String custID = params[1];
                            Month billingMonth = Month.valueOf(params[2].toUpperCase());
                            boolean result = employeeController.updateBillStatus(custID, billingMonth);
                            out.writeObject(result);
                        }
                        else if(request.startsWith("deleteBillRecord"))
                        {
                            String[] params = request.split(",", 2);
                            String custID = params[1];
                            Month billingMonth = Month.valueOf(params[2].toUpperCase());
                            boolean result = employeeController.deleteBillRecord(custID, billingMonth);
                            out.writeObject(result);
                        }
                        else if(request.startsWith("getTarrifAt"))
                        {
                            String[] params = request.split(",", 2);
                            int row = Integer.parseInt(params[1]);
                            TarrifTax tarrif = employeeController.getTarrifAt(row);
                            out.writeObject(tarrif);
                        }
                        else if(request.startsWith("editCustomer"))
                        {
                             Customer customer = (Customer) in.readObject(); // Read the Customer object sent by the client
                             employeeController.editCustomer(customer);
                             out.writeObject("Customer edited successfully");
                        } 
                        else if(request.startsWith("deleteCustomer"))
                        {
                            String[] params = request.split(",", 2);
                            String custID = params[1];
                            boolean result = employeeController.deleteCustomer(custID);
                            out.writeObject(result ? "Customer deleted" : "Failed to delete customer");
                        }

                        else 
                        {
                            out.writeObject("Invalid request!");
                        }
                    }
                    
                }
                
            }
        }
        catch(IOException e)
        {
            System.out.println("Error handling client: "+e.getMessage());
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(IOException e)
            {
                System.out.println("Error closing socket: "+e.getMessage());
            }
            finally
            {
                try
                {
                    socket.close();
                }
                catch(IOException e)
                {
                    System.out.println("Error Closing socket: "+e.getMessage());
                }
                Server.removeClient(this);
                System.out.println("Client disconnected: "+socket);
            }
        }
    }
    

    /*private String handleRequest(String request, CustomerController customerController)
    {
        try
        {
            String[] parts=request.split(",");
            String command=parts[0];
            
            switch(command)
            {
                case "authenticateCustomer":
                    if(parts.length!=3) return "Error: Invalid parameters for authenticateCustomer";
                    String custID=parts[1]; 
                    String CNIC=parts[2];
                    boolean isAuthenticated=customerController.login(custID, CNIC);
                    return isAuthenticated? "Authentication Successful" : "Authentication failed";
                
                case "getCNIC":
                    return customerController.getCNIC();
                    
                case "getExpiryDate":
                    LocalDate expiryDate=customerController.getExpiryDate();
                    return (expiryDate!=null)? expiryDate.toString(): "No expiry date found";
                   
                case "updateCNICExpiryDate":
                    if(parts.length!=2) return "Error: Invalid parameters for updateCNICExpiryDate";
                    String newExpiryDate=parts[1];
                    boolean isUpdated=customerController.updateCNICExpiryDate(newExpiryDate);
                    return isUpdated?"Expiry date updated successfully": "Failed to update expiry date";
                    
                case"viewCurrBill":
                    if(parts.length!=3) return "Error: Invalid parameters for viewCurrBill";
                    int meterReading=Integer.parseInt(parts[1]);
                    MeterType meterType=MeterType.valueOf(parts[2]);
                    Map<String,String> billDetails=customerController.viewCurrBill(meterReading, meterType);
                    return billDetails.toString();
                  
                //the customer ones are done now add the employee ones
                    
                default:
                    return "Error: Unknown Command";
            }
        }
        catch(Exception e)
        {
            return "Error handling request: "+e.getMessage();
        }
    }*/
}