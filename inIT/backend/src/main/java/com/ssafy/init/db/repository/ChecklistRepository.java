package com.ssafy.init.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.init.db.entity.Checklist;

public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {

}
