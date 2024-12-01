/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.CustomerController;
import com.mycompany.a1_scd_22l7942.MeterType;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author LENOVO
 */
public class CustomerViewCurrentBill1 extends JFrame
{

    JLabel jlMeterType;
    JRadioButton rbSinglePhase, rbThreePhase; // Radio buttons for meter type
    JLabel jlMeterReading;
    JTextField tfMeterReading;
    ButtonGroup bgMeterType;
    JButton jbViewBill;
    JLabel jlTitle;
    CustomerController custController;
    private JButton jbCancel;
    
    public CustomerViewCurrentBill1() 
    {
        init();
        setVisible(true);
    }
    
    public CustomerViewCurrentBill1(CustomerController custController) 
    {
        this.custController=custController;
        init();
        setVisible(true);
    }

    private void init() 
    {
        setBounds(100,100,500,500);
        setLayout(new BorderLayout());
        setTitle("View Bill");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //north will have heading
        JPanel header=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle=new JLabel("VIEW CURRENT BILL");
        
        header.add(jlTitle);
        
        //center will have buttons with flow layout or grid layout
        JPanel center=new JPanel(new GridLayout(3,2,10,10));
        jlMeterType=new JLabel("Meter Type: ");
        rbSinglePhase=new JRadioButton("Single Phase");
        rbThreePhase=new JRadioButton("Three Phase");
        bgMeterType=new ButtonGroup();
        bgMeterType.add(rbSinglePhase);
        bgMeterType.add(rbThreePhase);
        
        JPanel jpMeterType = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpMeterType.add(rbSinglePhase);
        jpMeterType.add(rbThreePhase);
        center.add(jpMeterType);
        
        jlMeterReading=new JLabel("Meter Reading: ");
        tfMeterReading=new JTextField();
        
        
        center.add(jlMeterType);
        center.add(jpMeterType);
        center.add(jlMeterReading);
        center.add(tfMeterReading);
        
        //south having button
        jbViewBill=new JButton("View Bill");
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbViewBill = new JButton("View Bill");
        jbViewBill.addActionListener(e -> handleViewBill()); 
        south.add(jbViewBill);
        jbCancel=new JButton("CANCEL");
        jbCancel.addActionListener(e->handleCancel());
        south.add(jbCancel);
        
        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        
    }

    String getMeterType()
    {
        return rbSinglePhase.isSelected() ? "Single Phase" : "Three Phase";
    }
    
    String getMeterReading()
    {
        return tfMeterReading.getText();
    }
    private void handleViewBill() 
    {
        try 
        {
            String meterType = getMeterType();
            int meterReading = Integer.parseInt(getMeterReading());
            MeterType mType;
            if("Three Phase".equals(meterType))
            {
                mType=MeterType.THREE_PHASE;
            }
            else
            {
                mType=MeterType.SINGLE_PHASE;
            }

            // Fetch the bill details using the controller
            Map<String, String> billDetails = custController.viewCurrBill(meterReading, mType);

            // If billDetails is null or contains an error, display an error message
            if (billDetails == null || billDetails.containsKey("error")) 
            {
                String errorMessage = (billDetails != null && billDetails.containsKey("error")) 
                    ? billDetails.get("error") 
                    : "Invalid input or no bill found.";

                // Show error dialog
                javax.swing.JOptionPane.showMessageDialog(this, errorMessage, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            } 
            else 
            {
                // Hide current window and show the new window with the bill details
                setVisible(false); dispose();
                new CustomerViewCurrentBill2(custController, billDetails);
            }
        } 
        catch (NumberFormatException ex) 
        {
            // Handle the case when meter reading is not a valid integer
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter a valid meter reading.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancel() 
    {
        setVisible(false); dispose();
        new CustomerMenu(custController);
    }
    
}
