package gs.teamup.bot.jdbc.client.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import gs.teamup.bot.jdbc.client.dao.ClientDAO;
import gs.teamup.bot.jdbc.client.model.Client;
import gs.teamup.bot.jdbc.client.model.ClientRowMapper;

import gs.teamup.bot.jdbc.common.DateUtil;
import gs.teamup.bot.jdbc.customer.dao.CustomerDAO;
import gs.teamup.bot.jdbc.customer.model.Customer;
import gs.teamup.bot.jdbc.customer.model.CustomerRowMapper;
import gs.teamup.bot.jdbc.itcuser.model.ItcUser;

public class JdbcClientDAO extends JdbcDaoSupport implements ClientDAO {
	// insert example
	public void insert(Customer customer) {

		String sql = "INSERT INTO CUSTOMER " + "(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";

		getJdbcTemplate().update(sql, new Object[] { customer.getCustId(), customer.getName(), customer.getAge() });

	}

	// insert with named parameter
	public void insertNamedParameter(Customer customer) {

		// not supported

	}

	// insert batch example
	public void insertBatch(final List<Customer> customers) {

		String sql = "INSERT INTO CUSTOMER " + "(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";

		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Customer customer = customers.get(i);
				ps.setLong(1, customer.getCustId());
				ps.setString(2, customer.getName());
				ps.setInt(3, customer.getAge());
			}

			public int getBatchSize() {
				return customers.size();
			}
		});
	}

	// insert batch with named parameter
	public void insertBatchNamedParameter(final List<Customer> customers) {

		// not supported
	}

	// insert batch with named parameter
	public void insertBatchNamedParameter2(final List<Customer> customers) {

		// not supported
	}

	// insert batch example with SQL
	public void insertBatchSQL(final String sql) {

		getJdbcTemplate().batchUpdate(new String[] { sql });

	}

	// query single row with RowMapper
	public Customer findByCustomerId(int custId) {

		String sql = "SELECT * FROM CUSTOMER WHERE CUST_ID = ?";

		Customer customer = (Customer) getJdbcTemplate().queryForObject(sql, new Object[] { custId },
				new CustomerRowMapper());

		return customer;
	}

	// query single row with BeanPropertyRowMapper (Customer.class)
	public Customer findByCustomerId2(int custId) {

		String sql = "SELECT * FROM CUSTOMER WHERE CUST_ID = ?";

		Customer customer = (Customer) getJdbcTemplate().queryForObject(sql, new Object[] { custId },
				new BeanPropertyRowMapper(Customer.class));

		return customer;
	}

	// query mutiple rows with manual mapping
	public List<Customer> findAll() {

		String sql = "SELECT * FROM CUSTOMER";

		List<Customer> customers = new ArrayList<Customer>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		for (Map row : rows) {
			Customer customer = new Customer();
			customer.setCustId((Long) (row.get("CUST_ID")));
			customer.setName((String) row.get("NAME"));
			customer.setAge((Integer) row.get("AGE"));
			customers.add(customer);
		}

		return customers;
	}

	// query mutiple rows with BeanPropertyRowMapper (Customer.class)
	public List<Client> findTelbyDeptname(String name ) {
       String[] UserValues = name.split("/");
       String instStr=UserValues[1];
       String DeptStr=UserValues[2];
      
		String sql = "select cntctel,deptplce,deptname from department where deptname like '%"+DeptStr+"%' and instcd='012'  and cntctel is not null and cntctel <> '{null}' and deptplce <> '-' and deptplce <> '{null}' and is_delete='0'";

		List<Client> clients = getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Client.class));

		return clients;
	}
	
	// query mutiple rows with BeanPropertyRowMapper (Customer.class)
	public List<ItcUser>  findUserbyPost(String post){
       String[] UserValues = post.split("/");
       String instStr=UserValues[1];
       String DeptStr=UserValues[2];
      
		String sql = "SELECT idx, post, department, team, usernm, extension, hp, email, history, create_date, update_date  FROM cmcnu.itcuser where post like'%"+DeptStr+"%' ";
		List<ItcUser> itu = getJdbcTemplate().query(sql, new BeanPropertyRowMapper(ItcUser.class));
        System.out.println(itu);
		return itu;
	}
	
	
	
	public String findPidByTmupidx(String idx)
	{
		
	       String[] UserValues = idx.split("/");
	       String tmup_idx=UserValues[2];
	      
			String sql = "select userid from user where tmup_idx=?";

			String userid = (String) getJdbcTemplate().queryForObject(sql, new Object[] { tmup_idx }, String.class);

			return userid;
		
	}
	
	

	public String findCustomerNameById(int custId) {

		String sql = "SELECT NAME FROM CUSTOMER WHERE CUST_ID = ?";

		String name = (String) getJdbcTemplate().queryForObject(sql, new Object[] { custId }, String.class);

		return name;

	}

	public String findCustomerAgeByName(String name) {

		String sql = "SELECT AGE FROM CUSTOMER WHERE NAME = ?";

		String age = (String) getJdbcTemplate().queryForObject(sql, new Object[] { name }, String.class);

		return age;
	}
	
	
	
	
	
	public String findTelByName(String name) {

//		String sql = "select cntctel,deptplce,deptname from department where deptname = ?  and instcd='012' and cntctel is not null";
//		//select cntctel,fulldeptname,deptname from department where deptname = '��ȭ�⳻��' and instcd='012' and cntctel is not null
//		String cntctel = (String) getJdbcTemplate().queryForObject(sql, new Object[] { name }, String.class);

		return name;
	}
	
	
	

	@Override
	public int findTotalCustomer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTodayLunchMenu() {
		String Today = null;
		Today = DateUtil.getToday(null);
		String sql = "SELECT LUNCH FROM TFOODMENU WHERE DD = ?";
		String infoMesaage = "==" + Today + "의 점심메뉴입니다" + "==\n\n";
		String foodmenu = infoMesaage
				+ (String) getJdbcTemplate().queryForObject(sql, new Object[] { Today }, String.class);

		return foodmenu;
	}

	@Override
	public String getTomorrowLunchMenu() {
		String Today = null;
		Today = DateUtil.getTomorrow(null);
		String sql = "SELECT LUNCH FROM TFOODMENU WHERE DD = ?";
		String infoMesaage = "==" + Today + "의 점심메뉴입니다" + "==\n\n";
		String foodmenu = infoMesaage
				+ (String) getJdbcTemplate().queryForObject(sql, new Object[] { Today }, String.class);

		return foodmenu;
	}

	@Override
	public String getTomorrowDinnerMenu() {
		String Today = null;
		Today = DateUtil.getTomorrow(null);
		String infoMesaage = "==" + Today + "의 저녁메뉴입니다" + "==\n\n";
		String sql = "SELECT DINNER FROM TFOODMENU WHERE DD = ?";

		String foodmenu = infoMesaage
				+ (String) getJdbcTemplate().queryForObject(sql, new Object[] { Today }, String.class);

		return foodmenu;
	}

	@Override
	public String getTodayDinnerMenu() {
		String Today = null;
		Today = DateUtil.getToday(null);
		String infoMesaage = "==" + Today + "의 저녁메뉴입니다" + "==\n\n";
		String sql = "SELECT DINNER FROM TFOODMENU WHERE DD = ?";

		String foodmenu = infoMesaage
				+ (String) getJdbcTemplate().queryForObject(sql, new Object[] { Today }, String.class);

		return foodmenu;
	}
	


	

}
