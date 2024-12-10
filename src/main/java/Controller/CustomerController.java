package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mycompany.a1_scd_22l7942.MeterType;
import com.mycompany.a1_scd_22l7942.TarrifTax;

public class CustomerController {
    private String hostName;
    private int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public CustomerController() {
        hostName = "172.20.10.4";
        port = 54321;

        try {
            this.socket = new Socket(hostName, port);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception ex) 
        {
            System.out.println("Error: "+ ex.getMessage());
        }
    
         
    }
    public void setCustomer() 
    {
        try
        {
            String message = "customer";
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

    public boolean login(String custID, String CNIC) {
        try {
            String request = "login," + custID + "," + CNIC;
            out.writeObject(request);
            out.flush();
            Object response = in.readObject();
            return Boolean.TRUE.equals(response);
        } catch (Exception ex) {
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
    }

    public String getCNIC() {
        try {
            out.writeObject("getCNIC");
            out.flush();
            return (String) in.readObject();
        } catch (Exception ex) {
            System.out.println("Error: "+ ex.getMessage());
        }
        return null;
    }

    public LocalDate getExpiryDate() {
        try {
            out.writeObject("getExpiryDate");
            out.flush();
            Object response = in.readObject();
            if (response instanceof LocalDate) {
                return (LocalDate) response;
            }
        } catch (Exception ex) {
            System.out.println("Error: "+ ex.getMessage());
        }
        return null;
    }

    public boolean updateCNICExpiryDate(String newExpiryDate) {
        try {
            String request = "updateCNICExpiryDate," + newExpiryDate;
            out.writeObject(request);
            out.flush();
            Object response = in.readObject();
            return Boolean.TRUE.equals(response);
        } catch (Exception ex) {
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
    }

    public Map<String, String> viewCurrBill(int meterReading, MeterType meterType) {
        try {
            String request = "viewCurrBill," + meterReading + "," + meterType;
            out.writeObject(request);
            out.flush();
            Object response = in.readObject();
            if (response instanceof Map) {
                return (Map<String, String>) response;
            }
        } catch (Exception ex) {
            System.out.println("Error: "+ ex.getMessage());
        }
        return null;
    }

    public List<TarrifTax> getTarrifList() {
        try {
            out.writeObject("getTarrifList");
            out.flush();
            Object response = in.readObject();
            if (response instanceof List) {
                return (List<TarrifTax>) response;
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return List.of();
    }

    public void closeConnection() {
        try {
            out.writeObject("STOP");
            out.flush();
            socket.close();
        } catch (Exception ex) {
            System.out.println("Error: "+ ex.getMessage());
        }
    }
}