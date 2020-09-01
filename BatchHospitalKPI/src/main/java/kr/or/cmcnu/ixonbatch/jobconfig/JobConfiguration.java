package kr.or.cmcnu.ixonbatch.jobconfig;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    protected Step step1(ItemReader<HashMap> reader,
//    						ItemProcessor<HashMap, HashMap> processor,
    						ItemWriter<HashMap> writer) {
        return steps.get("step1")
            .<HashMap, HashMap> chunk(10)
            .reader(reader)
//          .processor(itemProcessor)
            .writer(writer)
            .build();
    }
    
    @Bean
    @JobScope
    public MyBatisCursorItemReader<HashMap> reader(@Value("#{jobParameters[department]}") String department, @Qualifier("sSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    	Map<String, Object> parameterValues = new HashMap<>();
    	parameterValues.put("department", department);
        return new MyBatisCursorItemReaderBuilder<HashMap>()
        			.sqlSessionFactory(sqlSessionFactory)
        			.queryId("SourceMapper.selectPhoneInside")
        			.parameterValues(parameterValues)
        			.build();
    }

    @Bean
    @StepScope
    public MyBatisBatchItemWriter<HashMap> writer(@Qualifier("tSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    	return new MyBatisBatchItemWriterBuilder<HashMap>()
    				.sqlSessionFactory(sqlSessionFactory)
    				.statementId("TargetMapper.insertPhoneInside")
    				.build();
    }
    
}
