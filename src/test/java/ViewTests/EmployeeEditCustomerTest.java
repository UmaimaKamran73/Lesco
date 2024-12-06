package ViewTests;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.mycompany.a1_scd_22l7942.Customer;
import com.mycompany.a1_scd_22l7942.CustomerType;
import com.mycompany.a1_scd_22l7942.MeterType;

import Controller.EmployeeController;
import View.EmployeeEditCustomer;

class EmployeeEditCustomerTest {

  private EmployeeController mockController;
  private Customer customer;
  private EmployeeEditCustomer employeeEditCustomer;

  @BeforeEach
  void setUp() {

    mockController = Mockito.mock(EmployeeController.class);

    customer = new Customer(
        "123",
        "12345678901",
        "John Doe",
        "123 Main Street",
        "03219306126",
        CustomerType.COMMERCIAL,
        MeterType.THREE_PHASE,
        LocalDate.of(2022, 1, 1),
        50,
        20);

    employeeEditCustomer = new EmployeeEditCustomer(mockController, customer);
  }

  @Test
  @DisplayName("Test update with valid data")
  void testUpdateWithValidData() {
    employeeEditCustomer.tfPhone.setText("09876543210");
    employeeEditCustomer.tfAddress.setText("456 Elm Street");
    employeeEditCustomer.tfRegularUnitsConsumed.setText("100");
    employeeEditCustomer.tfPeakHourUnitsConsumed.setText("50");

    employeeEditCustomer.jbUpdate.doClick();

    verify(mockController, times(1)).editCustomer(customer);

    assert "09876543210".equals(customer.getPhoneNumber());
    assert "456 Elm Street".equals(customer.getCustAddress());
    assert customer.getRegularUnitsConsumed() == 100;
    assert customer.getPeakHourUnitsConsumed() == 50;
  }

  @Test
  @DisplayName("Test cancel button functionality")
  void testCancelButton() {
    employeeEditCustomer.jbCancel.doClick();

    assert !employeeEditCustomer.isVisible();

    verify(mockController, never()).editCustomer(any());
  }
}
