package kr.or.cmcnu.ixonbatch.jobconfig;

import java.util.HashMap;
import java.util.List;
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

import kr.or.cmcnu.ixonbatch.model.CommonDataset;
import kr.or.cmcnu.ixonbatch.processor.ConvertDatasetProcessor;
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
        return jobs.get("planning-team").start(step1).build();
    }

    @Bean
    protected Step step1(ItemReader<HashMap> reader,
    						ItemProcessor<HashMap, List<CommonDataset>> processor,
    						ItemWriter<List<CommonDataset>> writer) {
        return steps.get("step1")
            .<HashMap, List<CommonDataset>> chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }
    
    @Bean
    @JobScope
    public MyBatisCursorItemReader<HashMap> reader(@Qualifier("sSqlSessionFactory") SqlSessionFactory sqlSessionFactory,
    												@Value("#{jobParameters[department]}") String department) {
    	Map<String, Object> parameterValues = new HashMap<>();
    	parameterValues.put("department", department);
        return new MyBatisCursorItemReaderBuilder<HashMap>()
        			.sqlSessionFactory(sqlSessionFactory)
        			.queryId("SourceMapper.selectPhoneInside")
        			.parameterValues(parameterValues)
        			.build();
    }

    @Bean
    public ItemProcessor<HashMap, List<CommonDataset>> processor() {
        return new ConvertDatasetProcessor();
    }
    
    @Bean
    public MyBatisBatchItemWriter<List<CommonDataset>> writer(@Qualifier("tSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    	return new MyBatisBatchItemWriterBuilder<List<CommonDataset>>()
    				.sqlSessionFactory(sqlSessionFactory)
    				.assertUpdates(false)
    				.statementId("TargetMapper.insertCommonDataset")
    				.build();
    }
    
}
