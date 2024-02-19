package com.example.demo.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.ApplicationInputUpdateDto;
import com.example.demo.model.MainCategory;
import com.example.demo.model.RecordInputUpdateDto;
import com.example.demo.model.SubCategory;
import com.example.demo.model.SubCategoryInputUpdateDto;
import com.example.demo.model.Tag;
import com.example.demo.model.TagInputUpdateDto;
import com.example.demo.model.User;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.CategoryTotallingService;
import com.example.demo.service.RecordService;
import com.example.demo.service.SubCategoryService;
import com.example.demo.service.TagService;
import com.example.demo.service.UserService;

@Controller
public class AdminViewController {
	
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
    
    // 管理者ホーム画面
	@GetMapping("/adminIndex")
	public String showAdminIndex(Model model) {
	    return "admin/adminIndex";
	}
    
	// データベース変更結果の表示画面
	@GetMapping("/adminResultPage")
	public String showAdminResultPage(Model model) {
		// AdminChangeControllerからのリダイレクトとして、メッセージを受け取り表示
	    return "admin/adminResultPage";
	}
    
	// 備忘録の新規作成画面
	@GetMapping("/adminRecord")
	public String showAdminRecord(Model model) {
		
		model.addAttribute("recordInputUpdateDto", new RecordInputUpdateDto());
		
		// Viewで更新処理を行うことを伝えるための変数
		boolean update = false;
		model.addAttribute("update", update);
		
		// 全てのサブカテゴリーを取得
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		model.addAttribute("subCategories", subCategories);

		// 全てのタグを取得
		List<Tag> tags = tagService.getAllTags();
		model.addAttribute("tags", tags);
		
		return "admin/adminRecord";
	}

	// 備忘録の更新削除画面
	@PostMapping("/adminRecord")
	public String showAdminRecord(Model model, @RequestParam Long recordId) {
			
		// Viewで更新処理を行うことを伝えるための変数
		boolean update = true;
		model.addAttribute("update", update);
		
		// 備忘録詳細情報を取得
		RecordInputUpdateDto recordInputUpdateDto = recordService.getRecordUpdateData(recordId);
		
		// 関連サブカテゴリーを取得
		Set<Long> selectSubCategoryIds = subCategoryService.getRecordSubCategoryId(recordId);
		recordInputUpdateDto.setSubCategories(selectSubCategoryIds);

		// 関連タグを取得
		Set<Long> selectTagIds = tagService.getRecordTagIds(recordId);
		recordInputUpdateDto.setTags(selectTagIds);
		
		model.addAttribute("recordInputUpdateDto", recordInputUpdateDto);
		
		// 全てのサブカテゴリーを取得
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		model.addAttribute("subCategories", subCategories);
		
		// 全てのタグを取得
		List<Tag> tags = tagService.getAllTags();
		model.addAttribute("tags", tags);

		return "admin/adminRecord";
	}
	
	
	// アプリケーション情報の新規作成画面
	@GetMapping("/adminApplication")
	public String showAdminApplication(Model model) {
		
		model.addAttribute("applicationInputUpdateDto", new ApplicationInputUpdateDto());
		
		// Viewで更新処理を行うことを伝えるための変数
		boolean update = false;
		model.addAttribute("update", update);
		
		// 全てのサブカテゴリーを取得
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		model.addAttribute("subCategories", subCategories);

		// 全てのタグを取得
		List<Tag> tags = tagService.getAllTags();
		model.addAttribute("tags", tags);
		
		return "admin/adminApplication";
	}

	// アプリケーション情報の更新削除画面
	@PostMapping("/adminApplication")
	public String showAdminApplication(Model model, @RequestParam Long applicationId) {
			
		// Viewで更新処理を行うことを伝えるための変数
		boolean update = true;
		model.addAttribute("update", update);
		
		// アプリケーション詳細情報を取得
		ApplicationInputUpdateDto applicationInputUpdateDto = applicationService.getApplicationUpdateData(applicationId);
		
		// 関連サブカテゴリーを取得
		Set<Long> selectSubCategoryIds = subCategoryService.getApplicationSubCategoryId(applicationId);
		applicationInputUpdateDto.setSubCategories(selectSubCategoryIds);

		// 関連タグを取得
		Set<Long> selectTagIds = tagService.getApplicationTagIds(applicationId);
		applicationInputUpdateDto.setTags(selectTagIds);
		
		model.addAttribute("applicationInputUpdateDto", applicationInputUpdateDto);
		
		// 全てのサブカテゴリーを取得
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		model.addAttribute("subCategories", subCategories);
		
		// 全てのタグを取得
		List<Tag> tags = tagService.getAllTags();
		model.addAttribute("tags", tags);

		return "admin/adminApplication";
	}
	

	// ユーザー情報の更新画面
	@GetMapping("/adminProfile")
	public String showAdminProfile(Model model) {
		
		// ユーザー情報を取得
		User users = userService.getUsersData();
		model.addAttribute("users", users);

		return "admin/adminProfile";
	}


	// メインカテゴリーの新規作成または更新画面
	@GetMapping("/adminMainCategory")
	public String showAdminMainCategory(Model model, @RequestParam(required = false) Long mainCategoryId) {
		
		// Viewで更新処理を行うことを伝えるための変数
		boolean update = (mainCategoryId!=null);
		
		// 新規の場合
		if(!update) {
			// フォーム送信用Dto
			model.addAttribute("mainCategory", new MainCategory());
		// 更新の場合
		}else {
			// メインカテゴリー情報を取得
			MainCategory mainCategory = categoryTotallingService.getMainCategoryUpdateDeleteData(mainCategoryId);
			
			model.addAttribute("mainCategory", mainCategory);
		
		}
		
		model.addAttribute("update", update);
		
		// 全てのメインカテゴリーを取得
		List<MainCategory> mainCategories = categoryTotallingService.getAllMainCategories();
		model.addAttribute("mainCategories", mainCategories);

		return "admin/adminMainCategory";
	}


	// サブカテゴリーの新規作成または更新削除画面
	@GetMapping("/adminSubCategory")
	public String showAdminSubCategory(Model model, @RequestParam(required = false) Long subCategoryId) {
		
		// Viewで更新処理を行うことを伝えるための変数
		boolean update = (subCategoryId!=null);
		
		// 新規の場合
		if(!update) {
			// フォーム送信用Dto
			model.addAttribute("subCategoryInputUpdateDto", new SubCategoryInputUpdateDto());
		// 更新の場合
		}else {
			// サブカテゴリー情報を取得
			SubCategoryInputUpdateDto subCategoryInputUpdateDto = subCategoryService.getSubCategoryUpdateDeleteData(subCategoryId);
	
			// 保有タグを取得
			Set<Long> selectTagIds = tagService.getSubCategoryTagIds(subCategoryId);
			subCategoryInputUpdateDto.setTags(selectTagIds);
			
			model.addAttribute("subCategoryInputUpdateDto", subCategoryInputUpdateDto);
		
		}
		
		model.addAttribute("update", update);
		
		// 全てのメインカテゴリーを取得
		List<MainCategory> mainCategorys = categoryTotallingService.getAllMainCategories();
		model.addAttribute("mainCategorys", mainCategorys);
		
		// 全てのサブカテゴリーを取得
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		model.addAttribute("subCategories", subCategories);
		
		// 全てのタグを取得
		List<Tag> tags = tagService.getAllTags();
		model.addAttribute("tags", tags);

		return "admin/adminSubCategory";
	}
	

	// タグの新規作成または更新削除画面
	@GetMapping("/adminTag")
	public String showAdminTag(Model model, @RequestParam(required = false) Long tagId) {
		
		// Viewで更新処理を行うことを伝えるための変数
		boolean update = (tagId!=null);
		
		// 新規の場合
		if(!update) {
			// フォーム送信用Dto
			model.addAttribute("tagInputUpdateDto", new TagInputUpdateDto());
		// 更新の場合
		}else {
			// タグ情報を取得
			TagInputUpdateDto tagInputUpdateDto = tagService.getTagUpdateDeleteData(tagId);
			
			// 所属サブカテゴリーを取得
			Set<Long> selectSubCategoryIds = subCategoryService.getTagSubCategoryId(tagId);
			tagInputUpdateDto.setSubCategories(selectSubCategoryIds);
			
			model.addAttribute("tagInputUpdateDto", tagInputUpdateDto);
		}
		
		model.addAttribute("update", update);
		
		// 全てのサブカテゴリーを取得
		List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
		model.addAttribute("subCategories", subCategories);
		
		// 全てのタグを取得
		List<Tag> tags = tagService.getAllTags();
		model.addAttribute("tags", tags);

		return "admin/adminTag";
	}
	
}