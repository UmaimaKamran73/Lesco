
package View;

import Controller.EmployeeController;
import com.mycompany.a1_scd_22l7942.MeterType;
import com.mycompany.a1_scd_22l7942.TarrifTax;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EmployeeEditTarrifInfo extends JFrame
{
    JLabel jlMeterType,jlCustType;
    JLabel jlFixedPrice,jlTaxPercentage,jlRegularUnitPrice,jlPeakUnitPrice;
    JTextField tfFixedPrice,tfTaxPercentage,tfRegularUnitPrice,tfPeakUnitPrice;
    JLabel jlTitle;
    TarrifTax tarrif;
    EmployeeController empController;
    private JButton jbUpdate;
    private JButton jbCancel;
    public EmployeeEditTarrifInfo(TarrifTax tarrif, EmployeeController empController)
    {
        this.tarrif=tarrif;
        this.empController=empController;
        init();
        setVisible(true);
    }

    private void init() 
    {
        setBounds(100,100,400,300);
        setLayout(new BorderLayout());
        setTitle("Edit Tarrif");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //north will have heading
        JPanel header=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle=new JLabel("EDIT TARRIF INFORMATOIN");
        
        header.add(jlTitle);
        
        //center with content
        JPanel center=new JPanel(new GridLayout(5,2,10,10));
        jlMeterType=new JLabel("Meter Type: "+tarrif.getMeterType());
        jlCustType=new JLabel("Customer Type: "+tarrif.getCustType());
        
        jlFixedPrice=new JLabel("Fixed Charges: ");
        tfFixedPrice=new JTextField(tarrif.getFixedCharges());
        
        jlTaxPercentage=new JLabel("Tax Percentage: ");
        tfTaxPercentage=new JTextField((int) tarrif.getPercentageOfTax());
        
        jlRegularUnitPrice=new JLabel("Regular Unit Price: ");
        tfRegularUnitPrice=new JTextField(tarrif.getRegularUnitPrice().toString());
        
        
        center.add(jlMeterType);
        center.add(jlCustType);
        center.add(jlFixedPrice);
        center.add(tfFixedPrice);
        center.add(jlTaxPercentage);
        center.add(tfTaxPercentage);
        center.add(jlRegularUnitPrice);
        center.add(tfRegularUnitPrice);

        if(tarrif.getMeterType()!=MeterType.SINGLE_PHASE)
        {
            jlPeakUnitPrice=new JLabel("Peak Hour Unit Price: ");
            tfPeakUnitPrice=new JTextField(tarrif.getPeakHourUnitPrice().toString());
            center.add(jlPeakUnitPrice);
            center.add(tfPeakUnitPrice);
        }
                
        
        //south having login button
        JPanel south=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbUpdate=new JButton("UPDATE");
        jbUpdate.addActionListener(e->handleUpdate());
        south.add(jbUpdate);
        jbCancel=new JButton("CANCEL");
        jbCancel.addActionListener(e->handleCancel());
        south.add(jbCancel);
        
        add(header,BorderLayout.NORTH);
        add(center,BorderLayout.CENTER);
        add(south,BorderLayout.SOUTH);
        
        //pack();
        setLocationRelativeTo(null);
        
    }

    private void handleUpdate()
    {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();

        // Validate fixed price
        int fixedPrice = 0;
        try 
        {
            fixedPrice = Integer.parseInt(tfFixedPrice.getText());
            if (fixedPrice < 0) 
            {
                isValid = false;
                errorMessage.append("Fixed charges cannot be negative.\n");
            }
        } catch (NumberFormatException ex)
        {
            isValid = false;
            errorMessage.append("Invalid fixed charges. Please enter a valid number.\n");
        }

        // Validate tax percentage
        float taxPercentage = 0;
        try
        {
            taxPercentage = Float.parseFloat(tfTaxPercentage.getText());
            if (taxPercentage < 0) 
            {
                isValid = false;
                errorMessage.append("Tax percentage cannot be negative.\n");
            }
        } 
        catch (NumberFormatException ex) 
        {
            isValid = false;
            errorMessage.append("Invalid tax percentage. Please enter a valid number.\n");
        }

        // Validate regular unit price
        BigDecimal regularUnitPrice = BigDecimal.ZERO;
        try
        {
            regularUnitPrice = new BigDecimal(tfRegularUnitPrice.getText());
            if (regularUnitPrice.compareTo(BigDecimal.ZERO) <= 0) 
            {
                isValid = false;
                errorMessage.append("Regular unit price must be greater than 0.\n");
            }
        }
        catch (NumberFormatException ex) 
        {
            isValid = false;
            errorMessage.append("Invalid regular unit price. Please enter a valid number.\n");
        }

        // Validate peak unit price only if it is applicable
        if (!MeterType.SINGLE_PHASE.equals(tarrif.getMeterType())) 
        {
            BigDecimal peakUnitPrice = BigDecimal.ZERO;
            try 
            {
                peakUnitPrice = new BigDecimal(tfPeakUnitPrice.getText());
                if (peakUnitPrice.compareTo(BigDecimal.ZERO) <= 0) 
                {
                    isValid = false;
                    errorMessage.append("Peak unit price must be greater than 0.\n");
                }
            } 
            catch (NumberFormatException ex) 
            {
                isValid = false;
                errorMessage.append("Invalid peak unit price. Please enter a valid number.\n");
            }
        }

        // Check if all inputs are valid
        if (!isValid)
        {
            JOptionPane.showMessageDialog(this, errorMessage.toString(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        else 
        {
            // All inputs are valid, proceed with updating the tariff information
            tarrif.setFixedCharges(fixedPrice);
            tarrif.setPercentageOfTax(taxPercentage);
            tarrif.setRegularUnitPrice(regularUnitPrice);
            // Update peak hour unit price only if applicable
            if (!MeterType.SINGLE_PHASE.equals(tarrif.getMeterType())) 
            {
                tarrif.setPeakHourUnitPrice(new BigDecimal(tfPeakUnitPrice.getText()));
            }

            // Update tariff info through the controller
            empController.updateTarrif(tarrif);

            // Success message
            JOptionPane.showMessageDialog(this, "Tariff information updated successfully!");
            setVisible(false);
            dispose();
            new EmployeeViewTarrifInfo(empController);
        }
    }

    private void handleCancel() 
    {
        setVisible(false);
        dispose();
        new EmployeeViewTarrifInfo(empController);
       
    }
    
}
