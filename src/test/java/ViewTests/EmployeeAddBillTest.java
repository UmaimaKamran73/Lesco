package tests.ViewTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import Controller.EmployeeController;
import View.EmployeeAddBill;

class EmployeeAddBillTest {

  private EmployeeController mockController;
  private EmployeeAddBill employeeAddBill;

  @BeforeEach
  void setUp() {
    mockController = mock(EmployeeController.class);
    employeeAddBill = new EmployeeAddBill(mockController);
  }

  @AfterEach
  void tearDown() {
    employeeAddBill.dispose();
  }

  @Test
  @DisplayName("Test adding bill with valid inputs")
  void testAddBillWithValidInputs() {
    // Set valid inputs
    employeeAddBill.tfCustID.setText("123");
    employeeAddBill.tfCurrRegularMetterReading.setText("100");
    employeeAddBill.tfCurrPeakMeterReading.setText("50");

    // Mock the controller's behavior
    when(mockController.addBill("123", "100", "50")).thenReturn("Billing record added successfully.");

    // Simulate button click
    employeeAddBill.jbAddBill.doClick();

    // Verify that the controller's addBill method was called with correct
    // parameters
    verify(mockController).addBill("123", "100", "50");

    // Ensure the window is closed after successful addition
    Assertions.assertFalse(employeeAddBill.isVisible());
  }

  @Test
  @DisplayName("Test adding bill with invalid customer ID")
  void testAddBillWithInvalidCustomerID() {
    // Set invalid customer ID
    employeeAddBill.tfCustID.setText("InvalidID");
    employeeAddBill.tfCurrRegularMetterReading.setText("150");
    employeeAddBill.tfCurrPeakMeterReading.setText("70");

    // Mock the controller's behavior for invalid ID
    when(mockController.addBill("InvalidID", "150", "70")).thenReturn("Error: Invalid customer ID.");

    // Simulate button click
    employeeAddBill.jbAddBill.doClick();

    // Verify the error message is displayed
    verify(mockController).addBill("InvalidID", "150", "70");
    Assertions.assertTrue(employeeAddBill.isVisible());
  }

  @Test
  @DisplayName("Test cancel button functionality")
  void testCancelButton() {
    // Simulate cancel button click
    employeeAddBill.jbCancel.doClick();

    // Verify that the window is closed and no controller interaction occurs
    Assertions.assertFalse(employeeAddBill.isVisible());
    verifyNoInteractions(mockController);
  }
}
