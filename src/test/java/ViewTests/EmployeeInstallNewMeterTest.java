package ViewTests;

import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import Controller.EmployeeController;
import View.EmployeeInstallNewMeter;

class EmployeeInstallNewMeterTest {

  private EmployeeController mockController;
  private EmployeeInstallNewMeter employeeInstallNewMeter;

  @BeforeEach
  void setUp() {
    mockController = Mockito.mock(EmployeeController.class);
    SwingUtilities.invokeLater(() -> employeeInstallNewMeter = new EmployeeInstallNewMeter(mockController));
  }

  @AfterEach
  void tearDown() {
    SwingUtilities.invokeLater(() -> {
      if (employeeInstallNewMeter != null) {
        employeeInstallNewMeter.dispose();
      }
    });
  }

  @Test
  @DisplayName("Test empty input fields")
  void testEmptyInputFields() throws Exception {
    SwingUtilities.invokeAndWait(() -> {
      employeeInstallNewMeter.tfCNIC.setText("");
      employeeInstallNewMeter.tfName.setText("");
      employeeInstallNewMeter.tfAddress.setText("");
      employeeInstallNewMeter.tfPhoneNumber.setText("");

      employeeInstallNewMeter.jbInstallMeter.doClick();

      JOptionPane optionPane = getOptionPane();
      Assertions.assertEquals("Please fill in all fields.", optionPane.getMessage(), "Expected error for empty fields");
    });

    verify(mockController, never()).installNewMeter(any(), any(), any(), any(), any(), any());
  }

  @Test
  @DisplayName("Test invalid CNIC format")
  void testInvalidCNIC() throws Exception {
    when(mockController.isValidCNIC("12345")).thenReturn(false);

    SwingUtilities.invokeAndWait(() -> {
      employeeInstallNewMeter.tfCNIC.setText("12345");
      employeeInstallNewMeter.tfName.setText("John Doe");
      employeeInstallNewMeter.tfAddress.setText("123 Elm Street");
      employeeInstallNewMeter.tfPhoneNumber.setText("1234567890");

      employeeInstallNewMeter.jbInstallMeter.doClick();

      JOptionPane optionPane = getOptionPane();
      Assertions.assertEquals("Invalid CNIC format. Must be 13 digits.", optionPane.getMessage(),
          "Expected error for invalid CNIC");
    });

    verify(mockController, never()).installNewMeter(any(), any(), any(), any(), any(), any());
  }

  @Test
  @DisplayName("Test successful meter installation")
  void testSuccessfulMeterInstallation() throws Exception {
    String successMessage = "New meter installed successfully.";
    when(mockController.isValidCNIC("1234567890123")).thenReturn(true);
    when(mockController.installNewMeter(any(), any(), any(), any(), any(), any())).thenReturn(successMessage);

    SwingUtilities.invokeAndWait(() -> {
      employeeInstallNewMeter.tfCNIC.setText("1234567890123");
      employeeInstallNewMeter.tfName.setText("John Doe");
      employeeInstallNewMeter.tfAddress.setText("123 Elm Street");
      employeeInstallNewMeter.tfPhoneNumber.setText("1234567890");
      employeeInstallNewMeter.rbDomestic.setSelected(true);
      employeeInstallNewMeter.rbSinglePhase.setSelected(true);

      employeeInstallNewMeter.jbInstallMeter.doClick();

      JOptionPane optionPane = getOptionPane();
      Assertions.assertEquals(successMessage, optionPane.getMessage(),
          "Expected success message for meter installation");
    });

    verify(mockController).installNewMeter("1234567890123", "John Doe", "123 Elm Street", "1234567890", "Domestic",
        "Single Phase");
  }

  @Test
  @DisplayName("Test cancel button")
  void testCancelButton() throws Exception {
    SwingUtilities.invokeAndWait(() -> {
      employeeInstallNewMeter.jbCancel.doClick();
      Assertions.assertFalse(employeeInstallNewMeter.isVisible(), "Window should be closed after cancel");
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
