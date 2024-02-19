package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Record;
import com.example.demo.model.RecordInputUpdateDto;
import com.example.demo.model.RecordNameDto;

@Repository
// 備忘録のrepository
public interface RecordRepository extends JpaRepository<Record, Long> {
	
	// ▼一覧表示 ------------------------------------------------------------------------------------
	
	// 備忘録一覧と検索画面(全件)
	@Query(value = "SELECT new com.example.demo.model.RecordNameDto(r.recordId, r.recordName, r.creationDate) FROM Record r "
	        + "ORDER BY r.creationDate DESC",
	        countQuery = "SELECT count(r) FROM Record r")
	Set<RecordNameDto> findAllRecordName();
	
	// 備忘録一覧と検索画面(備忘録名と保有サブカテゴリーと保有タグをキーワード検索)
	@Query(value = "SELECT DISTINCT new com.example.demo.model.RecordNameDto(r.recordId, r.recordName, r.creationDate) "
	        + "FROM Record r JOIN r.recordTags rt JOIN r.recordSubCategories rs "
	        + "WHERE r.recordName LIKE %:keyword% OR rt.tagRec.tagName LIKE %:keyword% OR rs.subCategoryid.subCategoryName LIKE %:keyword% "
	        + "ORDER BY r.creationDate DESC",
	       countQuery = "SELECT count(DISTINCT r) "
	        + "FROM Record r JOIN r.recordTags rt JOIN r.recordSubCategories rs "
	        + "WHERE r.recordName LIKE %:keyword% OR rt.tagRec.tagName LIKE %:keyword% OR rs.subCategoryid.subCategoryName LIKE %:keyword%")
	Set<RecordNameDto> findRecordNameByKeyword(String keyword);
	
	// 備忘録一覧と検索画面(サブカテゴリー検索)
	@Query(value = "SELECT DISTINCT new com.example.demo.model.RecordNameDto(r.recordId, r.recordName, r.creationDate) "
	        + "FROM Record r JOIN r.recordSubCategories rs WHERE rs.subCategoryid.subCategoryId = :subCategoryId "
	        + "ORDER BY r.creationDate DESC",
	       countQuery = "SELECT count(DISTINCT r) "
	        + "FROM Record r JOIN r.recordSubCategories rs WHERE rs.subCategoryid.subCategoryId = :subCategoryId")
	Set<RecordNameDto> findRecordNameBySubCategoryId(Long subCategoryId);

	// 備忘録一覧と検索画面(タグ検索)
	@Query(value = "SELECT DISTINCT new com.example.demo.model.RecordNameDto(r.recordId, r.recordName, r.creationDate) "
	        + "FROM Record r JOIN r.recordTags rt WHERE rt.tagRec.tagId = :tagId "
	        + "ORDER BY r.creationDate DESC",
	       countQuery = "SELECT count(DISTINCT r) "
	        + "FROM Record r JOIN r.recordTags rt WHERE rt.tagRec.tagId = :tagId")
	Set<RecordNameDto> findRecordNameByTagId(Long tagId);
	
	// ▼ピックアップ表示 ----------------------------------------------------------------------------
	
	// 最新の備忘録を取得
	@Query(value = "SELECT new com.example.demo.model.RecordNameDto(r.recordId, r.recordName) "
	        + "FROM Record r ORDER BY r.creationDate DESC, r.lastUpdateDate DESC",
	        countQuery = "SELECT count(r) FROM Record r")
	Page<RecordNameDto> findNewRecordName(Pageable pageable);
	
	// タグから備忘録一覧情報を取得  ●分けてるやつ
	@Query(value = "SELECT new com.example.demo.model.RecordNameDto(r.recordId, r.recordName) "
	        + "FROM Record r JOIN r.recordTags rt "
	        + "WHERE rt.tagRec.tagId IN :tagIds",
	       countQuery = "SELECT count(DISTINCT r) "
	        + "FROM Record r JOIN r.recordTags rt "
	        + "WHERE rt.tagRec.tagId IN :tagIds")
	Page<RecordNameDto> findRecordNameByTagIds(@Param("tagIds") List<Long> tagIds, Pageable pageable);
	
	// 共通のタグを使い、特定の備忘録と関連する備忘録一覧基本情報を取得
	@Query(value = "SELECT new com.example.demo.model.RecordNameDto(r.recordId, r.recordName) "
	        + "FROM Record r JOIN r.recordTags rts WHERE rts.tagRec.tagId IN "
	        + "(SELECT rt.tagRec.tagId FROM RecordTag rt WHERE rt.recordTag.recordId = :recordId) "
	        + "AND r.recordId != :recordId",
	       countQuery = "SELECT count(DISTINCT r) "
	        + "FROM Record r JOIN r.recordTags rts WHERE rts.tagRec.tagId IN "
	        + "(SELECT rt.tagRec.tagId FROM RecordTag rt WHERE rt.recordTag.recordId = :recordId) "
	        + "AND r.recordId != :recordId")
	Page<RecordNameDto> findOverviewByRecordId(Long recordId, Pageable pageable);
	
	// ▼詳細表示 ------------------------------------------------------------------------------------
	
	// 備忘録の詳細情報を取得
	Optional<Record> findByRecordId(Long recordId);

	// ▼管理 ----------------------------------------------------------------------------------------
	
	// 備忘録の詳細情報をRecordInputUpdateDtoに取得
	@Query(value = "SELECT new com.example.demo.model.RecordInputUpdateDto(r.recordId, r.recordName, r.recordGenre, r.creationDate, "
	        + "r.recordText, r.referenceUrl, r.importance, r.visualElements) "
	        + "FROM Record r WHERE r.recordId = :recordId",
	       countQuery = "SELECT count(r) FROM Record r WHERE r.recordId = :recordId")
	Optional<RecordInputUpdateDto> findUpdateByRecordId(Long recordId);
	







	
}
