/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.EmployeeController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author LENOVO
 */
public class EmployeeInstallNewMeter extends JFrame
{
    JLabel jlCNIC,jlName,jlAddress,jlPhoneNumber,jlCustType,jlMeterType;
    JTextField tfCNIC,tfName,tfAddress,tfPhoneNumber;
    JRadioButton rbSinglePhase, rbThreePhase,rbDomestic,rbCommercial; // Radio buttons for meter type
    ButtonGroup bgMeterType,bgCustType;
    JButton jbInstallMeter;
    JLabel jlTitle;
    EmployeeController empController;
    private JButton jbCancel;
    
    public EmployeeInstallNewMeter(EmployeeController empController) 
    {
        this.empController=empController;
        init();
        setVisible(true);
    }

    private void init() 
    {
        setBounds(100, 100, 400, 300); 
        setLayout(new BorderLayout());
        setTitle("Install New Meter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //header
        JPanel header=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle=new JLabel("INSTALL NEW METER");
        header.add(jlTitle,BorderLayout.CENTER);
       
        //center
        //center will have buttons with flow layout or grid layout
        JPanel center=new JPanel(new GridLayout(8,1,10,10));
        
        jlCNIC = new JLabel("CNIC: ");
        tfCNIC = new JTextField();
        jlName = new JLabel("Name: ");
        tfName = new JTextField();
        center.add(jlCNIC);
        center.add(tfCNIC);
        center.add(jlName);
        center.add(tfName);
        
        jlAddress = new JLabel("Address: ");
        tfAddress = new JTextField();
        jlPhoneNumber = new JLabel("Phone Number: ");
        tfPhoneNumber = new JTextField();
        center.add(jlAddress);
        center.add(tfAddress);
        center.add(jlPhoneNumber);
        center.add(tfPhoneNumber);
        
        jlCustType = new JLabel("Customer Type: ");
        rbDomestic = new JRadioButton("Domestic");
        rbCommercial = new JRadioButton("Commercial");
        bgCustType = new ButtonGroup();
        bgCustType.add(rbDomestic);
        bgCustType.add(rbCommercial);
        
        JPanel jpCustType = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpCustType.add(rbDomestic);
        jpCustType.add(rbCommercial);
        center.add(jlCustType);
        center.add(jpCustType);
        
        jlMeterType=new JLabel("Meter Type: ");
        rbSinglePhase=new JRadioButton("Single Phase");
        rbThreePhase=new JRadioButton("Three Phase");
        bgMeterType=new ButtonGroup();
        bgMeterType.add(rbSinglePhase);
        bgMeterType.add(rbThreePhase);
        
        JPanel jpMeterType = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpMeterType.add(rbSinglePhase);
        jpMeterType.add(rbThreePhase);
        center.add(jlMeterType);
        center.add(jpMeterType);
        
        //south having a Install New Meter Button
        JPanel south=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbInstallMeter=new JButton("INSTALL METER");
        jbInstallMeter.addActionListener(e->handleInstallMeter());
        south.add(jbInstallMeter);
        
        jbCancel=new JButton("CANCEL");
        jbCancel.addActionListener(e->handleCancel());
        south.add(jbCancel);
        
        
        add(header,BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south,BorderLayout.SOUTH);
        
        // Pack and center the window
        pack();
        setLocationRelativeTo(null);
    }

    private void handleInstallMeter() 
    {
        String cnic = tfCNIC.getText().trim();
        String name = tfName.getText().trim();
        String address = tfAddress.getText().trim();
        String phoneNumber = tfPhoneNumber.getText().trim();
        String custType = rbDomestic.isSelected() ? "Domestic" : "Commercial";
        String meterType = rbSinglePhase.isSelected() ? "Single Phase" : "Three Phase";
                
        // Validate the input
        if (cnic.isEmpty() || name.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) 
        {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!empController.isValidCNIC(cnic)) 
        {
            JOptionPane.showMessageDialog(null, "Invalid CNIC format. Must be 13 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Install new meter and show the result
        String message = empController.installNewMeter(cnic,name,address,phoneNumber,custType,meterType);
        
        if (message.startsWith("New meter installed successfully."))
        {
            // Set visibility to false to return to the main menu
            JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
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
