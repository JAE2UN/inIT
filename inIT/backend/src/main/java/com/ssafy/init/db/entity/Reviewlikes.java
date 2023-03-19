package com.ssafy.init.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@Data
@Entity
@Table(name="reviewlikes")
@EqualsAndHashCode(callSuper = false)
public class Reviewlikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    @JsonIgnore
    Review review;
}
