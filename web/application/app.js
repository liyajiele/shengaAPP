var app = angular.module('webapp', ['oc.lazyLoad', 'ui.router','LocalStorageModule'], function($httpProvider) {   //'hj.gsapifyRouter'
  // Use x-www-form-urlencoded Content-Type
  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
 
  /**
   * converts an object to x-www-form-urlencoded serialization.
   * @param {Object} obj
   * @return {String}
   */ 
  var param = function(obj) {
    var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
      
    for(name in obj) {
      value = obj[name];
        
      if(value instanceof Array) {
        for(i=0; i<value.length; ++i) {
          subValue = value[i];
          fullSubName = name + '[' + i + ']';
          innerObj = {};
          innerObj[fullSubName] = subValue;
          query += param(innerObj) + '&';
        }
      }
      else if(value instanceof Object) {
        for(subName in value) {
          subValue = value[subName];
          fullSubName = name + '[' + subName + ']';
          innerObj = {};
          innerObj[fullSubName] = subValue;
          query += param(innerObj) + '&';
        }
      }
      else if(value !== undefined && value !== null)
        query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
    }
      
    return query.length ? query.substr(0, query.length - 1) : query;
  };
 
  // Override(推翻) $http service's default transformRequest
  $httpProvider.defaults.transformRequest = [function(data) {
    return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
  }];
});

app.run(['$rootScope', '$log','$location','$state', function($rootScope, $log,$location,$state){
    // document.domain = "mkjianzhi.com";
    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
        // $log.debug('successfully changed states') ;
        // $log.debug('event', event);
        // $log.debug('toState', toState);
        // $log.debug('toParams', toParams);
        // $log.debug('fromState', fromState);
        // $log.debug('fromParams', fromParams);
    });

    $rootScope.$on('$stateNotFound', function(event, unfoundState, fromState, fromParams){
        // $log.error('The request state was not found: ' + unfoundState);
    });

    $rootScope.$on('$stateChangeError', function(event, toState, toParams, fromState, fromParams, error){
        // $log.error('An error occurred while changing states: ' + error);
        // $log.debug('event', event);
        // $log.debug('toState', toState);
        // $log.debug('toParams', toParams);
        // $log.debug('fromState', fromState);
        // $log.debug('fromParams', fromParams);
        // console.info(toState.url);
        //$rootScope.toUrl(toState.url);
        $state.go(toState.name,toParams);
        //$state.go('classroom_detail',{id: vm.selectedClassroom.id, month: vm.seletedMonth});
        //$location.path(toState.url);
    });
}]);


app.config(['$httpProvider','$stateProvider',function($httpProvider,$stateProvider){
  var interceptor = function($q,$rootScope,util){
    return {
      'request':function(config){
        config.headers.sign = util.getRequestSign(config.url.substr(util.baseUrl.length));

        return config;
      },
      'response':function(resp){
        if(resp.data.code == -1){   //身份验证失败，重新回去身份
        	//判断商户和用户，分别进行身份验证
          window.href="http://api.51shenga.com/wx/user/index";
        }else if(resp.data.code != undefined && resp.data.code!=1){
            layer.open({
                content: resp.data.message,
                skin: 'msg',
                time: 2, //2秒后自动关闭
            });

          return;
        }
        return resp;
      },
      'requestError':function(rejection){

      },
      'responseError':function(rejection){
        if(rejection.status==-1){
            layer.open({
                content: "请检查网络",
                skin: 'msg',
                time: 2, //2秒后自动关闭
            });
        }
        return;
      }

    }
  };

  $httpProvider.interceptors.push(interceptor);
}]);


   
   
app.config(['$stateProvider', '$locationProvider', '$ocLazyLoadProvider', '$urlRouterProvider', 'localStorageServiceProvider',function($stateProvider, $locationProvider, $ocLazyLoadProvider, $urlRouterProvider ,localStorageServiceProvider) {






  localStorageServiceProvider.setPrefix("ls.lt");
  //localStorageServiceProvider.setStorageType('sessionStorage');
  localStorageServiceProvider.setStorageType('localStorage');
  localStorageServiceProvider.setDefaultToCookie(true);
  localStorageServiceProvider.setNotify(true,true);


  $urlRouterProvider.otherwise("/user/index");
  
	$stateProvider
      .state('userIndex', {
        url: "/user/index",
        views: {
          "main": {
            controller: 'indexController',
            templateUrl: 'application/modules/user/index/view/index.html'
          },
          "navBottom":{
            controller: 'indexNavController',
            templateUrl: 'application/modules/navBottom.html'
          }
        },
        resolve: { 
          loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
            return $ocLazyLoad.load([
              'application/modules/user/index/script/indexController.js',
              'application/modules/user/index/css/index.css',
              'application/modules/user/index/css/component.css',
              'application/modules/user/index/css/demo.css',
              'application/modules/user/index/script/function.js',
              'application/modules/user/index/script/animate.js',
              'libs/jquery/jquery.min.js',
              
              'application/service/user/userService.js',
              'application/service/user/merchantService.js',
              'application/service/commons/bannerService.js',
              'application/service/address/addressService.js',
              'application/service/user/accountRecordService.js',
              'libs/angular/ng-infinite-scroll.min.js',
              'libs/font/iconfont.css'

            ]);


          }]
        }
      })
      .state('userIndexAndLogin', {
        url: "/user/index/:uid/:token",
        views: {
          "main": {
            controller: 'indexController',
            templateUrl: 'application/modules/user/index/view/index.html'
          },
          "navBottom":{
            controller: 'indexNavController',
            templateUrl: 'application/modules/navBottom.html'
          }
        },
        resolve: { 
          loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
            return $ocLazyLoad.load([
              'application/modules/user/index/script/indexController.js',
              'application/modules/user/index/css/index.css',
              'application/modules/user/index/script/function.js',
              'application/modules/user/index/script/animate.js',
              'libs/jquery/jquery.min.js',
              
              'application/service/user/userService.js',
              'application/service/user/merchantService.js',
              'application/service/commons/bannerService.js',
              'application/service/address/addressService.js',
              'application/service/user/accountRecordService.js',
              'libs/angular/ng-infinite-scroll.min.js',
              'libs/font/iconfont.css'
            ]);


          }]
        }
      })
      
      .state('userNearby', {      //附近
        url: "/user/nearby",
        views: {
          "main": {
            controller: 'nearbyController',
            templateUrl: 'application/modules/user/nearby/html/nearby.html'
          },
          "navBottom":{
            controller: 'nearbyNavController',
            templateUrl: 'application/modules/navBottom.html'
          }
        },
        resolve: { 
          loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
            return $ocLazyLoad.load([ 
            	'libs/jquery/jquery.min.js',
              'application/modules/user/nearby/script/nearbyController.js',
//            'application/modules/user/nearby/script/cbpFWTabs.js',
              'application/modules/user/nearby/css/component.css',
              'application/modules/user/nearby/css/demo.css',
              'application/modules/user/nearby/css/nearby.css',
              'application/service/user/merchantService.js',
              'libs/swiper.min.js',
              'style/swiper.min.css',             
              'libs/angular/ng-infinite-scroll.min.js',
              'libs/jquery/jquery.min.js',
              'libs/font/iconfont.css'
              

            ]);
          }]
        }
      })
      
      .state('loginma', {    /*找回密码*/
          url: "/loginma",
          views: {
            "main": {
              controller: 'loginController',
              templateUrl: 'application/modules/login/html/loginma.html'
            }
          },
          resolve: {
            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
              return $ocLazyLoad.load([
                'application/modules/login/script/loginmaController.js',
                'application/service/user/userService.js',
                'application/modules/login/css/loginma.css',
                'style/reast.css'
              ]);
            }]
          }
        })
      .state('userCode', {       //网友打分
        url: "/user/code/:mid",
        views: {
          "main": {
            controller: 'codeController',
            templateUrl: 'application/modules/user/code/html/code.html'
          },
        },
        resolve: {
          loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
            return $ocLazyLoad.load([
              'application/modules/user/code/script/codeController.js',
              'application/modules/user/code/css/code.css',
              'application/service/user/merchantService.js',
              'libs/angular/ng-infinite-scroll.min.js',
              'libs/font/iconfont.css'
            ]);

          }]
        }
      })
      .state('userTou', {       //省啊头条
        url: "/user/tou",
        views: {
          "main": {
            controller: 'touController',
            templateUrl: 'application/modules/user/tou/html/tou.html'
          },
        },
        resolve: {
          loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
            return $ocLazyLoad.load([
              'application/modules/user/tou/script/touController.js',
              'application/modules/user/tou/css/tou.css',
              'libs/jquery/jquery.min.js',
              'application/service/user/accountRecordService.js',
              'libs/angular/ng-infinite-scroll.min.js',
              
            ]);


          }]
        }
      })
      .state('userDetails', {       //店铺详情
        url: "/user/details/:mid",
        views: {
          "main": {
            controller: 'detailsController',
            templateUrl: 'application/modules/user/details/html/details.html'
          },
        },
        resolve: {
          loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
            return $ocLazyLoad.load([
              'application/modules/user/details/script/detailsController.js',
              'application/modules/user/details/css/details.css',
              'application/service/user/merchantService.js',
              'application/service/user/userService.js',
              'libs/jquery/jquery.min.js',
              'libs/angular/ng-infinite-scroll.min.js',
              'libs/font/iconfont.css'
              
            ]);


          }]
        }
      })
    .state('userPay', {       //确认支付
      url: "/user/pay/:mid",
      views: {
        "main": {
          controller: 'payController',
          templateUrl: 'application/modules/user/pay/html/pay.html'
        },
      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/pay/script/payController.js',
            'application/modules/user/pay/css/pay.css',
            'application/service/user/merchantService.js',
            'application/service/user/orderService.js',
            'application/service/user/accountService.js',
            
          ]);
        }]
      }
    })
    
    .state('userSuccess', {       //支付成功
      url: "/user/success/:mid",
      views: {
        "main": {
          controller: 'successController',
          templateUrl: 'application/modules/user/success/html/success.html'
        },
      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/success/script/successController.js',
            'application/modules/user/success/css/success.css',
            'application/service/user/merchantService.js',
            'application/service/user/userService.js',
            'libs/angular/ng-infinite-scroll.min.js',
//          'libs/font/iconfont.css''
          ]);


        }]
      }
    })
    
    
    .state('userPayti', {       //提现
      url: "/user/payti",
      views: {
        "main": {
          controller: 'paytiController',
          templateUrl: 'application/modules/user/payti/html/payti.html'
        },
      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/payti/script/paytiController.js',
            'application/modules/user/payti/css/payti.css',
            'application/service/pay/paytiService.js',
            'application/service/user/userService.js',
            'application/service/user/accountService.js',
//          "libs/jquery/jquery.min.js"
            
          ]);
        }]
      }
    })
    .state('userTisuccess', {       //提现成功
      url: "/user/tisuccess",
      views: {
        "main": {
          controller: 'tisuccessController',
          templateUrl: 'application/modules/user/tisuccess/html/tisuccess.html'
        },
      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/tisuccess/js/tisuccessController.js',
            'application/modules/user/tisuccess/css/tisuccess.css',
//          'application/service/pay/paytiService.js',
//          'application/service/user/userService.js',
//          'application/service/user/accountService.js',
//          "libs/jquery/jquery.min.js"
            
          ]);


        }]
      }
    })
    .state('userSearch', {       //搜索
      url: "/user/search",
      views: {
        "main": {
          controller: 'searchController',
          templateUrl: 'application/modules/user/search/html/search.html'
        },
      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/search/script/searchController.js',
            'application/modules/user/search/css/search.css',
            'application/service/address/addressService.js',
            'application/service/user/merchantService.js',
            'libs/jquery/jquery.min.js'
          ]);


        }]
      }
    })
    
    .state('userNoyao', {       //摇红包
      url: "/user/noyao",
      views: {
        "main": {
          controller: 'noyaoController',
          templateUrl: 'application/modules/user/noyao/html/noyao.html'
        },
      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/noyao/script/noyaoController.js',
            'application/modules/user/noyao/css/noyao.css',
            'application/modules/user/index/script/function.js',
            'application/modules/user/index/script/animate.js',
            'application/service/user/accountRecordService.js',
            'application/service/user/accountService.js',
            'application/service/user/userService.js',
            'libs/angular/angular.min.js',
            'libs/angular/angular-animate.min.js',
          ]);
        }]
      }
    })
      .state('userCity', {       //城市搜索
        url: "/user/city",
        views: {
          "main": {
            controller: 'cityController',
            templateUrl: 'application/modules/user/city/html/city.html'
          },
        },
        resolve: {
          loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
            return $ocLazyLoad.load([
              'http://at.alicdn.com/t/font_420420_jrzewrwrpi0h33di.css',
              'application/modules/user/city/script/jquery-3.2.1.min.js',
              'application/modules/user/city/script/cityController.js',
              'application/modules/user/city/css/aui.2.0.css',
              'application/modules/user/city/css/city.css',
              'application/service/user/userService.js',
              'application/service/address/addressService.js',
              'libs/jquery/jquery.min.js',

              
              // 'libs/animate.script'
            ]);
          }]
        }
      })
    .state('user/Generalize', {       //推广赚钱
      url: "/user/generalize",
      views: {
        "main": {
          controller: 'generalizeController',
          templateUrl: 'application/modules/user/generalize/html/generalize.html'
        },
        "navBottom":{
          controller: 'generalizeNavController',
          templateUrl: 'application/modules/navBottom.html'
        }
      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/generalize/script/jquery.min.js',
            'application/modules/user/generalize/script/jquery.qrcode.min.js',
            'application/modules/user/generalize/script/generalizeController.js',
            'application/modules/user/generalize/css/generalize.css',
            'application/service/user/userService.js',
            
          ]);


        }]
      }
    })
    .state('userAboutour', {       //关于我们
      url: "/user/aboutour",
      views: {
        "main": {
          controller: 'aboutourController',
          templateUrl: 'application/modules/user/aboutour/html/aboutour.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/aboutour/script/aboutourController.js',
            'application/modules/user/aboutour/css/aboutour.css',
            'application/service/commons/aboutService.js',
          ]);
        }]
      }
    })
    .state('userRuler', {       //提现规则
      url: "/user/ruler",
      views: {
        "main": {
          controller: 'rulerController',
          templateUrl: 'application/modules/user/ruler/html/ruler.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/ruler/script/rulerController.js',
            'application/modules/user/ruler/css/ruler.css'
          ]);


        }]
      }
    })
    .state('redRuler', {       //提现规则
      url: "/user/redruler",
      views: {
        "main": {
          controller: 'redrulerController',
          templateUrl: 'application/modules/user/redruler/html/redruler.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/redruler/script/redrulerController.js',
            'application/modules/user/redruler/css/redruler.css'
          ]);


        }]
      }
    })
    .state('userCollect', {       //收藏
      url: "/user/collect",
      views: {
        "main": {
          controller: 'collectController',
          templateUrl: 'application/modules/user/collect/html/collect.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/collect/script/collectController.js',
            'application/modules/user/collect/css/collect.css',
            'application/service/user/userService.js',
            'application/service/user/merchantService.js',
            'libs/angular/ng-infinite-scroll.min.js',
              'libs/font/iconfont.css'
            
          ]);


        }]
      }
    })
    .state('userCompletedOrders', {       //已完成订单
      url: "/user/completedOrders",
      views: {
        "main": {
          controller: 'completedOrdersController',
          templateUrl: 'application/modules/user/completedOrders/html/completedOrders.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/completedOrders/script/completedOrdersController.js',
            'application/modules/user/completedOrders/css/completedOrders.css',
            'application/service/user/orderService.js',
            'application/service/user/merchantService.js',
            'libs/angular/ng-infinite-scroll.min.js',
              'libs/font/iconfont.css'
            
          ]);


        }]
      }
    })
    .state('userUnassess', {       //待评价订单
      url: "/user/unassess",
      views: {
        "main": {
          controller: 'unassessController',
          templateUrl: 'application/modules/user/unassess/html/unassess.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/unassess/script/unassessController.js',
            'application/modules/user/unassess/css/unassess.css',
            'application/service/user/orderService.js',
            'application/service/user/merchantService.js',
            'libs/angular/ng-infinite-scroll.min.js',
              'libs/font/iconfont.css'
            
          ]);


        }]
      }
    })
    .state('userPeople', {       //个人中心
      url: "/user/people",
      views: {
        "main": {
          controller: 'peopleController',
          templateUrl: 'application/modules/user/people/html/people.html'
        },
        "navBottom":{
          controller: 'peopleNavController',
          templateUrl: 'application/modules/navBottom.html'
        }
      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/people/script/jquery-1.12.4.js',
            'application/modules/user/people/script/jquery.qrcode.min.js',
            'application/modules/user/people/script/peopleController.js',
            'application/modules/user/people/css/people.css',
            'application/service/user/userService.js',
            'application/service/pay/paytiService.js',
            'application/service/user/orderService.js',
            'application/service/user/accountService.js',
            'application/service/commons/aboutService.js',
            
          ]);


        }]
      }
    })
    .state('userYijian', {       //意见反馈
      url: "/user/yijian",
      views: {
        "main": {
          controller: 'yijianController',
          templateUrl: 'application/modules/user/yijian/html/yijian.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/yijian/script/yijianController.js',
            'application/modules/user/yijian/css/yijian.css',
            'application/service/commons/yijianService.js',
            'libs/angular/ng-file-upload.min.js',
            'libs/angular/ng-file-upload-shim.min.js',
            'libs/jquery/jquery.min.js',
            
          ]);


        }]
      }
    })
    .state('userContacts', {       //我的人脉
      url: "/user/contacts",
      views: {
        "main": {
          controller: 'contactsController',
          templateUrl: 'application/modules/user/contacts/html/contacts.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/contacts/script/contactsController.js',
            'application/modules/user/contacts/css/contacts.css',
            'application/service/user/userService.js',
            'libs/angular/ng-infinite-scroll.min.js'
          ]);


        }]
      }
    })
    .state('userFinance', {       //财务记录
      url: "/user/finance",
      views: {
        "main": {
          controller: 'financeController',
          templateUrl: 'application/modules/user/finance/html/finance.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
          	'libs/jquery/jquery.min.js',
            'application/modules/user/finance/script/financeController.js',
            'application/modules/user/finance/css/finance.css',          
            'application/service/user/accountRecordService.js',
            'libs/angular/ng-infinite-scroll.min.js',
            'application/modules/user/finance/script/jquer_shijian.js',
            'application/modules/user/finance/css/shijian.css',          
            
//						'application/modules/user/finance/script/mobiscroll.js',
//						'application/modules/user/finance/script/mobiscroll_002.js',
//						'application/modules/user/finance/script/mobiscroll_004.js',
//						'application/modules/user/finance/css/mobiscroll_002.css',
//						'application/modules/user/finance/script/mobiscroll_003.js',
//						'application/modules/user/finance/script/mobiscroll_005.js',
//						'application/modules/user/finance/css/mobiscroll_003.css',
//	

          ]);


        }]
      }
    })
    .state('userFanli', {       //返利
      url: "/user/fanli",
      views: {
        "main": {
          controller: 'fanliController',
          templateUrl: 'application/modules/user/fanli/html/fanli.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/fanli/script/jquery.min.js',
            'application/modules/user/fanli/script/fanliController.js',
            'application/modules/user/fanli/script/LCalendar.js',
            'application/modules/user/fanli/css/fanli.css',
            'application/modules/user/fanli/css/LCalendar.css',
            'application/service/user/userService.js',
            'application/service/user/orderService.js',
            'libs/angular/ng-infinite-scroll.min.js',

          ]);


        }]
      }
    })


	.state('userGrade', {       //评分
      url: "/user/grade/:orderId",
      views: {
        "main": {
          controller: 'gradeController',
          templateUrl: 'application/modules/user/grade/html/grade.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([

            'application/modules/user/grade/script/gradeController.js',
            'application/modules/user/grade/css/grade.css',
            'application/service/user/userService.js',
            'application/service/user/orderService.js',
              'libs/font/iconfont.css'
            
          ]);


        }]
      }
    })
	.state('userGong', {       //省啊攻略
      url: "/user/gong",
      views: {
        "main": {
          controller: 'gongController',
          templateUrl: 'application/modules/user/gong/html/gong.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/gong/script/gongController.js',
            'application/modules/user/gong/css/gong.css',
          ]);


        }]
      }
    })
	.state('userWhatmai', {       //如何使用省啊买单
      url: "/user/whatmai",
      views: {
        "main": {
          controller: 'whatmaiController',
          templateUrl: 'application/modules/user/whatmai/html/whatmai.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/whatmai/script/whatmaiController.js',
            'application/modules/user/whatmai/css/whatmai.css',
          ]);


        }]
      }
    })
		.state('userWhatfen', {       //如何发展粉丝
      url: "/user/whatfen",
      views: {
        "main": {
          controller: 'whatfenController',
          templateUrl: 'application/modules/user/whatfen/html/whatfen.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/whatfen/script/whatfenController.js',
            'application/modules/user/whatfen/css/whatfen.css',
          ]);


        }]
      }
    })
		.state('userWhatfennum', {       //如何发展粉丝
      url: "/user/whatfennum",
      views: {
        "main": {
          controller: 'whatfennumController',
          templateUrl: 'application/modules/user/whatfennum/html/whatfennum.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/whatfennum/script/whatfennumController.js',
            'application/modules/user/whatfennum/css/whatfennum.css',
          ]);


        }]
      }
    })
		.state('userWhatfan', {       //如何发展粉丝
      url: "/user/whatfan",
      views: {
        "main": {
          controller: 'whatfanController',
          templateUrl: 'application/modules/user/whatfan/html/whatfan.html'
        },

      },
      resolve: {
        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
          return $ocLazyLoad.load([
            'application/modules/user/whatfan/script/whatfanController.js',
            'application/modules/user/whatfan/css/whatfan.css',
          ]);


        }]
      }
    })



















    //商户版
//  .state('mctLogin', {    //登录
//          url: "/mct/login",
//          views: {
//              "main": {
//                  controller: 'loginController',
//                  templateUrl: 'application/modules/mct/login/html/login.html'
//              },
//
//          },
//          resolve: {
//              loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
//                  return $ocLazyLoad.load([
//                      'application/modules/mct/login/script/loginController.js',
//                      'application/service/mct/userService.js',
////                      'application/directive/lunboModule.js',
//                      'application/modules/mct/login/css/login.css',
//                      'style/reast.css'
//                  ]);
//              }]
//          }
//      })
//  		
//    .state('mctLoginma', {    //找回密码
//          url: "/mct/loginma",
//          views: {
//              "main": {
//                  controller: 'loginmaController',
//                  templateUrl: 'application/modules/mct/loginma/html/loginma.html'
//              },
//
//          },
//          resolve: {
//              loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
//                  return $ocLazyLoad.load([
//                      'application/modules/mct/loginma/script/loginmaController.js',
//                      'application/service/mct/userService.js',
////                      'application/directive/lunboModule.js',
//                      'application/modules/mct/loginma/css/loginma.css',
//                      'style/reast.css'
//                  ]);
//              }]
//          }
//      })
//    .state('mctRegister', {    /*注册账号*/
//          url: "/mct/register",
//          views: {
//              "main": {
//                  controller: 'registerController',
//                  templateUrl: 'application/modules/mct/register/html/register.html'
//              }
//          },
//          resolve: {
//              loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
//                  return $ocLazyLoad.load([
//                      'application/modules/mct/register/script/registerController.js',
//                      'application/modules/mct/register/css/register.css',   
//                      'application/service/mct/userService.js',
//                      'application/service/commons/codeService.js',
//                      'style/reast.css'
//                  ]);
//              }]
//          }
//    })
      .state('mctRegistermd', {    /*门店信息填写*/
            url: "/mct/registermd",
            views: {
                "main": {
                    controller: 'registermdController',
                    templateUrl: 'application/modules/mct/registermd/html/registermd.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'libs/jquery/jquery.min.js',
                    	  'libs/angular/ng-infinite-scroll.min.js',
				      					'application/modules/mct/registermd/script/jquery.range.js',
                        'application/modules/mct/registermd/css/jquery.range.css',
                        'application/modules/mct/registermd/script/registermdController.js',
                        'application/modules/mct/registermd/css/registermd.css',
                        'application/service/mct/userService.js',
		                		'application/service/address/addressService.js',
                        'style/reast.css',

				      					
                    ]);
                }]
            }
        })
      .state('mctRegisterzz', {    /*提交资质*/
            url: "/mct/registerzz",
           views: {
                "main": {
                    controller: 'registerzzController',
                    templateUrl: 'application/modules/mct/registerzz/html/registerzz.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/registerzz/script/registerzzController.js',
                        'application/modules/mct/registerzz/css/registerzz.css',
                        'application/service/mct/userService.js',
                        'style/reast.css'
                    ]);
                }]
            }
        })
      .state('mctCity', {    /*城市选择*/
            url: "/mct/city",
            views: {
                "main": {
                    controller: 'cityController',
                    templateUrl: 'application/modules/mct/city/html/city.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'http://at.alicdn.com/t/font_420420_jrzewrwrpi0h33di.css',
					              'application/modules/mct/city/script/jquery-3.2.1.min.js',
					              'application/modules/mct/city/script/cityController.js',
					              'application/modules/mct/city/css/aui.2.0.css',
					              'application/modules/mct/city/css/city.css',
					              'application/service/user/userService.js',
					              'application/service/address/addressService.js',
					              'libs/jquery/jquery.min.js',
                        
                        
                    ]);
                }]
            }
        })
      .state('mctNearby', {    /*附近*/
            url: "/mct/nearby/:typeid",
            views: {
//              "header": {
//                  controller: 'indexHeaderController',
//                  templateUrl: 'application/modules/header.html'
//              },
                "main": {
                    controller: 'indexController',
                    templateUrl: 'application/modules/mct/nearby/view/nearby.html'
                },
                "navBottom":{
                    controller: 'indexNavController',
                    templateUrl: 'application/modules/navBottom.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/nearby/script/nearbyController.js',
                        'application/modules/mct/nearby/css/nearby.css',
                        
                    ]);
                }]
            }
        })
        .state('mctSpread', {   /*推广介绍*/
            url: "/mct/spread",
            views: {
                "main": {
                    controller: 'spreadController',
                    templateUrl: 'application/modules/mct/spread/view/spread.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/spread/script/spreadController.js',
                        'application/modules/mct/spread/css/spread.css'
                    ]);
                }]
            }
        })
        .state('mctcheck', {   /*省啊买单*/
            url: "/mct/check",
            views: {
                "main": {
                    controller: 'checkController',
                    templateUrl: 'application/modules/mct/check/view/check.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/check/script/checkController.js',
                        'application/modules/mct/check/css/check.css'
                    ]);
                }]
            }
        })
        .state('mctBusiness', {   /*商业模式*/
            url: "/mct/business",
            views: {
                "main": {
                    controller: 'businessController',
                    templateUrl: 'application/modules/mct/business/view/business.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/business/script/businessController.js',
                        'application/modules/mct/business/css/business.css',
                         'style/reast.css'
                    ]);
                }]
            }
        })
        .state('mctAuthentication', {   /*商户认证*/
            url: "/mct/authentication",
            views: {
                "main": {
                    controller: 'authenticationController',
                    templateUrl: 'application/modules/mct/authentication/view/authentication.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/authentication/script/authenticationController.js',
                        'application/modules/mct/authentication/css/authentication.css',
                        'style/reast.css'
                    ]);
                }]
            }
        })
        .state('mctLicence', {   /*提交资证*/
            url: "/mct/licence",
            views: {
                "main": {
                    controller: 'licenceController',
                    templateUrl: 'application/modules/mct/licence/view/licence.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/licence/script/licenceController.js',
                        'application/modules/mct/licence/css/licence.css',
                        'style/reast.css'
                    ]);
                }]
            }
        })
        .state('mctShu', {   /*提交资证*/
            url: "/mct/shu",
            views: {
                "main": {
                    controller: 'shuController',
                    templateUrl: 'application/modules/mct/shu/html/shu.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/shu/script/shuController.js',
                        'application/modules/mct/shu/css/shu.css',
                        'style/reast.css'
                    ]);
                }]
            }
        })
        .state('mctIDcard', {   /*身份证认证*/
            url: "/mct/IDcard",
            views: {
                "main": {
                    controller: 'IDcardController',
                    templateUrl: 'application/modules/mct/IDcard/view/IDcard.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/IDcard/script/IDcardController.js',
                        'application/modules/mct/IDcard/css/IDcard.css',
                        'style/reast.css'
                    ]);
                }]
            }
        })
        .state('mctinaudit', {   /*审核中*/
            url: "/mct/inaudit",
            views: {
                "main": {
                    controller: 'inauditController',
                    templateUrl: 'application/modules/mct/inaudit/view/inaudit.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/inaudit/script/inauditController.js',
                        'application/modules/mct/inaudit/css/inaudit.css',
                        'style/reast.css'
                    ]);
                }]
            }
        })
        .state('mctauditfailure', {   /*审核失败*/
            url: "/mct/auditfailure",
            views: {
                "main": {
                    controller: 'auditfailureController',
                    templateUrl: 'application/modules/mct/auditfailure/view/auditfailure.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/auditfailure/script/auditfailureController.js',
                        'application/modules/mct/auditfailure/css/auditfailure.css',
                        'style/reast.css'
                    ]);
                }]
            }
        })
        .state('mctAuditsuccess', {   /*审核成功*/
            url: "/mct/auditsuccess",
            views: {
                "main": {
                    controller: 'auditsuccessController',
                    templateUrl: 'application/modules/mct/auditsuccess/view/auditsuccess.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/auditsuccess/script/auditsuccessController.js',
                        'application/modules/mct/auditsuccess/css/auditsuccess.css',
                        'style/reast.css'
                    ]);
                }]
            }
        })

        
        .state('mctMystore', {   /*我的门店*/
            url: "/mct/mystore",
            views: {
                "main": {
                    controller: 'mystoreController',
                    templateUrl: 'application/modules/mct/mystore/view/mystore.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
//                  		'libs/angular/angular.min.js',
                        'application/modules/mct/mystore/script/mystoreController.js',
                        'style/reast.css',
                        'application/modules/mct/mystore/css/mystore.css',
                        'application/service/mct/userService.js',

                    ]);
                }]
            }
        })
        .state('mctMystoreAndLogin', {   /*我的门店*/
            url: "/mct/mystore/:uid/:mid/:token",
            views: {
                "main": {
                    controller: 'mystoreController',
                    templateUrl: 'application/modules/mct/mystore/view/mystore.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
//                  		'libs/angular/angular.min.js',
                        'application/modules/mct/mystore/script/mystoreController.js',
                        'style/reast.css',
                        'application/modules/mct/mystore/css/mystore.css',
                        'application/service/mct/userService.js',

                    ]);
                }]
            }
        })
        .state('mctStoreprogress', {   /*我的门店  入驻进度*/
            url: "/mct/storeprogress",
            views: {
                "main": {
                    controller: 'storeprogressController',
                    templateUrl: 'application/modules/mct/storeprogress/view/storeprogress.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/storeprogress/script/storeprogressController.js',
                        'style/reast.css',
                        'application/modules/mct/storeprogress/css/storeprogress.css',

                    ]);
                }]
            }
        })
        .state('mctstorecertification', {   /*我的门店  商户认证*/
            url: "/mct/storecertification",
            views: {
                "main": {
                    controller: 'storecertificationController',
                    templateUrl: 'application/modules/mct/storecertification/view/storecertification.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/storecertification/script/storecertificationController.js',
                        'style/reast.css',
                        'application/modules/mct/storecertification/css/storecertification.css',

                    ]);
                }]
            }
        })
        .state('mctDetails', {   /*门店管理*/
            url: "/mct/details",
            views: {
                "main": {
                    controller: 'detailsController',
                    templateUrl: 'application/modules/mct/details/html/details.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/details/script/detailsController.js',
                        'style/reast.css',
                        'application/modules/mct/details/css/details.css',
                        'application/service/mct/userService.js',
                        'application/modules/mct/details/script/jquery.range.js',
                        'application/modules/mct/details/css/jquery.range.css',
                        'libs/font/iconfont.css'
                    ]);
                }]
            }
        })
        
        .state('mctFanli', {   /*订单管理*/
            url: "/mct/fanli",
            views: {
                "main": {
                    controller: 'fanliController',
                    templateUrl: 'application/modules/mct/fanli/html/fanli.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/fanli/script/fanliController.js',
                        'application/modules/mct/fanli/script/LCalendar.js',
                        'application/modules/mct/fanli/css/LCalendar.css',
                        'style/reast.css',
                        'application/modules/mct/fanli/css/fanli.css',
												'application/service/mct/orderService.js',
												'application/service/mct/userService.js',
              					'libs/angular/ng-infinite-scroll.min.js',
												
                    ]);
                }]
            }
        })
        
        .state('mctFinance', {       //财务记录
		      url: "/mct/finance",
		      views: {
		        "main": {
		          controller: 'financeController',
		          templateUrl: 'application/modules/mct/finance/html/finance.html'
		        },
		
		      },
		      resolve: {
		        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
		          return $ocLazyLoad.load([
		            'application/modules/mct/finance/script/financeController.js',
		            'application/modules/mct/finance/css/finance.css',
		            'application/service/mct/accountRecordService.js',
		            'libs/angular/ng-infinite-scroll.min.js',
		            'libs/jquery/jquery.min.js',
		          ]);
		
		
		        }]
		      }
		    })
        .state('mctAddress', {   /*我的地址*/
            url: "/mct/address",
            views: {
                "main": {
                    controller: 'addressController',
                    templateUrl: 'application/modules/mct/address/view/address.html'
                }
            },
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        'application/modules/mct/address/script/addressController.js',
                        'style/reast.css',
                        'application/modules/mct/address/css/address.css',
                    ]);
                }]
            }
        })
        .state('mctAboutour', {       //关于我们
		      url: "/mct/aboutour",
		      views: {
		        "main": {
		          controller: 'aboutourController',
		          templateUrl: 'application/modules/mct/aboutour/html/aboutour.html'
		        },
		
		      },
		      resolve: {
		        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
		          return $ocLazyLoad.load([
		            'application/modules/mct/aboutour/script/aboutourController.js',
		            'application/modules/mct/aboutour/css/aboutour.css',
		            'application/service/commons/aboutService.js',
		          ]);
		        }]
		      }
		    })
        .state('mctPayti', {       //提现
		      url: "/mct/payti",
		      views: {
		        "main": {
		          controller: 'paytiController',
		          templateUrl: 'application/modules/mct/payti/html/payti.html'
		        },
		      },
		      resolve: {
		        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
		          return $ocLazyLoad.load([
		            'application/modules/mct/payti/script/paytiController.js',
		            'application/modules/mct/payti/css/payti.css',
		            'application/service/mct/paytiService.js',
//		            'application/service/user/userService.js',
		            'application/service/mct/userService.js',
		//          "libs/jquery/jquery.min.js"
		            
		          ]);
		        }]
		      }
		    })
        .state('mctTisuccess', {       //提现成功
		      url: "/mct/tisuccess",
		      views: {
		        "main": {
		          controller: 'tisuccessController',
		          templateUrl: 'application/modules/mct/tisuccess/html/tisuccess.html'
		        },
		      },
		      resolve: {
		        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
		          return $ocLazyLoad.load([
		            'application/modules/mct/tisuccess/js/tisuccessController.js',
		            'application/modules/mct/tisuccess/css/tisuccess.css',
		//          'application/service/pay/paytiService.js',
		//          'application/service/user/userService.js',
		//          'application/service/user/accountService.js',
		//          "libs/jquery/jquery.min.js"
		            
		          ]);
		
		
		        }]
		      }
		    })
        .state('mctCode', {       //网友打分
	        url: "/mct/code",
	        views: {
	          "main": {
	            controller: 'codeController',
	            templateUrl: 'application/modules/mct/code/html/code.html'
	          },
	        },
	        resolve: {
	          loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
	            return $ocLazyLoad.load([
	              'application/modules/mct/code/script/codeController.js',
	              'application/modules/mct/code/css/code.css',
	              'application/service/mct/merchantService.js',
	              'libs/angular/ng-infinite-scroll.min.js',
	              'libs/font/iconfont.css'
	            ]);
	
	          }]
	        }
	      })
        .state('mctContacts', {       //我的人脉
		      url: "/mct/contacts",
		      views: {
		        "main": {
		          controller: 'contactsController',
		          templateUrl: 'application/modules/mct/contacts/html/contacts.html'
		        },
		
		      },
		      resolve: {
		        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
		          return $ocLazyLoad.load([
		            'application/modules/mct/contacts/script/contactsController.js',
		            'application/modules/mct/contacts/css/contacts.css',
		            'application/service/mct/userService.js',
		            'libs/angular/ng-infinite-scroll.min.js'
		          ]);
		
		
		        }]
		      }
		    })
        .state('mctYijian', {       //意见反馈
		      url: "/mct/yijian",
		      views: {
		        "main": {
		          controller: 'yijianController',
		          templateUrl: 'application/modules/mct/yijian/html/yijian.html'
		        },
		
		      },
		      resolve: {
		        loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
		          return $ocLazyLoad.load([
		            'application/modules/mct/yijian/script/yijianController.js',
		            'application/modules/mct/yijian/css/yijian.css',
		            'application/service/commons/yijianService.js',
		            'libs/angular/ng-file-upload.min.js',
		            'libs/angular/ng-file-upload-shim.min.js',
		            'libs/jquery/jquery.min.js',
		            
		          ]);
		
		
		        }]
		      }
		    })
	$locationProvider.html5Mode(false);
}]);



app.filter('mobileFilter',function(){
    return function(mobile){
        if(mobile!=undefined){
        var start = mobile.substr(0,3);
        var end = mobile.substr(7,4);

        return start+"****"+end;
        }
    };
});

app.filter('nameFilter',function(){
    return function(name){
        if(name!=undefined){
        var end = name.substr(1,name.length-1);
          return "*"+end;
        }
    };
});

app.factory('cache',[function(){
  var cache = {};
  return {
    get:function(key){
        return cache.key;
    },
    put:function(key,value){
        cache.key = value;
    }
  };
}]);

app.factory('util',['cache','$location','localStorageService','$rootScope','$window',function(cache,$location,localStorageService,$rootScope,$window){
  var util = {
    baseUrl: 'http://api.51shenga.com',

    getRequestSign: function (url) {
      //var uid = cache.get("uid");
      //var token = cache.get("token");


      var uid = localStorageService.get('uid');
//    var mid = localStorageService.get('mid');
      var token = localStorageService.get('token');

      return this.MD5(url+uid+token);
    },
    toLogin : function(url){
      if($location.path()!="/login"){
        localStorageService.set("preLoginUrl",$location.path());
      }
      $location.path(url);
    },
    toUrl:function(url){
        $location.path(url);
    },
    logout: function(url){
        localStorageService.clearAll();
        if(url==undefined){
            url='/index';
        }
        $location.path(url);
    },
    headerConfig : function(title,showLeft,showRight,refreshFn){
        if(showRight==undefined){
            $rootScope.showRight = false;
        }else{
            $rootScope.showRight = showRight;
        }
        $rootScope.htitle = title;
        $rootScope.title = title;
        $rootScope.showLeft = showLeft;
        $rootScope.urlBack = function(){
          $window.history.back();
        }
        if(refreshFn == undefined){
            $rootScope.urlRefresh = function(){};
        }else{
            $rootScope.urlRefresh = refreshFn;
        }
        var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i)=="micromessenger") {
          $rootScope.showHeader = false;
        } else {
          $rootScope.showHeader = true;
        }
    },
    iswx: function () {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        },
        isIos: function () {
            var ua = navigator.userAgent.toLowerCase();
            if (/iphone|ipad|ipod/.test(ua)) {
                return true;
            } else if (/android/.test(ua)) {
                return false;
            }
        },
    navConfig:function(nav){
      $rootScope.nav1 = "images/btmnav/main.png";
      angular.element(document.querySelector('.menu_index')).removeClass('btm_menu_item_active');
//    angular.element(document.querySelector('.navdesc')).removeClass('zi');
      
      $rootScope.nav2 = "images/btmnav/fu.png";
      angular.element(document.querySelector('.menu_fujin')).removeClass('btm_menu_item_active');  
//    angular.element(document.querySelector('.navdesc')).removeClass('zi');
      
      $rootScope.nav3 = "images/btmnav/tui.png";
      angular.element(document.querySelector('.menu_tuiguang')).removeClass('btm_menu_item_active');
//    angular.element(document.querySelector('.navdesc')).removeClass('zi');
      
      $rootScope.nav4 = "images/btmnav/ge.png";
      angular.element(document.querySelector('.menu_user')).removeClass('btm_menu_item_active');
//    angular.element(document.querySelector('.navdesc')).removeClass('zi');
     

      switch(nav){  
        case 1:{
          $rootScope.nav1 = "images/btmnav/mainlan.png";
          angular.element(document.querySelector('.menu_index')).addClass('btm_menu_item_active');
//        angular.element(document.querySelector('.navdesc')).addClass('zi');    
          break;
        }
        case 2:{
          $rootScope.nav2 = "images/btmnav/fulan.png";
          angular.element(document.querySelector('.menu_fujin')) .addClass('btm_menu_item_active');
//        angular.element(document.querySelector('.navdesc')).addClass('zi');    
          break;
        }
        case 3:{
          $rootScope.nav3 = "images/btmnav/tuilan.png";
          angular.element(document.querySelector('.menu_tuiguang')).addClass('btm_menu_item_active');
//        angular.element(document.querySelector('.navdesc')).addClass('zi');          
          break;
        }
        case 4:{
          $rootScope.nav4 = "images/btmnav/gelan.png";
          angular.element(document.querySelector('.menu_user')).addClass('btm_menu_item_active');
//        angular.element(document.querySelector('.navdesc')).addClass('zi');       
          break;
        }
      }
    },
    MD5 : function (string) {

    function RotateLeft(lValue, iShiftBits) {
      return (lValue<<iShiftBits) | (lValue>>>(32-iShiftBits));
    }

    function AddUnsigned(lX,lY) {
      var lX4,lY4,lX8,lY8,lResult;
      lX8 = (lX & 0x80000000);
      lY8 = (lY & 0x80000000);
      lX4 = (lX & 0x40000000);
      lY4 = (lY & 0x40000000);
      lResult = (lX & 0x3FFFFFFF)+(lY & 0x3FFFFFFF);
      if (lX4 & lY4) {
        return (lResult ^ 0x80000000 ^ lX8 ^ lY8);
      }
      if (lX4 | lY4) {
        if (lResult & 0x40000000) {
          return (lResult ^ 0xC0000000 ^ lX8 ^ lY8);
        } else {
          return (lResult ^ 0x40000000 ^ lX8 ^ lY8);
        }
      } else {
        return (lResult ^ lX8 ^ lY8);
      }
    }

    function F(x,y,z) { return (x & y) | ((~x) & z); }
    function G(x,y,z) { return (x & z) | (y & (~z)); }
    function H(x,y,z) { return (x ^ y ^ z); }
    function I(x,y,z) { return (y ^ (x | (~z))); }

    function FF(a,b,c,d,x,s,ac) {
      a = AddUnsigned(a, AddUnsigned(AddUnsigned(F(b, c, d), x), ac));
      return AddUnsigned(RotateLeft(a, s), b);
    };

    function GG(a,b,c,d,x,s,ac) {
      a = AddUnsigned(a, AddUnsigned(AddUnsigned(G(b, c, d), x), ac));
      return AddUnsigned(RotateLeft(a, s), b);
    };

    function HH(a,b,c,d,x,s,ac) {
      a = AddUnsigned(a, AddUnsigned(AddUnsigned(H(b, c, d), x), ac));
      return AddUnsigned(RotateLeft(a, s), b);
    };

    function II(a,b,c,d,x,s,ac) {
      a = AddUnsigned(a, AddUnsigned(AddUnsigned(I(b, c, d), x), ac));
      return AddUnsigned(RotateLeft(a, s), b);
    };

    function ConvertToWordArray(string) {
      var lWordCount;
      var lMessageLength = string.length;
      var lNumberOfWords_temp1=lMessageLength + 8;
      var lNumberOfWords_temp2=(lNumberOfWords_temp1-(lNumberOfWords_temp1 % 64))/64;
      var lNumberOfWords = (lNumberOfWords_temp2+1)*16;
      var lWordArray=Array(lNumberOfWords-1);
      var lBytePosition = 0;
      var lByteCount = 0;
      while ( lByteCount < lMessageLength ) {
        lWordCount = (lByteCount-(lByteCount % 4))/4;
        lBytePosition = (lByteCount % 4)*8;
        lWordArray[lWordCount] = (lWordArray[lWordCount] | (string.charCodeAt(lByteCount)<<lBytePosition));
        lByteCount++;
      }
      lWordCount = (lByteCount-(lByteCount % 4))/4;
      lBytePosition = (lByteCount % 4)*8;
      lWordArray[lWordCount] = lWordArray[lWordCount] | (0x80<<lBytePosition);
      lWordArray[lNumberOfWords-2] = lMessageLength<<3;
      lWordArray[lNumberOfWords-1] = lMessageLength>>>29;
      return lWordArray;
    };

    function WordToHex(lValue) {
      var WordToHexValue="",WordToHexValue_temp="",lByte,lCount;
      for (lCount = 0;lCount<=3;lCount++) {
        lByte = (lValue>>>(lCount*8)) & 255;
        WordToHexValue_temp = "0" + lByte.toString(16);
        WordToHexValue = WordToHexValue + WordToHexValue_temp.substr(WordToHexValue_temp.length-2,2);
      }
      return WordToHexValue;
    };

    function Utf8Encode(string) {
      string = string.replace(/\r\n/g,"\n");
      var utftext = "";

      for (var n = 0; n < string.length; n++) {

        var c = string.charCodeAt(n);

        if (c < 128) {
          utftext += String.fromCharCode(c);
        }
        else if((c > 127) && (c < 2048)) {
          utftext += String.fromCharCode((c >> 6) | 192);
          utftext += String.fromCharCode((c & 63) | 128);
        }
        else {
          utftext += String.fromCharCode((c >> 12) | 224);
          utftext += String.fromCharCode(((c >> 6) & 63) | 128);
          utftext += String.fromCharCode((c & 63) | 128);
        }

      }

      return utftext;
    };

    var x=Array();
    var k,AA,BB,CC,DD,a,b,c,d;
    var S11=7, S12=12, S13=17, S14=22;
    var S21=5, S22=9 , S23=14, S24=20;
    var S31=4, S32=11, S33=16, S34=23;
    var S41=6, S42=10, S43=15, S44=21;

    string = Utf8Encode(string);

    x = ConvertToWordArray(string);

    a = 0x67452301; b = 0xEFCDAB89; c = 0x98BADCFE; d = 0x10325476;

    for (k=0;k<x.length;k+=16) {
      AA=a; BB=b; CC=c; DD=d;
      a=FF(a,b,c,d,x[k+0], S11,0xD76AA478);
      d=FF(d,a,b,c,x[k+1], S12,0xE8C7B756);
      c=FF(c,d,a,b,x[k+2], S13,0x242070DB);
      b=FF(b,c,d,a,x[k+3], S14,0xC1BDCEEE);
      a=FF(a,b,c,d,x[k+4], S11,0xF57C0FAF);
      d=FF(d,a,b,c,x[k+5], S12,0x4787C62A);
      c=FF(c,d,a,b,x[k+6], S13,0xA8304613);
      b=FF(b,c,d,a,x[k+7], S14,0xFD469501);
      a=FF(a,b,c,d,x[k+8], S11,0x698098D8);
      d=FF(d,a,b,c,x[k+9], S12,0x8B44F7AF);
      c=FF(c,d,a,b,x[k+10],S13,0xFFFF5BB1);
      b=FF(b,c,d,a,x[k+11],S14,0x895CD7BE);
      a=FF(a,b,c,d,x[k+12],S11,0x6B901122);
      d=FF(d,a,b,c,x[k+13],S12,0xFD987193);
      c=FF(c,d,a,b,x[k+14],S13,0xA679438E);
      b=FF(b,c,d,a,x[k+15],S14,0x49B40821);
      a=GG(a,b,c,d,x[k+1], S21,0xF61E2562);
      d=GG(d,a,b,c,x[k+6], S22,0xC040B340);
      c=GG(c,d,a,b,x[k+11],S23,0x265E5A51);
      b=GG(b,c,d,a,x[k+0], S24,0xE9B6C7AA);
      a=GG(a,b,c,d,x[k+5], S21,0xD62F105D);
      d=GG(d,a,b,c,x[k+10],S22,0x2441453);
      c=GG(c,d,a,b,x[k+15],S23,0xD8A1E681);
      b=GG(b,c,d,a,x[k+4], S24,0xE7D3FBC8);
      a=GG(a,b,c,d,x[k+9], S21,0x21E1CDE6);
      d=GG(d,a,b,c,x[k+14],S22,0xC33707D6);
      c=GG(c,d,a,b,x[k+3], S23,0xF4D50D87);
      b=GG(b,c,d,a,x[k+8], S24,0x455A14ED);
      a=GG(a,b,c,d,x[k+13],S21,0xA9E3E905);
      d=GG(d,a,b,c,x[k+2], S22,0xFCEFA3F8);
      c=GG(c,d,a,b,x[k+7], S23,0x676F02D9);
      b=GG(b,c,d,a,x[k+12],S24,0x8D2A4C8A);
      a=HH(a,b,c,d,x[k+5], S31,0xFFFA3942);
      d=HH(d,a,b,c,x[k+8], S32,0x8771F681);
      c=HH(c,d,a,b,x[k+11],S33,0x6D9D6122);
      b=HH(b,c,d,a,x[k+14],S34,0xFDE5380C);
      a=HH(a,b,c,d,x[k+1], S31,0xA4BEEA44);
      d=HH(d,a,b,c,x[k+4], S32,0x4BDECFA9);
      c=HH(c,d,a,b,x[k+7], S33,0xF6BB4B60);
      b=HH(b,c,d,a,x[k+10],S34,0xBEBFBC70);
      a=HH(a,b,c,d,x[k+13],S31,0x289B7EC6);
      d=HH(d,a,b,c,x[k+0], S32,0xEAA127FA);
      c=HH(c,d,a,b,x[k+3], S33,0xD4EF3085);
      b=HH(b,c,d,a,x[k+6], S34,0x4881D05);
      a=HH(a,b,c,d,x[k+9], S31,0xD9D4D039);
      d=HH(d,a,b,c,x[k+12],S32,0xE6DB99E5);
      c=HH(c,d,a,b,x[k+15],S33,0x1FA27CF8);
      b=HH(b,c,d,a,x[k+2], S34,0xC4AC5665);
      a=II(a,b,c,d,x[k+0], S41,0xF4292244);
      d=II(d,a,b,c,x[k+7], S42,0x432AFF97);
      c=II(c,d,a,b,x[k+14],S43,0xAB9423A7);
      b=II(b,c,d,a,x[k+5], S44,0xFC93A039);
      a=II(a,b,c,d,x[k+12],S41,0x655B59C3);
      d=II(d,a,b,c,x[k+3], S42,0x8F0CCC92);
      c=II(c,d,a,b,x[k+10],S43,0xFFEFF47D);
      b=II(b,c,d,a,x[k+1], S44,0x85845DD1);
      a=II(a,b,c,d,x[k+8], S41,0x6FA87E4F);
      d=II(d,a,b,c,x[k+15],S42,0xFE2CE6E0);
      c=II(c,d,a,b,x[k+6], S43,0xA3014314);
      b=II(b,c,d,a,x[k+13],S44,0x4E0811A1);
      a=II(a,b,c,d,x[k+4], S41,0xF7537E82);
      d=II(d,a,b,c,x[k+11],S42,0xBD3AF235);
      c=II(c,d,a,b,x[k+2], S43,0x2AD7D2BB);
      b=II(b,c,d,a,x[k+9], S44,0xEB86D391);
      a=AddUnsigned(a,AA);
      b=AddUnsigned(b,BB);
      c=AddUnsigned(c,CC);
      d=AddUnsigned(d,DD);
    }

    var temp = WordToHex(a)+WordToHex(b)+WordToHex(c)+WordToHex(d);

    return temp.toLowerCase();
  },
    tip: function (msg){
        layer.open({
            content: msg,
            skin: 'msg',
            time: 2, //2秒后自动关闭
        });
    },
  };





  return util;
}]);