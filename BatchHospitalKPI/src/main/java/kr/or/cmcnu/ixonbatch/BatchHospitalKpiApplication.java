package kr.or.cmcnu.ixonbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class BatchHospitalKpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchHospitalKpiApplication.class, args);
	}

}
