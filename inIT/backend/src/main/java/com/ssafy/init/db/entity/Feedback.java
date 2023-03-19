package com.ssafy.init.db.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name="feedback")
@EqualsAndHashCode(callSuper = false)
public class Feedback extends BaseEntity {
    @Column(nullable = false, length = 250)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private int comment_cnt;

    @Column(nullable = false, length = 150)
    private String video_url;

    @OneToOne
    @JoinColumn(name = "writer_id")
    private User user;
}