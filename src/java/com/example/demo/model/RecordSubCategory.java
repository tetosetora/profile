package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "record_sub_categories")
public class RecordSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_subCategory_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recordId")
    private Record recordId;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategoryid;

}
