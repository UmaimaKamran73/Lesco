/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.time.Month;
import java.util.ArrayList;

import com.mycompany.a1_scd_22l7942.Billing;
import com.mycompany.a1_scd_22l7942.Customer;
import com.mycompany.a1_scd_22l7942.Nadra;
import com.mycompany.a1_scd_22l7942.TarrifTax;

import Model.EmployeeModel;

/**
 *
 * @author LENOVO
 */
public class EmployeeController {
    public EmployeeModel employeeModel;

    public EmployeeController() {
        employeeModel = new EmployeeModel();
    }

    public EmployeeController(EmployeeModel employeeModel) {
        this.employeeModel = employeeModel;
    }

    public boolean login(String username, String password) {
        return employeeModel.authenticateEmployee(username, password);
    }

    public ArrayList<Integer> viewBillsReport() {
        return employeeModel.viewBillsReport();
    }

    public String addBill(String custID, String currentRegularMeterReading, String currentPeakMeterReading) {
        return employeeModel.addBill(custID, currentRegularMeterReading, currentPeakMeterReading);
    }

    public boolean isValidCNIC(String cnic) {
        return employeeModel.isValidCNIC(cnic);
    }

    public String installNewMeter(String cnic, String name, String address, String phoneNumber, String custType,
            String meterType) {
        return employeeModel.installNewMeter(cnic, name, address, phoneNumber, custType, meterType);
    }

    public String getUsername() {
        return employeeModel.getUsername();
    }

    public void changePassword(String currentPassword, String newPassword) {
        employeeModel.changePassword(newPassword);
    }

    public String getPassword() {
        return employeeModel.getPassword();
    }

    public ArrayList<TarrifTax> getTarrifList() {
        return employeeModel.getTarrifList();
    }

    public void updateTarrif(TarrifTax tarrif) {
        employeeModel.updateTarrif(tarrif);
    }

    public ArrayList<Customer> getCustomerList() {
        return employeeModel.getCustomerList();
    }

    public ArrayList<Nadra> getExpiringCNICList() {
        return employeeModel.getExpiringCNICList();
    }

    public ArrayList<Billing> getBillsList() {

        return employeeModel.getBillsWithCustomerInfo();
    }

    // view bills
    public boolean isMostRecentOrUnpaid(Billing bill) {
        return employeeModel.isMostRecentOrUnpaid(bill);
    }

    public boolean isMostRecent(Billing bill) {
        return employeeModel.isMostRecentOrUnpaid(bill);
    }

    public boolean updateBillStatus(String custID, Month billingMonth) {
        return employeeModel.updateBillStatus(custID, billingMonth);
    }

    public boolean deleteBillRecord(String custID, Month billingMonth) {
        return employeeModel.deleteBillRecord(custID, billingMonth);
    }

    public TarrifTax getTarrifAt(int row) {
        return employeeModel.getTarrifAt(row);
    }

    public void editCustomer(Customer customer) {
        employeeModel.editCustomer(customer);
    }

    public boolean deleteCustomer(String custID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
