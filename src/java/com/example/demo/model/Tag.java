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
@Table(name = "tags")
public class Tag {

    @Id // プライマリキー（一意に識別するためのキー）を示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動生成される値
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tag_name")
    private String tagName;
    
    @OneToMany(mappedBy = "tagApp", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ApplicationTag> applicationTags;
    
    @OneToMany( mappedBy = "tagRec",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecordTag> recordTags;
    
    @OneToMany(mappedBy = "tag",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubCategoryTag> subCategoryTags;

}
