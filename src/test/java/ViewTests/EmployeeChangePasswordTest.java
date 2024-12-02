package tests.ViewTests;

import javax.swing.JOptionPane;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import Controller.EmployeeController;
import View.EmployeeChangePassword;

class EmployeeChangePasswordTest {

  private EmployeeController mockController;
  private EmployeeChangePassword employeeChangePassword;

  @BeforeEach
  void setUp() {
    mockController = mock(EmployeeController.class);
    when(mockController.getUsername()).thenReturn("testUser");
    when(mockController.getPassword()).thenReturn("currentPassword");
    employeeChangePassword = new EmployeeChangePassword(mockController);
  }

  @AfterEach
  void tearDown() {
    employeeChangePassword.dispose();
  }

  @Test
  @DisplayName("Test password mismatch error")
  void testPasswordMismatchError() {
    // Set mismatched new password and confirm password
    employeeChangePassword.pfCurrentPassword.setText("currentPassword");
    employeeChangePassword.pfNewPassword.setText("newPassword");
    employeeChangePassword.pfConfirmPassword.setText("mismatchedPassword");

    // Simulate button click
    employeeChangePassword.jbChangePassword.doClick();

    // Verify the error message is displayed
    verify(mockController, never()).changePassword(anyString(), anyString());
    Assertions.assertEquals("Error: New password and confirm password do not match.",
        JOptionPane.getRootFrame().getAccessibleContext().getAccessibleDescription());
  }

  @Test
  @DisplayName("Test incorrect current password error")
  void testIncorrectCurrentPasswordError() {
    // Set incorrect current password
    employeeChangePassword.pfCurrentPassword.setText("wrongPassword");
    employeeChangePassword.pfNewPassword.setText("newPassword");
    employeeChangePassword.pfConfirmPassword.setText("newPassword");

    // Simulate button click
    employeeChangePassword.jbChangePassword.doClick();

    // Verify the error message is displayed
    verify(mockController, never()).changePassword(anyString(), anyString());
    Assertions.assertEquals("Error: Current Password is incorrect",
        JOptionPane.getRootFrame().getAccessibleContext().getAccessibleDescription());
  }

  @Test
  @DisplayName("Test successful password change")
  void testSuccessfulPasswordChange() {
    // Set valid passwords
    employeeChangePassword.pfCurrentPassword.setText("currentPassword");
    employeeChangePassword.pfNewPassword.setText("newPassword");
    employeeChangePassword.pfConfirmPassword.setText("newPassword");

    // Simulate button click
    employeeChangePassword.jbChangePassword.doClick();

    // Verify that the controller's changePassword method was called
    verify(mockController).changePassword("currentPassword", "newPassword");

    // Ensure the window is closed after successful password change
    Assertions.assertFalse(employeeChangePassword.isVisible());
  }

  @Test
  @DisplayName("Test cancel button functionality")
  void testCancelButton() {
    // Simulate cancel button click
    employeeChangePassword.jbCancel.doClick();

    // Verify that the window is closed
    Assertions.assertFalse(employeeChangePassword.isVisible());

    // Ensure no further interactions occurred beyond initialization
    verify(mockController, times(1)).getUsername();
    verifyNoMoreInteractions(mockController);
  }

}
