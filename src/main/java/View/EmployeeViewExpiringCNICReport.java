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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.mycompany.a1_scd_22l7942.Nadra;

import Controller.EmployeeController;


public class EmployeeViewExpiringCNICReport extends JFrame {

   /* JLabel jlTitle;
    private JButton jbBackToMainMenu;
    private DefaultTableModel model;
    EmployeeController empController;
    private JTable table;
    List<Nadra> data;
    private JTextField searchBar;*/
    
  /*  public EmployeeViewExpiringCNICReport(EmployeeController empController) 
    {
        this.empController=empController;
        data=empController.getExpiringCNICList();*/
    public JLabel jlTitle;
    public JButton jbBackToMainMenu;
    public DefaultTableModel model;
    public EmployeeController empController;
    public JTable table;
    public List<Nadra> data;
    public JTextField searchBar;

    public EmployeeViewExpiringCNICReport(EmployeeController empController) 
    {
        this.empController = empController;
        data = empController.getExpiringCNICList();
        init();
        setVisible(true);
    }

    public void init() {
        setBounds(100, 100, 400, 200);
        setLayout(new BorderLayout());
        setTitle("Expiring CNICs Report");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle = new JLabel("EXPIRING CNICs REPORT");
        header.add(jlTitle, BorderLayout.CENTER);

        // Search Bar
        JPanel jpSearchBar = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        searchBar = new JTextField(20);
        searchBar.setText("Search...");
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });

        jpSearchBar.add(searchBar);

        header.add(jpSearchBar, BorderLayout.AFTER_LAST_LINE); // Add search field to the header

        // body
        // center ie table
        model = new DefaultTableModel(new Object[] { "Customer ID", "CNIC", "Expiry Date" }, 0);
        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);

        populateTable();

        // south having button
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbBackToMainMenu = new JButton("BACK TO MAIN MENU");
        jbBackToMainMenu.addActionListener(e -> handleBackToMainMenu());
        south.add(jbBackToMainMenu);

        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        // pack();
        setLocationRelativeTo(null);

    }

    public void populateTable() {
        for (Nadra nadra : data) {
            model.addRow(new Object[] { nadra.getCustID(), nadra.getCNIC(), nadra.getExpiryDate() });
        }
    }

    public void handleBackToMainMenu() {
        setVisible(false);
        dispose();
        new EmployeeMenu(empController);
    }

    // filter table
    public void filterTable() {
        String searchText = searchBar.getText().trim().toLowerCase(); // Get the search text and make it lowercase
        model.setRowCount(0); // Clear the current table

        // Iterate through the list of Nadra objects
        for (Nadra nadra : data) {
            // Convert each field to lowercase for case-insensitive search
            String custID = nadra.getCustID().toLowerCase();
            String cnic = nadra.getCNIC().toLowerCase();
            String expiryDate = nadra.getExpiryDate().toString().toLowerCase(); // Assuming expiryDate is a date object,
                                                                                // convert it to a string

            // Check if any of the fields contain the search text
            if (custID.contains(searchText) || cnic.contains(searchText) || expiryDate.contains(searchText)) {
                // Add the matching rows back to the table
                model.addRow(new Object[] { nadra.getCustID(), nadra.getCNIC(), nadra.getExpiryDate() });
            }
        }
    }

}
