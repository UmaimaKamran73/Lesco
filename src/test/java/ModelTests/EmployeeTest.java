package ModelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.List;

import com.mycompany.a1_scd_22l7942.Employee;

class EmployeeTest {

  @Test
  void testGetUsername() {
    Employee employee = new Employee("JohnDoe123", "password123");
    assertEquals("JohnDoe123", employee.getUsername());
  }

  @Test
  void testSetUsername() {
    Employee employee = new Employee("JohnDoe123", "password123");
    employee.setUsername("JaneDoe456");
    assertEquals("JaneDoe456", employee.getUsername());
  }

  @Test
  void testGetPassword() {
    Employee employee = new Employee("JohnDoe123", "password123");
    assertEquals("password123", employee.getPassword());
  }

  @Test
  void testSetPassword() {
    Employee employee = new Employee("JohnDoe123", "password123");
    employee.setPassword("newPassword456");
    assertEquals("newPassword456", employee.getPassword());
  }

  @Test
  void testInvalidUsernameWithComma() {
    Employee employee = new Employee("John,Doe", "password123");
    assertFalse(employee.getUsername().matches("^[a-zA-Z0-9]*$"));
  }

  @Test
  void testInvalidPasswordWithComma() {
    Employee employee = new Employee("JohnDoe123", "pass,word123");
    assertFalse(employee.getPassword().matches("^[a-zA-Z0-9]*$"));
  }

  @Test
  void testValidUsername() {
    Employee employee = new Employee("JohnDoe123", "password123");
    assertTrue(employee.getUsername().matches("^[a-zA-Z0-9]*$"));
  }

  @Test
  void testValidPassword() {
    Employee employee = new Employee("JohnDoe123", "password123");
    assertTrue(employee.getPassword().matches("^[a-zA-Z0-9]*$"));
  }
}
