/**
 * Created by Administrator on 2017/9/13.
 */

var tisuccess = angular.module('tisuccessController', ['webapp','LocalStorageModule']);

tisuccess.controller('tisuccessNavController',['util',function(util){
  util.navConfig(1);
}]);
tisuccess.controller('tisuccessController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','$http',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,$http) {
  util.headerConfig('申请提现',true);
//alert(1)
//跳转
	$scope.toUrl = function(url){
       util.toUrl(url)
    }
	
  
}]);
