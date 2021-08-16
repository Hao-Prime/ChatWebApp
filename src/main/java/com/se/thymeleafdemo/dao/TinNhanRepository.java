package com.se.thymeleafdemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.se.thymeleafdemo.entity.TinNhan;

@Repository
public interface TinNhanRepository extends JpaRepository<TinNhan, Integer> {
	
	@Query(value = "select * from tinnhan limit 10", nativeQuery = true)
	List<TinNhan> findFirt();

	@Query(value = "select * from tinnhan order by id DESC limit 10", nativeQuery = true)
	List<TinNhan> findNew();



}
