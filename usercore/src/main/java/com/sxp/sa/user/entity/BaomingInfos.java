package com.sxp.sa.user.entity;

import com.sxp.sa.basic.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_u_baoming")
@ApiModel(value = "报名信息")
@Getter@Setter
public class BaomingInfos extends BaseEntity{


    @ApiModelProperty("姓名")
    private String realname;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("qq")
    private String qq;

    @ApiModelProperty("email")
    private String email;

    @ApiModelProperty("wx")
    private String wx;

    @ApiModelProperty("渠道 1.oss bm 2.oss bm1")
    private Integer qudao;


}
