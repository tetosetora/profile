package com.example.demo.model;

public class SubCategoryCountDto {
	
    private Long subCategoryId;
    private String subCategoryName;
    private Long subCategoryApplicationCount;
    private Long subCategoryRecordCount;
    
	public SubCategoryCountDto(Long subCategoryId, String subCategoryName, Long subCategoryApplicationCount, Long subCategoryRecordCount) {
		this.subCategoryId = subCategoryId;
		this.subCategoryName = subCategoryName;
		this.subCategoryApplicationCount = subCategoryApplicationCount;
		this.subCategoryRecordCount = subCategoryRecordCount;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public Long getSubCategoryApplicationCount() {
		return subCategoryApplicationCount;
	}

	public Long getSubCategoryRecordCount() {
		return subCategoryRecordCount;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public void setSubCategoryApplicationCount(Long subCategoryApplicationCount) {
		this.subCategoryApplicationCount = subCategoryApplicationCount;
	}

	public void setSubCategoryRecordCount(Long subCategoryRecordCount) {
		this.subCategoryRecordCount = subCategoryRecordCount;
	}
	
}
    
