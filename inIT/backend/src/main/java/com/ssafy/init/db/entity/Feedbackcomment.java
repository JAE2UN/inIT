package com.ssafy.init.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name="feedbackcomment")
@EqualsAndHashCode(callSuper = false)
public class Feedbackcomment extends BaseEntity {
    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private boolean pick;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne
    @JoinColumn(name = "feedback_id")
    Feedback feedback;
}
