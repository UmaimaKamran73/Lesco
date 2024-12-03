/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.EmployeeController;
import com.mycompany.a1_scd_22l7942.Customer;
import com.mycompany.a1_scd_22l7942.TarrifTax;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
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
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author LENOVO
 */
public class EmployeeViewCustomers extends JFrame
{
    private JLabel jlTitle;
    private DefaultTableModel model;
    private JTable table;
    EmployeeController empController;
    private List<Customer> data;
    private JButton jbBackToMainMenu;
    private JTextField searchBar;

    public EmployeeViewCustomers(EmployeeController empController) 
    {
        this.empController=empController;
        //taking the list of rows from empController
        data=empController.getCustomerList();
        init();
        setVisible(true);
    }

    private void init() 
    {
        setBounds(250,100,850,400);
        setLayout(new BorderLayout());
        setTitle("View Customer Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //header
        JPanel header=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle=new JLabel("CUSTOMER INFORMATION");
        header.add(jlTitle,BorderLayout.CENTER);
        
        // Search Bar
        JPanel jpSearchBar=new JPanel(new FlowLayout(FlowLayout.TRAILING));
        searchBar = new JTextField(20);
        searchBar.setText("Search...");
        searchBar.getDocument().addDocumentListener(new DocumentListener() 
        {
            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) 
            {
                filterTable();
            }
        });

        jpSearchBar.add(searchBar);
        
        header.add(jpSearchBar,BorderLayout.AFTER_LAST_LINE); // Add search field to the header

        
        //center ie table
        model=new DefaultTableModel(new Object[]{"CustomreID","Name","Address","Phone","Customer Type","Meter Type","Connection Date","Regular Units Consumed","Peak Hour Units Consumed","Edit","Delete"},0);
        table=new JTable(model);
        
        JScrollPane scroll=new JScrollPane(table);
        
                
        // Add custom renderer and editor for the "Edit" button column
        // Set Button Renderer and Editor for "Edit" and "Delete" buttons
        table.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Edit").setCellEditor(new ButtonEditor(table, e -> handleEditButton(table)));

        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(table, e -> handleDeleteButton(table)));


        populateTable();
        
        //south having button
        JPanel south=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbBackToMainMenu=new JButton("BACK TO MAIN MENU");
        jbBackToMainMenu.addActionListener(e->handleBackToMainMenu());
        south.add(jbBackToMainMenu);
        
        add(header,BorderLayout.NORTH);
        add(scroll,BorderLayout.CENTER);
        add(south,BorderLayout.SOUTH);
    }

    private void populateTable() 
    {
        model.setRowCount(0); // Clear the existing rows in the table
        for(Customer customer:data)
        {
            JButton jbEdit=new JButton("EDIT");
            JButton jbDelete=new JButton("DELETE");
            jbEdit.addActionListener(e->handleEdit(customer));
            jbDelete.addActionListener(e->handleDelete(customer));
            model.addRow(new Object[]{
                customer.getCustID(),
                customer.getCustName(),
                customer.getCustAddress(),
                customer.getPhoneNumber(),
                customer.getCustType(),
                customer.getMeterType(),
                customer.getConnectionDate(),
                customer.getRegularUnitsConsumed(),
                customer.getPeakHourUnitsConsumed(),
                "Edit",
                "Delete"});
            //rough
            //only confirmation message ie only the paid status can be updated.. oppps not here but in bill. iska idk rn
            //new EmployeeUpdateCustomerInfo(customer,empController);
            //new EmployeeEditCustomer(empController,customer);
        }
    }

    private void handleEdit(Customer customer) 
    {
        setVisible(false); dispose();
        //still dk what to edit or update here lol
        //NOW i KNOWW HAHAHAHAHA
        new EmployeeEditCustomer(empController,customer);
    }    

    private void handleDelete(Customer customer)
    {
        //simply delete the customer from arraylist and save array back to the file
        // Prompt user for confirmation
        int response = JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to delete this customer?", 
        "Confirm Deletion", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.WARNING_MESSAGE);
    
        if (response == JOptionPane.YES_OPTION)
        {
            // Attempt to delete the customer through the controller
            boolean success = empController.deleteCustomer(customer.getCustID());

            if (success) 
            {
                // Show success message
                JOptionPane.showMessageDialog(this, "Customer deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Reload or refresh table data
                data = empController.getCustomerList();  // Re-fetch updated customer list
                model.setRowCount(0);  // Clear table rows
                populateTable();  // Repopulate table
            } 
            else 
            {
                // Show failure message
                JOptionPane.showMessageDialog(this, "Failed to delete customer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleBackToMainMenu() 
    {
        setVisible(false); dispose();
        new EmployeeMenu(empController);
    }
    private void handleEditButton(JTable table) 
    {
    int row = table.getSelectedRow();
    if (row != -1)
    {
        Customer customer = data.get(row);
        handleEdit(customer);
    }
}

    private void handleDeleteButton(JTable table) 
    {
        int row = table.getSelectedRow();
        if (row != -1) 
        {
            Customer customer = data.get(row);
            handleDelete(customer);
        }
    }

    
    //filter table
    private void filterTable() 
    {
    String searchText = searchBar.getText().trim().toLowerCase();
    model.setRowCount(0); // Clear the existing rows in the table
    
    for (Customer customer : data)
    {
        // Convert all relevant fields to lowercase for case-insensitive search
        String custID = customer.getCustID().toLowerCase();
        String custName = customer.getCustName().toLowerCase();
        String custAddress = customer.getCustAddress().toLowerCase();
        String phoneNumber = customer.getPhoneNumber().toLowerCase();
        String custType = customer.getCustType().toString().toLowerCase();
        String meterType = customer.getMeterType().toString().toLowerCase();
        String connectionDate = customer.getConnectionDate().toString().toLowerCase();
        String regularUnits = String.valueOf(customer.getRegularUnitsConsumed()).toLowerCase();
        String peakUnits = String.valueOf(customer.getPeakHourUnitsConsumed()).toLowerCase();
        
        // Check if the search text is contained in any of the fields
        if (custID.contains(searchText) || custName.contains(searchText) || custAddress.contains(searchText) || 
            phoneNumber.contains(searchText) || custType.contains(searchText) || meterType.contains(searchText) || 
            connectionDate.contains(searchText) || regularUnits.contains(searchText) || peakUnits.contains(searchText)) {
            
            // If a match is found, re-add the customer row
            JButton jbEdit = new JButton("EDIT");
            JButton jbDelete = new JButton("DELETE");
            jbEdit.addActionListener(e -> handleEdit(customer));
            jbDelete.addActionListener(e -> handleDelete(customer));
            model.addRow(new Object[]{customer.getCustID(), customer.getCustName(), customer.getCustAddress(), 
                                      customer.getPhoneNumber(), customer.getCustType(), customer.getMeterType(), 
                                      customer.getConnectionDate(), customer.getRegularUnitsConsumed(), 
                                      customer.getPeakHourUnitsConsumed(), jbEdit, jbDelete});
        }
    }
}
    
    
    class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private JButton button;
    private String label;
    private boolean clicked;

    public ButtonEditor(JTable table, ActionListener actionListener) {
        button = new JButton();
        button.addActionListener(actionListener);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

}