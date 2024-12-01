/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.EmployeeController;
import com.mycompany.a1_scd_22l7942.Customer;
import com.mycompany.a1_scd_22l7942.CustomerType;
import com.mycompany.a1_scd_22l7942.MeterType;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author LENOVO
 */
public class EmployeeEditCustomer extends JFrame
{
    EmployeeController empController;
    Customer customer;
   // "CustomreID","Name","Address","Phone","Customer Type","Meter Type","Connection Date","Regular Units Consumed","Peak Hour Units Consumed"
    private JLabel jlTitle;
    JLabel jlCustID,jlName,jlAddress,jlPhone,jlCNIC;
    JLabel jlCustType,jlMeterType;
    JLabel jlConnectionDate;
    JLabel jlRegularUnitsConsumed,jlPeakHourUnitsConsumed;
    
    JTextField tfAddress,tfPhone;
    JTextField tfRegularUnitsConsumed,tfPeakHourUnitsConsumed;
    
    JButton jbUpdate,jbCancel;
    public EmployeeEditCustomer(EmployeeController empController, Customer customer) 
    {
        this.empController = empController;
        this.customer = customer;
        init();
        setVisible(true);
    }

    private void init() 
    {
        setBounds(100, 100, 500, 500);
        setTitle("Edit Customer");
        setLayout(new BorderLayout()); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle = new JLabel("EDIT CUSTOMER");
        header.add(jlTitle);
        
        //center
        JPanel center=new JPanel(new GridLayout(7,2,10,10));
        jlCustID=new JLabel("Customer ID: "+customer.getCustID());
        jlName=new JLabel("Customer Name: "+customer.getCustName());
        
        jlCNIC=new JLabel("CNIC: "+customer.getCNIC());
        jlConnectionDate=new JLabel("Connection Date: "+customer.getConnectionDate());
        
        jlCustType=new JLabel("Customer Type: "+customer.getCustType());
        jlMeterType=new JLabel("Meter Type: "+customer.getMeterType());
        
        jlPhone=new JLabel("Phone Number: ");
        tfPhone=new JTextField(customer.getPhoneNumber());
        
        jlAddress=new JLabel("Address: ");
        tfAddress=new JTextField(customer.getCustAddress());
        
        jlRegularUnitsConsumed=new JLabel("Regular Units Consumed: ");
        tfRegularUnitsConsumed=new JTextField(customer.getRegularUnitsConsumed());
        
        jlPeakHourUnitsConsumed=new JLabel("Peak Hour Units Consumed: ");
        tfPeakHourUnitsConsumed=new JTextField(customer.getPeakHourUnitsConsumed());
        
        center.add(jlCustID);
        center.add(jlName);
        center.add(jlCNIC);
        center.add(jlConnectionDate);
        center.add(jlCustType);
        center.add(jlMeterType);
        center.add(jlPhone);
        center.add(tfPhone);
        center.add(jlAddress);
        center.add(tfAddress);
        center.add(jlRegularUnitsConsumed);
        center.add(tfRegularUnitsConsumed);
        
        // Show Peak Hour Units only if Meter Type is 3-phase
        if (customer.getMeterType().equals(MeterType.THREE_PHASE))
        {
            center.add(jlPeakHourUnitsConsumed);
            center.add(tfPeakHourUnitsConsumed);
        }
        
        //south
        JPanel south=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbUpdate=new JButton("UPDATE");
        jbUpdate.addActionListener(e->handleUpdate());
        south.add(jbUpdate);
        jbCancel=new JButton("CANCEL");
        jbCancel.addActionListener(e->handleCancel());
        south.add(jbCancel);
        
        add(header,BorderLayout.NORTH);
        add(center,BorderLayout.CENTER);
        add(south,BorderLayout.SOUTH);
        
        //pack();
        setLocationRelativeTo(null);
        
    }

    private void handleUpdate() 
    {
        // Check if all fields are filled
        if (tfPhone.getText().isEmpty() || tfAddress.getText().isEmpty() || tfRegularUnitsConsumed.getText().isEmpty() ||
            (customer.getMeterType().equals(MeterType.THREE_PHASE) && tfPeakHourUnitsConsumed.getText().isEmpty())) 
        {
            javax.swing.JOptionPane.showMessageDialog(this, "All fields must be filled.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        String phoneNumber = tfPhone.getText();

        // CheckING if the phone number has 11 digits
        if (phoneNumber.length() != 11 || !phoneNumber.matches("\\d{11}")) 
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter a valid 11-digit phone number.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            tfPhone.requestFocus();
            return;
        }
        // Get the address from the text field
        String address = tfAddress.getText();

        // Checking if the address contains a comma (BECAUSE THE DELIMETER USED IN TEEXT FILES IS COMMA)
        if (address.contains(",")) 
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter a valid address without a comma.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            tfAddress.requestFocus();
            return;
        }

        // Update the customer object with the new values from the text fields
        customer.setPhoneNumber(phoneNumber);
        customer.setCustAddress(address);
        try 
        {
            // Parse the regular and peak units consumed from the text fields
            int regularUnits = Integer.parseInt(tfRegularUnitsConsumed.getText());
            customer.setRegularUnitsConsumed(regularUnits);
            if(customer.getMeterType()==MeterType.THREE_PHASE)
            {
                int peakUnits = Integer.parseInt(tfPeakHourUnitsConsumed.getText());
                customer.setPeakHourUnitsConsumed(peakUnits);
            }

            // Call the controller's method to update the customer in the database or storage
            empController.editCustomer(customer);

            // Show a success message
            javax.swing.JOptionPane.showMessageDialog(this, "Customer updated successfully.");
           
            setVisible(false); dispose();
            new EmployeeViewCustomers(empController);

        } 
        catch (NumberFormatException e) 
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter valid numbers for units consumed.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancel() 
    {
        //back to view customers
        setVisible(false);dispose();
        new EmployeeViewCustomers(empController);
    }
    
}
