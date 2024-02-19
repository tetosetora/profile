package com.example.demo.model;

public class ApplicationOverviewDto {

	private Long applicationId;
	private String applicationName;
	private String overview;
	private String startDate;

	public ApplicationOverviewDto(Long applicationId, String applicationName, String overview) {
		this.applicationId = applicationId;
		this.applicationName = applicationName;
		this.overview = overview;
	}
	
	public ApplicationOverviewDto(Long applicationId, String applicationName, String overview ,String startDate) {
		this.applicationId = applicationId;
		this.applicationName = applicationName;
		this.overview = overview;
		this.startDate = startDate;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getOverview() {
		return overview;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
