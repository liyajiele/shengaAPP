package com.sxp.sa.api.controller.user;

import com.github.sd4324530.fastweixin.api.MenuAPI;
import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.entity.Menu;
import com.github.sd4324530.fastweixin.api.entity.MenuButton;
import com.github.sd4324530.fastweixin.api.enums.MenuType;
import com.github.sd4324530.fastweixin.api.enums.OauthScope;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.github.sd4324530.fastweixin.message.req.MenuEvent;
import com.github.sd4324530.fastweixin.message.req.QrCodeEvent;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.servlet.WeixinControllerSupport;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.constant.WxConst;
import com.sxp.sa.basic.exception.BusinessException;
import com.sxp.sa.user.entity.User;
import com.sxp.sa.user.service.ThirdInfoService;
import com.sxp.sa.user.service.UserService;
import com.sxp.sa.user.vo.ThirdInfoVo;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.sxp.sa.basic.utils.Util.isEmpty;
import static com.sxp.sa.basic.utils.Util.isNotEmpty;


/**
 * Created by Administrator on 2017/2/18.
 */

@Controller
@RequestMapping("wx/user")
public class WxUserController extends WeixinControllerSupport{


    @Autowired
    private ApiConfig apiConfig;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThirdInfoService thirdInfoService;

    @Autowired
    private UserService userService;





    @Override
    protected String getToken() {
        return WxConst.UserWeixinAccount.TOEKN;
    }



    private BaseMsg commonMsg(){
        return null;
//        String title = "感谢关注";
//        String desc = "有问题请直接找客服。如果咨询量大，请耐心等候！";
//        String picUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490346518856&di=59b06fd03066cbea8f3495da8bc619a3&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3D67440d2e0ed162d985bb6a1824ef85da%2F5366d0160924ab18f267a2dd31fae6cd7a890be2.jpg";
//        String url = "";
//
//        String redisTitle = (String)redisTemplate.opsForValue().get(WxConst.YiWeixinAccount.BASEMSG_TITLE);
//        String redisDesc = (String)redisTemplate.opsForValue().get(WxConst.YiWeixinAccount.BASEMSG_DESC);
//        String redisPicUrl = (String)redisTemplate.opsForValue().get(WxConst.YiWeixinAccount.BASEMSG_PICURL);
//        String redisUrl = (String)redisTemplate.opsForValue().get(WxConst.YiWeixinAccount.BASEMSG_URL);
//        if(isNotEmpty(redisTitle)){
//            title = redisTitle;
//        }
//        if(isNotEmpty(redisDesc)){
//            desc = redisDesc;
//        }
//        if(isNotEmpty(redisPicUrl)){
//            picUrl = redisPicUrl;
//        }
//        if(isNotEmpty(redisUrl)){
//            url = redisUrl;
//        }
//        Article subArticle = new Article(title,desc,picUrl,url);
//        NewsMsg newsMsg = new NewsMsg();
//        newsMsg.add(subArticle);
//        return newsMsg;

    }

    /**
     * 入口
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public void wxLogin(
            HttpServletResponse response
    ) throws IOException {
        OauthAPI oauthAPI = new OauthAPI(apiConfig);
        String pageUrl = oauthAPI.getOauthPageUrl(WxConst.UserWeixinAccount.OAUTH_CBURL, OauthScope.SNSAPI_USERINFO, WxConst.UserWeixinAccount.USER);
        response.sendRedirect(pageUrl);
    }

    /**
     * 处理添加关注事件，有需要时子类重写
     *
     * @param event 添加关注事件对象
     * @return 响应消息对象
     */
    @Override
    protected BaseMsg handleSubscribe(BaseEvent event) {
        //用户openId
        String openId = event.getFromUserName();
        //获取用户信息
        UserAPI userAPI = new UserAPI(apiConfig);
        GetUserInfoResponse userInfo = userAPI.getUserInfo(openId);



        //如果查询不到用户三方信息
        Long thirdInfoId = thirdInfoService.isExists(openId, Const.ThirdType.WX);

        ThirdInfoVo thirdInfo = this.regHelper(event,thirdInfoId,userInfo);

        return commonMsg();
    }

    //注册或更新三方信息
    private ThirdInfoVo regHelper(BaseEvent event,Long thirdInfoId,GetUserInfoResponse userInfo){
        Pattern emoji = Pattern. compile (
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]" ,
                Pattern . UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
        String nickname = emoji.matcher(userInfo.getNickname()).replaceAll("");
        if(isEmpty(thirdInfoId)){
            //保存三方信息
            try {

                User user = thirdInfoService.getEmptyOrLinkUser(userInfo.getUnionid(),userInfo.getHeadimgurl(),nickname);
                //扫描代餐二维码
                if(isNotEmpty(event) && event instanceof QrCodeEvent && isNotEmpty(((QrCodeEvent) event).getEventKey())){
                    //获取到了 旧用户的 openId
                    String parentId = ((QrCodeEvent) event).getEventKey().substring(8);
                    //绑定上级
                    user = userService.bindParent(user,Long.parseLong(parentId));
                }
                return thirdInfoService.addThirdInfo(user,nickname,userInfo.getHeadimgurl(),userInfo.getGroupid(),Const.ThirdType.WX,userInfo.getOpenid(),userInfo.getUnionid(),WxConst.UserWeixinAccount.USER , userInfo.getSubscribe());

            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }else{
            //更新三方信息
            try {
                return thirdInfoService.updateThirdInfo(thirdInfoId,null,nickname,userInfo.getHeadimgurl(),userInfo.getGroupid(),Const.ThirdType.WX,userInfo.getOpenid(),userInfo.getUnionid(),WxConst.UserWeixinAccount.USER , userInfo.getSubscribe());
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param event 扫描二维码事件对象
     * @return
     */
    @Override
    protected BaseMsg handleQrCodeEvent(QrCodeEvent event) {
        return super.handleQrCodeEvent(event);
    }

    /**
     * 处理文本消息，有需要时子类重写
     *
     * @param msg 请求消息对象
     * @return 响应消息对象
     */
    protected BaseMsg handleTextMsg(TextReqMsg msg) {
        //TODO 3级需求 所有信息保存数据库 ,做到管理员后台修改后,回复直接改变
        //TODO 3级需求 匹配不同的关键字 回复不同的内容
        return commonMsg();
    }






    @RequestMapping(value = "/wxLogin",method = RequestMethod.GET)
    public void getToken(
            @ApiParam(value = "获取的授权code")@RequestParam String code,
            @ApiParam(value = "state")@RequestParam String state,
            HttpServletResponse response
    ) throws Exception {
        OauthAPI api = new OauthAPI(apiConfig);
        //根据 code 获取 access_token 和 openid
        OauthGetTokenResponse tokenResp = api.getToken(code);

        //根据 token 和 openid 获取用户数据
        GetUserInfoResponse userInfoResponse = api.getUserInfo(tokenResp.getAccessToken(),tokenResp.getOpenid());


        //如果查询不到用户三方信息
        Long thirdInfoId = thirdInfoService.isExists(tokenResp.getOpenid(), Const.ThirdType.WX);
        //三方信息
        ThirdInfoVo thirdInfoVo = this.regHelper(null,thirdInfoId,userInfoResponse);




            //正常用户数据 index/:id/:token/:username
            String redirectUrl = Const.BASE_URL+"#/user/index/"+thirdInfoVo.getUser().getId()+"/"+userService.getToken(thirdInfoVo.getUser().getId());
            response.sendRedirect(redirectUrl);



    }


    @RequestMapping(value = "/createMenu",method=RequestMethod.GET)
    public void createMenu(
            HttpServletRequest req
    ){

        String to = "";

        String url = (String)redisTemplate.opsForValue().get("yiMnueUrl");
        if(isNotEmpty(url)){
            to = url;
        }
        MenuAPI menuApi = new MenuAPI(apiConfig);

        //菜单
        Menu menu = new Menu();
        //一级菜单
        List<MenuButton> menuButtons = new ArrayList<MenuButton>();
        //Mnue 1
        MenuButton menuButton1 = new MenuButton();
        menuButton1.setType(MenuType.VIEW);
        menuButton1.setName("乐");
        menuButton1.setUrl("");

        //Mnue 2
        MenuButton menuButton2 = new MenuButton();
        menuButton2.setType(MenuType.VIEW);
        menuButton2.setName("E");
        menuButton2.setUrl("");

        //Mnue 3
        MenuButton menuButton3 = new MenuButton();
        menuButton3.setName("帮助");


            List<MenuButton> subMenuButtons = new ArrayList<MenuButton>();

            MenuButton menuButton31 = new MenuButton();
            menuButton31.setType(MenuType.VIEW);
            menuButton31.setName("攻略");
            menuButton31.setUrl("");

            MenuButton menuButton32 = new MenuButton();
            menuButton32.setType(MenuType.CLICK);
            menuButton32.setName("下");
            menuButton32.setKey("V1001_TODAY_MUSIC");

            subMenuButtons.add(menuButton31);
            subMenuButtons.add(menuButton32);
        menuButton3.setSubButton(subMenuButtons);


        menuButtons.add(menuButton1);
        menuButtons.add(menuButton2);
        menuButtons.add(menuButton3);
        menu.setButton(menuButtons);

        ResultType resultType = menuApi.createMenu(menu);
    }


    /**
     * 处理菜单点击事件，有需要时子类重写
     *
     * @param event 菜单点击事件对象
     * @return 响应消息对象
     */
    protected BaseMsg handleMenuClickEvent(MenuEvent event) {
        if(event.getEventKey().equals("V1001_TODAY_MUSIC")){
            TextMsg textMsg = new TextMsg();
            textMsg.setContent("");
            return textMsg;
        }else{
            return commonMsg();
        }
    }
}
