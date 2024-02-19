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
@Table(name = "application_sub_categories")
public class ApplicationSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_sub_category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application applicationId;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategoryId;

}
