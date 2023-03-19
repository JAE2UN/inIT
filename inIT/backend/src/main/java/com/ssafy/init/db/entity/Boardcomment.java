package com.ssafy.init.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "boardcomment")
@EqualsAndHashCode(callSuper = false)
public class Boardcomment extends BaseEntity {
    @Column(nullable = false, length = 500)
    private String content;

    @OneToOne
    @JoinColumn(name = "user2_id")
    User user;

    @OneToOne
    @JoinColumn(name = "board2_id")
    Board board;
}
