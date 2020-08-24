package gs.teamup.bot.jdbc.customer.dao;

import java.util.List;
import gs.teamup.bot.jdbc.customer.model.Customer;

public interface CustomerDAO 
{
	public void insert(Customer customer);
	
	public void insertNamedParameter(Customer customer);
			
	public void insertBatch(List<Customer> customer);
	
	public void insertBatchNamedParameter(List<Customer> customer);
	
	public void insertBatchNamedParameter2(List<Customer> customer);
			
	public void insertBatchSQL(String sql);
	
	public Customer findByCustomerId(int custId);
	
	public Customer findByCustomerId2(int custId);

	public List<Customer> findAll();
	
	public List<Customer> findAll2();
	
	public String findCustomerNameById(int custId);
	
	public String findCustomerAgeByName(String  name);
	
	public String getTodayLunchMenu();
	public String getTodayDinnerMenu();
	public String getTomorrowLunchMenu();
	public String getTomorrowDinnerMenu();
	public int findTotalCustomer();

	public String getDeptDoctorCode(String deptname);
	public String getSvrHealth(String svrname);
	public String getSvrHealth();

	public List<String> getPhoneNumber(String phonename);
	public List<String> getPhoneName(String phonenumber);

	
	
}




