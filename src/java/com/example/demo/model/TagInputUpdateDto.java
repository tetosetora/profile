package com.example.demo.model;

import java.util.Set;

// タグの作成および更新時の受け渡し用DTO

public class TagInputUpdateDto {

    private Long tagId;
    private String tagName;

    private Set<Long> subCategories;

    public TagInputUpdateDto() {}
    
    // コンストラクタ（基本フィールド用）
    public TagInputUpdateDto(Long tagId, String tagName) {
		this.tagId = tagId;
		this.tagName = tagName;
	}

    // コンストラクタ（全フィールド用）
    public TagInputUpdateDto(Long tagId, String tagName, Set<Long> subCategories) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.subCategories = subCategories;
    }

	public Long getTagId() {
		return tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public Set<Long> getSubCategories() {
		return subCategories;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public void setSubCategories(Set<Long> subCategories) {
		this.subCategories = subCategories;
	}
    
}
