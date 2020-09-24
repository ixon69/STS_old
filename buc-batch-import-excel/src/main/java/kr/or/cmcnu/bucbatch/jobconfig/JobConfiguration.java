package kr.or.cmcnu.bucbatch.jobconfig;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import kr.or.cmcnu.bucbatch.jobconfig.rowmapper.RowMapperImpl;
import kr.or.cmcnu.bucbatch.jobconfig.runidIncrementer.UniqueRunIdIncrementer;
import kr.or.cmcnu.bucbatch.model.CommonDataset;
import kr.or.cmcnu.bucbatch.model.Excel;
import kr.or.cmcnu.bucbatch.processor.ConvertDatasetProcessor;
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
        return jobs.get("KPI-1")
        			.start(step1)
        			.incrementer(new UniqueRunIdIncrementer())
        			.build();
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
    public ItemStreamReader<HashMap> reader() {
        PoiItemReader reader = new PoiItemReader();
        reader.setResource(new ClassPathResource(Excel.filename));
        reader.setRowMapper(new RowMapperImpl());
        reader.setLinesToSkip(1);
 
        return reader;
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
