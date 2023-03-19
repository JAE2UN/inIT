package com.ssafy.init.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import com.ssafy.init.db.entity.Question;
import com.ssafy.init.db.repository.QuestionJdbcRepository;
import com.ssafy.init.db.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CsvWriter implements ItemWriter<Question>{
	
	private final QuestionJdbcRepository questionJdbcRepository;
	//private final QuestionRepository questionRepository;
	
	@Override
	public void write(List<? extends Question> list) throws Exception {
		// list: Reader를 통해 csv 파일에서 읽어온 데이터가 담긴 리스트 -> repository 거쳐서 DB에 전부 저장
		questionJdbcRepository.saveAllinOne(new ArrayList<Question>(list)); // batch 사용해 한 번에 insert
		//questionRepository.saveAll(new ArrayList<Question>(list));
		System.out.println("writing 완료");
	}

}
