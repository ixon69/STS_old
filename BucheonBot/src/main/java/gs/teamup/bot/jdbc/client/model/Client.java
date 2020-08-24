package gs.teamup.bot.jdbc.client.model;

import java.io.Serializable;

public class Client implements Serializable
{
	
	

	String deptname; //부서정보
	String deptplce;//위치정보
	String cntctel;//전화번호
	
	public String getDeptname() {
		return deptname;
	}



	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}



	public String getdeptplce() {
		return deptplce;
	}



	public void setdeptplce(String deptplce) {
		this.deptplce = deptplce;
	}



	public String getCntctel() {
		return cntctel;
	}



	public void setCntctel(String cntctel) {
		this.cntctel = cntctel;
	}



	
	public Client(){
	}
	


	@Override
	public String toString() {
		return deptname + " = " + cntctel+"("+deptplce+")"+"\n";
	}
	
	
}
