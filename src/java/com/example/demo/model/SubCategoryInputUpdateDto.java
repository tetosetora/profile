package com.example.demo.model;

import java.util.Set;

// タグの作成および更新時の受け渡し用DTO

public class SubCategoryInputUpdateDto {

    private Long subCategoryId;
    private String subCategoryName;
    
    private Long mainCategoryId;
    private Set<Long> tags;

    public SubCategoryInputUpdateDto() {}
    
    // コンストラクタ（基本フィールド用）
    public SubCategoryInputUpdateDto(Long subCategoryId, String subCategoryName, Long mainCategoryId) {
		this.subCategoryId = subCategoryId;
		this.subCategoryName = subCategoryName;
        this.mainCategoryId = mainCategoryId;
	}

    // コンストラクタ（全フィールド用）
    public SubCategoryInputUpdateDto(Long subCategoryId, String subCategoryName, Long mainCategoryId, Set<Long> tags) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.mainCategoryId = mainCategoryId;
        this.tags = tags;
    }

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public Long getMainCategoryId() {
		return mainCategoryId;
	}

	public Set<Long> getTags() {
		return tags;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public void setMainCategoryId(Long mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}

	public void setTags(Set<Long> tags) {
		this.tags = tags;
	}
    
}
