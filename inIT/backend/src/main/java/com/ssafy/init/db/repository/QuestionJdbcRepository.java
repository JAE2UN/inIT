package com.ssafy.init.db.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ssafy.init.db.entity.Question;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QuestionJdbcRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	private int batchSize = 100; // 한번에 100개  insert
	
	public void saveAllinOne(List<Question> items) {
		int batchCnt = 0;
		List<Question> subItems = new ArrayList<>();
		
		for(int i=0; i<items.size(); i++) {
			subItems.add(items.get(i));
			if((i+1)%batchSize == 0) {
				batchCnt = batchInsert(batchCnt, subItems);
			}
		}
		if(!subItems.isEmpty()) {
			batchCnt = batchInsert(batchCnt, subItems);
		}
		System.out.println("batchCnt: "+batchCnt);
	}
	
	private int batchInsert(int batchCnt, List<Question> subItems) {
		jdbcTemplate.batchUpdate("INSERT INTO question (is_admin, priority, type, quest) VALUES (?, ?, ?, ?)", new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setBoolean(1, true);
				ps.setInt(2, subItems.get(i).getPriority());
				ps.setInt(3, subItems.get(i).getType());
				ps.setString(4, subItems.get(i).getQuest());
			}
			
			@Override
			public int getBatchSize() {
				return subItems.size();
			}
		});
		subItems.clear();
		batchCnt++;
		return batchCnt;
	}

}
