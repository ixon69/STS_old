package kr.or.cmcnu.bucbatch.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:excel.properties")
public class DataSet {
	
	public static String name;
	public static String keys;
	public static String worker;

    @Value("${dataset.name}")
    public void setName(String name) {
    	this.name = name;
    }

    @Value("${dataset.keys}")
    public void setKeys(String keys) {
    	this.keys = keys;
    }

    @Value("${dataset.worker}")
    public void setWorker(String worker) {
    	this.worker = worker;
    }

}