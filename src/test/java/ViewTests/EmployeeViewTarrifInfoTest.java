package ViewTests;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mycompany.a1_scd_22l7942.CustomerType;
import com.mycompany.a1_scd_22l7942.MeterType;
import com.mycompany.a1_scd_22l7942.TarrifTax;

import Controller.EmployeeController;
import View.EmployeeViewTarrifInfo;

class EmployeeViewTarrifInfoTest {

  private EmployeeViewTarrifInfo view;
  private EmployeeController empControllerMock;
  private ArrayList<TarrifTax> tarrifList;

  @BeforeEach
  void setUp() {
    empControllerMock = Mockito.mock(EmployeeController.class);
    tarrifList = new ArrayList<>();
    TarrifTax sampleTarrif = new TarrifTax(MeterType.SINGLE_PHASE, CustomerType.COMMERCIAL, new BigDecimal(5.0), 7, 10);
    TarrifTax sampleTarrif2 = new TarrifTax(MeterType.THREE_PHASE, CustomerType.DOMESTIC, new BigDecimal(2.0), 3, 5);
    tarrifList.add(sampleTarrif);
    tarrifList.add(sampleTarrif2);
    Mockito.when(empControllerMock.getTarrifList()).thenReturn(tarrifList);

    view = new EmployeeViewTarrifInfo(empControllerMock);
  }

  @Test
  void testPopulateTable() {
    DefaultTableModel model = (DefaultTableModel) view.table.getModel();
    assertEquals(2, model.getRowCount());
    assertEquals(MeterType.SINGLE_PHASE, model.getValueAt(0, 0));
    assertEquals(CustomerType.COMMERCIAL, model.getValueAt(0, 1));
  }

  @Test
  void testFilterTableWithMatchingText() {
    JTextField searchBar = view.searchBar;
    searchBar.setText("Electric");

    view.filterTable();

    DefaultTableModel model = (DefaultTableModel) view.table.getModel();
    assertEquals(0, model.getRowCount());
  }

  @Test
  void testFilterTableWithNoMatches() {
    JTextField searchBar = view.searchBar;
    searchBar.setText("Nonexistent");

    view.filterTable();

    DefaultTableModel model = (DefaultTableModel) view.table.getModel();
    assertEquals(0, model.getRowCount());
  }

  @Test
  void testHandleEditButton() {
    JButton editButton = (JButton) view.table.getModel().getValueAt(0, 6);
    editButton.doClick();
  }

  @Test
  void testHandleBackToMainMenu() {
    JButton backButton = view.jbBackToMainMenu;
    backButton.doClick();
  }

  @Test
  void testSearchBarUpdatesTableOnTextChange() {
    DefaultTableModel model = (DefaultTableModel) view.table.getModel();
    assertEquals(2, model.getRowCount());

    view.searchBar.setText("Commercial");
    view.filterTable();

    assertEquals(1, model.getRowCount());
    assertEquals(MeterType.SINGLE_PHASE, model.getValueAt(0, 0));
  }
}
