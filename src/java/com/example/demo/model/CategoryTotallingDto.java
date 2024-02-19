package com.example.demo.model;

import java.util.Set;

public class CategoryTotallingDto {

    private Long mainCategoryId;
    private String mainCategoryName;
    private Set<SubCategoryCountDto> subCategoryCountDto;

	public CategoryTotallingDto(Long mainCategoryId, String mainCategoryName) {
		this.mainCategoryId = mainCategoryId;
		this.mainCategoryName = mainCategoryName;
	}
	
	public CategoryTotallingDto(Long mainCategoryId, String mainCategoryName, Set<SubCategoryCountDto> subCategoryCountDto) {
		this.mainCategoryId = mainCategoryId;
		this.mainCategoryName = mainCategoryName;
		this.subCategoryCountDto = subCategoryCountDto;
	}

	public Long getMainCategoryId() {
		return mainCategoryId;
	}

	public String getMainCategoryName() {
		return mainCategoryName;
	}

	public Set<SubCategoryCountDto> getSubCategoryCountDto() {
		return subCategoryCountDto;
	}

	public void setMainCategoryId(Long mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}

	public void setMainCategoryName(String mainCategoryName) {
		this.mainCategoryName = mainCategoryName;
	}

	public void setSubCategoryCountDto(Set<SubCategoryCountDto> subCategoryCountDto) {
		this.subCategoryCountDto = subCategoryCountDto;
	}
	
}
