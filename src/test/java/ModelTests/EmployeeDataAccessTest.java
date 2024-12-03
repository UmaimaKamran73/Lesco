package ModelTests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.mycompany.a1_scd_22l7942.Employee;
import com.mycompany.a1_scd_22l7942.EmployeeDataAccess;

class EmployeeDataAccessTest {

  @Test
  void testAuthenticateEmployeeValidCredentials() throws IOException {
    String testFile = "EmployeeInfo.txt";
    BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
    writer.write("JohnDoe,password123");
    writer.newLine();
    writer.close();

    EmployeeDataAccess dataAccess = new EmployeeDataAccess();
    Employee employee = dataAccess.authenticateEmployee("JohnDoe", "password123");
    assertNotNull(employee);
    assertEquals("JohnDoe", employee.getUsername());
    assertEquals("password123", employee.getPassword());

    new File(testFile).delete();
  }

  @Test
  void testAuthenticateEmployeeInvalidPassword() throws IOException {
    String testFile = "EmployeeInfo.txt";
    BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
    writer.write("JohnDoe,password123");
    writer.newLine();
    writer.close();

    EmployeeDataAccess dataAccess = new EmployeeDataAccess();
    Employee employee = dataAccess.authenticateEmployee("JohnDoe", "wrongPassword");
    assertNull(employee);

    new File(testFile).delete();
  }

  @Test
  void testAuthenticateEmployeeInvalidUsername() throws IOException {
    String testFile = "EmployeeInfo.txt";
    BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
    writer.write("JohnDoe,password123");
    writer.newLine();
    writer.close();

    EmployeeDataAccess dataAccess = new EmployeeDataAccess();
    Employee employee = dataAccess.authenticateEmployee("InvalidUser", "password123");
    assertNull(employee);

    new File(testFile).delete();
  }

  @Test
  void testUpdatePasswordSuccess() throws IOException {
    String testFile = "EmployeeInfo.txt";
    BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
    writer.write("JohnDoe,password123");
    writer.newLine();
    writer.close();

    EmployeeDataAccess dataAccess = new EmployeeDataAccess();
    boolean updated = dataAccess.updatePassword("JohnDoe", "newPassword123");
    assertTrue(updated);

    BufferedReader reader = new BufferedReader(new FileReader(testFile));
    String line = reader.readLine();
    assertEquals("JohnDoe,newPassword123", line);
    reader.close();

    new File(testFile).delete();
  }

  @Test
  void testUpdatePasswordUserNotFound() throws IOException {
    String testFile = "EmployeeInfo.txt";
    BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
    writer.write("JohnDoe,password123");
    writer.newLine();
    writer.close();

    EmployeeDataAccess dataAccess = new EmployeeDataAccess();
    boolean updated = dataAccess.updatePassword("InvalidUser", "newPassword123");
    assertFalse(updated);

    BufferedReader reader = new BufferedReader(new FileReader(testFile));
    String line = reader.readLine();
    assertEquals("JohnDoe,password123", line);
    reader.close();

    new File(testFile).delete();
  }
}
