/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.CustomerController;
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
public class CustomerUpdateCNICExpiryDate extends JFrame
{
    JLabel jlCNIC;
    JLabel jlExpiryDate;
    JLabel jlNewExpiryDate;
    //JXDatePicker datePickerNewExpiry;
    JTextField tfNewExpiryDate;
    JButton jbUpdate;
    JLabel jlTitle;
    CustomerController custController;
    private JButton jbCancel;
    
    public CustomerUpdateCNICExpiryDate() 
    {
        //String cnic="1122334455667";
        //String expiryDate="12/06/2025";
        init();//cnic,expiryDate);
        setVisible(true);
    }
    
    public CustomerUpdateCNICExpiryDate(CustomerController custController) 
    {
        //String cnic="1122334455667";
        //String expiryDate="12/06/2025";
        this.custController=custController;
        init();
        setVisible(true);
    }

    private void init()//String cnic, String expiryDate) 
    {
        setBounds(100,100,400,250);
        setLayout(new BorderLayout());
        setTitle("Update CNIC Expiry Date");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //header having the heading and a home/main menu button too
        JPanel header=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle = new JLabel("UPDATE CNIC EXPIRY DATE");
        
        header.add(jlTitle);
        
        //center having the content
        JPanel center=new JPanel(new GridLayout(2,2));
        jlCNIC=new JLabel("CNIC: "+custController.getCNIC());
        jlExpiryDate=new JLabel("Expiry Date: "+custController.getExpiryDate());
        jlNewExpiryDate=new JLabel("New Expiry Date: ");
        tfNewExpiryDate=new JTextField("dd/mm/yyyy");
        center.add(jlCNIC);
        center.add(jlExpiryDate);
        center.add(jlNewExpiryDate);
        center.add(tfNewExpiryDate);
        
        //
        //south having login button
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
    
    String getNewExpiryDate()
    {
        return tfNewExpiryDate.getText();
    }

    private void handleUpdate() 
    {
        String newExpiryDate = getNewExpiryDate();
        boolean isUpdated = custController.updateCNICExpiryDate(newExpiryDate);

        //display msg of updated successfully andddd back to main menu
        if (isUpdated) 
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Expiry date updated successfully!");
            setVisible(false); dispose();
            new CustomerMenu(custController);
        } else 
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Failed to update expiry date.");
        }

        // Optionally, navigate back to the main menu
        //new CustomerMenu();
    }

    private void handleCancel() 
    {
        setVisible(false);
        dispose();
        new CustomerMenu(custController);
        
    }
    
}
