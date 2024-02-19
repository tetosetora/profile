package com.example.demo.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "records")
public class Record {

    @Id // プライマリキー（一意に識別するためのキー）を示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動生成される値
    @Column(name = "record_id")
    private Long recordId;

    @Column(name = "record_name")
    private String recordName;

    @Column(name = "record_genre")
    private String recordGenre;
    
    @Column(name = "creation_date")
    private String creationDate;
    
    @Column(name = "last_update_date")
    private String lastUpdateDate;
    
    @Column(name = "record_text")
    private String recordText;
    
    @Column(name = "reference_url")
    private String referenceUrl;
    
    @Column(name = "importance")
    private String importance;
    
    @Column(name = "visual_elements")
    private String visualElements;
    
    @OneToMany(mappedBy = "recordId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecordSubCategory> recordSubCategories;
    
    @OneToMany(mappedBy = "recordTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecordTag> recordTags;

}
