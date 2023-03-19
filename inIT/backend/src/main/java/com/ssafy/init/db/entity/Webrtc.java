package com.ssafy.init.db.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@ApiModel(value = "Webrtc : 영상 정보", description = "Openvidu를 사용한 후 영상에 대한 정보를 담는다.")
@Data
@Entity
@Table(name="webrtc")
@EqualsAndHashCode(callSuper=false)
public class Webrtc extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @Column(nullable = false, length = 150)
    private String code;

}
