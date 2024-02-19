package com.example.demo.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Application;
import com.example.demo.model.ApplicationOverviewDto;
import com.example.demo.model.CategoryTotallingDto;
import com.example.demo.model.Record;
import com.example.demo.model.RecordNameDto;
import com.example.demo.model.SubCategory;
import com.example.demo.model.Tag;
import com.example.demo.model.User;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.CategoryTotallingService;
import com.example.demo.service.RecordService;
import com.example.demo.service.SubCategoryService;
import com.example.demo.service.TagService;
import com.example.demo.service.UserService;
import com.example.demo.util.MarkdownUtils;

@Controller
public class PublicViewController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private RecordService recordService;
    
    @Autowired
    private CategoryTotallingService categoryTotallingService;
    
    @Autowired
    private SubCategoryService subCategoryService;
    
    @Autowired
    private TagService tagService;

	// ホーム画面
	@GetMapping("/")
	public String showHome(Model model) {
		User users = userService.getUsersData();
		// サニタイズ
		String userIntroduction = MarkdownUtils.markdownToHtmlAndSanitize(users.getUserIntroduction());
		model.addAttribute("userIntroduction", userIntroduction);
		String futureChallenges = MarkdownUtils.markdownToHtmlAndSanitize(users.getFutureChallenges());
		model.addAttribute("futureChallenges", futureChallenges);
		
		// 最新のアプリケーションを取得
		Page<ApplicationOverviewDto> applications = applicationService.getNewApplicationsData();
		model.addAttribute("applications", applications);
		
		// 最新の備忘録を取得
		Page<RecordNameDto> records = recordService.getNewRecordsData();
		model.addAttribute("records", records);
		
		// カテゴリー集計を取得
		List<CategoryTotallingDto> categoryTotallings = categoryTotallingService.getCategoryTotallingData();
		model.addAttribute("categoryTotallings", categoryTotallings);
		
		return "home";
	}
	
	// ▼アプリケーション管理 ------------------------------------------------------------------------------

	// アプリケーション一覧と検索画面(キーワード検索)
	@PostMapping("/applicationListAndSearch")
	public String showApplicationList(Model model, @RequestParam String keyword){
		Set<ApplicationOverviewDto> applications = applicationService.getApplicationByKeyword(keyword);
		// サニタイズ
		for(ApplicationOverviewDto a : applications) {
			a.setOverview(MarkdownUtils.markdownToHtmlAndSanitize(a.getOverview()));
		}
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		List<Tag> tags = tagService.getAllTags();
		model.addAttribute("searchName", keyword);
		model.addAttribute("applications", applications);
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("tags", tags);
		return "applicationListAndSearch";
	}
	// アプリケーション一覧と検索画面(全件orサブカテゴリー選択orタグ選択)
	@GetMapping("/applicationListAndSearch")
	public String showApplicationList(Model model, 
			@RequestParam(required = false) Long subCategoryId, 
			@RequestParam(required = false) String subCategoryName, 
			@RequestParam(required = false) Long tagId, 
			@RequestParam(required = false) String tagName) {
		Set<ApplicationOverviewDto> applications = null;
		if(subCategoryId != null) {
			applications = applicationService.getApplicationBySubCategoryId(subCategoryId);
			// サブカテゴリー名も送る
			model.addAttribute("searchName", subCategoryName);
		}else if(tagId != null){
			applications = applicationService.getApplicationByTagId(tagId);
			// タグ名も送る
			model.addAttribute("searchName", tagName);
		}else{
			applications = applicationService.getAllApplicationsData();
			model.addAttribute("searchName", "全件");
		}
		// サニタイズ
		for(ApplicationOverviewDto a : applications) {
			a.setOverview(MarkdownUtils.markdownToHtmlAndSanitize(a.getOverview()));
		}
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		List<Tag> tags = tagService.getAllTags();
		model.addAttribute("applications", applications);
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("tags", tags);
		return "applicationListAndSearch";
	}
	
	// アプリケーション詳細画面
	@GetMapping("/applicationDetail")
	public String showApplicationDetail(Model model, @RequestParam Long applicationId) {
		
		// アプリケーション詳細情報を取得
		Application applicationDetail = applicationService.getApplicationDetailData(applicationId);
		// サニタイズ
		applicationDetail.setOverview(MarkdownUtils.markdownToHtmlAndSanitize(applicationDetail.getOverview()));
		applicationDetail.setRequirements(MarkdownUtils.markdownToHtmlAndSanitize(applicationDetail.getRequirements()));
		applicationDetail.setResponsibility(MarkdownUtils.markdownToHtmlAndSanitize(applicationDetail.getResponsibility()));
		applicationDetail.setChallenges(MarkdownUtils.markdownToHtmlAndSanitize(applicationDetail.getChallenges()));
		applicationDetail.setFutureVision(MarkdownUtils.markdownToHtmlAndSanitize(applicationDetail.getFutureVision()));
		model.addAttribute("applicationDetail", applicationDetail);
		
		// 関連サブカテゴリーを取得
		Set<SubCategory> subCategories = subCategoryService.getApplicationSubCategoriesData(applicationId);
		model.addAttribute("subCategories", subCategories);
		
		// 関連タグを取得
		Set<Tag> tags = tagService.getApplicationTagsData(applicationId);
		model.addAttribute("tags", tags);
		
		// 関連アプリケーション概要を取得
		Page<ApplicationOverviewDto> applications = applicationService.getApplicationApplicationsData(applicationId);
		model.addAttribute("applications", applications);
		
		// 関連備忘録名を取得
		Page<RecordNameDto> records = recordService.getApplicationRecordsData(applicationId);
		model.addAttribute("records", records);
		
		return "applicationDetail";
	}
	
	// ▼備忘録管理 ------------------------------------------------------------------------------
	
	// 備忘録一覧と検索画面(キーワード検索)
	@PostMapping("/recordListAndSearch")
	public String showRecordList(Model model, @RequestParam String keyword){
		Set<RecordNameDto> records = recordService.getRecordByKeyword(keyword);
		// 全てのサブカテゴリーとタグを取得
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		List<Tag> tags = tagService.getAllTags();
		model.addAttribute("searchName", keyword);
		model.addAttribute("records", records);
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("tags", tags);
		return "recordListAndSearch";
	}
	// 備忘録一覧と検索画面(全件orサブカテゴリー選択orタグ選択)
	@GetMapping("/recordListAndSearch")
	public String showRecordList(Model model, 
			@RequestParam(required = false) Long subCategoryId, 
			@RequestParam(required = false) String subCategoryName, 
			@RequestParam(required = false) Long tagId, 
			@RequestParam(required = false) String tagName) {
		Set<RecordNameDto> records = null;
		if(subCategoryId != null) {
			records = recordService.getRecordBySubCategoryId(subCategoryId);
			// サブカテゴリー名も送る
			model.addAttribute("searchName", subCategoryName);
		}else if(tagId != null){
			records = recordService.getRecordByTagId(tagId);
			// タグ名も送る
			model.addAttribute("searchName", tagName);
		}else{
			records = recordService.getAllRecordsData();
			model.addAttribute("searchName", "全件");
		}
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		List<Tag> tags = tagService.getAllTags();
		model.addAttribute("records", records);
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("tags", tags);
		return "recordListAndSearch";
	}
	
	// 備忘録詳細画面
	@GetMapping("/recordDetail")
	public String showRecordDetail(Model model, @RequestParam Long recordId) {
		
		// 備忘録詳細情報を取得
		Record recordDetail = recordService.getRecordDetailData(recordId);
		// サニタイズ
		recordDetail.setRecordText(MarkdownUtils.markdownToHtmlAndSanitize(recordDetail.getRecordText()));
		model.addAttribute("recordDetail", recordDetail);
		
		// 関連サブカテゴリーを取得
		Set<SubCategory> subCategories = subCategoryService.getRecordSubCategoriesData(recordId);
		model.addAttribute("subCategories", subCategories);
		
		// 関連タグを取得
		Set<Tag> tags = tagService.getRecordTagsData(recordId);
		model.addAttribute("tags", tags);
		
		// 関連アプリケーション概要を取得
		Page<ApplicationOverviewDto> applications = applicationService.getRecordApplicationsData(recordId);
		model.addAttribute("applications", applications);
		
		// 関連備忘録名を取得
		Page<RecordNameDto> records = recordService.getRecordRecordsData(recordId);
		model.addAttribute("records", records);
		
		return "recordDetail";
	}
	
}
