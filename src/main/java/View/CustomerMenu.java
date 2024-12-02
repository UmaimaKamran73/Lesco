/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.CustomerController;
import Controller.EmployeeController;

/**
 *
 * @author LENOVO
 */
public class CustomerMenu extends JFrame {
    public JButton jbViewCurrentBill;
    public JButton jbUpdateCNICExpiryDate;
    public JButton jbLogout;
    public JLabel jlTitle;
    public CustomerController custController;

    // JButton jbHomePage;
    public CustomerMenu() {
        init();
        setVisible(true);
    }

    public CustomerMenu(CustomerController custController) {
        init();
        setVisible(true);
        this.custController = custController;
    }

    public void init() {
        setBounds(100, 100, 500, 300);
        setLayout(new BorderLayout());
        setTitle("Customer Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // north will have heading
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle = new JLabel("CUSTOMER MENU");

        header.add(jlTitle);

        // center will have buttons with flow layout or grid layout
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbViewCurrentBill = new JButton("View Current Bill");
        jbUpdateCNICExpiryDate = new JButton("Update CNIC Expiry Date");
        jbLogout = new JButton("Logout");

        jbViewCurrentBill.addActionListener(e -> handleViewCurrentBill());
        jbUpdateCNICExpiryDate.addActionListener(e -> handleUpdateCNICExpiryDate());
        jbLogout.addActionListener(e -> handleLogout());
        center.add(jbViewCurrentBill);
        center.add(jbUpdateCNICExpiryDate);
        center.add(jbLogout);
        // bottom will have nothing so far

        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        // pack();

        setLocationRelativeTo(null);
    }

    public void handleUpdateCNICExpiryDate() {
        setVisible(false);
        new CustomerUpdateCNICExpiryDate(custController);
    }

    public void handleViewCurrentBill() {
        setVisible(false);
        new CustomerViewCurrentBill1(custController);
    }

    public void handleLogout() {
        setVisible(false);
        new Welcome(new CustomerController(), new EmployeeController());
    }

}
