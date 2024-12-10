package ModelTests;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.List;

import com.mycompany.a1_scd_22l7942.Nadra;

class NadraTest {

  @Test
  void testIsCNICExpiredTrue() {
    Nadra nadra = new Nadra("C001", "12345", LocalDate.now().minusDays(1));
    assertTrue(nadra.isCNICExpired());
  }

  @Test
  void testIsCNICExpiredFalse() {
    Nadra nadra = new Nadra("C002", "67890", LocalDate.now().plusDays(1));
    assertFalse(nadra.isCNICExpired());
  }

  @Test
  void testIsCNICExpiringIn30DaysTrue() {
    Nadra nadra = new Nadra("C003", "54321", LocalDate.now().plusDays(15));
    assertTrue(nadra.isCNICExpiringIn30Days());
  }

  @Test
  void testIsCNICExpiringIn30DaysFalseExpired() {
    Nadra nadra = new Nadra("C004", "98765", LocalDate.now().minusDays(1));
    assertFalse(nadra.isCNICExpiringIn30Days());
  }

  @Test
  void testIsCNICExpiringIn30DaysFalseBeyond30Days() {
    Nadra nadra = new Nadra("C005", "13579", LocalDate.now().plusDays(40));
    assertFalse(nadra.isCNICExpiringIn30Days());
  }

  @Test
  void testGetCustID() {
    Nadra nadra = new Nadra("C006", "24680", LocalDate.now().plusDays(10));
    assertEquals("C006", nadra.getCustID());
  }

  @Test
  void testSetCustID() {
    Nadra nadra = new Nadra();
    nadra.setCustID("C007");
    assertEquals("C007", nadra.getCustID());
  }

  @Test
  void testGetCNIC() {
    Nadra nadra = new Nadra("C008", "11223", LocalDate.now().plusDays(20));
    assertEquals("11223", nadra.getCNIC());
  }

  @Test
  void testSetCNIC() {
    Nadra nadra = new Nadra();
    nadra.setCNIC("44556");
    assertEquals("44556", nadra.getCNIC());
  }

  @Test
  void testGetExpiryDate() {
    LocalDate expiryDate = LocalDate.now().plusDays(30);
    Nadra nadra = new Nadra("C009", "77889", expiryDate);
    assertEquals(expiryDate, nadra.getExpiryDate());
  }

  @Test
  void testSetExpiryDate() {
    Nadra nadra = new Nadra();
    LocalDate expiryDate = LocalDate.now().plusDays(25);
    nadra.setExpiryDate(expiryDate);
    assertEquals(expiryDate, nadra.getExpiryDate());
  }
}
