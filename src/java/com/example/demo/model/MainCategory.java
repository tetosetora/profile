package com.example.demo.model;

import java.util.Set;

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
@Table(name = "main_categories")
public class MainCategory {

    @Id // プライマリキー（一意に識別するためのキー）を示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動生成される値
    @Column(name = "main_category_id")
    private Long mainCategoryId;

    @Column(name = "main_category_name")
    private String mainCategoryName;
    
    @OneToMany(mappedBy = "mainCategories")
    private Set<SubCategory> subCategories;
    
}
