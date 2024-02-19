package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.CategoryTotallingDto;
import com.example.demo.model.MainCategory;

//アプリケーション一覧と詳細画面のrepository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {

	// メインカテゴリー名をCategoryTotallingDtoに取得
	@Query(value = "SELECT new com.example.demo.model.CategoryTotallingDto(m.mainCategoryId, m.mainCategoryName) FROM MainCategory m",
		       countQuery = "SELECT count(m) FROM MainCategory m")
	List<CategoryTotallingDto> findNameForCategoryTotalling();	
	
}