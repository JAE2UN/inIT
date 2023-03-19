package com.ssafy.init.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="review")
@EqualsAndHashCode(callSuper = false)
public class Review extends BaseEntity {

    @Column(nullable = false, length = 250)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private int likes_cnt;

    @Column(nullable = false)
    private int comment_cnt;

    @OneToOne
    @JoinColumn(name = "realreport_id")
    private RealReport realReport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
