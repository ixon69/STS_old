package gs.teamup.bot.jdbc.homepageuser.model;

import java.io.Serializable;

public class HomePageUser implements Serializable
{
	String drno;
	String drname;
	String empid;
	String deptno;
	String profurl;

	public String getDrno() {
		return drno;
	}
	public void setDrno(String drno) {
		this.drno = drno;
	}
	public String getDrname() {
		return drname;
	}
	public void setDrname(String drname) {
		this.drname = drname;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getDeptno() {
		return deptno;
	}
	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}
	public String getProfurl() {
		return profurl;
	}
	public void setProfurl(String profurl) {
		this.profurl = profurl;
	}
	@Override
	public String toString() {
		return "HomePageUser [drno=" + drno + ", drname=" + drname + ", empid=" + empid + ", deptno=" + deptno
				+ ", profurl=" + profurl + "]";
	}
	
	
	
	
	
}
