package ModelTests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.mycompany.a1_scd_22l7942.Customer;
import com.mycompany.a1_scd_22l7942.CustomerType;
import com.mycompany.a1_scd_22l7942.FileCustomerDataAccess;
import com.mycompany.a1_scd_22l7942.MeterType;
import static com.mycompany.a1_scd_22l7942.MeterType.SINGLE_PHASE;

class FileCustomerDataAccessTest {

  @Test
  void testLoadAllCustomersValidData() throws IOException {
    String testFile = "CustomerInfo.txt";
    BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
    writer.write("C001,12345,John Doe,123 Elm St,1234567890,COMMERCIAL,SINGLE_PHASE,01/01/2023,100,50");
    writer.newLine();
    writer.close();

    FileCustomerDataAccess dataAccess = new FileCustomerDataAccess();
    List<Customer> customers = dataAccess.loadAllCustomers();
    assertEquals(1, customers.size());

    Customer customer = customers.get(0);
    assertEquals("C001", customer.getCustID());
    assertEquals("12345", customer.getCNIC());
    assertEquals("John Doe", customer.getCustName());
    assertEquals("123 Elm St", customer.getCustAddress());
    assertEquals("1234567890", customer.getPhoneNumber());
    assertEquals(CustomerType.COMMERCIAL, customer.getCustType());
    assertEquals(SINGLE_PHASE, customer.getMeterType());
    assertEquals(LocalDate.parse("01/01/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
        customer.getConnectionDate());
    assertEquals(100, customer.getRegularUnitsConsumed());
    assertEquals(50, customer.getPeakHourUnitsConsumed());

    new File(testFile).delete();
  }

  @Test
  void testLoadAllCustomersInvalidData() throws IOException {
    String testFile = "CustomerInfo.txt";
    BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
    writer.write("C001,12345,John Doe,123 Elm St,1234567890,INVALID,ELECTRIC,01/01/2023,100,50");
    writer.newLine();
    writer.close();

    FileCustomerDataAccess dataAccess = new FileCustomerDataAccess();
    List<Customer> customers = dataAccess.loadAllCustomers();
    assertTrue(customers.isEmpty());

    new File(testFile).delete();
  }

  @Test
  void testSaveAllCustomers() throws IOException {
    String testFile = "CustomerInfo.txt";
    List<Customer> customers = new ArrayList<>();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    Customer customer = new Customer();
    customer.setCustID("C001");
    customer.setCNIC("12345");
    customer.setCustName("John Doe");
    customer.setCustAddress("123 Elm St");
    customer.setPhoneNumber("1234567890");
    customer.setCustType(CustomerType.COMMERCIAL);
    customer.setMeterType(MeterType.SINGLE_PHASE);
    customer.setConnectionDate(LocalDate.parse("01/01/2023", dateFormatter));
    customer.setRegularUnitsConsumed(100);
    customer.setPeakHourUnitsConsumed(50);

    customers.add(customer);

    FileCustomerDataAccess dataAccess = new FileCustomerDataAccess();
    dataAccess.saveAllCustomers(customers);

    BufferedReader reader = new BufferedReader(new FileReader(testFile));
    String line = reader.readLine();
    assertEquals("C001,12345,John Doe,123 Elm St,1234567890,COMMERCIAL,SINGLE_PHASE,01/01/2023,100,50", line);
    reader.close();

    new File(testFile).delete();
  }
}
