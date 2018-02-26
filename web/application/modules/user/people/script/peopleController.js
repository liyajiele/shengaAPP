/**
 * Created by Administrator on 2017/9/13.
 */
var people = angular.module('peopleController', ['webapp','LocalStorageModule','userService','paytiService','orderService','aboutService','accountService']);

people.controller('peopleNavController',['util',function(util){
  util.navConfig(4);
}]);
people.controller('peopleController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','userService','paytiService','orderService','aboutService','accountService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,userService,paytiService,orderService,aboutService,accountService) {
  util.headerConfig('个人中心',true);
//alert(1)
var uid=localStorageService.get("uid");
  userService.people(uid).success(function(resp){
		$scope.aboutInfo = resp.object;
		var pad = "000000";  
        $scope.codema= 'SA'+pad.substring(0, pad.length - uid.length) + uid ; 

	});
	
	//兑吧跳转
	$scope.duiba=function(){    //兑吧
			var time=new Date().getTime();
			console.log(time)
			userService.duiba().success(function(resp){
				console.log(resp.object)
				window.location.href=resp.object;
			})
		}
	
   $scope.toUrl = function(url){
       util.toUrl(url)
   }

//打电话
	$scope.callPhone = function (officialMobile) {
	//console.log("拨打:" + mobilePhone);
	  $window.location.href = "tel:" + officialMobile;
	};

//收藏数量
	userService.collectnums().success(function(resp){
		$scope.collectnums=resp.object;
//		console.log($scope.collectnums);
	})
	
//待评价订单
	orderService.unnum().success(function(resp){
		$scope.unnum=resp.object;
//		console.log($scope.unnum)
	})
   
//账户信息
	accountService.getAccountInfo().success(function(resp){
        $scope.userInfo = resp.object;
        $scope.money=$scope.userInfo.balance+$scope.userInfo.redBalance+$scope.userInfo.retateBalance;
        console.log($scope.money)
    	console.log($scope.userInfo)
  });
   
}]);
