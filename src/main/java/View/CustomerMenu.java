/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.CustomerController;
import Controller.EmployeeController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author LENOVO
 */
public class CustomerMenu extends JFrame
{
    JButton jbViewCurrentBill;
    JButton jbUpdateCNICExpiryDate;
    JButton jbLogout;
    JLabel jlTitle;
    CustomerController custController;
    //JButton jbHomePage;
    public CustomerMenu() 
    {
        init();
        setVisible(true);
    }
    public CustomerMenu(CustomerController custController) 
    {
        init();
        setVisible(true);
        this.custController=custController;
    }

    private void init() 
    {
        setBounds(100,100,500,300);
        setLayout(new BorderLayout());
        setTitle("Customer Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //north will have heading
        JPanel header=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle=new JLabel("CUSTOMER MENU");
        
        header.add(jlTitle);
        
        //center will have buttons with flow layout or grid layout
        JPanel center=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbViewCurrentBill=new JButton("View Current Bill");
        jbUpdateCNICExpiryDate=new JButton("Update CNIC Expiry Date");
        jbLogout=new JButton("Logout");
        
        jbViewCurrentBill.addActionListener(e->handleViewCurrentBill());
        jbUpdateCNICExpiryDate.addActionListener(e->handleUpdateCNICExpiryDate());
        jbLogout.addActionListener(e->handleLogout());
        center.add(jbViewCurrentBill);
        center.add(jbUpdateCNICExpiryDate);
        center.add(jbLogout);
        //bottom will have nothing so far
        
        add(header,BorderLayout.NORTH);
        add(center,BorderLayout.CENTER);
        
        //pack();
        
        setLocationRelativeTo(null);
    }

    private void handleUpdateCNICExpiryDate() 
    {
        setVisible(false);
        new CustomerUpdateCNICExpiryDate(custController);
    }

    private void handleViewCurrentBill() 
    {
        setVisible(false);
        new CustomerViewCurrentBill1(custController);
    }

    private void handleLogout() 
    {
        setVisible(false);
        new Welcome(new CustomerController(),new EmployeeController());
    }
    
}
