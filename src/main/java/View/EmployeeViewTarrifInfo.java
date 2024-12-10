/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.mycompany.a1_scd_22l7942.TarrifTax;

import Controller.EmployeeController;


public class EmployeeViewTarrifInfo extends JFrame {

/*    private JLabel jlTitle;
    private DefaultTableModel model;
    private JTable table;
    EmployeeController empController;
    private List<TarrifTax> data;
    private JButton jbBackToMainMenu;
    private JTextField searchBar;*/
    public JLabel jlTitle;
    public DefaultTableModel model;
    public JTable table;
    public EmployeeController empController;
    public List<TarrifTax> data;
    public JButton jbBackToMainMenu;
    public JTextField searchBar;

    public EmployeeViewTarrifInfo(EmployeeController empController) {
        this.empController = empController;
        // taking the list of rows from empController
        data = empController.getTarrifList();
        if(data==null)
        {
            data=new ArrayList<>();
        }
        init();
        setVisible(true);
    }

    public void init() {
        setBounds(100, 100, 600, 350);
        setLayout(new BorderLayout());
        setTitle("View Tarrif Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle = new JLabel("TARRIF INFORMATION");
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

        // center ie table
        model = new DefaultTableModel(new Object[] { "Meter Type", "Customer Type", "Regular Unit Price",
                "Peak Hour Unit Price", "Fixed Charges", "Percentage of Tax", "Update" }, 0);
        // table=new JTable(model);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only allow "Update" and "Delete" columns to be editable
                return column == 6;
            }
        };
        // Set button renderer and editor
        table.getColumn("Update").setCellRenderer(new EmployeeViewTarrifInfo.ButtonRenderer());
        table.getColumn("Update")
                .setCellEditor(new EmployeeViewTarrifInfo.ButtonEditor(new JCheckBox(), this, empController));

        JScrollPane scroll = new JScrollPane(table);

        populateTable();

        // south having button
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbBackToMainMenu = new JButton("BACK TO MAIN MENU");
        jbBackToMainMenu.addActionListener(e -> handleBackToMainMenu());
        south.add(jbBackToMainMenu);

        // pack();
        setLocationRelativeTo(null);

        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
    }

    public void populateTable() {
        for (TarrifTax tarrif : data) {
            JButton jbEdit = new JButton("EDIT");
            jbEdit.addActionListener(e -> handleEdit(tarrif));
            model.addRow(new Object[] {
                    tarrif.getMeterType(),
                    tarrif.getCustType(),
                    tarrif.getRegularUnitPrice(),
                    tarrif.getPeakHourUnitPrice(),
                    tarrif.getFixedCharges(),
                    tarrif.getPercentageOfTax(),
                    jbEdit });
            // rough
            // new EmployeeEditTarrifInfo(tarrif,empController);
        }
    }

    public void handleEdit(TarrifTax tarrif) {
        setVisible(false);
        dispose();
        new EmployeeEditTarrifInfo(tarrif, empController);
        // populateTable();
    }

    public void handleBackToMainMenu() {
        setVisible(false);
        dispose();
        new EmployeeMenu(empController);
    }

    // filtertable function
    public void filterTable() {
        String searchText = searchBar.getText().trim().toLowerCase(); // Get the search text and make it lowercase
        model.setRowCount(0); // Clear the current table

        // Iterate through the list of TarrifTax objects
        for (TarrifTax tarrif : data) {
            // Convert each field to lowercase for case-insensitive search
            String meterType = tarrif.getMeterType().toString().toLowerCase();
            String customerType = tarrif.getCustType().toString().toLowerCase();
            String regularUnitPrice = String.valueOf(tarrif.getRegularUnitPrice()).toLowerCase();
            String peakHourUnitPrice = String.valueOf(tarrif.getPeakHourUnitPrice()).toLowerCase();
            String fixedCharges = String.valueOf(tarrif.getFixedCharges()).toLowerCase();
            String percentageOfTax = String.valueOf(tarrif.getPercentageOfTax()).toLowerCase();

            // Check if any of the fields contain the search text
            if (meterType.contains(searchText) || customerType.contains(searchText) ||
                    regularUnitPrice.contains(searchText) || peakHourUnitPrice.contains(searchText) ||
                    fixedCharges.contains(searchText) || percentageOfTax.contains(searchText)) {

                // Add the matching rows back to the table
                JButton jbEdit = new JButton("EDIT");
                jbEdit.addActionListener(e -> handleEdit(tarrif));
                model.addRow(new Object[] { tarrif.getMeterType(), tarrif.getCustType(), tarrif.getRegularUnitPrice(),
                        tarrif.getPeakHourUnitPrice(), tarrif.getFixedCharges(),
                        tarrif.getPercentageOfTax(), jbEdit });
            }
        }
    }

    // Custom Button Renderer
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText("EDIT"); // Always show "EDIT" as the button label
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        public JButton button;
        public String label;
        public boolean isPushed;
        public EmployeeViewTarrifInfo view; // Reference to the main view
        public EmployeeController empController;
        public TarrifTax tarrif;

        public ButtonEditor(JCheckBox checkBox, EmployeeViewTarrifInfo view, EmployeeController empController) {
            super(checkBox);
            this.view = view;
            this.empController = empController;
            button = new JButton("Edit");
            // button.setOpaque(true);
            button.addActionListener(e -> {
                isPushed = true;
                if (isPushed) {
                    // view.handleEdit(tarrif); // Call the update method
                }
                fireEditingStopped(); // Stop editing on button click
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            this.label = value.toString();
            button.setText(label);
            isPushed = true;
            this.tarrif = empController.getTarrifAt(row); // Assuming you have a method to get the specific bill
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            // if (isPushed)
            // {
            // return label;
            // }
            // return null;
            if (isPushed) {
                TarrifTax tarrif = empController.getTarrifAt(table.convertRowIndexToModel(table.getSelectedRow())); // Get
                                                                                                                    // the
                                                                                                                    // correct
                                                                                                                    // tarrif
                                                                                                                    // based
                                                                                                                    // on
                                                                                                                    // the
                                                                                                                    // selected
                                                                                                                    // row
                view.handleEdit(tarrif); // Handle the edit action
            }
            isPushed = false; // Reset after the action
            return null;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

}
