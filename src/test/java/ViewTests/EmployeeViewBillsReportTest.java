package ViewTests;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import Controller.EmployeeController;
import View.EmployeeViewBillsReport;

public class EmployeeViewBillsReportTest {

  private EmployeeViewBillsReport view;

  @Mock
  private EmployeeController mockController;

  private List<Integer> mockBills;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockBills = new ArrayList<>();
    mockBills.add(100);
    mockBills.add(50);
    mockBills.add(50);
    view = new EmployeeViewBillsReport(mockController, (ArrayList<Integer>) mockBills);
  }

  @Test
  public void testTitleLabel() {
    JPanel headerPanel = (JPanel) view.getContentPane().getComponent(0);
    JLabel titleLabel = (JLabel) headerPanel.getComponent(0);
    assertNotNull(titleLabel);
    assertEquals("BILLS REPORT", titleLabel.getText());
  }

  @Test
  public void testBillLabels() {

    JPanel centerPanel = (JPanel) view.getContentPane().getComponent(1);

    JLabel totalBillsLabel = (JLabel) centerPanel.getComponent(0);
    assertNotNull(totalBillsLabel);
    assertEquals("Total Bills: 100", totalBillsLabel.getText());

    JLabel paidBillsLabel = (JLabel) centerPanel.getComponent(1);
    assertNotNull(paidBillsLabel);
    assertEquals("Paid Bills: 50", paidBillsLabel.getText());

    JLabel unpaidBillsLabel = (JLabel) centerPanel.getComponent(2);
    assertNotNull(unpaidBillsLabel);
    assertEquals("Unpaid Bills: 50", unpaidBillsLabel.getText());
  }

  @Test
  public void testOKButtonAction() {
    JPanel southPanel = (JPanel) view.getContentPane().getComponent(2);
    JButton okButton = (JButton) southPanel.getComponent(0);
    assertNotNull(okButton);
    okButton.doClick();

    assertFalse(view.isVisible());
  }
}
