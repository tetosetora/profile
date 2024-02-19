package com.example.demo.model;

import java.util.Set;

// アプリケーション情報の作成および更新時の受け渡し用DTO

public class ApplicationInputUpdateDto {

    private Long applicationId;
    private String applicationName;
    private String applicationNameEn;
    private String overview;
    private String requirements;
    private String startDate;
    private int duration;
    private String responsibility;
    private String challenges;
    private String futureVision;
    private String status;

    private Set<Long> subCategories;
    private Set<Long> tags;

    public ApplicationInputUpdateDto() {}

    // コンストラクタ（基本フィールド用）
    public ApplicationInputUpdateDto(Long applicationId, String applicationName, String applicationNameEn, 
                                     String overview, String requirements, String startDate, int duration, 
                                     String responsibility, String challenges, String futureVision, String status) {
        this.applicationId = applicationId;
        this.applicationName = applicationName;
        this.applicationNameEn = applicationNameEn;
        this.overview = overview;
        this.requirements = requirements;
        this.startDate = startDate;
        this.duration = duration;
        this.responsibility = responsibility;
        this.challenges = challenges;
        this.futureVision = futureVision;
        this.status = status;
    }

    // コンストラクタ（全フィールド用）
    public ApplicationInputUpdateDto(Long applicationId, String applicationName, String applicationNameEn, 
                                     String overview, String requirements, String startDate, int duration, 
                                     String responsibility, String challenges, String futureVision, 
                                     String status, Set<Long> subCategories, Set<Long> tags) {
        this.applicationId = applicationId;
        this.applicationName = applicationName;
        this.applicationNameEn = applicationNameEn;
        this.overview = overview;
        this.requirements = requirements;
        this.startDate = startDate;
        this.duration = duration;
        this.responsibility = responsibility;
        this.challenges = challenges;
        this.futureVision = futureVision;
        this.status = status;
        this.subCategories = subCategories;
        this.tags = tags;
    }

	public Long getApplicationId() {
		return applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getApplicationNameEn() {
		return applicationNameEn;
	}

	public String getOverview() {
		return overview;
	}

	public String getRequirements() {
		return requirements;
	}

	public String getStartDate() {
		return startDate;
	}

	public int getDuration() {
		return duration;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public String getChallenges() {
		return challenges;
	}

	public String getFutureVision() {
		return futureVision;
	}

	public String getStatus() {
		return status;
	}

	public Set<Long> getSubCategories() {
		return subCategories;
	}

	public Set<Long> getTags() {
		return tags;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void setApplicationNameEn(String applicationNameEn) {
		this.applicationNameEn = applicationNameEn;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public void setChallenges(String challenges) {
		this.challenges = challenges;
	}

	public void setFutureVision(String futureVision) {
		this.futureVision = futureVision;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSubCategories(Set<Long> subCategories) {
		this.subCategories = subCategories;
	}

	public void setTags(Set<Long> tags) {
		this.tags = tags;
	}

}

