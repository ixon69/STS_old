package gs.teamup.bot.jdbc.client.dao;

import java.util.List;

import gs.teamup.bot.jdbc.client.model.Client;
import gs.teamup.bot.jdbc.customer.model.Customer;
import gs.teamup.bot.jdbc.itcuser.model.ItcUser;

public interface ClientDAO 
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
	
	public List<Client> findTelbyDeptname(String name);
	public List<ItcUser> findUserbyPost(String post);
	
	public String findCustomerNameById(int custId);
	
	public String findCustomerAgeByName(String  name);
	public String findTelByName(String name);
	public String findPidByTmupidx(String idx);
	public String getTodayLunchMenu();
	public String getTodayDinnerMenu();
	public String getTomorrowLunchMenu();
	public String getTomorrowDinnerMenu();
	public int findTotalCustomer();

	
	
}




