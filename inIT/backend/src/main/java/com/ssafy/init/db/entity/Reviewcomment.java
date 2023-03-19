package com.ssafy.init.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@Data
@Entity
@Table(name = "reviewcomment")
@EqualsAndHashCode(callSuper = false)
public class Reviewcomment extends BaseEntity {
    @Column(nullable = false, length = 500)
    private String content;

    @OneToOne
    @JoinColumn(name = "user2_id")
    User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review2_id")
    @JsonIgnore
    Review review;
}
