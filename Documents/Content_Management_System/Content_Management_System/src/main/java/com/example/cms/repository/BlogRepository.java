package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.model.Blog;

public interface BlogRepository extends JpaRepository<Blog,Integer> {

	boolean existsByTitle(String title); 
}
