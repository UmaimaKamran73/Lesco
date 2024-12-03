/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.CustomerController;
import Controller.EmployeeController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author LENOVO
 */
public class EmployeeMenu extends JFrame
{
    JButton jbViewBillReport,jbViewExpiringCNICReport;
    JButton jbViewBills,jbViewCustomers;
    JButton jbAddNewMeter,jbAddBill;
    JButton jbChangePassword,jbLogout;
    JButton jbViewTarrifInfo;
    JLabel jlTitle;
    EmployeeController empController;
    
    public EmployeeMenu() 
    {
        init();
        setVisible(true);
    }
    
    public EmployeeMenu(EmployeeController empController) 
    {
        this.empController=empController;
        init();
        setVisible(true);
    }

    private void init() 
    {
        setBounds(100,100,500,300);
        setLayout(new BorderLayout());
        setTitle("Employee Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //north will have heading
        JPanel header=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle=new JLabel("EMPLOYEE MENU");
        
        header.add(jlTitle);
        
        //center will have all the buttons
        JPanel center=new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        //reports buttons
        jbViewBillReport=new JButton("View Bills Report");
        jbViewExpiringCNICReport=new JButton("View Expiring CNICs Report");
        
        jbViewBillReport.addActionListener(e->handleViewBillsReport());
        jbViewExpiringCNICReport.addActionListener(e->handleViewExpiringCNICReport());
        
        //view blah blah buttons(which will have table format
        jbViewBills=new JButton("View Bills");
        jbViewCustomers=new JButton("View Customers");
        jbViewTarrifInfo=new JButton("View Tarrif Information");
        
        jbViewBills.addActionListener(e->handleViewBills());
        jbViewCustomers.addActionListener(e->handleViewCustomers());
        jbViewTarrifInfo.addActionListener(e->handleViewTarrifInfo());
        
        //adding things buttons
        jbAddNewMeter=new JButton("Add New Meter");
        jbAddBill=new JButton("Add Bill");
        
        jbAddNewMeter.addActionListener(e->handleAddNewMeter());
        jbAddBill.addActionListener(e->handleAddBill());
        
        //other buttons
        jbChangePassword=new JButton("Change Password");
        jbLogout=new JButton("Logout");
        
        jbChangePassword.addActionListener(e->handleChangePassword());
        jbLogout.addActionListener(e->handleLogout());
        //adding all the buttons in the center panel
        center.add(jbViewBillReport);
        center.add(jbViewExpiringCNICReport);
        
        center.add(jbViewBills);
        center.add(jbViewCustomers);
        center.add(jbViewTarrifInfo);
        
        center.add(jbAddNewMeter);
        center.add(jbAddBill);
        
        center.add(jbChangePassword);
        center.add(jbLogout);
        
        
        //adding header and center 
        add(header,BorderLayout.NORTH);
        add(center,BorderLayout.CENTER);
        
        //pack();
        setLocationRelativeTo(null);
    }

    private void handleViewExpiringCNICReport() 
    {
        setVisible(false); dispose();
        new EmployeeViewExpiringCNICReport(empController);//empController);
    }

    private void handleViewBillsReport()
    {
        List<Integer> bills=empController.viewBillsReport();
        setVisible(false); dispose();
        new EmployeeViewBillsReport(empController, (ArrayList<Integer>) bills);
    }

    private void handleViewBills() 
    {
        setVisible(false); dispose();
        new EmployeeViewBills(empController);
    }
    
    private void handleViewCustomers() 
    {
        setVisible(false); dispose();
        new EmployeeViewCustomers(empController);
    }

    private void handleViewTarrifInfo() 
    {
        setVisible(false); dispose();
        new EmployeeViewTarrifInfo(empController);
    }

    private void handleAddNewMeter() 
    {
        setVisible(false); dispose();
        new EmployeeInstallNewMeter(empController);
    }

    private void handleAddBill()
    {
        setVisible(false); dispose();
        new EmployeeAddBill(empController);
        
    }

    private void handleChangePassword() 
    {
        setVisible(false); dispose();
        new EmployeeChangePassword(empController);
    }

    private void handleLogout() 
    {
        setVisible(false); dispose();
        new Welcome(new CustomerController(),new EmployeeController());
        //and also close the code
        //show the previous screen of welcome or not is your choice.
    }
    
    
}
