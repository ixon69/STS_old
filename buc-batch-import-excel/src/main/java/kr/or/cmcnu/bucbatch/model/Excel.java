package kr.or.cmcnu.bucbatch.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:excel.properties")
public class Excel {
	
	public static String columntypes;
	public static String filename;

    @Value("${excel.columntypes}")
    public void setColumntype(String columntypes) {
    	this.columntypes = columntypes;
    }

    @Value("${excel.filename}")
    public void setFilename(String filename) {
    	this.filename = filename;
    }

}