package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id // プライマリキー（一意に識別するためのキー）を示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動生成される値
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_introduction")
    private String userIntroduction;

    @Column(name = "future_challenges")
    private String futureChallenges;

}
