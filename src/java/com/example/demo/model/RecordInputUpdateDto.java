package com.example.demo.model;

import java.util.Set;

// 備忘録の作成および更新時の受け渡し用DTO

public class RecordInputUpdateDto {

    private Long recordId;
    private String recordName;
    private String recordGenre;
    private String creationDate;
    private String recordText;
    private String referenceUrl;
    private String importance;
    private String visualElements;

    private Set<Long> subCategories;
    private Set<Long> tags;

    public RecordInputUpdateDto() {}
    
    // コンストラクタ（基本フィールド用）
    public RecordInputUpdateDto(Long recordId, String recordName, String recordGenre, String creationDate,
    							String recordText, String referenceUrl, String importance, String visualElements) {
		this.recordId = recordId;
		this.recordName = recordName;
		this.recordGenre = recordGenre;
		this.creationDate = creationDate;
		this.recordText = recordText;
		this.referenceUrl = referenceUrl;
		this.importance = importance;
		this.visualElements = visualElements;
	}

    // コンストラクタ（全フィールド用）
    public RecordInputUpdateDto(Long recordId, String recordName, String recordGenre, String creationDate,
                                String recordText, String referenceUrl, String importance, String visualElements,
                                Set<Long> subCategories, Set<Long> tags) {
        this.recordId = recordId;
        this.recordName = recordName;
        this.recordGenre = recordGenre;
        this.creationDate = creationDate;
        this.recordText = recordText;
        this.referenceUrl = referenceUrl;
        this.importance = importance;
        this.visualElements = visualElements;
        this.subCategories = subCategories;
        this.tags = tags;
    }

	public Long getRecordId() {
		return recordId;
	}

	public String getRecordName() {
		return recordName;
	}

	public String getRecordGenre() {
		return recordGenre;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getRecordText() {
		return recordText;
	}

	public String getReferenceUrl() {
		return referenceUrl;
	}

	public String getImportance() {
		return importance;
	}

	public String getVisualElements() {
		return visualElements;
	}

	public Set<Long> getSubCategories() {
		return subCategories;
	}

	public Set<Long> getTags() {
		return tags;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public void setRecordGenre(String recordGenre) {
		this.recordGenre = recordGenre;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setRecordText(String recordText) {
		this.recordText = recordText;
	}

	public void setReferenceUrl(String referenceUrl) {
		this.referenceUrl = referenceUrl;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public void setVisualElements(String visualElements) {
		this.visualElements = visualElements;
	}

	public void setSubCategories(Set<Long> subCategories) {
		this.subCategories = subCategories;
	}

	public void setTags(Set<Long> tags) {
		this.tags = tags;
	}
    
}
