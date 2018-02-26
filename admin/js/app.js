/**
 * Created by Administrator on 2016/8/2.
 */
'use strict';

var app = angular.module('app', ['ui.router','qbhttpFactory','ngSanitize','ls.bmap']).run(['$rootScope', '$state',
    function ($rootScope, $state) {
    $rootScope.$state = $state;
        $rootScope.token=null;
    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
        console.log(toState.name);
        if((toState.name=='login')){return;}// 如果是进入登录界面则允许
        // 如果用户不存�?||(toState.name="forgetPsd")
           if(!sessionStorage.getItem("token")){
               event.preventDefault();// 取消默认跳转行为
               $state.go("login",{from:fromState.name,w:'notLogin'});//跳转到登录界�?
               console.log(1);
        }
    });
} ])

/*run(
    ['$rootScope', '$state', '$stateParams',
        function ($rootScope, $state, $stateParams) {

            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
            $rootScope.ip = "http://127.0.0.1";
            $rootScope.port = "8080";
        }
    ])*/
    .config(['$stateProvider', '$urlRouterProvider',function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('login');
//      location.href="#/input/123";

        $stateProvider.state('main.advertisement', { //广告管理
                controller: 'advertisementCtrl',
                url: '/advertisement',
                templateUrl: 'tpl/advertisement.html'
            })
            .state('main.merchant', {
                url: '/merchant',
                controller: 'MerchantCtrl',
                templateUrl: 'tpl/merchant.html'
            })
            .state('login', {
                url: '/login',
                controller: 'loginCtrl',
                templateUrl: 'login.html'
            })
            .state('main', {
                url: '/main',
                controller: 'mainCtrl',
                templateUrl: 'main.html'
            })
            .state('main.merchantInfo', {
                url: '/merchantInfo',
                controller: 'merchantInfoCtrl',
                templateUrl: 'tpl/merchantInfo.html'
            })
            .state('main.userManger', {
                controller: 'userMangerCtrl',
                url: '/userManger',
                templateUrl: 'tpl/userManger.html'
            })
            .state('main.notice', {  //公告管理
                url: '/notice',
                controller: 'noticeCtrl',
                templateUrl: 'tpl/notice.html'
            })
            .state('main.merchantType', { //商户类型
                url: '/merchantType',
                controller: 'merchantTypeCtrl',
                templateUrl: 'tpl/merchantType.html'
            })
            .state('main.agent', {  //代理商管�?
                    url: '/agent',
                    controller: 'agentCtrl',
                    templateUrl: 'tpl/agent.html'
                })
            .state('main.authority', { //权限管理
                url: '/authority',
                controller: 'authorityCtrl',
                templateUrl: 'tpl/authority.html'
            })
            .state('forgetPsd', {
                url: '/forgetPsd',
                controller: 'forgetPsdCtrl',
                templateUrl: 'tpl/forgetPsd.html'
            })
            .state('main.accountShow', {//账户�?�?
                url: '/accountShow',
                controller: 'accountShowCtrl',
                templateUrl: 'tpl/accountShow.html'
            })
            .state('main.orderManage', { //订单管理
                url: '/orderManage',
                controller: 'orderManageCtrl',
                templateUrl: 'tpl/orderManage.html'
            })
            .state('main.withdrawal', { //提现管理
                url: '/withdrawal',
                controller: 'withdrawalCtrl',
                templateUrl: 'tpl/withdrawal.html'
            })
            .state('main.panel', { //提现管理
                url: '/panel',
                controller: 'panelCtrl',
                templateUrl: 'tpl/panel.html'
            })
            .state('main.agentWithdrawal', { //代理商提现管理
                url: '/agentWithdrawal',
                controller: 'agentWithdrawalCtrl',
                templateUrl: 'tpl/agentWithdrawal.html'
            })
            .state('main.merchantWithdrawal', { //代理商提现管理
                url: '/merchantWithdrawal',
                controller: 'merchantWithdrawalCtrl',
                templateUrl: 'tpl/merchantWithdrawal.html'
            })
            .state('main.cashier', { //代理商提现管理
                url: '/cashier',
                controller: 'cashierCtrl',
                templateUrl: 'tpl/cashier.html'
            })

    }]);
