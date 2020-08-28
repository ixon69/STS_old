package kr.or.cmcnu.ixonbatch.jobconfig;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Job job(@Qualifier("step1") Step step1) {
        return jobs.get("myJob").start(step1).build();
    }

    @Bean
    protected Step step1(ItemReader<String> reader,
//    						ItemProcessor<HashMap, HashMap> processor,
    						ItemWriter<HashMap> writer) {
        return steps.get("step1")
            .<String, HashMap> chunk(10)
            .reader(reader)
//          .processor(itemProcessor)
            .writer(writer)
            .build();
    }    
    
    @Bean
    public MyBatisCursorItemReader<String> reader() {
        return new MyBatisCursorItemReaderBuilder<String>()
        			.sqlSessionFactory(sqlSessionFactory())
        			.queryId("com.my.name.space.batch.EmployeeMapper.getEmployee")
        			.build();
    }

    @Bean
    public ItemWriter<Bill> jdbcBillWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Bill> writer = new JdbcBatchItemWriterBuilder<Bill>()
                        .beanMapped()
                .dataSource(dataSource)
                .sql("INSERT INTO BILL_STATEMENTS (id, first_name, " +
                   "last_name, minutes, data_usage,bill_amount) VALUES " +
                   "(:id, :firstName, :lastName, :minutes, :dataUsage, " +
                   ":billAmount)")
                .build();
        return writer;
    }
    
}
