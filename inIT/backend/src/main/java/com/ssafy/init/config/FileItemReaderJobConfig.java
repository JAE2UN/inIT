package com.ssafy.init.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ssafy.init.db.entity.Question;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileItemReaderJobConfig {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final CsvReader csvReader;
	private final CsvWriter csvWriter;
	
	// chunkSize만큼 처리될때마다 커밋 실행(이만큼 트랜잭션 실행)
	private static final int chunkSize = 100;
	
	@Bean
	public Job csvFileItemReaderJob() {
		return jobBuilderFactory.get("csvFileItemReaderJob")
				.start(csvFileItemReaderStep())
				.build();
	}
	
	@Bean
	public Step csvFileItemReaderStep() {
		return stepBuilderFactory.get("csvFileItemReaderStep")
				.<Question, Question>chunk(chunkSize)
				.reader(csvReader.csvFileItemReader())
				.writer(csvWriter)
				.build();
	}

}
