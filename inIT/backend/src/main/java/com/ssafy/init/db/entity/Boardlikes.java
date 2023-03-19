package com.ssafy.init.db.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name="boardlikes")
@EqualsAndHashCode(callSuper = false)
public class Boardlikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne
    @JoinColumn(name = "board_id")
    Board board;
}
