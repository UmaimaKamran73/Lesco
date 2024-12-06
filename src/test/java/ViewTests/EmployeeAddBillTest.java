package ViewTests;

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

    employeeAddBill.tfCustID.setText("123");
    employeeAddBill.tfCurrRegularMetterReading.setText("100");
    employeeAddBill.tfCurrPeakMeterReading.setText("50");

    when(mockController.addBill("123", "100", "50")).thenReturn("Billing record added successfully.");

    employeeAddBill.jbAddBill.doClick();

    verify(mockController).addBill("123", "100", "50");

    Assertions.assertFalse(employeeAddBill.isVisible());
  }

  @Test
  @DisplayName("Test adding bill with invalid customer ID")
  void testAddBillWithInvalidCustomerID() {

    employeeAddBill.tfCustID.setText("InvalidID");
    employeeAddBill.tfCurrRegularMetterReading.setText("150");
    employeeAddBill.tfCurrPeakMeterReading.setText("70");

    when(mockController.addBill("InvalidID", "150", "70")).thenReturn("Error: Invalid customer ID.");

    employeeAddBill.jbAddBill.doClick();

    verify(mockController).addBill("InvalidID", "150", "70");
    Assertions.assertTrue(employeeAddBill.isVisible());
  }

  @Test
  @DisplayName("Test cancel button functionality")
  void testCancelButton() {

    employeeAddBill.jbCancel.doClick();

    Assertions.assertFalse(employeeAddBill.isVisible());
    verifyNoInteractions(mockController);
  }
}
