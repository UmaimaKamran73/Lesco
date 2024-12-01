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
import javax.swing.JPasswordField;

/**
 *
 * @author LENOVO
 */
public class EmployeeChangePassword extends JFrame
{
    JLabel jlUsername,jlCurrentPassword,jlNewPassword,jlConfirmPassword;
    JPasswordField pfCurrentPassword,pfNewPassword,pfConfirmPassword;
    JButton jbChangePassword;
    JLabel jlTitle;
    EmployeeController empController;
    private JButton jbCancel;
    
    public EmployeeChangePassword(EmployeeController empController) 
    {
        this.empController=empController;
        init();
        setVisible(true);
    }

    private void init()//String username) 
    {
        setBounds(100, 100, 400, 250);
        setTitle("Employee Change Password");
        setLayout(new BorderLayout()); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle = new JLabel("EMPLOYEE CHANGE PASSWORD");
        header.add(jlTitle);
        
        //center
        JPanel center=new JPanel(new GridLayout(4,2,10,10));
        jlUsername=new JLabel("Username: "+empController.getUsername());
        
        jlCurrentPassword=new JLabel("Current Password: ");
        pfCurrentPassword=new JPasswordField();
        
        jlNewPassword=new JLabel("New Password: ");
        pfNewPassword=new JPasswordField();
        
        jlConfirmPassword=new JLabel("Confirm Password: ");
        pfConfirmPassword=new JPasswordField();
        
        center.add(jlUsername);
        center.add(new JLabel());
        
        center.add(jlCurrentPassword);
        center.add(pfCurrentPassword);
        
        center.add(jlNewPassword);
        center.add(pfNewPassword);
        
        center.add(jlConfirmPassword);
        center.add(pfConfirmPassword);
        
        
        //south having a Change Password Button
        JPanel south=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbChangePassword=new JButton("CHANGE PASSWORD");
        jbChangePassword.addActionListener(e->handleChangePassword());
        south.add(jbChangePassword);
        jbCancel=new JButton("CANCEL");
        jbCancel.addActionListener(e->handleCancel());
        south.add(jbCancel);
        
        add(header,BorderLayout.NORTH);
        add(center,BorderLayout.CENTER);
        add(south,BorderLayout.SOUTH);
        
        //pack();
        setLocationRelativeTo(null);
        
    }

    private void handleChangePassword() 
    {
        String currentPassword = new String(pfCurrentPassword.getPassword());
        String newPassword = new String(pfNewPassword.getPassword());
        String confirmPassword = new String(pfConfirmPassword.getPassword());
    
        if(newPassword == null ? confirmPassword != null : !newPassword.equals(confirmPassword))
        {
            JOptionPane.showMessageDialog(this, "Error: New password and confirm password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(!currentPassword.equals(empController.getPassword()))
        {
            JOptionPane.showMessageDialog(this, "Error: Current Password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            empController.changePassword(currentPassword,newPassword);
        
            String msg="Password Changed Successfully.";
            JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false); dispose();
            new EmployeeMenu(empController);
        }
        
        
    }

    private void handleCancel() 
    {
        setVisible(false); dispose();
        new EmployeeMenu(empController);
    }
    
}
