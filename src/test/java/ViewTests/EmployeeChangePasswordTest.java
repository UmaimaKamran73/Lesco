package ViewTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.List;

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
  @DisplayName("Test successful password change")
  void testSuccessfulPasswordChange() {

    employeeChangePassword.pfCurrentPassword.setText("currentPassword");
    employeeChangePassword.pfNewPassword.setText("newPassword");
    employeeChangePassword.pfConfirmPassword.setText("newPassword");

    employeeChangePassword.jbChangePassword.doClick();

    verify(mockController).changePassword("currentPassword", "newPassword");

    Assertions.assertFalse(employeeChangePassword.isVisible());
  }

  @Test
  @DisplayName("Test cancel button functionality")
  void testCancelButton() {

    employeeChangePassword.jbCancel.doClick();

    Assertions.assertFalse(employeeChangePassword.isVisible());

    verify(mockController, times(1)).getUsername();
    verifyNoMoreInteractions(mockController);
  }

}
