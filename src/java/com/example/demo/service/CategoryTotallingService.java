package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.AdminResult;
import com.example.demo.model.CategoryTotallingDto;
import com.example.demo.model.MainCategory;
import com.example.demo.model.SubCategory;
import com.example.demo.model.SubCategoryCountDto;
import com.example.demo.repository.MainCategoryRepository;
import com.example.demo.repository.SubCategoryRepository;

@Service
public class CategoryTotallingService {

    @Autowired
    private MainCategoryRepository mainCategoryRepository;
    
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    // メインカテゴリーごとに、サブカテゴリーを集計するメソッド
	public List<CategoryTotallingDto> getCategoryTotallingData() {
	    // メインカテゴリーのリストを取得
        List<CategoryTotallingDto> categoryTotallings = mainCategoryRepository.findNameForCategoryTotalling();

        // 各MainCategoriesに対して、集計したSubCategoryCountDtoを代入
        for(CategoryTotallingDto c : categoryTotallings) {
        	Set<SubCategoryCountDto> subCategoryCountDto = subCategoryRepository.findByMainCategoryIdAndRelationCount(c.getMainCategoryId());
        	c.setSubCategoryCountDto(subCategoryCountDto);
        }
        return categoryTotallings;
	}
	
	
	// 全てのメインカテゴリーを取得するメソッド
	public List<MainCategory> getAllMainCategories() {
		List<MainCategory> mainCategories = mainCategoryRepository.findAll();
		return mainCategories;
	}

    // Idに基づいたメインカテゴリーを取得するメソッド
	public MainCategory getMainCategoryUpdateDeleteData(Long mainCategoryId) {
		Optional<MainCategory> mainCategory = mainCategoryRepository.findById(mainCategoryId);
		return mainCategory.orElseThrow();
	}

	// メインカテゴリーを作成または更新するメソッド
    @Transactional
    public AdminResult InsertUpdateMainCategoryData(MainCategory newMainCategory) {
        try {
        	// DTOから各エンティティへのデータ移行
        	
        	// メインカテゴリーエンティティへの移行
        	MainCategory mainCategory = null;
        	if(newMainCategory.getMainCategoryId()!=null) {
        		// 更新の場合
        		mainCategory = mainCategoryRepository.findById(newMainCategory.getMainCategoryId()).orElseThrow();
        	}else {
        		// 新規作成の場合
        		mainCategory = new MainCategory();
        		mainCategory.setSubCategories(new HashSet<SubCategory>());
        	}
        	
        	// MainCategoryエンティティの基本情報をセット
        	mainCategory.setMainCategoryName(newMainCategory.getMainCategoryName());
            
            // saveメソッドはIdがある場合は更新、ない場合は新規作成してくれる
            mainCategoryRepository.save(mainCategory);
        	
            return new AdminResult(true, "メインカテゴリーの更新に成功しました。");
        } catch (Exception e) {
            return new AdminResult(false, "メインカテゴリーの更新に失敗しました。");
        }
    }

}
