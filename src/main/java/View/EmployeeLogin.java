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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.CustomerController;
import Controller.EmployeeController;

/**
 *
 * @author LENOVO
 */
public class EmployeeLogin extends JFrame {
    public JLabel jlUsername, jlPassword;
    public JTextField tfUsername;
    public JPasswordField pfPassword;
    public JButton jbLogin;
    public JLabel jlTitle;
    public EmployeeController empController;
    public JButton jbCancel;

    public EmployeeLogin() {

        init();
        setVisible(true);
    }

    public EmployeeLogin(EmployeeController empController) {
        this.empController = empController;
        init();
        setVisible(true);
    }

    private void init() {
        setBounds(100, 100, 400, 200);
        setLayout(new BorderLayout());
        setTitle("Employee Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // label of login on north
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle = new JLabel("EMPLOYEE LOGIN");
        header.add(jlTitle, BorderLayout.CENTER);

        // center having grid
        JPanel center = new JPanel(new GridLayout(2, 2, 10, 10));

        jlUsername = new JLabel("Username:");
        tfUsername = new JTextField();
        jlPassword = new JLabel("Password: ");
        pfPassword = new JPasswordField();

        center.add(jlUsername);
        center.add(tfUsername);
        center.add(jlPassword);
        center.add(pfPassword);

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

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }

    private void handleLogin() {
        String username = getUsername();
        String password = getPassword();

        if (empController.login(username, password)) {
            setVisible(false);
            dispose();
            // Proceed to the employee menu or next page
            new EmployeeMenu(empController);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancel() {
        setVisible(false);
        dispose();
        new Welcome(new CustomerController(), new EmployeeController());
    }

}
