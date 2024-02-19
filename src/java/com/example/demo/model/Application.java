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
@Table(name = "applications")
public class Application {

    @Id // プライマリキー（一意に識別するためのキー）を示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動生成される値
    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "application_name")
    private String applicationName;

    @Column(name = "application_name_en")
    private String applicationNameEn;

    @Column(name = "overview")
    private String overview;
    
    @Column(name = "requirements")
    private String requirements;
    
    @Column(name = "start_date")
    private String startDate;
    
    @Column(name = "duration")
    private int duration;
    
    @Column(name = "responsibility")
    private String responsibility;
    
    @Column(name = "challenges")
    private String challenges;
    
    @Column(name = "future_vision")
    private String futureVision;
    
    @Column(name = "update_date")
    private String updateDate;
    
    @Column(name = "status")
    private String status;
    
    @OneToMany(mappedBy = "applicationId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ApplicationSubCategory> applicationSubCategories;
    
    @OneToMany(mappedBy = "appTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ApplicationTag> applicationTags;
    
}

