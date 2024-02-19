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
@Table(name = "application_tags")
public class ApplicationTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_tag_id")
    private Long applicationTagId;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application appTag;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tagApp;

}