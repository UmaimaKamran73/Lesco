// package main.java.test.java.ViewTests;
package main.java.test.java.ViewTests;

import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import Controller.CustomerController;
import View.CustomerLogin;

public class CustomerLoginTest {

  private CustomerController mockController;
  private CustomerLogin customerLogin;

  @BeforeEach
  public void setUp() throws Exception {
    mockController = mock(CustomerController.class);

    SwingUtilities.invokeAndWait(() -> customerLogin = new CustomerLogin(mockController));
  }

  @Test
  public void testGetCustID() throws Exception {
    SwingUtilities.invokeAndWait(() -> customerLogin.tfCustID.setText("12345"));
    SwingUtilities.invokeAndWait(() -> assertEquals("12345", customerLogin.getCustID()));
  }

  @Test
  public void testGetCNIC() throws Exception {
    SwingUtilities.invokeAndWait(() -> customerLogin.tfCNIC.setText("12345-6789012-3"));
    SwingUtilities.invokeAndWait(() -> assertEquals("12345-6789012-3", customerLogin.getCNIC()));
  }

  @Test
  public void testHandleLoginSuccess() throws Exception {
    SwingUtilities.invokeAndWait(() -> {
      customerLogin.tfCustID.setText("12345");
      customerLogin.tfCNIC.setText("12345-6789012-3");
    });

    when(mockController.login("12345", "12345-6789012-3")).thenReturn(true);

    SwingUtilities.invokeAndWait(() -> customerLogin.jbLogin.doClick());

    verify(mockController).login("12345", "12345-6789012-3");

    SwingUtilities.invokeAndWait(() -> assertFalse(customerLogin.isVisible()));
  }

  @Test
  public void testHandleLoginFailure() throws Exception {
    SwingUtilities.invokeAndWait(() -> {
      customerLogin.tfCustID.setText("12345");
      customerLogin.tfCNIC.setText("wrong-cnic");
    });

    when(mockController.login("12345", "wrong-cnic")).thenReturn(false);

    SwingUtilities.invokeAndWait(() -> customerLogin.jbLogin.doClick());

    verify(mockController).login("12345", "wrong-cnic");

    SwingUtilities.invokeAndWait(() -> assertTrue(customerLogin.isVisible()));
  }

  @Test
  public void testHandleCancel() throws Exception {
    SwingUtilities.invokeAndWait(() -> customerLogin.jbCancel.doClick());
    SwingUtilities.invokeAndWait(() -> assertFalse(customerLogin.isDisplayable()));
  }
}
