package ModelTests;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.a1_scd_22l7942.BillStatus;
import com.mycompany.a1_scd_22l7942.Billing;

class BillingTest {

  private Billing billing;

  @BeforeEach
  void setUp() {
    billing = new Billing(
        "C001",
        Month.JANUARY,
        500,
        300,
        LocalDate.of(2024, 12, 1),
        new BigDecimal("100.00"),
        new BigDecimal("10.00"),
        new BigDecimal("5.00"),
        new BigDecimal("115.00"),
        LocalDate.of(2024, 12, 15),
        BillStatus.UNPAID,
        LocalDate.of(2024, 12, 10));
  }

  @Test
  void testSetReadingEntryDate_ValidDate() {
    LocalDate validDate = LocalDate.of(2024, 12, 1);
    billing.setReadingEntryDate(validDate);
    assertEquals(validDate, billing.getReadingEntryDate());
  }

  @Test
  void testSetReadingEntryDate_FutureDate() {
    LocalDate futureDate = LocalDate.now().plusDays(1);
    assertThrows(IllegalArgumentException.class, () -> billing.setReadingEntryDate(futureDate));
  }

  @Test
  void testSetDueDate_ValidDate() {
    LocalDate validDueDate = LocalDate.of(2024, 12, 15);
    billing.setDueDate(validDueDate);
    assertEquals(validDueDate, billing.getDueDate());
  }

  @Test
  void testSetDueDate_InvalidDate() {
    LocalDate invalidDueDate = LocalDate.of(2024, 12, 10); // Less than 14 days after readingEntryDate
    assertThrows(IllegalArgumentException.class, () -> billing.setDueDate(invalidDueDate));
  }

  @Test
  void testSetBillPaymentDate_ValidDate() {
    LocalDate validPaymentDate = LocalDate.of(2024, 12, 10);
    billing.setBillPaymentDate(validPaymentDate);
    assertEquals(validPaymentDate, billing.getBillPaymentDate());
  }

  @Test
  void testSetBillPaymentDate_InvalidDate() {
    LocalDate invalidPaymentDate = LocalDate.of(2024, 11, 30); // Before readingEntryDate
    assertThrows(IllegalArgumentException.class, () -> billing.setBillPaymentDate(invalidPaymentDate));
  }

  @Test
  void testSetAndGetCustID() {
    billing.setCustID("C002");
    assertEquals("C002", billing.getCustID());
  }

  @Test
  void testSetAndGetBillingMonth() {
    billing.setBillingMonth(Month.FEBRUARY);
    assertEquals(Month.FEBRUARY, billing.getBillingMonth());
  }

  @Test
  void testSetAndGetElectricityCost() {
    BigDecimal newCost = new BigDecimal("200.00");
    billing.setElectricityCost(newCost);
    assertEquals(newCost, billing.getElectricityCost());
  }

  @Test
  void testSetAndGetSalesTaxAmount() {
    BigDecimal newTax = new BigDecimal("15.00");
    billing.setSalesTaxAmount(newTax);
    assertEquals(newTax, billing.getSalesTaxAmount());
  }

  @Test
  void testSetAndGetFixedCharges() {
    BigDecimal newCharges = new BigDecimal("20.00");
    billing.setFixedCharges(newCharges);
    assertEquals(newCharges, billing.getFixedCharges());
  }

  @Test
  void testSetAndGetTotalBillingAmount() {
    BigDecimal newAmount = new BigDecimal("150.00");
    billing.setTotalBillingAmount(newAmount);
    assertEquals(newAmount, billing.getTotalBillingAmount());
  }

  @Test
  void testSetAndGetBillPaidStatus() {
    billing.setBillPaidStatus(BillStatus.PAID);
    assertEquals(BillStatus.PAID, billing.getBillPaidStatus());
  }

  @Test
  void testParseDate_ValidDate() {
    String validDateString = "01/12/2024";
    LocalDate expectedDate = LocalDate.of(2024, 12, 1);
    assertEquals(expectedDate, Billing.parseDate(validDateString));
  }

  @Test
  void testParseDate_InvalidDate() {
    String invalidDateString = "2024-12-01";
    assertThrows(IllegalArgumentException.class, () -> Billing.parseDate(invalidDateString));
  }

  @Test
  void testSetAndGetCustomerName() {
    billing.setCustomerName("John Doe");
    assertEquals("John Doe", billing.getCustName());
  }

  @Test
  void testDisplayInfo() {
    String expectedInfo = "CustID= C001 Billing Month=JANUARY Current Regular Meter Reading= 500\n" +
        "Current Peak Meter Reading= 300 Reading Entry Date= 2024-12-01\n" +
        "Electricity Cost= 100.00 SalesTaxAmount= 10.00 Fixed Charges= 5.00\n" +
        "Total Billing Amount= 115.00 Due Date= 2024-12-15 Bill Paid Status= UNPAID Bill Payment Date= 2024-12-10";
    assertEquals(expectedInfo, billing.toString());
  }
}
