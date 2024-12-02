/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mycompany.a1_scd_22l7942.TarrifTax;

import Controller.CustomerController;

/**
 *
 * @author LENOVO
 */
public class CustomerViewCurrentBill2 extends JFrame {
    public JLabel jlCustomerID;
    public JLabel jlCustomerName;
    public JLabel jlAddress;
    public JLabel jlPhoneNumber;
    public JLabel jlCustomerType;
    public JLabel jlMeterType;
    public JLabel jlRegularUnitsConsumed;
    public JLabel jlPeakHourUnitsConsumed;
    public JLabel jlConstOfElectricity;
    public JLabel jlTax;
    public JLabel jlFixedCharges;
    public JLabel jlTotalAmountDue;
    public JLabel jlDueDate;
    public JLabel jlPaymentStatus;
    public JLabel TarrifTaxInfo; // 4 rows will be there. grid will be made. rn idk what to do.may be when making
                                 // employee I can use something from there
    public JLabel jlTarrifTitle;
    public JLabel jlRegularTarrif;
    public JLabel jlPeakTarrif;
    public JLabel jlTaxTarrif;
    public JLabel jlFixedTarrif;
    public CustomerController custController;
    public JButton jbOK;
    public JLabel jlTitle;
    public Font normalFont;

    /*
     * public CustomerViewCurrentBill2()
     * {
     * 
     * init();
     * setVisible(true);
     * }
     */

    public CustomerViewCurrentBill2(CustomerController custController, Map<String, String> billDetails) {
        this.custController = custController;
        normalFont = new Font("Arial", Font.PLAIN, 12);
        init(billDetails);
        setVisible(true);
    }

    public void init(Map<String, String> billDetails) {

        // all this needs to be changed!!
        setBounds(100, 100, 400, 600);
        setTitle("Customer Bill Details");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTitle = new JLabel("CURRENT BILL");
        header.add(jlTitle);

        // center
        JPanel bill = new JPanel(new BorderLayout());
        JPanel center = new JPanel(new GridLayout(7, 2, 10, 10)); // 7 rows, 2 columns

        jlCustomerID = new JLabel("Customer ID: " + billDetails.get("CustomerID"));// + customerID);
        jlCustomerID.setFont(normalFont);

        jlCustomerName = new JLabel("Name: " + billDetails.get("CustomerName"));// + customerName);
        jlCustomerName.setFont(normalFont);

        jlAddress = new JLabel("Address: " + billDetails.get("Address"));// + address);
        jlAddress.setFont(normalFont);

        jlPhoneNumber = new JLabel("Phone: " + billDetails.get("PhoneNumber"));// + phoneNumber);
        jlPhoneNumber.setFont(normalFont);

        jlCustomerType = new JLabel("Customer Type: " + billDetails.get("CustomerType"));// + customerType);
        jlCustomerType.setFont(normalFont);

        jlMeterType = new JLabel("Meter Type: " + billDetails.get("MeterType"));// + meterType);
        jlMeterType.setFont(normalFont);

        jlRegularUnitsConsumed = new JLabel("Regular Units Consumed: " + billDetails.get("RegularUnitsConsumed"));// +
                                                                                                                  // regularUnitsConsumed);
        jlRegularUnitsConsumed.setFont(normalFont);

        jlPeakHourUnitsConsumed = new JLabel("Peak Hour Units Consumed: " + billDetails.get("PeakHourUnitsConsumed"));// +
                                                                                                                      // peakHourUnitsConsumed);
        jlPeakHourUnitsConsumed.setFont(normalFont);

        jlConstOfElectricity = new JLabel("Cost of Electricity: " + billDetails.get("ElectricityCost"));// +
                                                                                                        // costOfElectricity);
        jlConstOfElectricity.setFont(normalFont);

        jlTax = new JLabel("Tax: " + billDetails.get("Tax"));// + tax);
        jlTax.setFont(normalFont);

        jlFixedCharges = new JLabel("Fixed Charges: " + billDetails.get("FixedCharges"));// + fixedCharges);
        jlFixedCharges.setFont(normalFont);

        jlTotalAmountDue = new JLabel("Total Amount Due: " + billDetails.get("TotalAmountDue"));// + totalAmountDue);
        jlTotalAmountDue.setFont(normalFont);

        jlDueDate = new JLabel("Due Date: " + billDetails.get("DueDate"));// + dueDate);
        jlDueDate.setFont(normalFont);

        jlPaymentStatus = new JLabel("Payment Status: " + billDetails.get("PaymentStatus"));// + paymentStatus);
        jlPaymentStatus.setFont(normalFont);

        // Adding components to center panel
        center.add(jlCustomerID);
        center.add(jlCustomerName);
        center.add(jlAddress);
        center.add(jlPhoneNumber);
        center.add(jlCustomerType);
        center.add(jlMeterType);
        center.add(jlRegularUnitsConsumed);
        center.add(jlPeakHourUnitsConsumed);
        center.add(jlConstOfElectricity);
        center.add(jlTax);
        center.add(jlFixedCharges);
        center.add(jlTotalAmountDue);
        center.add(jlDueDate);
        center.add(jlPaymentStatus);

        bill.add(center, BorderLayout.NORTH);

        // Tariff Tax Info section
        JPanel jpTarrif = new JPanel(new BorderLayout());
        JPanel jpTarrifTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jlTarrifTitle = new JLabel("TARRIF TAX INFORMATION");
        jpTarrifTitle.add(jlTarrifTitle);

        jpTarrif.add(jpTarrifTitle, BorderLayout.NORTH);

        JPanel tariffPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // extra row for title
        for (TarrifTax t : custController.getTarrifList()) {
            JLabel jlMType = new JLabel("Meter Type: " + t.getMeterType());
            jlMType.setFont(normalFont);

            JLabel jlCustType = new JLabel("Customer Type: " + t.getCustType());
            jlCustType.setFont(normalFont);

            JLabel jlRegularPrice = new JLabel("Regular Unit Price: " + t.getRegularUnitPrice());
            jlRegularPrice.setFont(normalFont);

            JLabel jlPeakPrice = new JLabel("Peak Hour Unit Price: " + t.getPeakHourUnitPrice());
            jlPeakPrice.setFont(normalFont);

            JLabel jlFixedCharge = new JLabel("Fixed Charges: " + t.getFixedCharges());
            jlFixedCharge.setFont(normalFont);

            JLabel jlTaxes = new JLabel("Tax Percentage: " + t.getPercentageOfTax());
            jlTaxes.setFont(normalFont);

            JPanel tariffInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
            tariffInfo.add(jlMType);
            tariffInfo.add(jlCustType);
            tariffInfo.add(jlRegularPrice);
            tariffInfo.add(jlPeakPrice);
            tariffInfo.add(jlFixedCharge);
            tariffInfo.add(jlTaxes);

            tariffPanel.add(tariffInfo);
        }
        jpTarrif.add(tariffPanel, BorderLayout.CENTER); // edit it a bit okayy!??
        bill.add(jpTarrif, BorderLayout.CENTER);
        // south having okay button
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jbOK = new JButton("BACK TO MAIN MENU");
        jbOK.addActionListener(e -> handleOK());
        south.add(jbOK);

        add(header, BorderLayout.NORTH);
        add(bill, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        // add(south, BorderLayout.SOUTH);

        // pack();
        setLocationRelativeTo(null);

    }

    public void handleOK() {
        setVisible(false);
        new CustomerMenu(custController);
    }

}
