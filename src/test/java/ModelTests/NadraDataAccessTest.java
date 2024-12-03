package ModelTests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.mycompany.a1_scd_22l7942.Nadra;
import com.mycompany.a1_scd_22l7942.NadraDataAccess;

class NadraDataAccessTest {

  @Test
  void testLoadNadraData() throws IOException {
    String mockData = "C001,12345,25/12/2024\nC002,67890,25/12/2024";
    BufferedReader reader = new BufferedReader(new StringReader(mockData));
    NadraDataAccess nadraDataAccess;
    nadraDataAccess = new NadraDataAccess() {
      public BufferedReader createReader() {
        return reader;
      }
    };

    ArrayList<Nadra> nadraList = nadraDataAccess.loadNadraData();
    assertEquals(1, nadraList.size());
    assertEquals("C001", nadraList.get(0).getCustID());
    assertEquals("12345", nadraList.get(0).getCNIC());
    assertEquals(LocalDate.of(2025, 02, 01), nadraList.get(0).getExpiryDate());
  }

  @Test
  void testUpdateCNICExpiryDate() {
    ArrayList<Nadra> nadraList = new ArrayList<>();
    nadraList.add(new Nadra("C001", "12345", LocalDate.now().plusDays(30)));

    NadraDataAccess nadraDataAccess = new NadraDataAccess();
    boolean result = nadraDataAccess.updateCNICExpiryDate(nadraList, "12345", LocalDate.now().plusDays(60));

    assertTrue(result);
    assertEquals(LocalDate.now().plusDays(60), nadraList.get(0).getExpiryDate());
  }

  @Test
  void testUpdateCNICExpiryDateNotFound() {
    ArrayList<Nadra> nadraList = new ArrayList<>();
    nadraList.add(new Nadra("C001", "12345", LocalDate.now().plusDays(30)));

    NadraDataAccess nadraDataAccess = new NadraDataAccess();
    boolean result = nadraDataAccess.updateCNICExpiryDate(nadraList, "67890", LocalDate.now().plusDays(60));

    assertFalse(result);
  }

  @Test
  void testCheckCNICExpiring() {
    ArrayList<Nadra> nadraList = new ArrayList<>();
    nadraList.add(new Nadra("C001", "12345", LocalDate.now().plusDays(15)));
    nadraList.add(new Nadra("C002", "67890", LocalDate.now().plusDays(45)));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    NadraDataAccess nadraDataAccess = new NadraDataAccess();
    nadraDataAccess.checkCNICExpiring(nadraList);

    String output = outputStream.toString();
    assertTrue(output.contains("C001"));
    assertFalse(output.contains("C002"));
  }

  @Test
  void testSaveNadraData() throws IOException {
    ArrayList<Nadra> nadraList = new ArrayList<>();
    nadraList.add(new Nadra("C001", "12345", LocalDate.now().plusDays(30)));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

    NadraDataAccess nadraDataAccess = new NadraDataAccess() {
      public BufferedWriter createWriter() {
        return writer;
      }
    };

    nadraDataAccess.saveNadraData(nadraList);
    writer.flush();

    String output = outputStream.toString();
    assertFalse(output.contains("C001,12345"));
  }
}
