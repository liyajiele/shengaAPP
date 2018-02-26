package com.sxp.sa.api.controller.user;

import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.basic.utils.Rst;
import com.sxp.sa.user.entity.DrawChannel;
import com.sxp.sa.user.service.DrawChannelService;
import com.sxp.sa.user.service.DrawService;
import com.sxp.sa.user.vo.DrawChannelVo;
import com.sxp.sa.user.vo.DrawVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Api(value = "用户-提现接口", description = "用户-提现接口")
@RestController
@RequestMapping(value = "/api/user/draw", method = RequestMethod.POST)
public class ApiDrawController {

    @Autowired
    private DrawChannelService drawChannelService;



    @Autowired
    private DrawService drawService;

    /**
     * 保存提现渠道
     *
     * @param uid
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/authc/saveAliPayDrawChannel", method = RequestMethod.POST)
    @ApiOperation(value = "保存用户支付宝提现渠道", httpMethod = "POST", notes = "保存用户支付宝提现渠道")
    public Rst<DrawChannelVo> saveAliPayDrawChannel(
            @ApiParam(value = "用户id", required = true) @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "提现联系人", required = true) @RequestParam(required = true, value = "concatUser") String concatUser,
            @ApiParam(value = "提现联系人电话", required = true) @RequestParam(required = true, value = "concatMobile") String concatMobile,
            @ApiParam(value = "支付宝账号", required = true) @RequestParam(required = true, value = "aliPay") String aliPay
    ) throws BusinessException {
        Rst<DrawChannelVo> rst = new Rst<DrawChannelVo>();
        DrawChannel drawChannel = new DrawChannel();
        drawChannel.setConcatUser(concatUser);
        drawChannel.setConcatMobile(concatMobile);
        drawChannel.setChannelType(Const.DrawChannelType.ALI_PAY);
        drawChannel.setAliPay(aliPay);

        DrawChannelVo drawChannelVo = drawChannelService.save(uid,drawChannel);
        rst.setObject(drawChannelVo);
        return rst;
    }

    /**
     * 保存用户银行卡提现渠道
     * @param uid
     * @param concatUser
     * @param concatMobile
     * @param cardNum
     * @param bankName
     * @param bankZh
     * @param cardMobile
     * @param cardOwner
     * @param cardOwnerID
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/authc/saveCardDrawChannel", method = RequestMethod.POST)
    @ApiOperation(value = "保存用户银行卡提现渠道", httpMethod = "POST", notes = "保存用户银行卡提现渠道")
    public Rst<DrawChannelVo> saveCardDrawChannel(
            @ApiParam(value = "用户id", required = true) @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "提现联系人", required = true) @RequestParam(required = true, value = "concatUser") String concatUser,
            @ApiParam(value = "提现联系人电话", required = true) @RequestParam(required = true, value = "concatMobile") String concatMobile,
            @ApiParam(value = "银行卡号", required = true) @RequestParam(required = true, value = "cardNum") String cardNum,
            @ApiParam(value = "银行名", required = true) @RequestParam(required = true, value = "bankName") String bankName,
            @ApiParam(value = "支行名", required = true) @RequestParam(required = true, value = "bankZh") String bankZh,
            @ApiParam(value = "开卡手机号", required = true) @RequestParam(required = true, value = "cardMobile") String cardMobile,
            @ApiParam(value = "开卡人姓名", required = true) @RequestParam(required = true, value = "ardOwner") String cardOwner,
            @ApiParam(value = "开卡人身份证", required = true) @RequestParam(required = true, value = "cardOwnerID") String cardOwnerID
    ) throws BusinessException {


        Rst<DrawChannelVo> rst = new Rst<DrawChannelVo>();
        DrawChannel drawChannel = new DrawChannel();
        drawChannel.setConcatUser(concatUser);
        drawChannel.setConcatMobile(concatMobile);
        drawChannel.setChannelType(Const.DrawChannelType.CARD_NUM);
        drawChannel.setCardNum(cardNum);
        drawChannel.setBankName(bankName);
        drawChannel.setBankZh(bankZh);
        drawChannel.setCardMobile(cardMobile);
        drawChannel.setCardOwner(cardOwner);
        drawChannel.setCardOwnerID(cardOwnerID);

        DrawChannelVo drawChannelVo = drawChannelService.save(uid,drawChannel);
        rst.setObject(drawChannelVo);
        return rst;
    }
    /**
     * 根据提现渠道id 删除提现方式
     * @param id
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/authc/deleteDrawChannel", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户提现渠道", httpMethod = "POST", notes = "删除用户提现渠道")
    public Rst<DrawChannelVo> deleteDrawChannel(
            @ApiParam(value = "用户id", required = true) @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "提现渠道id", required = true) @RequestParam(required = true, value = "id") Long id
    ) throws BusinessException {
        Rst<DrawChannelVo> rst = new Rst<DrawChannelVo>();
        DrawChannelVo drawChannelVo=drawChannelService.delete(uid,id);
        rst.setObject(drawChannelVo);
        return rst;
    }

    /**
     * 显示用户所有的提现渠道
     * @param uid
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/authc/queryUserAllDrawChannel", method = RequestMethod.POST)
    @ApiOperation(value = "显示用户所有的提现渠道", httpMethod = "POST", notes = "显示用户所有的提现渠道")
    public Rst<List<DrawChannelVo>> queryUserAllDrawChannel(
            @ApiParam(value = "用户id", required = true) @RequestParam(required = true, value = "uid") Long uid
    ) throws BusinessException {
        Rst<List<DrawChannelVo>> rst = new Rst<List<DrawChannelVo>>();
        List<DrawChannelVo> drawChannelVoList=drawChannelService.queryUserAllDrawChannel(uid);
        rst.setObject(drawChannelVoList);
        return rst;
    }

    @RequestMapping(value = "/authc/appylForDraw", method = RequestMethod.POST)
    @ApiOperation(value = "申请提现", httpMethod = "POST", notes = "申请提现")
    public Rst<DrawVo> appylForDraw(
            @ApiParam(value = "用户id", required = true) @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "提现金额", required = true) @RequestParam(required = true, value = "drawNum") Double drawNum,
            @ApiParam(value = "提款渠道id", required = true) @RequestParam(required = true, value = "drawCId") Long drawCId
    ) throws BusinessException {
        Rst<DrawVo> rst = new Rst<DrawVo>();

        if(drawNum<=0){
            throw new BusinessException(Const.Code.ERROR,"参数异常");
        }

        DrawVo drawVo=drawService.appylForDraw(uid,drawCId,drawNum);
        rst.setObject(drawVo);
        return rst;
    }

    @RequestMapping(value = "/authc/appylForDrawWithoutChannelId", method = RequestMethod.POST)
    @ApiOperation(value = "申请提现(无渠道id时使用)", httpMethod = "POST", notes = "申请提现(无渠道id时使用)")
    public Rst<DrawVo> appylForDraw(
            @ApiParam(value = "用户id", required = true) @RequestParam(required = true, value = "uid") Long uid,
            @ApiParam(value = "提现金额", required = true) @RequestParam(required = true, value = "drawNum") Double drawNum,
            @ApiParam(value = "支付宝账户", required = true) @RequestParam(required = true, value = "alipay") String  alipay,
            @ApiParam(value = "支付宝账户", required = true) @RequestParam(required = true, value = "concatUser") String  concatUser
    ) throws BusinessException {
        Rst<DrawVo> rst = new Rst<DrawVo>();

        if(drawNum<=0){
            throw new BusinessException(Const.Code.ERROR,"参数异常");
        }

       DrawChannel drawChannel = drawService.getDrawChannel(uid,alipay,concatUser);

        DrawVo drawVo=drawService.appylForDraw(uid,drawChannel.getId(),drawNum);
        rst.setObject(drawVo);
        return rst;
    }



    @RequestMapping(value = "/authc/queryDrawPage" ,method=RequestMethod.POST)
    @ApiOperation(value = "提现列表分页查询" , httpMethod = "POST", notes = "提现列表分页查询")
    public Rst<Pager<DrawVo>> queryDefaultInAddress(
            @ApiParam(value="用户id" ,required = true) @RequestParam(required = true,value = "uid") Long uid,
            @PageableDefault(page = 0,size = 30) Pageable pageable,
            HttpServletRequest request
    )throws BusinessException {
        Rst<Pager<DrawVo>> rst =  new Rst<>();
        rst.setObject(drawService.findDrawByPage(uid,pageable));
        return rst;
    }

}
