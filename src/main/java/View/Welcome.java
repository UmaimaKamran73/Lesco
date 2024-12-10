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
public class Welcome extends JFrame
{
    JLabel jlWelcomeMsg;
    JButton jbCustomer;
    JButton jbEmployee;
    CustomerController custController;
    EmployeeController empController;
    private JButton jbExit;
    //JButton jbExit;
    //choosing between Employee or Customer
    public Welcome(CustomerController custController,EmployeeController empController)
    {
        init();
        //add(jbCustomer);
        //add(jbEmployee);
        //add(jbExit);
        setVisible(true);
        this.custController=custController;
        this.empController=empController;
    }
    
    public Welcome()
    {
        init();
        //add(jbCustomer);
        //add(jbEmployee);
        //add(jbExit);
        setVisible(true);
        //this.custController=custController;
    }

    private void init() 
    {
        setBounds(100,100,400,400);
        //setPrefferedSize();
        //setIcon
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("LESCO BILLING SYSTEM");
        setLayout(new BorderLayout());
        //on north we will have a welcome label and then we will have buttons
        
        JPanel northLabel=new JPanel();
        jlWelcomeMsg=new JLabel("LESCO BILLING SYSTEM");
        //jlWelcomeMsg.setFont();
        //northLabel.setBounds(100, 100, 300, 300);
        northLabel.add(jlWelcomeMsg);
        
        //center Panel with flow layout having3 buttons
        JPanel jpCenterMenu=new JPanel(new FlowLayout());
        
        //buttons
        jbCustomer=new JButton("Customer");
        jbEmployee=new JButton("Employee");
        jbExit=new JButton("Exit");
        
        jbCustomer.addActionListener(e->handleCustomer());
        
        jbEmployee.addActionListener(e->handleEmployee());
        
        jbExit.addActionListener(e->handleExit());
        //set them false in the action listener
        //jbCustomer.setEnabled(false);
        //jbEmployee.setEnabled(false);
        
        jpCenterMenu.add(jbCustomer);
        jpCenterMenu.add(jbEmployee);
        jpCenterMenu.add(jbExit);
        //jpCenterMenu.add(jbExit);
        
        add(northLabel,BorderLayout.NORTH);
        add(jpCenterMenu,BorderLayout.CENTER);
        
        //pack();
        
        setLocationRelativeTo(null);
    }

    private void handleEmployee()
    {
        jbEmployee.setEnabled(false);
        setVisible(false); dispose();
        empController.setEmployee();
        new EmployeeLogin(empController);
    }

    private void handleCustomer() 
    {
         jbCustomer.setEnabled(false);
        setVisible(false); dispose(); //closing this window
        custController.setCustomer();
        new CustomerLogin(custController);
    }

    private void handleExit() 
    {
        dispose();
        System.exit(0);
    }
    
}
