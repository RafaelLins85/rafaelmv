package com.breakfast.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.breakfast.domain.model.CafeDaManha;

@Repository
public interface CafeDaManhaRepository extends JpaRepository<CafeDaManha, Long>{
	
	

}
