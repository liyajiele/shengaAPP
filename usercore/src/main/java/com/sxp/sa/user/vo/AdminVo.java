package com.sxp.sa.user.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminVo extends BaseVo{

    @ApiModelProperty(value = "关联用户")
    private UserVo user;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty("token")
    private String token;

}
