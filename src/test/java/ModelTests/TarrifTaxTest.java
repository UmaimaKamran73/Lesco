package ModelTests;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.mycompany.a1_scd_22l7942.CustomerType;
import com.mycompany.a1_scd_22l7942.MeterType;
import com.mycompany.a1_scd_22l7942.TarrifTax;
import java.util.List;

class TarrifTaxTest {

  @Test
  void testConstructorWithRegularUnitPriceOnly() {
    TarrifTax tarrifTax = new TarrifTax(
        MeterType.SINGLE_PHASE,
        CustomerType.COMMERCIAL,
        BigDecimal.valueOf(5.25),
        10.5f,
        100);

    assertEquals(MeterType.SINGLE_PHASE, tarrifTax.getMeterType());
    assertEquals(CustomerType.COMMERCIAL, tarrifTax.getCustType());
    assertEquals(BigDecimal.valueOf(5.25), tarrifTax.getRegularUnitPrice());
    assertEquals(10.5f, tarrifTax.getPercentageOfTax());
    assertEquals(100, tarrifTax.getFixedCharges());
  }

  @Test
  void testConstructorWithPeakHourUnitPrice() {
    TarrifTax tarrifTax = new TarrifTax(
        MeterType.THREE_PHASE,
        CustomerType.COMMERCIAL,
        BigDecimal.valueOf(7.50),
        BigDecimal.valueOf(9.00),
        15.0f,
        200);

    assertEquals(MeterType.THREE_PHASE, tarrifTax.getMeterType());
    assertEquals(CustomerType.COMMERCIAL, tarrifTax.getCustType());
    assertEquals(BigDecimal.valueOf(7.50), tarrifTax.getRegularUnitPrice());
    assertEquals(BigDecimal.valueOf(9.00), tarrifTax.getPeakHourUnitPrice());
    assertEquals(15.0f, tarrifTax.getPercentageOfTax());
    assertEquals(200, tarrifTax.getFixedCharges());
  }

  @Test
  void testSetAndGetRegularUnitPrice() {
    TarrifTax tarrifTax = new TarrifTax(
        MeterType.SINGLE_PHASE,
        CustomerType.COMMERCIAL,
        BigDecimal.valueOf(5.25),
        10.5f,
        100);

    tarrifTax.setRegularUnitPrice(BigDecimal.valueOf(6.00));
    assertEquals(BigDecimal.valueOf(6.00), tarrifTax.getRegularUnitPrice());
  }

  @Test
  void testSetAndGetPeakHourUnitPrice() {
    TarrifTax tarrifTax = new TarrifTax(
        MeterType.THREE_PHASE,
        CustomerType.COMMERCIAL,
        BigDecimal.valueOf(7.50),
        BigDecimal.valueOf(9.00),
        15.0f,
        200);

    tarrifTax.setPeakHourUnitPrice(BigDecimal.valueOf(10.00));
    assertEquals(BigDecimal.valueOf(10.00), tarrifTax.getPeakHourUnitPrice());
  }

  @Test
  void testSetAndGetPercentageOfTax() {
    TarrifTax tarrifTax = new TarrifTax(
        MeterType.SINGLE_PHASE,
        CustomerType.COMMERCIAL,
        BigDecimal.valueOf(5.25),
        10.5f,
        100);

    tarrifTax.setPercentageOfTax(12.5f);
    assertEquals(12.5f, tarrifTax.getPercentageOfTax());
  }

  @Test
  void testSetAndGetFixedCharges() {
    TarrifTax tarrifTax = new TarrifTax(
        MeterType.SINGLE_PHASE,
        CustomerType.COMMERCIAL,
        BigDecimal.valueOf(5.25),
        10.5f,
        100);

    tarrifTax.setFixedCharges(150);
    assertEquals(150, tarrifTax.getFixedCharges());
  }
}
