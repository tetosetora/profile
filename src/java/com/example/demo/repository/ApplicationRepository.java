package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Application;
import com.example.demo.model.ApplicationInputUpdateDto;
import com.example.demo.model.ApplicationOverviewDto;

//アプリケーション一覧と詳細画面のrepository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
	
	// ▼一覧表示 ------------------------------------------------------------------------------------
	
	// アプリケーション一覧と検索画面(全件)
	@Query(value = "SELECT new com.example.demo.model.ApplicationOverviewDto(a.applicationId, a.applicationName, a.overview, a.startDate) "
			+ "FROM Application a ORDER BY a.startDate DESC",
			countQuery = "SELECT count(a) FROM Application a")
	Set<ApplicationOverviewDto> findAllOverview();
	
	// アプリケーション一覧と検索画面(アプリケーション名と保有サブカテゴリーと保有タグをキーワード検索)
	@Query(value = "SELECT DISTINCT new com.example.demo.model.ApplicationOverviewDto(a.applicationId, a.applicationName, a.overview, a.startDate) "
			+ "FROM Application a JOIN a.applicationTags at JOIN a.applicationSubCategories asc "
			+ "WHERE a.applicationName LIKE %:keyword% OR at.tagApp.tagName LIKE %:keyword% OR asc.subCategoryId.subCategoryName LIKE %:keyword% "
			+ "ORDER BY a.startDate DESC",
			countQuery = "SELECT COUNT(DISTINCT a) FROM Application a JOIN a.applicationTags at JOIN a.applicationSubCategories asc "
			+ "WHERE a.applicationName LIKE %:keyword% OR at.tagApp.tagName LIKE %:keyword% OR asc.subCategoryId.subCategoryName LIKE %:keyword%")
	Set<ApplicationOverviewDto> findApplicationOverviewNameByKeyword(String keyword);
	
	// アプリケーション一覧と検索画面(サブカテゴリー検索)
	@Query(value = "SELECT DISTINCT new com.example.demo.model.ApplicationOverviewDto(a.applicationId, a.applicationName, a.overview, a.startDate) "
	        + "FROM Application a JOIN a.applicationSubCategories asc WHERE asc.subCategoryId.subCategoryId = :subCategoryId "
	        + "ORDER BY a.startDate DESC",
	        countQuery = "SELECT count(DISTINCT a) FROM Application a JOIN a.applicationSubCategories asc "
	       		+ "WHERE asc.subCategoryId.subCategoryId = :subCategoryId")
	Set<ApplicationOverviewDto> findApplicationOverviewBySubCategoryId(Long subCategoryId);

	// アプリケーション一覧と検索画面(タグ検索)
	@Query(value = "SELECT DISTINCT new com.example.demo.model.ApplicationOverviewDto(a.applicationId, a.applicationName, a.overview, a.startDate) "
	        + "FROM Application a JOIN a.applicationTags at WHERE at.tagApp.tagId = :tagId "
	        + "ORDER BY a.startDate DESC",
	       countQuery = "SELECT count(DISTINCT a) FROM Application a JOIN a.applicationTags at WHERE at.tagApp.tagId = :tagId")
	Set<ApplicationOverviewDto> findApplicationOverviewByTagId(Long tagId);
	
	// ▼ピックアップ表示 ----------------------------------------------------------------------------
	
	// 最新のアプリケーションを取得
	@Query(value = "SELECT new com.example.demo.model.ApplicationOverviewDto(a.applicationId, a.applicationName, a.overview) "
	        + "FROM Application a ORDER BY a.startDate DESC, a.updateDate DESC",
	        countQuery = "SELECT count(a) FROM Application a")
	Page<ApplicationOverviewDto> findNewApplicationOverview(Pageable pageable);
	
	// 共通のタグを使い、特定のアプリケーションと関連するアプリケーション一覧基本情報を取得
	@Query(value = "SELECT new com.example.demo.model.ApplicationOverviewDto(a.applicationId, a.applicationName, a.overview) "
	        + "FROM Application a JOIN a.applicationTags ats WHERE ats.tagApp.tagId IN "
	        + "(SELECT at.tagApp.tagId FROM ApplicationTag at WHERE at.appTag.applicationId = :applicationId) "
	        + "AND a.applicationId != :applicationId",
	       countQuery = "SELECT count(a) FROM Application a JOIN a.applicationTags ats WHERE ats.tagApp.tagId IN "
	        + "(SELECT at.tagApp.tagId FROM ApplicationTag at WHERE at.appTag.applicationId = :applicationId) "
	        + "AND a.applicationId != :applicationId")
	Page<ApplicationOverviewDto> findOverviewByApplicationId(Long applicationId, Pageable pageable);
	
	// タグからアプリケーション一覧情報を取得  ●new
	@Query(value = "SELECT new com.example.demo.model.ApplicationOverviewDto(a.applicationId, a.applicationName, a.overview) "
	        + "FROM Application a JOIN a.applicationTags ats "
	        + "WHERE ats.tagApp.tagId IN :tagIds",
	       countQuery = "SELECT count(a) FROM Application a JOIN a.applicationTags ats "
	        + "WHERE ats.tagApp.tagId IN :tagIds")
	Page<ApplicationOverviewDto> findOverviewByTagIds(@Param("tagIds") List<Long> tagIds, Pageable pageable);

	// ▼詳細表示 ------------------------------------------------------------------------------------
	
	// アプリケーションの詳細情報を取得
	Optional<Application> findByApplicationId(Long applicationId);

	// ▼管理 ----------------------------------------------------------------------------------------
	
	// アプリケーションの詳細情報をRecordInputUpdateDtoに取得
	@Query(value = "SELECT new com.example.demo.model.ApplicationInputUpdateDto(a.applicationId, a.applicationName, a.applicationNameEn, a.overview, "
	        + "a.requirements, a.startDate, a.duration, a.responsibility, a.challenges, a.futureVision, a.status) "
	        + "FROM Application a WHERE a.applicationId = :applicationId",
	       countQuery = "SELECT count(a) FROM Application a WHERE a.applicationId = :applicationId")
	Optional<ApplicationInputUpdateDto> findUpdateByApplicationId(Long applicationId);
    
}