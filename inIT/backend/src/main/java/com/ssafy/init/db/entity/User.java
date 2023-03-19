package com.ssafy.init.db.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@ApiModel(value = "User : 회원 정보" , description = "회원 정보를 담는다.")
@Data
@Entity
@Table(name="user")
@EqualsAndHashCode(callSuper=false)
@DynamicInsert
public class User extends BaseEntity {
    @ApiModelProperty(value = "이메일(로그인 시 사용), Null 허용x, Unique")
    @Column(nullable = false, length = 50)
    private String email;

    @ApiModelProperty(value = "닉네임, Null 허용x, Unique")
    @Column(nullable = false, length = 40)
    private String nick;

    @ApiModelProperty(value = "프로필 이미지 주소, Null 허용")
    @Column(length = 350)
    private String imgUrl;

    @ApiModelProperty(value = "비밀번호, Null 허용x")
    @Column(nullable = false, length = 250)
    private String pw;

    @ApiModelProperty(value = "상태 메시지, Null 허용")
    @Column(length = 50)
    private String statusMsg;

    @ApiModelProperty(value = "등급, default: 1")
    @ColumnDefault("1")
    private int grade;

    @ApiModelProperty(value = "온도, default: 36")
    @ColumnDefault("36")
    private int temperature;
}
