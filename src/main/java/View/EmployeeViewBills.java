/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.EmployeeController;
import com.mycompany.a1_scd_22l7942.BillStatus;
import com.mycompany.a1_scd_22l7942.Billing;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.PopupMenu;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author LENOVO
 */
public class EmployeeViewBills extends JFrame
{
    //will do it at the end with other view things
    JLabel jlTitle;
     //when till button will be clicked the bill will be displayed on the screen and will become editable(only the status will be changed thou. 
    //may be I can do it like displaying only 1 option or making the whole different page by ourselves
    JTable table;
    DefaultTableModel model;
    private JButton jbBackToMainMenu;
    EmployeeController empController;
    ArrayList<Billing> data;
    JTextField searchField;
    ArrayList<Billing> filteredData; // To hold filtered results
    private JTextField searchBar;

    public EmployeeViewBills(EmployeeController empController) 
    {
        this.empController=empController;
        data=empController.getBillsList();
        init();
        setVisible(true);
    }

    private void init() 
    {
        setBounds(100,100,900,500);
        setLayout(new BorderLayout());
        setTitle("Employee View Bill");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //header
        JPanel header=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle=new JLabel("VIEW BILLS");
        header.add(jlTitle,BorderLayout.CENTER);
        
        // Search Bar
        JPanel jpSearchBar=new JPanel(new FlowLayout(FlowLayout.TRAILING));
        searchBar = new JTextField(20);
        searchBar.setText("Search...");
        searchBar.getDocument().addDocumentListener(new DocumentListener() 
        {
            public void insertUpdate(DocumentEvent e) 
            {
                filterTable();
            }

            public void removeUpdate(DocumentEvent e) 
            {
                filterTable();
            }

            public void changedUpdate(DocumentEvent e) 
            {
                filterTable();
            }
        });

        jpSearchBar.add(searchBar);
        
        header.add(jpSearchBar,BorderLayout.AFTER_LAST_LINE); // Add search field to the header
        
        //the table the search bar the back button and all that
        model=new DefaultTableModel(new Object[]{"CustomerID","Name","Month","Current Regular Meter Reading","Current Peak Meter Reading","Total Amount","Due Date","Bill Status","Payment Date","Update","Delete"},0);
        //table=new JTable(model);
        
        table = new JTable(model) 
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                // Only allow "Update" and "Delete" columns to be editable
                return column == 9 || column == 10; }
        };

        // Set button renderer and editor
        table.getColumn("Update").setCellRenderer(new ButtonRenderer());
        table.getColumn("Update").setCellEditor(new ButtonEditor(new JCheckBox(), this, empController));
        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), this, empController));
 
        
        JScrollPane scroll=new JScrollPane(table);        
        
        //south having button
        JPanel south=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbBackToMainMenu=new JButton("BACK TO MAIN MENU");
        jbBackToMainMenu.addActionListener(e->handleBackToMainMenu());
        south.add(jbBackToMainMenu,BorderLayout.CENTER);
        
        populateTable();
        
        add(header,BorderLayout.NORTH);
        add(scroll,BorderLayout.CENTER);
        add(south,BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }
    
    private void populateTable() 
    {
         model.setRowCount(0); // Clear existing rows
            
        for(Billing bill:data)
        {
            JButton jbUpdatePaidStatus = null;
            JButton jbDelete = null;
            
            boolean isPaid=bill.getBillPaidStatus().equals(BillStatus.PAID);
            // Update button
            if (empController.isMostRecentOrUnpaid(bill)&&!isPaid) 
            {
                jbUpdatePaidStatus = new JButton("Update");
                jbUpdatePaidStatus.addActionListener(e -> handleUpdatePaidStatus(bill));
            }

            // Delete button
            if (empController.isMostRecent(bill)) 
            {
                jbDelete = new JButton("Delete");
                jbDelete.addActionListener(e -> handleDeleteBillRecord(bill));
            }
            model.addRow(new Object[]
            {
                bill.getCustID(), 
                bill.getCustName(), 
                bill.getBillingMonth(),
                bill.getCurrentRegularMeterReading(),
                bill.getCurrentPeakMeterReading(),
                bill.getTotalBillingAmount(),
                bill.getDueDate(),
                bill.getBillPaidStatus(),
                (bill.getBillPaidStatus().equals(BillStatus.PAID)) ? bill.getBillPaymentDate() : "N/A",
                jbUpdatePaidStatus!=null ? "Update" : null,
                empController.isMostRecent(bill) ? "Delete" : null
            }
            );
        }
    }
    
   private void filterTable() 
   {
    String searchText = searchBar.getText().toLowerCase(); // Get search text
    model.setRowCount(0); // Clear existing rows

    for (Billing bill : data) 
    {
        // Check if any of the fields match the search text
        if (String.valueOf(bill.getCustID()).toLowerCase().contains(searchText) || 
            bill.getCustName().toLowerCase().contains(searchText) || 
            bill.getBillingMonth().toString().toLowerCase().contains(searchText) || 
            String.valueOf(bill.getCurrentRegularMeterReading()).contains(searchText) || 
            String.valueOf(bill.getCurrentPeakMeterReading()).contains(searchText) || 
            String.valueOf(bill.getTotalBillingAmount()).contains(searchText) || 
            String.valueOf(bill.getDueDate()).toLowerCase().contains(searchText) || 
            bill.getBillPaidStatus().name().toLowerCase().contains(searchText) || // Convert enum to String
            (bill.getBillPaidStatus().equals(BillStatus.PAID) ? 
                String.valueOf(bill.getBillPaymentDate()).toLowerCase() : "N/A").contains(searchText)) {
                
            model.addRow(new Object[]{
                bill.getCustID(),
                bill.getCustName(),
                bill.getBillingMonth(),
                bill.getCurrentRegularMeterReading(),
                bill.getCurrentPeakMeterReading(),
                bill.getTotalBillingAmount(),
                bill.getDueDate(),
                bill.getBillPaidStatus(),
                (bill.getBillPaidStatus().equals(BillStatus.PAID)) ? 
                    String.valueOf(bill.getBillPaymentDate()) : "N/A",
                empController.isMostRecentOrUnpaid(bill) ? "Update" : null,
                empController.isMostRecent(bill) ? "Delete" : null
            });
        }
    }
}

    
    private void handleBackToMainMenu() 
    {
        setVisible(false); dispose();
        new EmployeeMenu(empController);
    }

    private void handleUpdatePaidStatus(Billing bill) 
    {
        //update thestatus and save the record back into the file okayy!?
        // Call controller to update the status of the bill
            boolean success = empController.updateBillStatus(bill.getCustID(), bill.getBillingMonth());

            if (success)
            {
                // Show success message using JOptionPane
                JOptionPane.showMessageDialog(this, "Bill status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Reload or refresh table data
                data = empController.getBillsList();  // Re-fetch updated bills
                model.setRowCount(0);  // Clear table rows
                populateTable();  // Repopulate table
            } 
            else 
            {
                // Show failure message using JOptionPane
                JOptionPane.showMessageDialog(this, "Failed to update bill status.", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }

    private void handleDeleteBillRecord(Billing bill)
    {
        //delete the bill record and save the changes back into the file
         int response = JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to delete this bill?", 
        "Confirm Deletion", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.WARNING_MESSAGE);
    
        if (response == JOptionPane.YES_OPTION)
        {
        boolean success = empController.deleteBillRecord(bill.getCustID(), bill.getBillingMonth());

            if (success) 
            {
                // Show success message using JOptionPane
                JOptionPane.showMessageDialog(this, "Bill deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Reload or refresh table data
                data = empController.getBillsList();  // Re-fetch updated bills
                model.setRowCount(0);  // Clear table rows
                populateTable();  // Repopulate table
            } 
            else 
            {
                // Show failure message using JOptionPane
                JOptionPane.showMessageDialog(this, "Failed to delete bill.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Custom Button Renderer
    class ButtonRenderer extends JButton implements TableCellRenderer
    {
        public ButtonRenderer()
        {
            setOpaque(true);
        }

        //@Override
        //public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row) {
            
        //}

        @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {
        if (value == null) 
        {
            setText("");
            setEnabled(false); // Disable button if value is null
        }
        else
        {
            setText(value.toString());
            setEnabled(true); // Enable button if value is present
        }
        if (isSelected)
        {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } 
        else 
        {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        return this;
    }
    }

    // Custom Button Editor
class ButtonEditor extends DefaultCellEditor 
{
    private JButton button;
    private String label;
    private boolean isPushed;
    private EmployeeController empController;
    private EmployeeViewBills view;
    private Billing bill; // Hold the current bill being edited

    public ButtonEditor(JCheckBox checkBox, EmployeeViewBills view, EmployeeController empController)
    {
        super(checkBox);
        this.view = view;
        this.empController = empController;
        button = new JButton();
        button.setOpaque(true);
        
        button.addActionListener(e ->
        {
            if (label.equals("Update")) 
            {
                handleUpdatePaidStatus(bill);
            } 
            else if (label.equals("Delete")) 
            {
                handleDeleteBillRecord(bill);
            }

            fireEditingStopped(); // Notify the table that editing has stopped
        });  }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) 
    {
        // Get the bill object for the selected row
        bill = view.data.get(row);
        
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() 
    {
        /*if (isPushed) {
            if ("Update".equals(label)) {
                // Handle Update operation
                view.handleUpdatePaidStatus(bill);
            } else if ("Delete".equals(label)) {
                // Handle Delete operation
                view.handleDeleteBillRecord(bill);
            }
        }
        isPushed = false; */
        return label;
    }

    @Override
    protected void fireEditingStopped() 
    {
        super.fireEditingStopped(); // Ensure editing stops after action is taken
    }
    
    private void refreshTable()
    {
        data = empController.getBillsList();
        model.setRowCount(0); // Clear table rows
        view.populateTable(); // Repopulate table
    }
    
    @Override
    public boolean stopCellEditing()
    {
        isPushed = false;
        return super.stopCellEditing();
    }
}

}
