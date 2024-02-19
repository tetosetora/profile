package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.AdminResult;
import com.example.demo.model.MainCategory;
import com.example.demo.model.SubCategory;
import com.example.demo.model.SubCategoryInputUpdateDto;
import com.example.demo.model.SubCategoryTag;
import com.example.demo.model.Tag;
import com.example.demo.repository.MainCategoryRepository;
import com.example.demo.repository.SubCategoryRepository;
import com.example.demo.repository.TagRepository;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;
   
    @Autowired
    private MainCategoryRepository mainCategoryRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    // 全てのサブカテゴリーを取得するメソッド
	public List<SubCategory> getAllSubCategories() {
		List<SubCategory> subCategories = subCategoryRepository.findAll();
		return subCategories;
	}
    
    // 特定アプリケーションのサブカテゴリーを取得するメソッド
    public Set<SubCategory> getApplicationSubCategoriesData(Long applicationId) {
    	Set<SubCategory> subCategories = subCategoryRepository.findByApplicationId(applicationId);
        return subCategories;
    }
    
    // 特定アプリケーションのサブカテゴリーIdを取得するメソッド
    public Set<Long> getApplicationSubCategoryId(Long applicationId) {
    	Set<Long> subCategoryIds = subCategoryRepository.findIdByApplicationId(applicationId);
        return subCategoryIds;
    }

    // 特定備忘録のサブカテゴリーを取得するメソッド
    public Set<SubCategory> getRecordSubCategoriesData(Long recordId) {
    	Set<SubCategory> subCategories = subCategoryRepository.findByRecordId(recordId);
        return subCategories;
    }
    
    // 特定備忘録のサブカテゴリーIdを取得するメソッド
    public Set<Long> getRecordSubCategoryId(Long recordId) {
    	Set<Long> subCategoryIds = subCategoryRepository.findIdByRecordId(recordId);
        return subCategoryIds;
    }
    
    // 特定タグのサブカテゴリーIdを取得するメソッド
	public Set<Long> getTagSubCategoryId(Long tagId) {
		Set<Long> subCategoryIds = subCategoryRepository.findIdByTagId(tagId);
        return subCategoryIds;
    }
	
    // ▼管理 ------------------------------------------------------------------------------------

    // サブカテゴリーの編集情報の取得メソッド
	public SubCategoryInputUpdateDto getSubCategoryUpdateDeleteData(Long subCategoryId) {
        Optional<SubCategoryInputUpdateDto> subCategoryInputUpdateDto = subCategoryRepository.findUpdateBySubCategoryId(subCategoryId);
        // Optionalの値を返す。値がない場合はnullを返す
        return subCategoryInputUpdateDto.orElse(null);
    }

	// サブカテゴリーを作成または更新するメソッド
    @Transactional
	public AdminResult InsertUpdateSubCategoryData(SubCategoryInputUpdateDto subCategoryInputUpdateDto) {
        try {
        	// DTOから各エンティティへのデータ移行
        	
        	// サブカテゴリーエンティティへの移行
        	SubCategory subCategory = null;
        	if(subCategoryInputUpdateDto.getSubCategoryId()!=null) {
        		subCategory = subCategoryRepository.findById(subCategoryInputUpdateDto.getSubCategoryId()).orElseThrow();
        	    // 既存のSubCategoryTag関連を削除
        		subCategory.getSubCategoryTags().clear();
        	}else {
        		subCategory = new SubCategory();
        		subCategory.setSubCategoryTags(new HashSet<SubCategoryTag>());
        	}
        	
        	// サブカテゴリーエンティティの基本情報をセット
        	subCategory.setSubCategoryName(subCategoryInputUpdateDto.getSubCategoryName());
        	
        	// メインカテゴリーIdからメインカテゴリー情報を取得しセット
        	MainCategory mainCategory = mainCategoryRepository.findById(subCategoryInputUpdateDto.getMainCategoryId()).orElseThrow();
        	subCategory.setMainCategories(mainCategory);
        	
        	// サブカテゴリーエンティティのSet<SubCategoryTag>を作成
        	for(Long tagId : subCategoryInputUpdateDto.getTags()) {
                // Tagエンティティの取得
                Tag tag = tagRepository.findById(tagId).orElseThrow();
                SubCategoryTag subCategoryTag = new SubCategoryTag();
                subCategoryTag.setSubCategory(subCategory);
                subCategoryTag.setTag(tag);
                subCategory.getSubCategoryTags().add(subCategoryTag);
        	}
            
            // saveメソッドはIdがある場合は更新、ない場合は新規作成してくれる
            subCategoryRepository.save(subCategory);
        	
            return new AdminResult(true, "サブカテゴリーの更新に成功しました。");
        } catch (Exception e) {
            return new AdminResult(false, "サブカテゴリーの更新に失敗しました。");
        }
    }

    // サブカテゴリーを削除するメソッド
	public AdminResult deleteSubCategoryData(Long subCategoryId) {
		try {
			subCategoryRepository.deleteById(subCategoryId);
	        return new AdminResult(true, "サブカテゴリーの削除に成功しました。");
	    } catch (Exception e) {
	        return new AdminResult(false, "サブカテゴリーの削除に失敗しました。");
	    }
	}
    
}
