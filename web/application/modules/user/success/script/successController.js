/**
 * Created by Administrator on 2017/9/13.
 */
var success = angular.module('successController', ['webapp','LocalStorageModule','merchantService','merchantService','userService']);

success.controller('successNavController',['util',function(util){
  util.navConfig(1);
}]);
success.controller('successController', ['cache','$filter','$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','merchantService','merchantService','userService',function (cache,$filter,$window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,merchantService,merchantService,userService) {
  util.headerConfig('支付成功',true);
  
	var mid = $stateParams.mid;
 //附近商家
	$scope.merchant=merchantService;          //mct.distance
	//merchantService.nearmerchant();
	console.log($scope.merchant)
//跳转商家详情
	$scope.xiang=function(mid){
		var url = "/user/details/"+mid;
		console.log(url)
		util.toUrl(url);
	}
	
  //跳转
   	$scope.toUrl = function(url){
        util.toUrl(url);
    }
  	
  	var  orderRst = cache.get("orderRst");
  	cache.get("orderRst");
  	$scope.totalAmount=orderRst.totalAmount;
  	$scope.orderNum=orderRst.orderNum;
  	$scope.rebateAmount=orderRst.rebateAmount;
  	$scope.rebate=orderRst.rebate;
  	$scope.createTime=$filter("date")(orderRst.createTime,'yyyy-MM-dd HH:mm:ss');
  	$scope.nickname=orderRst.merchant.nickname;
//	console.log(orderRst)

   //商家详情
	merchantService.details(mid).success(function(resp){
//	  console.log(resp);
	    $scope.details=resp.object;
	    if($scope.details.hasCollect==0){		  //判断是否收藏
			//收藏与取消收藏
			$scope.xuan=false;
			$scope.display=function () {
		    	$scope.xuan=!$scope.xuan;
			    if($scope.xuan){
			    	$scope.type=1;
			    	console.log($scope.type)
			    }else{
			    	$scope.type=0;
			    	console.log($scope.type)
			    }    
			    userService.collect(mid,$scope.type).success(function (resp) {
				    console.log(resp);
				    if($scope.type==1){
				    	 util.tip("收藏店铺成功!")
				    }else{
				    	 util.tip("取消店铺成功!")		    	
				    }
				   
				})
			}
		}else{
			$scope.xuan=true;
		}
	})
	

	
	console.log(location.href);
	if(location.href=="http://127.0.0.1:8020/shenga/web/index.html#/user/index"){
		location.href=="http://127.0.0.1:8020/shenga/web/index.html#/user/index";
	}else if(location.href=="http://127.0.0.1:8020/shenga/web/index.html#/user/success"+mid){
		location.href=="http://127.0.0.1:8020/shenga/web/index.html#/user/success"+mid;
	}else if(location.href=="http://127.0.0.1:8020/shenga/web/index.html#/user/pay"+mid){
		location.href="http://127.0.0.1:8020/shenga/web/index.html#/user/details/"+mid;
		
	}

	

	
	
	
	$scope.max = 5;
  $scope.ratingVal = 3;
  $scope.readonly = true;
  $scope.onHover = function(val){
    $scope.hoverVal = val;
  };
  $scope.onLeave = function(){
    $scope.hoverVal = null;
  }
  $scope.onChange = function(val){
    $scope.ratingVal = val;
  }

   }])


//app.run(['$rootScope', '$location' ,'$cookieStore', '$state', 'CacheManager',  function($rootScope, $location, $cookieStore, $state,CacheManager){
////监听路由事件
//
//      $rootScope.$on('$stateChangeStart',function(event, toState, toParams, fromState, fromParams){
//
////              if(toState.name=="tabs.post"&&fromState.name=="tabs.orderList"){
////console.log("sssss")
//                     $location.path();//获取路由地址
//
////                  $location.path('/tabs/home');//设置路由地址
//
////              }
//
//      })
//
//}]);

//success.run(['$rootScope', '$location', function($rootScope, $location) {    //监测路由变化，小键盘消失
//  /* 监听路由的状态变化 */
//  $rootScope.$on('$stateChangeSuccess', function(evt, current, previous) {
////   console.log('route have already changed ：'+$location.path());
////   var jian=document.querySelector(".ngcalculator_area");
////   angular.element(jian).css("display","none");
//CONSOLE.LO
//  }); 
// }])




//星星
app.directive('star', function () {
  return {
    template: '<ul class="rating" ng-mouseleave="leave()">' +
        '<li ng-repeat="star in stars" ng-class="star" ng-click="click($index + 1)" ng-mouseover="over($index + 1)" style="font-size:0.4rem">' +
        '<i class="iconfont icon-xing"></i>' +
        '</li>' +
        '</ul>',
    scope: {
      ratingValue: '=',
      max: '=',
      readonly: '@',
      onHover: '=',
      onLeave: '='
    },
    controller: function($scope){
      $scope.ratingValue = $scope.ratingValue || 0;
      $scope.max = $scope.max || 5;
      $scope.click = function(val){
        if ($scope.readonly && $scope.readonly === 'true') {
          return;
        }
        $scope.ratingValue = val;
      };
      $scope.over = function(val){
        $scope.onHover(val);
      };
      $scope.leave = function(){
        $scope.onLeave();
      }
    },
    link: function (scope, elem, attrs) {
      elem.css("text-align", "center");
      var updateStars = function () {
        scope.stars = [];
        for (var i = 0; i < scope.max; i++) {
          scope.stars.push({
            filled: i < scope.ratingValue
          });
        }
      };
      updateStars();
 
      scope.$watch('ratingValue', function (oldVal, newVal) {
        if (newVal) {
          updateStars();
        }
      });
      scope.$watch('max', function (oldVal, newVal) {
        if (newVal) {
          updateStars();
        }
      });
    }
  };
});
