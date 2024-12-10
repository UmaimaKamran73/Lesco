package ViewTests;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mycompany.a1_scd_22l7942.BillStatus;
import com.mycompany.a1_scd_22l7942.Billing;

import Controller.EmployeeController;
import View.EmployeeViewBills;

class EmployeeViewBillsTest {
  @Mock
  private EmployeeController mockController;
  private EmployeeViewBills view;
  private List<Billing> bills;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    bills = new ArrayList<>();
    Billing mockBill = new Billing(
        "1",
        Month.DECEMBER,
        100,
        500,
        LocalDate.of(2023, 12, 12),
        new BigDecimal("100.00"),
        new BigDecimal("10.00"),
        new BigDecimal("20.00"),
        new BigDecimal("130.00"),
        LocalDate.of(2023, 12, 31),
        BillStatus.UNPAID);

    bills.add(mockBill);

    Mockito.when(mockController.getBillsList()).thenReturn(bills);

    view = new EmployeeViewBills(mockController);
  }

  @Test
  void testInitializeView() {
    assertNotNull(view);
    assertEquals("Employee View Bill", view.getTitle());
  }

  @Test
  void testUpdatePaidStatus() {
    Billing mockBill = bills.get(0);
    Mockito.when(mockController.updateBillStatus(mockBill.getCustID(), mockBill.getBillingMonth())).thenReturn(true);

    view.handleUpdatePaidStatus(mockBill);

    Mockito.verify(mockController).updateBillStatus(mockBill.getCustID(), mockBill.getBillingMonth());
  }

  @Test
  void testDeleteBillRecord() {
    Billing mockBill = bills.get(0);
    Mockito.when(mockController.deleteBillRecord(mockBill.getCustID(), mockBill.getBillingMonth())).thenReturn(true);

    view.handleDeleteBillRecord(mockBill);

    Mockito.verify(mockController).deleteBillRecord(mockBill.getCustID(), mockBill.getBillingMonth());
  }

  @Test
  void testBackToMainMenuAction() {
    view.handleBackToMainMenu();

    assertFalse(view.isVisible());
  }
}
