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
@Table(name = "record_tags")
public class RecordTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_tag_id")
    private Long recordTagId;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record recordTag;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tagRec;

}