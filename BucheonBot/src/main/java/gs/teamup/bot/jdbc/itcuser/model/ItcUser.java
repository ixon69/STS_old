package gs.teamup.bot.jdbc.itcuser.model;

import java.io.Serializable;

public class ItcUser implements Serializable
{
	String department;
	String team;
	String usernm;
	String extension;
	String hp;
	String email;
	String history;
	String post; //분야 소속 성명 내선 핸드폰 이메일 이력관리
	public String getPost() {
		return post;
	}




	public void setPost(String post) {
		this.post = post;
	}




	public String getDepartment() {
		return department;
	}




	public void setDepartment(String department) {
		this.department = department;
	}




	public String getTeam() {
		return team;
	}




	public void setTeam(String team) {
		this.team = team;
	}




	public String getUsernm() {
		return usernm;
	}




	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}




	public String getExtension() {
		return extension;
	}




	public void setExtension(String extension) {
		this.extension = extension;
	}




	public String getHp() {
		return hp;
	}




	public void setHp(String hp) {
		this.hp = hp;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getHistory() {
		return history;
	}




	public void setHistory(String history) {
		this.history = history;
	}





	
	public ItcUser(){
	}
	
	@Override
	public String toString() {
		return post + " = " + usernm +"("+hp+")"+"\n";
	}
	

	
	
}
