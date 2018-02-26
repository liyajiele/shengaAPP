package com.sxp.sa.merchant.vo;

import com.sxp.sa.basic.entity.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantVo extends BaseVo{

    @ApiModelProperty(value = "店名", notes = "店名")
    private String nickname;

    @ApiModelProperty(value = "星级", notes = "星级")
    private String stars;

    @ApiModelProperty(value = "地址", notes = "地址")
    private String addr;

    @ApiModelProperty(value = "主图", notes = "主图")
    private String mainImage;

    @ApiModelProperty("距离")
    private Double distance;

    public MerchantVo(){}

    public MerchantVo(Long id,Long createTime,String nickname,String starts,String attr,String mainImage,Double distance){
        this.setId(id);
        this.setCreateTime(createTime);
        this.nickname = nickname;
        this.stars = starts;
        this.addr = attr;
        this.mainImage = mainImage;
        this.distance = distance;
    }
}
