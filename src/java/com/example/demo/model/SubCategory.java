package com.example.demo.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "sub_categories")
public class SubCategory {

    @Id // プライマリキーを示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動生成される値
    @Column(name = "sub_category_id")
    private Long subCategoryId;

    @Column(name = "sub_category_name")
    private String subCategoryName;
    
    @ManyToOne
    @JoinColumn(name = "main_category_id")
    private MainCategory mainCategories;

    @OneToMany(mappedBy = "subCategoryId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ApplicationSubCategory> applicationSubCategories;
    
    @OneToMany(mappedBy = "subCategoryid", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecordSubCategory> recordSubCategories;
    
    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubCategoryTag> subCategoryTags;
    
}
