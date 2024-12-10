package ModelTests;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.List;

import com.mycompany.a1_scd_22l7942.CustomerType;
import com.mycompany.a1_scd_22l7942.MeterType;
import com.mycompany.a1_scd_22l7942.TarrifTax;
import com.mycompany.a1_scd_22l7942.TarrifTaxDataAccess;

class TarrifTaxDataAccessTest {

  @Test
  void testLoadData() {
    TarrifTaxDataAccess dataAccess = new TarrifTaxDataAccess();
    List<TarrifTax> tarrifList = dataAccess.loadData();

    assertNotNull(tarrifList);
    assertTrue(tarrifList.size() > 0);

    TarrifTax firstRecord = tarrifList.get(0);
    assertNotNull(firstRecord.getMeterType());
    assertNotNull(firstRecord.getCustType());
    assertNotNull(firstRecord.getRegularUnitPrice());
    assertTrue(firstRecord.getPercentageOfTax() > 0);
    assertTrue(firstRecord.getFixedCharges() > 0);
  }

  @Test
  void testSaveData() {
    TarrifTaxDataAccess dataAccess = new TarrifTaxDataAccess();
    List<TarrifTax> tarrifList = new ArrayList<>();

    TarrifTax tarrifTax;
    tarrifTax = new TarrifTax(
        MeterType.SINGLE_PHASE,
        CustomerType.COMMERCIAL,
        BigDecimal.valueOf(5.25),
        BigDecimal.valueOf(7.50),
        12.5f,
        150);

    tarrifList.add(tarrifTax);
    dataAccess.saveData(tarrifList);

    List<TarrifTax> loadedList = dataAccess.loadData();

    assertEquals(1, loadedList.size());
    TarrifTax loadedTarrif = loadedList.get(0);

    assertEquals(tarrifTax.getMeterType(), loadedTarrif.getMeterType());
    assertEquals(tarrifTax.getCustType(), loadedTarrif.getCustType());
    assertEquals(tarrifTax.getRegularUnitPrice(), loadedTarrif.getRegularUnitPrice());
    assertEquals(tarrifTax.getPeakHourUnitPrice(), loadedTarrif.getPeakHourUnitPrice());
    assertEquals(tarrifTax.getPercentageOfTax(), loadedTarrif.getPercentageOfTax());
    assertEquals(tarrifTax.getFixedCharges(), loadedTarrif.getFixedCharges());
  }
}
