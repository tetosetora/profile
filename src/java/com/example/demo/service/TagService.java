package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.AdminResult;
import com.example.demo.model.SubCategory;
import com.example.demo.model.SubCategoryTag;
import com.example.demo.model.Tag;
import com.example.demo.model.TagInputUpdateDto;
import com.example.demo.repository.SubCategoryRepository;
import com.example.demo.repository.TagRepository;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;
    
	@Autowired
	private SubCategoryRepository subCategoryRepository;
    
    // 全てのタグを取得するメソッド
	public List<Tag> getAllTags() {
		List<Tag> tags = tagRepository.findAll();
		return tags;
	}
    
    // 特定アプリケーションのタグを取得するメソッド
    public Set<Tag> getApplicationTagsData(Long applicationId) {
    	Set<Tag> tags = tagRepository.findTagByApplicationId(applicationId);
        return tags;
    }
    
    // 特定アプリケーションのタグIdを取得するメソッド
    public Set<Long> getApplicationTagIds(Long applicationId) {
    	Set<Long> tagIds = tagRepository.findTagIdByApplicationId(applicationId);
        return tagIds;
    }
    
    // 特定備忘録のタグを取得するメソッド
    public Set<Tag> getRecordTagsData(Long recordId) {
    	Set<Tag> tags = tagRepository.findTagByRecordId(recordId);
        return tags;
    }
    
    // 特定備忘録のタグIdを取得するメソッド
    public Set<Long> getRecordTagIds(Long recordId) {
    	Set<Long> tagIds = tagRepository.findTagIdByRecordId(recordId);
        return tagIds;
    }
    
    // 特定のサブカテゴリーのタグIdを取得するメソッド
	public Set<Long> getSubCategoryTagIds(Long subCategoryId) {
		Set<Long> tagIds = tagRepository.findTagIdBySubCategoryId(subCategoryId);
        return tagIds;
	}
	
    // ▼管理 ------------------------------------------------------------------------------------

    // タグの編集情報の取得メソッド
	public TagInputUpdateDto getTagUpdateDeleteData(Long tagId) {
		Optional<TagInputUpdateDto> tag = tagRepository.findUpdateByTagId(tagId);
		return tag.orElseThrow();
	}

	// タグを作成または更新するメソッド
    @Transactional
    public AdminResult InsertUpdateTagData(TagInputUpdateDto tagInputUpdateDto) {
        try {
        	// DTOから各エンティティへのデータ移行
        	
        	// タグエンティティへの移行
        	Tag tag = null;
        	if(tagInputUpdateDto.getTagId()!=null) {
        		// 更新の場合
        		tag = tagRepository.findById(tagInputUpdateDto.getTagId()).orElseThrow();
        	    // 既存のsubCategoryTags関連を削除
        		tag.getSubCategoryTags().clear();
        	}else {
        		// 新規作成の場合
        		tag = new Tag();
        		tag.setSubCategoryTags(new HashSet<SubCategoryTag>());
        	}
        	
        	// タグエンティティの基本情報をセット
        	tag.setTagName(tagInputUpdateDto.getTagName());
        	
        	// タグエンティティのSet<RecordSubCategory>を作成
        	for(Long subCategoryId : tagInputUpdateDto.getSubCategories()) {
                // サブカテゴリーエンティティの取得
                SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow();
                SubCategoryTag subCategoryTag = new SubCategoryTag();
                subCategoryTag.setTag(tag);
                subCategoryTag.setSubCategory(subCategory);
                tag.getSubCategoryTags().add(subCategoryTag);
        	}
            
            // saveメソッドはIdがある場合は更新、ない場合は新規作成してくれる
            tagRepository.save(tag);
        	
            return new AdminResult(true, "タグの更新に成功しました。");
        } catch (Exception e) {
            return new AdminResult(false, "タグの更新に失敗しました。");
        }
    }
	
    // タグを削除するメソッド
	public AdminResult deleteTagData(Long tagId) {
		try {
			tagRepository.deleteById(tagId);
	        return new AdminResult(true, "タグの削除に成功しました。");
	    } catch (Exception e) {
	        return new AdminResult(false, "タグの削除に失敗しました。");
	    }
	}

}
