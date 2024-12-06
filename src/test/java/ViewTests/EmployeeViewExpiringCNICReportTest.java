package ViewTests;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mycompany.a1_scd_22l7942.Nadra;

import Controller.EmployeeController;
import View.EmployeeViewExpiringCNICReport;

public class EmployeeViewExpiringCNICReportTest {

  private EmployeeController empControllerMock;
  private EmployeeViewExpiringCNICReport employeeView;
  private ArrayList<Nadra> mockData;

  @BeforeEach
  public void setUp() {
    empControllerMock = mock(EmployeeController.class);
    mockData = new ArrayList<>();

    mockData.add(new Nadra("C001", "3520105043723", LocalDate.of(2023, 12, 1)));
    mockData.add(new Nadra("C002", "3520105043724", LocalDate.of(2024, 1, 1)));

    when(empControllerMock.getExpiringCNICList()).thenReturn(mockData);

    employeeView = new EmployeeViewExpiringCNICReport(empControllerMock);
  }

  @Test
  public void testViewInitialization() {
    assertEquals(2, employeeView.model.getRowCount());
    assertEquals("C001", employeeView.model.getValueAt(0, 0));
    assertEquals("3520105043723", employeeView.model.getValueAt(0, 1));
    assertEquals(LocalDate.of(2023, 12, 1), employeeView.model.getValueAt(0, 2));
  }

  @Test
  public void testHandleBackToMainMenu() {
    employeeView.handleBackToMainMenu();

    verify(empControllerMock, times(1)).getExpiringCNICList();
  }
}
