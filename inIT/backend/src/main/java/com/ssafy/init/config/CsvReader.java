package com.ssafy.init.config;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.ssafy.init.db.entity.Question;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CsvReader {
	
	@Bean
	public FlatFileItemReader<Question> csvFileItemReader() {
		
		FlatFileItemReader<Question> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("/question.csv")); // resources에 존재
        flatFileItemReader.setLinesToSkip(1); // header line skip
        flatFileItemReader.setEncoding("UTF-8"); // encoding
        
        // 읽을 데이터를 LineMapper를 통해 내부적으로 mapping
        DefaultLineMapper<Question> defaultLineMapper = new DefaultLineMapper<>();
        
        // csv 파일 구분자(,) 설정, delimitedLineTokenizer: setNames를 통해 데이터 이름 설정
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
		delimitedLineTokenizer.setNames("is_admin", "priority", "type", "quest"); // entity col과 동일
		delimitedLineTokenizer.setStrict(false); // csv 파일 colum과 불일치 가능(개수 달라도 됨)
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		
		// beanWrapperFieldSetMapper: Tokenizer에서 갖고온 데이터들을 바인딩
		BeanWrapperFieldSetMapper<Question> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(Question.class);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		
		// lineMapper 지정
		flatFileItemReader.setLineMapper(defaultLineMapper);
		
		return flatFileItemReader;
	}

}
