package tests.ViewTests;

import java.time.LocalDate;

import javax.swing.JButton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import Controller.CustomerController;
import View.CustomerUpdateCNICExpiryDate;

public class CustomerUpdateCNICExpiryDateTest {

  private CustomerController mockCustomerController;
  private CustomerUpdateCNICExpiryDate updateCNICView;

  @BeforeEach
  public void setUp() {
    mockCustomerController = mock(CustomerController.class);
    when(mockCustomerController.getCNIC()).thenReturn("1122334455667");
    when(mockCustomerController.getExpiryDate()).thenReturn(LocalDate.of(2025, 6, 12));
    updateCNICView = new CustomerUpdateCNICExpiryDate(mockCustomerController);
  }

  @Test
  public void testGetNewExpiryDate() {
    updateCNICView.tfNewExpiryDate.setText("2030-12-15");
    assert updateCNICView.getNewExpiryDate().equals("2030-12-15");
  }

  @Test
  public void testHandleUpdate_Success() {
    when(mockCustomerController.updateCNICExpiryDate("2030-12-15")).thenReturn(true);

    updateCNICView.tfNewExpiryDate.setText("2030-12-15");
    JButton jbUpdate = updateCNICView.jbUpdate;
    jbUpdate.doClick();

    verify(mockCustomerController).updateCNICExpiryDate("2030-12-15");
    assert !updateCNICView.isVisible();
  }

  @Test
  public void testHandleUpdate_Failure() {
    when(mockCustomerController.updateCNICExpiryDate("2030-12-15")).thenReturn(false);

    updateCNICView.tfNewExpiryDate.setText("2030-12-15");
    JButton jbUpdate = updateCNICView.jbUpdate;
    jbUpdate.doClick();

    verify(mockCustomerController).updateCNICExpiryDate("2030-12-15");
    assert updateCNICView.isVisible();
  }

  @Test
  public void testHandleCancel() {
    JButton jbCancel = updateCNICView.jbCancel;
    jbCancel.doClick();

    assert !updateCNICView.isVisible();
  }
}
