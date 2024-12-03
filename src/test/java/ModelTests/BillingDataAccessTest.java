package ModelTests;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.a1_scd_22l7942.BillStatus;
import com.mycompany.a1_scd_22l7942.Billing;
import com.mycompany.a1_scd_22l7942.BillingDataAccess;
import com.mycompany.a1_scd_22l7942.Customer;
import com.mycompany.a1_scd_22l7942.CustomerType;
import com.mycompany.a1_scd_22l7942.MeterType;

class BillingDataAccessTest {

  private BillingDataAccess billingDataAccess;
  private ArrayList<Billing> billingList;
  private ArrayList<Customer> customerList;

  @BeforeEach
  void setUp() {
    billingDataAccess = new BillingDataAccess();
    billingList = new ArrayList<>();
    customerList = new ArrayList<>();

    Customer customer = new Customer("C001", "2520105043723", "John Doe", "123 Street", "1234567890",
        CustomerType.COMMERCIAL, MeterType.SINGLE_PHASE, LocalDate.of(2023, 12, 1), 20, 30);
    customerList.add(customer);

    Billing billing = new Billing(
        "C001",
        Month.JANUARY,
        1500,
        200,
        LocalDate.now().minusDays(10),
        new BigDecimal("300.00"),
        new BigDecimal("45.00"),
        new BigDecimal("50.00"),
        new BigDecimal("395.00"),
        LocalDate.now().minusDays(3),
        BillStatus.UNPAID);
    billingList.add(billing);
  }

  @Test
  void testLoadFileData() {
    ArrayList<Billing> loadedData = billingDataAccess.loadFileData();
    assertNotNull(loadedData);
  }

  @Test
  void testAddBillingToCustomer() {
    BillingDataAccess.addBillingToCustomer("C001", billingList, customerList);

    Customer customer = BillingDataAccess.findCustomer(customerList, "C001");
    assertNotNull(customer);
    assertEquals(1, customer.billingHistory.size());
    assertEquals("C001", customer.billingHistory.get(0).getCustID());
  }

  @Test
  void testFindCustomer() {
    Customer customer = BillingDataAccess.findCustomer(customerList, "C001");
    assertNotNull(customer);
    assertEquals("John Doe", customer.getCustName());
  }

  @Test
  void testSaveFileData() {
    billingDataAccess.saveFileData(billingList);

    ArrayList<Billing> reloadedBilling = billingDataAccess.loadFileData();
    assertNotNull(reloadedBilling);
    assertFalse(reloadedBilling.isEmpty());
    assertEquals("C001", reloadedBilling.get(0).getCustID());
    assertEquals(BillStatus.UNPAID, reloadedBilling.get(0).getBillPaidStatus());
  }

  @Test
  void testLoadInvalidFile() {
    billingDataAccess = new BillingDataAccess() {
      @Override
      public ArrayList<Billing> loadFileData() {
        return new ArrayList<>();
      }
    };

    ArrayList<Billing> loadedData = billingDataAccess.loadFileData();
    assertTrue(loadedData.isEmpty());
  }

  @Test
  void testSaveFileWithInvalidData() {
    billingList.add(new Billing(
        "C002",
        Month.FEBRUARY,
        1500,
        200,
        LocalDate.now().minusDays(15),
        new BigDecimal("400.00"),
        new BigDecimal("60.00"),
        new BigDecimal("70.00"),
        new BigDecimal("530.00"),
        LocalDate.now().minusDays(5),
        BillStatus.UNPAID));

    billingDataAccess.saveFileData(billingList);

    ArrayList<Billing> reloadedBilling = billingDataAccess.loadFileData();
    assertEquals(2, reloadedBilling.size());
    assertEquals("C002", reloadedBilling.get(1).getCustID());
  }
}
