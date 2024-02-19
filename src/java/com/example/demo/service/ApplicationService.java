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
import com.example.demo.model.Application;
import com.example.demo.model.ApplicationInputUpdateDto;
import com.example.demo.model.ApplicationOverviewDto;
import com.example.demo.model.ApplicationSubCategory;
import com.example.demo.model.ApplicationTag;
import com.example.demo.model.SubCategory;
import com.example.demo.model.Tag;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.SubCategoryRepository;
import com.example.demo.repository.TagRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;
    
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
    @Autowired
    private TagRepository tagRepository;
    
	// ▼一覧表示 ------------------------------------------------------------------------------------
    
    // アプリケーション一覧と検索画面(キーワード検索)
	public Set<ApplicationOverviewDto> getApplicationByKeyword(String keyword) {
		Set<ApplicationOverviewDto> applications = applicationRepository.findApplicationOverviewNameByKeyword(keyword);
		return applications;
	}
    
	// アプリケーション一覧と検索画面(全件)
	public Set<ApplicationOverviewDto> getAllApplicationsData() {
		Set<ApplicationOverviewDto> applications = applicationRepository.findAllOverview();
		return applications;
	}

	// アプリケーション一覧と検索画面(サブカテゴリー検索)
	public Set<ApplicationOverviewDto> getApplicationBySubCategoryId(Long subCategoryId) {
		Set<ApplicationOverviewDto> applications = applicationRepository.findApplicationOverviewBySubCategoryId(subCategoryId);
		return applications;
	}
    
	// アプリケーション一覧と検索画面(タグ検索)
	public Set<ApplicationOverviewDto> getApplicationByTagId(Long tagId) {
		Set<ApplicationOverviewDto> applications = applicationRepository.findApplicationOverviewByTagId(tagId);
		return applications;
	}
    
	// ▼ピックアップ表示 ----------------------------------------------------------------------------
    
    // 最新のアプリケーション
	public Page<ApplicationOverviewDto> getNewApplicationsData() {
		// 3件のみ表示
    	Pageable pageable = PageRequest.of(0, 3);
		Page<ApplicationOverviewDto> applications = applicationRepository.findNewApplicationOverview(pageable);
		return applications;
	}
	
    // 特定のアプリケーションと関連するアプリケーション一覧を取得するメソッド
    public Page<ApplicationOverviewDto> getApplicationApplicationsData(Long applicationId) {
    	// 5件のみ表示
    	Pageable pageable = PageRequest.of(0, 5);
    	Page<ApplicationOverviewDto> applications = applicationRepository.findOverviewByApplicationId(applicationId, pageable);
        return applications;
    }
    
	// 共通のタグを使って、特定の備忘録と関連するアプリケーション一覧を取得するメソッド　●new
	public Page<ApplicationOverviewDto> getRecordApplicationsData(Long recordId) {
		// 備忘録の持つタグを取得
		Set<Tag> applicationTags = tagRepository.findTagByRecordId(recordId);
		// recordTagsからタグIdのみを取り出す
		List<Long> tagIds = new ArrayList<>();
		for(Tag t : applicationTags) {
			tagIds.add(t.getTagId());
		}
		// タグから関連するレコードを取得する
		// 5件のみ表示
		Pageable pageable = PageRequest.of(0, 5);
		Page<ApplicationOverviewDto> records = applicationRepository.findOverviewByTagIds(tagIds, pageable);
	    return records;
	}

	// ▼詳細表示 ------------------------------------------------------------------------------------

    // アプリケーション詳細の取得メソッド
    public Application getApplicationDetailData(Long applicationId) {
        Optional<Application> applicationDetail = applicationRepository.findByApplicationId(applicationId);
        // Optionalの値を返す。値がない場合はnullを返す
        return applicationDetail.orElse(null);
    }

    // ▼管理 ----------------------------------------------------------------------------------------
	
    // アプリケーションの編集情報の取得メソッド
	public ApplicationInputUpdateDto getApplicationUpdateData(Long applicationId) {
        Optional<ApplicationInputUpdateDto> applicationInputUpdateDto = applicationRepository.findUpdateByApplicationId(applicationId);
        // Optionalの値を返す。値がない場合はnullを返す
        return applicationInputUpdateDto.orElse(null);
    }

    // アプリケーション情報を作成または更新するメソッド
    @Transactional
	public AdminResult InsertUpdateApplicationData(ApplicationInputUpdateDto applicationInputUpdateDto) {
        try {
        	// DTOから各エンティティへのデータ移行
        	
        	// アプリケーション情報エンティティへの移行
        	Application application = null;
        	if(applicationInputUpdateDto.getApplicationId()!=null) {
        		// 更新の場合
        		application = applicationRepository.findById(applicationInputUpdateDto.getApplicationId()).orElseThrow();
        	    // 既存のRepositorySubCategoryとgetApplicationTags関連を削除
        		application.getApplicationSubCategories().clear();
        		application.getApplicationTags().clear();
        	}else {
        		// 新規作成の場合
        		application = new Application();
        		application.setApplicationSubCategories(new HashSet<ApplicationSubCategory>());
        		application.setApplicationTags(new HashSet<ApplicationTag>());
        	}
        	
        	// Applicationエンティティの基本情報をセット
        	application.setApplicationName(applicationInputUpdateDto.getApplicationName());
        	application.setApplicationNameEn(applicationInputUpdateDto.getApplicationNameEn());
        	application.setOverview(applicationInputUpdateDto.getOverview());
        	application.setRequirements(applicationInputUpdateDto.getRequirements());
        	application.setStartDate(applicationInputUpdateDto.getStartDate());
        	application.setDuration(applicationInputUpdateDto.getDuration());
        	application.setResponsibility(applicationInputUpdateDto.getResponsibility());
        	application.setChallenges(applicationInputUpdateDto.getChallenges());
        	application.setFutureVision(applicationInputUpdateDto.getFutureVision());
        	application.setStatus(applicationInputUpdateDto.getStatus());
        	// updateDateフィールドはテーブルの設定で自動作成されるため、saveメソッドには渡さない
            
        	// ApplicationエンティティのSet<ApplicationSubCategory>を作成
        	for(Long subCategoryId : applicationInputUpdateDto.getSubCategories()) {
                // SubCategoryエンティティの取得
                SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow();
                ApplicationSubCategory applicationSubCategory = new ApplicationSubCategory();
                applicationSubCategory.setApplicationId(application);
                applicationSubCategory.setSubCategoryId(subCategory);
                application.getApplicationSubCategories().add(applicationSubCategory);
        	}
            
        	// ApplicationエンティティのSet<ApplicationTag>を作成
        	for(Long tagId : applicationInputUpdateDto.getTags()) {
                // Tagエンティティの取得
                Tag tag = tagRepository.findById(tagId).orElseThrow();
                ApplicationTag applicationTag = new ApplicationTag();
                applicationTag.setAppTag(application);
                applicationTag.setTagApp(tag);
                application.getApplicationTags().add(applicationTag);
        	}
            
            // saveメソッドはIdがある場合は更新、ない場合は新規作成してくれる
            applicationRepository.save(application);
        	
            return new AdminResult(true, "アプリケーション情報の更新に成功しました。");
        } catch (Exception e) {
            return new AdminResult(false, "アプリケーション情報の更新に失敗しました。");
        }
    }

    // アプリケーション情報を削除するメソッド
	public AdminResult deleteApplicationData(Long applicationId) {
		try {
			applicationRepository.deleteById(applicationId);
	        return new AdminResult(true, "アプリケーション情報の削除に成功しました。");
	    } catch (Exception e) {
	        return new AdminResult(false, "アプリケーション情報の削除に失敗しました。");
	    }
	}

}
