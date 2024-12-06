/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.CustomerController;
import Controller.EmployeeController;

/**
 *
 * @author LENOVO
 */
public class CustomerLogin extends JFrame {
    public JLabel jlCustID, jlCNIC;
    public JTextField tfCustID, tfCNIC;
    public JButton jbLogin;
    public JLabel jlTitle;
    public CustomerController custController;
    public JButton jbCancel;

    public CustomerLogin(CustomerController custController) {
        init();
        setVisible(true);
        this.custController = custController;
    }

    public CustomerLogin() {
        init();
        setVisible(true);
    }

    public void init() {
        setBounds(100, 100, 400, 200);
        setLayout(new BorderLayout());
        setTitle("Customer Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // label of login on north
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle = new JLabel("CUSTOMER LOGIN");
        header.add(jlTitle, BorderLayout.CENTER);

        // center having grid
        JPanel center = new JPanel(new GridLayout(2, 2, 10, 10));

        jlCustID = new JLabel("Customer ID:");
        tfCustID = new JTextField();
        jlCNIC = new JLabel("CNIC: ");
        tfCNIC = new JTextField();

        center.add(jlCustID);
        center.add(tfCustID);
        center.add(jlCNIC);
        center.add(tfCNIC);

        // south having login button
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbLogin = new JButton("LOGIN");
        jbLogin.addActionListener(e -> handleLogin());
        south.add(jbLogin);
        jbCancel = new JButton("CANCEL");
        jbCancel.addActionListener(e -> handleCancel());
        south.add(jbCancel);

        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        // pack();
        setLocationRelativeTo(null);
    }

    public String getCustID() {
        return tfCustID.getText();
    }

    public String getCNIC() {
        return tfCNIC.getText();
    }

    public void handleLogin() {
        String custID = getCustID();
        String CNIC = getCNIC();

        if (custController.login(custID, CNIC)) {
            setVisible(false);
            // Proceed to the customer menu or next page
            new CustomerMenu(custController);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Customer ID or CNIC", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleCancel() {
        setVisible(false);
        dispose();
        new Welcome(new CustomerController(), new EmployeeController());
    }

}
