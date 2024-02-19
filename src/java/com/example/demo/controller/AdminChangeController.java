package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.AdminResult;
import com.example.demo.model.ApplicationInputUpdateDto;
import com.example.demo.model.MainCategory;
import com.example.demo.model.RecordInputUpdateDto;
import com.example.demo.model.SubCategoryInputUpdateDto;
import com.example.demo.model.TagInputUpdateDto;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.CategoryTotallingService;
import com.example.demo.service.RecordService;
import com.example.demo.service.SubCategoryService;
import com.example.demo.service.TagService;
import com.example.demo.service.UserService;

@Controller
public class AdminChangeController {
	
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

	// 備忘録の作成と更新
	@PostMapping("/createUpdateRecord")
	public String createUpdateRecord(@ModelAttribute RecordInputUpdateDto recordInputUpdateDto,
			RedirectAttributes redirectAttributes,
			@RequestParam("password") String password) {
		
        if ("任意のパスワード".equals(password)) {
    		AdminResult result = recordService.InsertUpdateRecordData(recordInputUpdateDto);

    	    redirectAttributes.addFlashAttribute("message", result.getMessage());
        }else {
        	redirectAttributes.addFlashAttribute("message", "パスワードが間違っています");
        }
	      
	    // ファームの再送信を防ぐためリダイレクトを使用する
	    return "redirect:/adminResultPage";
	}
	
	// 備忘録の削除
	@PostMapping("/deleteRecord")
	public String deleteRecord(@RequestParam Long recordId,
			RedirectAttributes redirectAttributes,
			@RequestParam("password") String password) {
		
        if ("任意のパスワード".equals(password)) {
    		AdminResult result = recordService.deleteRecordData(recordId);

    	    redirectAttributes.addFlashAttribute("message", result.getMessage());
        }else {
        	redirectAttributes.addFlashAttribute("message", "パスワードが間違っています");
        }
	      
	    // ファームの再送信を防ぐためリダイレクトを使用する
	    return "redirect:/adminResultPage";
	}
	
	
	// アプリケーション情報の作成と更新
	@PostMapping("/createUpdateApplication")
	public String createUpdateApplication(@ModelAttribute ApplicationInputUpdateDto applicationInputUpdateDto, 
			RedirectAttributes redirectAttributes,
			@RequestParam("password") String password) {
		
        if ("任意のパスワード".equals(password)) {
    		AdminResult result = applicationService.InsertUpdateApplicationData(applicationInputUpdateDto);

    	    redirectAttributes.addFlashAttribute("message", result.getMessage());
        } else {
        	redirectAttributes.addFlashAttribute("message", "パスワードが間違っています");
        }
	      
	    // ファームの再送信を防ぐためリダイレクトを使用する
	    return "redirect:/adminResultPage";
	}
	
	// アプリケーション情報の削除
	@PostMapping("/deleteApplication")
	public String deleteApplication(@RequestParam Long applicationId,
			RedirectAttributes redirectAttributes,
			@RequestParam("password") String password) {
		
        if ("任意のパスワード".equals(password)) {
    		AdminResult result = applicationService.deleteApplicationData(applicationId);

    	    redirectAttributes.addFlashAttribute("message", result.getMessage());
        }else {
        	redirectAttributes.addFlashAttribute("message", "パスワードが間違っています");
        }
	      
	    // ファームの再送信を防ぐためリダイレクトを使用する
	    return "redirect:/adminResultPage";
	}
	
	
	// ユーザー情報の更新
	@PostMapping("/updateUser")
	public String updateUser(@RequestParam String userIntroduction, 
			@RequestParam String futureChallenges, 
			RedirectAttributes redirectAttributes,
			@RequestParam("password") String password) {
		
        if ("任意のパスワード".equals(password)) {
    		AdminResult result = userService.UpdateUserData(userIntroduction, futureChallenges);

    	    redirectAttributes.addFlashAttribute("message", result.getMessage());
        }else {
        	redirectAttributes.addFlashAttribute("message", "パスワードが間違っています");
        }
	      
	    // ファームの再送信を防ぐためリダイレクトを使用する
	    return "redirect:/adminResultPage";
	}
	
	
	// メインカテゴリーの更新
	@PostMapping("/createUpdateMainCategory")
	public String createUpdateMainCategory(@ModelAttribute MainCategory mainCategory, 
			RedirectAttributes redirectAttributes,
			@RequestParam("password") String password) {
		
        if ("任意のパスワード".equals(password)) {
    		AdminResult result = categoryTotallingService.InsertUpdateMainCategoryData(mainCategory);

    	    redirectAttributes.addFlashAttribute("message", result.getMessage());
        }else {
        	redirectAttributes.addFlashAttribute("message", "パスワードが間違っています");
        }
	      
	    // ファームの再送信を防ぐためリダイレクトを使用する
	    return "redirect:/adminResultPage";
	}
	
	
	// サブカテゴリーの作成と更新
	@PostMapping("/createUpdateSubCategory")
	public String createUpdateSubCategory(@ModelAttribute SubCategoryInputUpdateDto subCategoryInputUpdateDto, 
			RedirectAttributes redirectAttributes,
			@RequestParam("password") String password) {
		
        if ("任意のパスワード".equals(password)) {
    		AdminResult result = subCategoryService.InsertUpdateSubCategoryData(subCategoryInputUpdateDto);

    	    redirectAttributes.addFlashAttribute("message", result.getMessage());
        }else {
        	redirectAttributes.addFlashAttribute("message", "パスワードが間違っています");
        }
	      
	    // ファームの再送信を防ぐためリダイレクトを使用する
	    return "redirect:/adminResultPage";
	}
	
	// サブカテゴリーの削除
	@PostMapping("/deleteSubCategory")
	public String deleteSubCategory(@RequestParam Long subCategoryId, 
			RedirectAttributes redirectAttributes,
			@RequestParam("password") String password) {
        if ("任意のパスワード".equals(password)) {
    		AdminResult result = subCategoryService.deleteSubCategoryData(subCategoryId);

    	    redirectAttributes.addFlashAttribute("message", result.getMessage());
        }else {
        	redirectAttributes.addFlashAttribute("message", "パスワードが間違っています");
        }
	      
	    // ファームの再送信を防ぐためリダイレクトを使用する
	    return "redirect:/adminResultPage";
	}
	
	
	// タグの作成と更新
	@PostMapping("/createUpdateTag")
	public String createUpdateTag(@ModelAttribute TagInputUpdateDto tagInputUpdateDto, 
			RedirectAttributes redirectAttributes,
			@RequestParam("password") String password) {
		
        if ("任意のパスワード".equals(password)) {
    		AdminResult result = tagService.InsertUpdateTagData(tagInputUpdateDto);

    	    redirectAttributes.addFlashAttribute("message", result.getMessage());
        }else {
        	redirectAttributes.addFlashAttribute("message", "パスワードが間違っています");
        }
	      
	    // ファームの再送信を防ぐためリダイレクトを使用する
	    return "redirect:/adminResultPage";
	}
	
	// タグの削除
	@PostMapping("/deleteTag")
	public String deleteTag(@RequestParam Long tagId, 
			RedirectAttributes redirectAttributes,
			@RequestParam("password") String password) {
		
        if ("任意のパスワード".equals(password)) {
    		AdminResult result = tagService.deleteTagData(tagId);

    	    redirectAttributes.addFlashAttribute("message", result.getMessage());
        }else {
        	redirectAttributes.addFlashAttribute("message", "パスワードが間違っています");
        }
	      
	    // ファームの再送信を防ぐためリダイレクトを使用する
	    return "redirect:/adminResultPage";
	}
	
}
