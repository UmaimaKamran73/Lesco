package ViewTests;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mycompany.a1_scd_22l7942.MeterType;

import Controller.CustomerController;
import View.CustomerViewCurrentBill1;

public class CustomerViewCurrentBill1Test {

  private CustomerController mockCustomerController;
  private CustomerViewCurrentBill1 customerView;

  @BeforeEach
  public void setUp() {
    mockCustomerController = mock(CustomerController.class);
    customerView = new CustomerViewCurrentBill1(mockCustomerController);
  }

  @Test
  public void testGetMeterType_SinglePhase() {

    customerView.rbSinglePhase.setSelected(true);
    assertEquals("Single Phase", customerView.getMeterType());
  }

  @Test
  public void testGetMeterType_ThreePhase() {

    customerView.rbThreePhase.setSelected(true);
    assertEquals("Three Phase", customerView.getMeterType());
  }

  @Test
  public void testGetMeterReading() {
    JTextField tfMeterReading = customerView.tfMeterReading;
    tfMeterReading.setText("1000");
    assertEquals("1000", customerView.getMeterReading());
  }

  @Test
  public void testHandleViewBill_Success() {

    when(mockCustomerController.viewCurrBill(1000, MeterType.SINGLE_PHASE)).thenReturn(Map.of("bill", "details"));

    customerView.tfMeterReading.setText("1000");
    customerView.rbSinglePhase.setSelected(true);

    JButton viewBillButton = customerView.jbViewBill;
    viewBillButton.doClick();

    verify(mockCustomerController).viewCurrBill(1000, MeterType.SINGLE_PHASE);

  }

  @Test
  public void testHandleViewBill_Failure_InvalidInput() {

    customerView.tfMeterReading.setText("invalid");

    JButton viewBillButton = customerView.jbViewBill;
    viewBillButton.doClick();

  }

  @Test
  public void testHandleViewBill_Failure_BillNotFound() {

    when(mockCustomerController.viewCurrBill(1000, MeterType.SINGLE_PHASE))
        .thenReturn(Map.of("error", "No bill found"));

    customerView.tfMeterReading.setText("1000");
    customerView.rbSinglePhase.setSelected(true);

    JButton viewBillButton = customerView.jbViewBill;
    viewBillButton.doClick();

  }

  @Test
  public void testHandleCancel() {

    JButton cancelButton = customerView.jbCancel;
    cancelButton.doClick();

    assertFalse(customerView.isVisible());
  }
}
