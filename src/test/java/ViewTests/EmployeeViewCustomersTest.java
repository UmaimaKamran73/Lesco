package ViewTests;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.mycompany.a1_scd_22l7942.Customer;
import com.mycompany.a1_scd_22l7942.CustomerType;
import com.mycompany.a1_scd_22l7942.MeterType;

import Controller.EmployeeController;
import View.EmployeeViewCustomers;

public class EmployeeViewCustomersTest {

  @Mock
  private EmployeeController empControllerMock;
  private EmployeeViewCustomers employeeView;
  private ArrayList<Customer> mockCustomerData;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    mockCustomerData = new ArrayList<>();
    mockCustomerData.add(new Customer("C001", "3520105043723", "John Doe", "123 Main St", "1234567890",
        CustomerType.COMMERCIAL, MeterType.SINGLE_PHASE,
        LocalDate.of(2022, 1, 1), 150, 100));
    mockCustomerData.add(
        new Customer("C002", "3520105043724", "Jane Smith", "456 Elm St", "0987654321", CustomerType.DOMESTIC,
            MeterType.THREE_PHASE, LocalDate.of(2022, 2, 1), 200, 150));

    when(empControllerMock.getCustomerList()).thenReturn(mockCustomerData);

    employeeView = new EmployeeViewCustomers(empControllerMock);
  }

  @Test
  public void testPopulateTable() {
    employeeView.populateTable();

    assertEquals(2, employeeView.model.getRowCount());

    assertEquals("C001", employeeView.model.getValueAt(0, 0));
    assertEquals("John Doe", employeeView.model.getValueAt(0, 1));
  }

  @Test
  public void testSearchBarFilter() {
    employeeView.searchBar.setText("John");
    employeeView.filterTable();

    assertEquals(1, employeeView.model.getRowCount());
    assertEquals("C001", employeeView.model.getValueAt(0, 0));
  }

  @Test
  public void testDeleteCustomer() {
    Customer customerToDelete = mockCustomerData.get(0);

    employeeView.handleDelete(customerToDelete);

    verify(empControllerMock, times(1)).deleteCustomer(customerToDelete.getCustID());
  }

  @Test
  public void testHandleBackToMainMenu() {
    employeeView.handleBackToMainMenu();

    assertFalse(employeeView.isVisible());
  }

  @Test
  public void testHandleEdit() {
    Customer customerToEdit = mockCustomerData.get(0);

    employeeView.handleEdit(customerToEdit);

    assertTrue(employeeView.isVisible() == false);
  }

  @Test
  public void testConfirmationDialogDelete() {
    Customer customerToDelete = mockCustomerData.get(0);
    when(JOptionPane.showConfirmDialog(any(), any(), any(), anyInt(), anyInt())).thenReturn(0);

    employeeView.handleDelete(customerToDelete);

    verify(empControllerMock, times(1)).deleteCustomer(customerToDelete.getCustID());
  }

  @Test
  public void testNoDeleteOnCancel() {
    Customer customerToDelete = mockCustomerData.get(0);
    when(JOptionPane.showConfirmDialog(any(), any(), any(), anyInt(), anyInt())).thenReturn(1);

    employeeView.handleDelete(customerToDelete);

    verify(empControllerMock, times(0)).deleteCustomer(customerToDelete.getCustID());
  }

  @Test
  public void testPopulateTableAfterDelete() {
    Customer customerToDelete = mockCustomerData.get(0);

    employeeView.handleDelete(customerToDelete);

    ArrayList<Customer> updatedList = new ArrayList<>();
    updatedList.add(mockCustomerData.get(1));
    when(empControllerMock.getCustomerList()).thenReturn(updatedList);

    employeeView.populateTable();

    assertEquals("C002", employeeView.model.getValueAt(0, 0));
  }

}
