package ViewTests;

import java.math.BigDecimal;

import javax.swing.JOptionPane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mycompany.a1_scd_22l7942.CustomerType;
import com.mycompany.a1_scd_22l7942.MeterType;
import com.mycompany.a1_scd_22l7942.TarrifTax;

import Controller.EmployeeController;
import View.EmployeeEditTarrifInfo;

class EmployeeEditTarrifInfoTest {

  private TarrifTax mockTarrif;
  private EmployeeController mockController;
  private EmployeeEditTarrifInfo editTarrifInfo;

  @BeforeEach
  void setUp() {
    mockTarrif = mock(TarrifTax.class);
    mockController = mock(EmployeeController.class);

    when(mockTarrif.getMeterType()).thenReturn(MeterType.SINGLE_PHASE);

    when(mockTarrif.getCustType()).thenReturn(CustomerType.COMMERCIAL);

    when(mockTarrif.getPercentageOfTax()).thenReturn(5.0f);
    when(mockTarrif.getRegularUnitPrice()).thenReturn(new BigDecimal("10.5"));

    editTarrifInfo = new EmployeeEditTarrifInfo(mockTarrif, mockController);
  }

  @Test
  @DisplayName("Test valid input for single-phase meter type")
  void testValidInputSinglePhase() {
    editTarrifInfo.tfFixedPrice.setText("200");
    editTarrifInfo.tfTaxPercentage.setText("8.5");
    editTarrifInfo.tfRegularUnitPrice.setText("15.0");

    editTarrifInfo.jbUpdate.doClick();

    verify(mockController, times(1)).updateTarrif(mockTarrif);

    verify(mockTarrif).setFixedCharges(200);
    verify(mockTarrif).setPercentageOfTax(8.5f);
    verify(mockTarrif).setRegularUnitPrice(new BigDecimal("15.0"));

    JOptionPane.getRootFrame().dispose();
  }

  @Test
  @DisplayName("Test valid input for three-phase meter type")
  void testValidInputThreePhase() {
    when(mockTarrif.getMeterType()).thenReturn(MeterType.THREE_PHASE);
    when(mockTarrif.getPeakHourUnitPrice()).thenReturn(new BigDecimal("20.0"));

    editTarrifInfo = new EmployeeEditTarrifInfo(mockTarrif, mockController);

    editTarrifInfo.tfFixedPrice.setText("250");
    editTarrifInfo.tfTaxPercentage.setText("7.0");
    editTarrifInfo.tfRegularUnitPrice.setText("12.0");
    editTarrifInfo.tfPeakUnitPrice.setText("18.0");

    editTarrifInfo.jbUpdate.doClick();

    verify(mockController, times(1)).updateTarrif(mockTarrif);

    verify(mockTarrif).setFixedCharges(250);
    verify(mockTarrif).setPercentageOfTax(7.0f);
    verify(mockTarrif).setRegularUnitPrice(new BigDecimal("12.0"));
    verify(mockTarrif).setPeakHourUnitPrice(new BigDecimal("18.0"));
  }

  @Test
  @DisplayName("Test cancel button functionality")
  void testCancelButton() {
    editTarrifInfo.jbCancel.doClick();

    assert !editTarrifInfo.isVisible();
  }
}
