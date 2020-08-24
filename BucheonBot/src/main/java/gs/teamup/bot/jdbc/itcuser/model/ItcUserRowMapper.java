package gs.teamup.bot.jdbc.itcuser.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ItcUserRowMapper implements RowMapper
{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItcUser itu = new ItcUser();
	    itu.setUsernm(rs.getString("USERNM"));
	    itu.setExtension(rs.getString("EXTENSION"));
		return itu;
	}
	
}
