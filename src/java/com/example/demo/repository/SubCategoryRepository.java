package com.example.demo.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.SubCategory;
import com.example.demo.model.SubCategoryCountDto;
import com.example.demo.model.SubCategoryInputUpdateDto;

// サブカテゴリーのrepository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
	
	// アプリケーションのサブカテゴリーを取得するカスタムクエリメソッド
	@Query(value = "SELECT s FROM SubCategory s JOIN s.applicationSubCategories asc WHERE asc.applicationId.applicationId = :applicationId",
		       countQuery = "SELECT count(s) FROM SubCategory s JOIN s.applicationSubCategories asc WHERE asc.applicationId.applicationId = :applicationId")
	Set<SubCategory> findByApplicationId(Long applicationId);
	
	// アプリケーションのサブカテゴリーIdを取得
	@Query(value = "SELECT s.subCategoryId FROM SubCategory s JOIN s.applicationSubCategories asc WHERE asc.applicationId.applicationId = :applicationId",
		       countQuery = "SELECT count(s.subCategoryId) FROM SubCategory s JOIN s.applicationSubCategories asc WHERE asc.applicationId.applicationId = :applicationId")
	Set<Long> findIdByApplicationId(Long applicationId);

	// 備忘録のサブカテゴリーを取得するカスタムクエリメソッド
	@Query(value = "SELECT s FROM SubCategory s JOIN s.recordSubCategories rsc WHERE rsc.recordId.recordId = :recordId",
		       countQuery = "SELECT count(s) FROM SubCategory s JOIN s.recordSubCategories rsc WHERE rsc.recordId.recordId = :recordId")
	Set<SubCategory> findByRecordId(Long recordId);
	
	// 備忘録のサブカテゴリーIdを取得
	@Query(value = "SELECT s.subCategoryId FROM SubCategory s JOIN s.recordSubCategories rsc WHERE rsc.recordId.recordId = :recordId",
		       countQuery = "SELECT count(s.subCategoryId) FROM SubCategory s JOIN s.recordSubCategories rsc WHERE rsc.recordId.recordId = :recordId")
	Set<Long> findIdByRecordId(Long recordId);
	
	// タグのサブカテゴリーIdを取得
	@Query(value = "SELECT s.subCategoryId FROM SubCategory s JOIN s.subCategoryTags sct WHERE sct.tag.tagId = :tagId",
		       countQuery = "SELECT count(s.subCategoryId) FROM SubCategory s JOIN s.subCategoryTags sct WHERE sct.tag.tagId = :tagId")
	Set<Long> findIdByTagId(Long tagId);

	// サブカテゴリー名と関連するアプリケーションと備忘録の数をSubCategoryCountDtoに取得
	@Query(value = "SELECT new com.example.demo.model.SubCategoryCountDto(s.subCategoryId, s.subCategoryName, COUNT(DISTINCT asc.applicationId), COUNT(DISTINCT rsc.recordId)) "
	        + "FROM SubCategory s "
	        + "JOIN s.mainCategories m "
	        + "JOIN s.applicationSubCategories asc "
	        + "JOIN s.recordSubCategories rsc "
	        + "WHERE m.mainCategoryId = :mainCategoryId "
	        + "GROUP BY s.subCategoryId, s.subCategoryName",
	       countQuery = "SELECT count(DISTINCT s) "
	        + "FROM SubCategory s "
	        + "JOIN s.mainCategories m "
	        + "JOIN s.applicationSubCategories asc "
	        + "JOIN s.recordSubCategories rsc "
	        + "WHERE m.mainCategoryId = :mainCategoryId")
	Set<SubCategoryCountDto> findByMainCategoryIdAndRelationCount(Long mainCategoryId);

	// サブカテゴリーの詳細情報をRecordInputUpdateDtoに取得
	@Query(value = "SELECT new com.example.demo.model.SubCategoryInputUpdateDto(s.subCategoryId, s.subCategoryName, s.mainCategories.mainCategoryId) "
	        + "FROM SubCategory s WHERE s.subCategoryId = :subCategoryId",
	        countQuery = "SELECT count(s) FROM SubCategory s WHERE s.subCategoryId = :subCategoryId")
	Optional<SubCategoryInputUpdateDto> findUpdateBySubCategoryId(Long subCategoryId);

}

