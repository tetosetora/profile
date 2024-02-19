package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.AdminResult;
import com.example.demo.model.Record;
import com.example.demo.model.RecordInputUpdateDto;
import com.example.demo.model.RecordNameDto;
import com.example.demo.model.RecordSubCategory;
import com.example.demo.model.RecordTag;
import com.example.demo.model.SubCategory;
import com.example.demo.model.Tag;
import com.example.demo.repository.RecordRepository;
import com.example.demo.repository.SubCategoryRepository;
import com.example.demo.repository.TagRepository;

@Service
public class RecordService {

	@Autowired
	private RecordRepository recordRepository;
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
    @Autowired
    private TagRepository tagRepository;
	
	// ▼一覧表示 ------------------------------------------------------------------------------------
    
    // 備忘録一覧と検索画面(キーワード検索)
	public Set<RecordNameDto> getRecordByKeyword(String keyword) {
		Set<RecordNameDto> records = recordRepository.findRecordNameByKeyword(keyword);
		return records;
	}
    
	// 備忘録一覧と検索画面(全件)
	public Set<RecordNameDto> getAllRecordsData() {
		Set<RecordNameDto> records = recordRepository.findAllRecordName();
		return records;
	}

	// 備忘録一覧と検索画面(サブカテゴリー検索)
	public Set<RecordNameDto> getRecordBySubCategoryId(Long subCategoryId) {
		Set<RecordNameDto> records = recordRepository.findRecordNameBySubCategoryId(subCategoryId);
		return records;
	}
    
	// 備忘録一覧と検索画面(タグ検索)
	public Set<RecordNameDto> getRecordByTagId(Long tagId) {
		Set<RecordNameDto> records = recordRepository.findRecordNameByTagId(tagId);
		return records;
	}
    
	// ▼ピックアップ表示 ------------------------------------------------------------------------------------
	
    // 最新の備忘録
	public Page<RecordNameDto> getNewRecordsData() {
		// 3件のみ表示
    	Pageable pageable = PageRequest.of(0, 3);
		Page<RecordNameDto> records = recordRepository.findNewRecordName(pageable);
		return records;
	}
	
    // 共通のタグを使って、特定のアプリケーションと関連する備忘録一覧を取得するメソッド
    public Page<RecordNameDto> getApplicationRecordsData(Long applicationId) {
    	// アプリケーションの持つタグを取得
    	Set<Tag> recordTags = tagRepository.findTagByApplicationId(applicationId);
    	// recordTagsからタグIdのみを取り出す
    	List<Long> tagIds = new ArrayList<>();
    	for(Tag t : recordTags) {
    		tagIds.add(t.getTagId());
    	}
    	// タグから関連するレコードを取得する
    	// 5件のみ表示
    	Pageable pageable = PageRequest.of(0, 5);
    	Page<RecordNameDto> records = recordRepository.findRecordNameByTagIds(tagIds, pageable);
        return records;
    }
    
    // 特定の備忘録と関連する備忘録一覧を取得するメソッド
    public Page<RecordNameDto> getRecordRecordsData(Long recordId) {
    	// 5件のみ表示
    	Pageable pageable = PageRequest.of(0, 5);
    	Page<RecordNameDto> records = recordRepository.findOverviewByRecordId(recordId, pageable);
        return records;
    }
    
	// ▼詳細表示 ------------------------------------------------------------------------------------
    
    // 備忘録詳細の取得メソッド
    public Record getRecordDetailData(Long recordId) {
        Optional<Record> recordDetail = recordRepository.findByRecordId(recordId);
        // Optionalの値を返す。値がない場合はnullを返す
        return recordDetail.orElse(null);
    }
    
    // ▼管理 ------------------------------------------------------------------------------------
    
    // 備忘録の編集情報の取得メソッド
    public RecordInputUpdateDto getRecordUpdateData(Long recordId) {
        Optional<RecordInputUpdateDto> recordInputUpdateDto = recordRepository.findUpdateByRecordId(recordId);
        // Optionalの値を返す。値がない場合はnullを返す
        return recordInputUpdateDto.orElse(null);
    }
    
    // 備忘録を作成または更新するメソッド
    @Transactional
    public AdminResult InsertUpdateRecordData(RecordInputUpdateDto recordInputUpdateDto) {
        try {
        	// DTOから各エンティティへのデータ移行
        	
        	// 備忘録エンティティへの移行
        	Record record = null;
        	if(recordInputUpdateDto.getRecordId()!=null) {
        		// 更新の場合
        		record = recordRepository.findById(recordInputUpdateDto.getRecordId()).orElseThrow();
        	    // 既存のRepositorySubCategoryとRecordTag関連を削除
        		record.getRecordSubCategories().clear();
        		record.getRecordTags().clear();
        	}else {
        		// 新規作成の場合
        		record = new Record();
        		record.setRecordSubCategories(new HashSet<RecordSubCategory>());
        		record.setRecordTags(new HashSet<RecordTag>());
        	}
        	
        	// Recordエンティティの基本情報をセット
        	record.setRecordName(recordInputUpdateDto.getRecordName());
        	record.setRecordGenre(recordInputUpdateDto.getRecordGenre());
        	record.setCreationDate(recordInputUpdateDto.getCreationDate());
        	record.setRecordText(recordInputUpdateDto.getRecordText());
        	record.setReferenceUrl(recordInputUpdateDto.getReferenceUrl());
        	record.setImportance(recordInputUpdateDto.getImportance());
        	record.setVisualElements(recordInputUpdateDto.getVisualElements());
        	// updateDateフィールドはテーブルの設定で自動作成されるため、saveメソッドには渡さない
            
        	// RecordエンティティのSet<RecordSubCategory>を作成
        	for(Long subCategoryId : recordInputUpdateDto.getSubCategories()) {
                // SubCategoryエンティティの取得
                SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow();
                RecordSubCategory recordSubCategory = new RecordSubCategory();
                recordSubCategory.setRecordId(record);
                recordSubCategory.setSubCategoryid(subCategory);
                record.getRecordSubCategories().add(recordSubCategory);
        	}
            
        	// RecordエンティティのSet<RecordTag>を作成
        	for(Long tagId : recordInputUpdateDto.getTags()) {
                // Tagエンティティの取得
                Tag tag = tagRepository.findById(tagId).orElseThrow();
                RecordTag recordTag = new RecordTag();
                recordTag.setRecordTag(record);
                recordTag.setTagRec(tag);
                record.getRecordTags().add(recordTag);
        	}
            
            // saveメソッドはIdがある場合は更新、ない場合は新規作成してくれる
            recordRepository.save(record);
        	
            return new AdminResult(true, "備忘録の更新に成功しました。");
        } catch (Exception e) {
            return new AdminResult(false, "備忘録の更新に失敗しました。");
        }
    }

    // 備忘録を削除するメソッド
	public AdminResult deleteRecordData(Long recordId) {
		try {
			recordRepository.deleteById(recordId);
	        return new AdminResult(true, "備忘録の削除に成功しました。");
	    } catch (Exception e) {
	        return new AdminResult(false, "備忘録の削除に失敗しました。");
	    }
	}
	
}

