package ViewTests;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.CustomerController;
import View.CustomerViewCurrentBill2;

class CustomerViewCurrentBill2Test {

  private CustomerController customerController;
  private CustomerViewCurrentBill2 customerView;
  private Map<String, String> billDetails;

  @BeforeEach
  void setUp() {

    customerController = new CustomerController();

    billDetails = new HashMap<>();
    billDetails.put("CustomerID", "123");
    billDetails.put("CustomerName", "John Doe");
    billDetails.put("Address", "123 Main Street");
    billDetails.put("PhoneNumber", "555-1234");
    billDetails.put("CustomerType", "Residential");
    billDetails.put("MeterType", "Digital");
    billDetails.put("RegularUnitsConsumed", "150");
    billDetails.put("PeakHourUnitsConsumed", "50");
    billDetails.put("ElectricityCost", "200");
    billDetails.put("Tax", "20");
    billDetails.put("FixedCharges", "30");
    billDetails.put("TotalAmountDue", "250");
    billDetails.put("DueDate", "2024-12-15");
    billDetails.put("PaymentStatus", "Unpaid");

    customerView = new CustomerViewCurrentBill2(customerController, billDetails);
  }

  @Test
  void testViewTitle() {
    assertEquals("Customer Bill Details", customerView.getTitle(), "The window title is incorrect.");
  }

  @Test
  void testCustomerIDLabel() {
    assertNotNull(customerView.jlCustomerID, "Customer ID label should not be null.");
    assertEquals("Customer ID: 123", customerView.jlCustomerID.getText(), "Customer ID label text is incorrect.");
  }

  @Test
  void testCustomerNameLabel() {
    assertNotNull(customerView.jlCustomerName, "Customer Name label should not be null.");
    assertEquals("Name: John Doe", customerView.jlCustomerName.getText(), "Customer Name label text is incorrect.");
  }

  @Test
  void testAddressLabel() {
    assertNotNull(customerView.jlAddress, "Address label should not be null.");
    assertEquals("Address: 123 Main Street", customerView.jlAddress.getText(), "Address label text is incorrect.");
  }

  @Test
  void testPhoneNumberLabel() {
    assertNotNull(customerView.jlPhoneNumber, "Phone Number label should not be null.");
    assertEquals("Phone: 555-1234", customerView.jlPhoneNumber.getText(), "Phone Number label text is incorrect.");
  }

  @Test
  void testCustomerTypeLabel() {
    assertNotNull(customerView.jlCustomerType, "Customer Type label should not be null.");
    assertEquals("Customer Type: Residential", customerView.jlCustomerType.getText(),
        "Customer Type label text is incorrect.");
  }

  @Test
  void testMeterTypeLabel() {
    assertNotNull(customerView.jlMeterType, "Meter Type label should not be null.");
    assertEquals("Meter Type: Digital", customerView.jlMeterType.getText(), "Meter Type label text is incorrect.");
  }

  @Test
  void testRegularUnitsConsumedLabel() {
    assertNotNull(customerView.jlRegularUnitsConsumed, "Regular Units Consumed label should not be null.");
    assertEquals("Regular Units Consumed: 150", customerView.jlRegularUnitsConsumed.getText(),
        "Regular Units Consumed label text is incorrect.");
  }

  @Test
  void testPeakHourUnitsConsumedLabel() {
    assertNotNull(customerView.jlPeakHourUnitsConsumed, "Peak Hour Units Consumed label should not be null.");
    assertEquals("Peak Hour Units Consumed: 50", customerView.jlPeakHourUnitsConsumed.getText(),
        "Peak Hour Units Consumed label text is incorrect.");
  }

  @Test
  void testElectricityCostLabel() {
    assertNotNull(customerView.jlConstOfElectricity, "Electricity Cost label should not be null.");
    assertEquals("Cost of Electricity: 200", customerView.jlConstOfElectricity.getText(),
        "Electricity Cost label text is incorrect.");
  }

  @Test
  void testTaxLabel() {
    assertNotNull(customerView.jlTax, "Tax label should not be null.");
    assertEquals("Tax: 20", customerView.jlTax.getText(), "Tax label text is incorrect.");
  }

  @Test
  void testFixedChargesLabel() {
    assertNotNull(customerView.jlFixedCharges, "Fixed Charges label should not be null.");
    assertEquals("Fixed Charges: 30", customerView.jlFixedCharges.getText(), "Fixed Charges label text is incorrect.");
  }

  @Test
  void testTotalAmountDueLabel() {
    assertNotNull(customerView.jlTotalAmountDue, "Total Amount Due label should not be null.");
    assertEquals("Total Amount Due: 250", customerView.jlTotalAmountDue.getText(),
        "Total Amount Due label text is incorrect.");
  }

  @Test
  void testDueDateLabel() {
    assertNotNull(customerView.jlDueDate, "Due Date label should not be null.");
    assertEquals("Due Date: 2024-12-15", customerView.jlDueDate.getText(), "Due Date label text is incorrect.");
  }

  @Test
  void testPaymentStatusLabel() {
    assertNotNull(customerView.jlPaymentStatus, "Payment Status label should not be null.");
    assertEquals("Payment Status: Unpaid", customerView.jlPaymentStatus.getText(),
        "Payment Status label text is incorrect.");
  }

  @Test
  void testBackToMainMenuButton() {
    assertNotNull(customerView.jbOK, "Back to Main Menu button should not be null.");
    assertEquals("BACK TO MAIN MENU", customerView.jbOK.getText(), "Back to Main Menu button text is incorrect.");
  }
}
