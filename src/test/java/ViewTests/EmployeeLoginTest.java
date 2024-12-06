package ViewTests;

import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import Controller.EmployeeController;
import View.EmployeeLogin;

class EmployeeLoginTest {

  private EmployeeController mockController;
  private EmployeeLogin employeeLogin;

  @BeforeEach
  void setUp() {
    mockController = Mockito.mock(EmployeeController.class);
    SwingUtilities.invokeLater(() -> employeeLogin = new EmployeeLogin(mockController));
  }

  @AfterEach
  void tearDown() {
    SwingUtilities.invokeLater(() -> {
      if (employeeLogin != null) {
        employeeLogin.dispose();
      }
    });
  }

  @Test
  @DisplayName("Test successful login")
  void testSuccessfulLogin() throws Exception {
    when(mockController.login("validUser", "validPass")).thenReturn(true);

    SwingUtilities.invokeAndWait(() -> {
      employeeLogin.tfUsername.setText("validUser");
      employeeLogin.pfPassword.setText("validPass");

      employeeLogin.jbLogin.doClick();

      Assertions.assertFalse(employeeLogin.isVisible(), "Login window should close on successful login");
    });

    verify(mockController).login("validUser", "validPass");
  }

  @Test
  @DisplayName("Test invalid login")
  void testInvalidLogin() throws Exception {
    when(mockController.login("invalidUser", "wrongPass")).thenReturn(false);

    SwingUtilities.invokeAndWait(() -> {
      employeeLogin.tfUsername.setText("invalidUser");
      employeeLogin.pfPassword.setText("wrongPass");

      employeeLogin.jbLogin.doClick();

      JOptionPane optionPane = getOptionPane();
      Assertions.assertNotNull(optionPane, "Error dialog should appear for invalid login");
      Assertions.assertEquals("Invalid Username or Password", optionPane.getMessage(),
          "Expected error message for invalid login");
    });

    verify(mockController).login("invalidUser", "wrongPass");
  }

  @Test
  @DisplayName("Test empty username or password")
  void testEmptyUsernameOrPassword() throws Exception {
    SwingUtilities.invokeAndWait(() -> {
      employeeLogin.tfUsername.setText("");
      employeeLogin.pfPassword.setText("");

      employeeLogin.jbLogin.doClick();

      JOptionPane optionPane = getOptionPane();
      Assertions.assertNotNull(optionPane, "Error dialog should appear for empty fields");
      Assertions.assertEquals("Invalid Username or Password", optionPane.getMessage(),
          "Expected error message for empty fields");
    });

    verify(mockController).login("", "");
  }

  @Test
  @DisplayName("Test cancel button")
  void testCancelButton() throws Exception {
    SwingUtilities.invokeAndWait(() -> {
      employeeLogin.jbCancel.doClick();
      Assertions.assertFalse(employeeLogin.isVisible(), "Login window should close when cancel is clicked");
    });

    verifyNoInteractions(mockController);
  }

  private JOptionPane getOptionPane() {
    for (Window window : JFrame.getWindows()) {
      if (window instanceof JDialog) {
        JDialog dialog = (JDialog) window;
        if (dialog.getContentPane().getComponentCount() > 0
            && dialog.getContentPane().getComponent(0) instanceof JOptionPane) {
          return (JOptionPane) dialog.getContentPane().getComponent(0);
        }
      }
    }
    return null;
  }
}
