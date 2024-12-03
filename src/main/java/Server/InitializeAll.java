
package Server;

import Controller.CustomerController;
import Controller.EmployeeController;
import Model.CustomerModel;
import View.Welcome;
import com.mycompany.a1_scd_22l7942.Billing;
import com.mycompany.a1_scd_22l7942.BillingDataAccess;
import com.mycompany.a1_scd_22l7942.Customer;
import com.mycompany.a1_scd_22l7942.FileCustomerDataAccess;
import com.mycompany.a1_scd_22l7942.Nadra;
import com.mycompany.a1_scd_22l7942.NadraDataAccess;
import com.mycompany.a1_scd_22l7942.TarrifTax;
import com.mycompany.a1_scd_22l7942.TarrifTaxDataAccess;
import java.util.ArrayList;
import java.util.List;


public class InitializeAll 
{
    public void InitializeCusomter()
    {
        FileCustomerDataAccess customerFile = new FileCustomerDataAccess();
        NadraDataAccess nadraFile = new NadraDataAccess();
        BillingDataAccess billingFile = new BillingDataAccess();
        TarrifTaxDataAccess tariffFile = new TarrifTaxDataAccess();
        
        List<Customer> customerList= customerFile.loadAllCustomers();
        List<Nadra> nadraList= nadraFile.loadNadraData();
        List<Billing> billingList= billingFile.loadFileData();
        List<TarrifTax> tariffList= tariffFile.loadData();
     
        CustomerModel customerModel=new CustomerModel(customerFile,nadraFile,billingFile,tariffFile,customerList,nadraList,billingList,tariffList);
        CustomerController custController=new CustomerController(customerModel);
        EmployeeController empController=new EmployeeController();
        new Welcome(custController,empController);
    }
}
