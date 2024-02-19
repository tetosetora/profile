package com.example.demo.model;

public class RecordNameDto {

	private Long recordId;
	private String recordName;
	private String creationDate;

	public RecordNameDto(Long recordId, String recordName) {
		this.recordId = recordId;
		this.recordName = recordName;
	}
	
	public RecordNameDto(Long recordId, String recordName, String creationDate) {
		this.recordId = recordId;
		this.recordName = recordName;
		this.creationDate = creationDate;
	}

	public Long getRecordId() {
		return recordId;
	}

	public String getRecordName() {
		return recordName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
}
