package gs.teamup.bot.jdbc.homepageuser.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HomePageUserRowMapper implements RowMapper
{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		HomePageUser hpu = new HomePageUser();
		hpu.setProfurl(rs.getString("PROF_URL"));
	    
		return hpu;
	}
	
}
