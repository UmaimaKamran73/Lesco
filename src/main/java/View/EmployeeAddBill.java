/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.EmployeeController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author LENOVO
 */
public class EmployeeAddBill extends JFrame
{
    JLabel jlCustID,jlCurrRegularMeterReading,jlCurrPeakMeterReading;
    JTextField tfCustID,tfCurrRegularMetterReading,tfCurrPeakMeterReading;
    JButton jbAddBill;
    JLabel jlTitle;
    EmployeeController empController;
    private JButton jbCancel;
    public EmployeeAddBill(EmployeeController empController) 
    {
        this.empController=empController;
        init();
        setVisible(true);
    }

    private void init() 
    {
        setBounds(100,100,400,200);
        setLayout(new BorderLayout());
        setTitle("Employee Add Bill");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //header
        JPanel header=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle=new JLabel("ADD BILL");
        header.add(jlTitle,BorderLayout.CENTER);
        
        //center
        JPanel center=new JPanel(new GridLayout(3,2,10,10));
       
        jlCustID=new JLabel("Customer ID:");
        tfCustID=new JTextField();
        jlCurrRegularMeterReading=new JLabel("Current Regular Meter Reading: ");
        tfCurrRegularMetterReading=new JTextField();
        jlCurrPeakMeterReading=new JLabel("Current Peak Meter Reading: ");
        tfCurrPeakMeterReading=new JTextField();
                
        center.add(jlCustID);
        center.add(tfCustID);
        center.add(jlCurrRegularMeterReading);
        center.add(tfCurrRegularMetterReading);
        center.add(jlCurrPeakMeterReading);
        center.add(tfCurrPeakMeterReading);
        
        //south
        JPanel south=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbAddBill=new JButton("ADD BILL");
        jbAddBill.addActionListener(e->handleAddBill());
        south.add(jbAddBill);
        jbCancel=new JButton("CANCEL");
        jbCancel.addActionListener(e->handleCancel());
        south.add(jbCancel);
        
        add(header,BorderLayout.NORTH);
        add(center,BorderLayout.CENTER);
        add(south,BorderLayout.SOUTH);
        
        //pack();
        setLocationRelativeTo(null);
    }
    
    public String getCustID() 
    {
        return tfCustID.getText().trim();
    }

    public String getCurrRegularMeterReading() 
    {
        return tfCurrRegularMetterReading.getText().trim();
    }

    public String getCurrPeakMeterReading()
    {
        return tfCurrPeakMeterReading.getText().trim();
    }

    private void handleAddBill()
    {
        
        String custID = getCustID();
        String currentRegularMeterReading = getCurrRegularMeterReading();
        String currentPeakMeterReading = getCurrPeakMeterReading();
        
        String message;
        if(custID.isEmpty()||currentRegularMeterReading.isEmpty()||currentPeakMeterReading.isEmpty())
        {
            message="Error: Please fill all the enteries";
        }
        else
        {
            message = empController.addBill(custID, currentRegularMeterReading, currentPeakMeterReading);
        }
        
        JOptionPane.showMessageDialog(this, message);

        // Check if the operation was successful
        if (message.startsWith("Billing record added successfully."))
        {
            // Set visibility to false to return to the main menu
            this.setVisible(false); dispose();
            new EmployeeMenu(empController);
        }
    }

    private void handleCancel() 
    {
        setVisible(false); dispose();
        new EmployeeMenu(empController);
    }
    
}
