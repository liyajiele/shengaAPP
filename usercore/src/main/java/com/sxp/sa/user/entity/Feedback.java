package com.sxp.sa.user.entity;

import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "tb_u_feedback")
@ApiModel(value = "用户反馈")
@Setter
@Getter
public class Feedback extends BaseEntity {

    @ApiModelProperty("反馈者")
    private User user;

    @ApiModelProperty("反馈类型 1.产品 ,2.账号,3.其他")
    private Integer type;

    @ApiModelProperty("反馈描述")
    private String typeDesc;

    @ApiModelProperty("反馈内容")
    private  String content;

    @ApiModelProperty("反馈图片地址 []")
    private String images;


    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Lob
    @Column(columnDefinition = "TEXT")
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
