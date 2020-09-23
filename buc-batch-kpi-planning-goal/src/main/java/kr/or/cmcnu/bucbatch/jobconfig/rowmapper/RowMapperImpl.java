package kr.or.cmcnu.bucbatch.jobconfig.rowmapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import kr.or.cmcnu.bucbatch.model.Excel;

public class RowMapperImpl implements RowMapper<HashMap> {

	private String[] columntypes = Excel.columntypes.split(",");
	
    public RowMapperImpl() {
    }

    @Override
    public HashMap mapRow(RowSet rs) throws Exception {
        if (rs == null || rs.getCurrentRow() == null) {
            return null;
        }

        Map<String, String> goals = new HashMap<>();
        for (int i=0; i < columntypes.length; i++) {
        	String v = new String(rs.getColumnValue(i));
        	if (columntypes[i].equals("Long")) v = Long.toString((long)Double.parseDouble(v));
        	else if (columntypes[i].equals("Double")) v = Double.toString(Double.parseDouble(v));
    		goals.put("C" + String.format("%2s", i+1).replace(' ',  '0'), v);
        }
 
        return (HashMap) goals;
    }
}
