package gs.teamup.bot.jdbc.customer.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import gs.teamup.bot.jdbc.common.DateUtil;
import gs.teamup.bot.jdbc.customer.dao.CustomerDAO;
import gs.teamup.bot.jdbc.customer.model.Customer;
import gs.teamup.bot.jdbc.customer.model.CustomerRowMapper;
import gs.teamup.bot.service.TeamupService;
import lombok.extern.apachecommons.CommonsLog;
@CommonsLog
public class JdbcCustomerDAO extends JdbcDaoSupport implements CustomerDAO {
	
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
	public List<Customer> findAll2() {

		String sql = "SELECT * FROM CUSTOMER";

		List<Customer> customers = getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Customer.class));

		return customers;
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
	
	public String getDeptDoctorCode(String deptname) {
		   String[] UserValues = deptname.split("/");
	       String instStr=UserValues[1];
	       String DeptStr=UserValues[2];
	      
		String DEPTFLOORCODE = null;
	     //SELECT * FROM COM.ABC where userid='222';
		String sql = "select (select DEPT_CD from web.CM_DEPARTMENT where inst_no = ( select inst_no from WEB.CM_INSTITUTION where inst_cd = '012')AND  DEPT_NM =? ) DEPTCODE from web.CM_DOCTOR where rownum=1";
		//String sql= "select DEPTCD1 FROM COM.ABC WHERE USERID=?";
		
		String DEPTCODE =  (String) getJdbcTemplate().queryForObject(sql, new Object[] { DeptStr }, String.class);
		
		return DEPTCODE;
	}
	
	
	public String getSvrHealth(String svrname) {
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH"); 
		   String[] UserValues = svrname.split("/");
	       String instStr=UserValues[1];
	       String SvrStr="cengwms" + UserValues[2];
	       Calendar c1 = Calendar.getInstance();
	       c1.add(Calendar.HOUR, -1);
		   String strNow = sdf.format(c1.getTime());
		   c1.add(Calendar.HOUR, 1);
		   String hourLater = sdf.format(c1.getTime());
  
		
	   
	    //   String sql= "select svryn from COM.RPBVGWSVR where FSTRGSTDT between to_timestamp( '2018082412','YYYYMMDDHH24MISS') and to_timestamp( '2018082413','YYYYMMDDHH24MISS')and SVRNM=?";
	   String sql= "select svryn from COM.RPBVGWSVR where FSTRGSTDT between to_timestamp( '"+strNow +"','YYYYMMDDHH24MISS') and to_timestamp( '"+hourLater +"','YYYYMMDDHH24MISS')and SVRNM=?";
			  // select max(decode(svrnm,'cengwms1',1))||max(decode(svrnm,'cengwms2',2))||max(decode(svrnm,'cengwms3',3))||max(decode(svrnm,'cengwms4',4)) svr from COM.RPBVGWSVR where FSTRGSTDT between to_timestamp( '"+strNow +"','YYYYMMDDHH24MISS') and to_timestamp( '"+hourLater +"','YYYYMMDDHH24MISS');
		log.debug(sql);
		String result =  (String) getJdbcTemplate().queryForObject(sql, new Object[] { SvrStr }, String.class);
       
		if(result.equals("Y")){result=UserValues[2]+"호기 서버가 정상동작하고 있습니다 "+ "==\n\n";}
		//if(result.equals("Y")){result=UserValues[2];}
		else{
			result="서버상태를 확인해 주십시요";
		}
		
		return result;
	}

	public String getSvrHealth() {
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH"); 
		
	       Calendar c1 = Calendar.getInstance();
	       c1.add(Calendar.HOUR, -1);
		   String strNow = sdf.format(c1.getTime());
		   c1.add(Calendar.HOUR, 1);
		   String hourLater = sdf.format(c1.getTime());

	   String sql=" select max(decode(svrnm,'cengwms1',1))||max(decode(svrnm,'cengwms2',2))||max(decode(svrnm,'cengwms3',3))||max(decode(svrnm,'cengwms4',4)) svr from COM.RPBVGWSVR where FSTRGSTDT between to_timestamp( '"+strNow +"','YYYYMMDDHH24MISS') and to_timestamp( '"+hourLater +"','YYYYMMDDHH24MISS')";
	
		String test =  (String) getJdbcTemplate().queryForObject(sql, String.class);
		
		log.debug(sql);
		String msg = "";
		if (test.equals("1234")) {
			msg = "AP가 정상동작중입니다.";
		} else {
			boolean flag;
			if (flag = !(test.contains("1"))) {
				msg = msg + "1,";
			}
			if (flag = !(test.contains("2"))) {
				msg = msg + "2,";
			}
			if (flag = !(test.contains("3"))) {
				msg = msg + "3,";
			}
			if (flag = !(test.contains("4"))) {
				msg = msg + "4";
			}
			if (msg.endsWith(",")) {
				msg = msg.replaceAll(",$", "");
			}
			msg = msg + "호기 메일서버를 확인해주십시오";
		}

		System.out.println(msg);
		
		
	
		
		return msg;
	}//svrhealth
	
	public List<String> getPhoneNumber(String phonename) {
		String[] s = phonename.trim().split(" ");
		String sql = "SELECT CONCAT(phone, ' - ', department, ' ', part) FROM bucheon.phone_inside";
		for(int i = 0; i < s.length; i++) {
			if(s[i].equals("")) continue;
			if(i == 0) sql = sql + " WHERE";
			else sql = sql + " AND";
			if(!s[i].equals("팀") & s[i].endsWith("팀")) s[i] = s[i].substring(0, s[i].lastIndexOf("팀"));
			if(!s[i].equals("과") & s[i].endsWith("과")) s[i] = s[i].substring(0, s[i].lastIndexOf("과"));
			if(!s[i].equals("실") & s[i].endsWith("실")) s[i] = s[i].substring(0, s[i].lastIndexOf("실"));
			if(!s[i].equals("센터") & s[i].endsWith("센터")) s[i] = s[i].substring(0, s[i].lastIndexOf("센터"));
			sql = sql + " (department LIKE '%"+s[i]+"%' OR part LIKE '%"+s[i]+"%')";
		}
		return getJdbcTemplate().queryForList(sql, String.class);
	}
	
	public List<String> getPhoneName(String phonenumber) {
		String sql = "SELECT CONCAT(phone, ' - ', department, ' ', part) FROM bucheon.phone_inside WHERE phone LIKE '%"+phonenumber+"%'"
					+ "OR (INSTR(phone, '~') > 0 " 
					+ "    AND "+phonenumber+" BETWEEN CAST(SUBSTR(phone, 1, 4) AS UNSIGNED) AND CAST(CONCAT(SUBSTR(phone, 1, 3), SUBSTR(phone, 6, 1)) AS UNSIGNED))"; 
		return getJdbcTemplate().queryForList(sql, String.class);
	}
	
	
	

}
