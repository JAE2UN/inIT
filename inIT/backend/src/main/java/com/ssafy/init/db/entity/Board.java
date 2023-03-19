package com.ssafy.init.db.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="board")
@EqualsAndHashCode(callSuper = false)
public class Board extends  BaseEntity {
    @Column(nullable = false)
    private int type;

    @Column(nullable = false, length = 250)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private boolean isInfo;

    @Column(nullable = false)
    private int likes_cnt;

    @Column(nullable = false)
    private int comment_cnt;

//    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    //@ToString.Exclude
    //    @JoinColumn(referencedColumnName = "id")
    @OneToOne
    @JoinColumn(name = "writer_id")
    private User user;
}
