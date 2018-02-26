package com.sxp.sa.api.controller;

import com.sxp.sa.merchant.repository.NewsInfoRepository;
import com.sxp.sa.order.repository.AccountRecordRepository;
import com.sxp.sa.order.repository.RedBagPoolRepository;
import com.sxp.sa.user.repository.AccountRepository;
import com.sxp.sa.user.repository.UserRepository;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuickRun {


    @Autowired
    private AbstractShiroFilter abstractShiroFilter;

    @Autowired(required = true)
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRecordRepository accountRecordRepository;

    @Autowired
    private RedBagPoolRepository redBagPoolRepositoryl;

    @Autowired
    private NewsInfoRepository newsInfoRepository;

//    @RequestMapping(value = "/test8")
//    public String test(){
//
//        Long uid = 1l;
//        User user = userRepository.findByIdAndStatus(uid,valid);
//        user.setNickname("123");
//
//        userRepository.save(user);


//
//        if(isEmpty(user)){
//            throw new BusinessException(Const.Code.USER_NOT_EXISTS,Const.Code.USER_NOT_EXISTS_MSG);
//        }
//        Account account = accountRepository.findByUser(uid);
//        //账户异常
//        if(isEmpty(account)){
//            throw new BusinessException(Const.Code.ACCOUNT_ERROR,ACCOUNT_ERROR_MSG);
//        }
//        if(account.getCanGetRebNum()<=0){
//            throw new BusinessException(ACCOUNT_GET_RB_NUMS_LOW,ACCOUNT_GET_RB_NUMS_LOW_MSG);
//        }
//        //获取当天的红包池
//        List<RedBagPool> redList = redBagPoolRepositoryl.findPool(valid);
//        if(isEmpty(redList) || redList.size()==0 || redList.size()>1){
//            throw new BusinessException(RED_POOL_EXCEPTION,RED_POOL_EXCEPTION_MSG);
//        }
//        RedBagPool pool = redList.get(0);
//        //如果余额不足
//        if(pool.getBalance() <=0d){
//            throw new BusinessException(RED_POOL_EMPTY,RED_POOL_EMPTY_MSG);
//        }
//        Double redBag = Math.random()*(pool.getMaxSingleBag()-pool.getMinSingleBag())+pool.getMinSingleBag();
//        //如果超过池中余额
//        if(redBag>pool.getBalance()){
//            redBag = pool.getBalance();
//        }
//
//        //账户增加redBalance
//        if(isEmpty(account.getRedBalance())){
//            account.setRedBalance(0d);
//        }
//        account.setRedBalance(account.getRedBalance()+redBag);
//
//        //减少可抢红包次数
//        account.setCanGetRebNum(account.getCanGetRebNum()-1);
//
//        accountRepository.save(account);
//
//
//        //生成AccountRecord
//        AccountRecord ar = new AccountRecord();
//        ar.setUser(user);
//        ar.setAmount(redBag);
//        ar.setAmountType(1);
//        ar.setRecordType(3); //红包
//        ar = accountRecordRepository.save(ar);
//
//        //减少红包池
//        pool.setBalance(pool.getBalance()-redBag);
//        redBagPoolRepositoryl.save(pool);
//
//
//        //生成NewsInfo
//        NewsInfo newsInfo = new NewsInfo();
//        newsInfo.setNewsType(1);
//        newsInfo.setContent(user.getNickname()+" 抢到大红包一个");
//        newsInfo.setNewsTypeDesc("红包");
//
//        newsInfoRepository.save(newsInfo);

//
//        return "sb";
//    }


//
//
//    @RequestMapping(value = "/test1")
//    @ResponseBody
//    public String test1(){
//        abstractShiroFilter.getFilterChainResolver();
//        return "test1";
//    }
//
//    @RequestMapping(value = "/test2")
//    @ResponseBody
//    public String test2(){
//        return "test2";
//    }
//
//
//    @RequestMapping(value = "/first", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> firstResp (HttpServletRequest request){
//        Map<String, Object> map = new HashMap<>();
//        request.getSession().setAttribute("map", request.getRequestURL());
//        map.put("map", request.getRequestURL());
//        return map;
//    }
//
//    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
//    @ResponseBody
//    public Object sessions (HttpServletRequest request){
//        Map<String, Object> map = new HashMap<>();
//        map.put("sessionId", request.getSession().getId());
//        map.put("message", request.getSession().getAttribute("map"));
//        return map;
//    }
//
//
//
//    @RequestMapping(value = "/authc/test")
//    public String testStateless(){
//        return "stateless";
//    }
//
//
//    @RequestMapping(value = "/sb")
//    public String login(
//            HttpServletRequest request,
//            Model model
//    ){
//
//        String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
//        String error = null;
//        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
//            error = "用户名/密码错误";
//        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
//            error = "用户名/密码错误";
//        } else if(exceptionClassName != null) {
//            error = "其他错误：" + exceptionClassName;
//        }
//        if(request.getMethod().equalsIgnoreCase("get")){
//            return "redirect:/login.html";
//        }else{
//            return "redirect:/sb?error="+error;
//        }
//    }
}
