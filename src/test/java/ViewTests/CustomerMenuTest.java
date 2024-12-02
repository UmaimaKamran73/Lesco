package tests.ViewTests;

import javax.swing.JButton;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import Controller.CustomerController;
import View.CustomerMenu;

public class CustomerMenuTest {

  private CustomerController mockCustomerController;
  private CustomerMenu customerMenu;

  @BeforeEach
  public void setUp() {
    mockCustomerController = mock(CustomerController.class);
    customerMenu = new CustomerMenu(mockCustomerController);
  }

  @Test
  public void testHandleUpdateCNICExpiryDate() {
    JButton jbUpdateCNICExpiryDate = customerMenu.jbUpdateCNICExpiryDate;
    jbUpdateCNICExpiryDate.doClick();
    assertFalse(customerMenu.isVisible());
  }

  @Test
  public void testHandleViewCurrentBill() {
    JButton jbViewCurrentBill = customerMenu.jbViewCurrentBill;
    jbViewCurrentBill.doClick();
    assertFalse(customerMenu.isVisible());
  }

  @Test
  public void testHandleLogout() {
    JButton jbLogout = customerMenu.jbLogout;
    jbLogout.doClick();
    assertFalse(customerMenu.isVisible());
  }
}
