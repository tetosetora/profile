package com.example.demo.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Tag;
import com.example.demo.model.TagInputUpdateDto;

// タグのrepository
public interface TagRepository extends JpaRepository<Tag, Long> {

	// アプリケーションのタグを取得するカスタムクエリメソッド
	@Query(value = "SELECT at.tagApp FROM ApplicationTag at WHERE at.appTag.applicationId = :applicationId",
		       countQuery = "SELECT count(at.tagApp) FROM ApplicationTag at WHERE at.appTag.applicationId = :applicationId")
	Set<Tag> findTagByApplicationId(@Param("applicationId") Long applicationId);
	
	// アプリケーションのタグIdを取得
	@Query(value = "SELECT at.tagApp.tagId FROM ApplicationTag at WHERE at.appTag.applicationId = :applicationId",
		       countQuery = "SELECT count(at.tagApp.tagId) FROM ApplicationTag at WHERE at.appTag.applicationId = :applicationId")
	Set<Long> findTagIdByApplicationId(Long applicationId);
    
	// 備忘録のタグを取得するカスタムクエリメソッド
	@Query(value = "SELECT rt.tagRec FROM RecordTag rt WHERE rt.recordTag.recordId = :recordId",
		       countQuery = "SELECT count(rt.tagRec) FROM RecordTag rt WHERE rt.recordTag.recordId = :recordId")
	Set<Tag> findTagByRecordId(@Param("recordId") Long recordId);

	// 備忘録のタグIdを取得
	@Query(value = "SELECT rt.tagRec.tagId FROM RecordTag rt WHERE rt.recordTag.recordId = :recordId",
		       countQuery = "SELECT count(rt.tagRec.tagId) FROM RecordTag rt WHERE rt.recordTag.recordId = :recordId")
	Set<Long> findTagIdByRecordId(Long recordId);
	
	// サブカテゴリーの保有するタグIdを取得
	@Query(value = "SELECT st.tag.tagId FROM SubCategoryTag st WHERE st.subCategory.subCategoryId = :subCategoryId",
		       countQuery = "SELECT count(st.tag.tagId) FROM SubCategoryTag st WHERE st.subCategory.subCategoryId = :subCategoryId")
	Set<Long> findTagIdBySubCategoryId(Long subCategoryId);
	
	// 備忘録の詳細情報をTagInputUpdateDtoに取得
	@Query(value = "SELECT new com.example.demo.model.TagInputUpdateDto(t.tagId, t.tagName) "
	        + "FROM Tag t WHERE t.tagId = :tagId",
	        countQuery = "SELECT count(t) FROM Tag t WHERE t.tagId = :tagId")
	Optional<TagInputUpdateDto> findUpdateByTagId(Long tagId);
    
}