/**
 * Created by Administrator on 2017/9/13.
 */
var completed = angular.module('completedOrdersController', ['webapp','LocalStorageModule','orderService']);

completed.controller('completedNavController',['util',function(util){
  util.navConfig(1);
}]);
completed.controller('completedOrdersController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','$http','orderService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,$http,orderService) {
  util.headerConfig('已完成订单',true);


//已完成列表
var uid=localStorageService.uid;
$scope.finish=orderService;
//orderService.finish();

//跳转商家详情
	var mid = $stateParams.mid;
	$scope.xiang=function(mid){
		var url = "/user/details/"+mid;
		util.toUrl(url);
	}
   
}]);
