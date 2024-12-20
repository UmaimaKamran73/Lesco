/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.EmployeeController;

public class EmployeeViewBillsReport extends JFrame {
    public JLabel jlTitle;
    public JLabel jlTotalBills, jlPaidBills, jlUnpaidBills;
    public JButton jbOK;
    public EmployeeController empController;

    public EmployeeViewBillsReport(EmployeeController empController, List<Integer> bills) {
        this.empController = empController;
        init(bills);
        setVisible(true);
    }

    private void init(List<Integer> bills)//String totalBills, String paidBills, String unpaidBills) 
   // private void init(ArrayList<Integer> bills)// String totalBills, String paidBills, String unpaidBills)
    {
        setBounds(100, 100, 400, 200);
        setLayout(new BorderLayout());
        setTitle("Bills Report");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // label of login on north
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle = new JLabel("BILLS REPORT");
        header.add(jlTitle, BorderLayout.CENTER);

        // center having grid
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));

        jlTotalBills = new JLabel("Total Bills: " + bills.get(0));
        jlPaidBills = new JLabel("Paid Bills: " + bills.get(1));
        jlUnpaidBills = new JLabel("Unpaid Bills: " + bills.get(2));

        center.add(jlTotalBills, BorderLayout.CENTER);
        center.add(jlPaidBills, BorderLayout.CENTER);
        center.add(jlUnpaidBills, BorderLayout.CENTER);

        // south having okay button
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbOK = new JButton("OK");
        jbOK.addActionListener(e -> handleOK());
        south.add(jbOK);

        add(header, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        // pack();
        setLocationRelativeTo(null);
    }

    private void handleOK() {
        setVisible(false);
        new EmployeeMenu(empController);
    }

}
