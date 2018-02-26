/**
 * Created by Administrator on 2017/9/13.
 */

var shu = angular.module('shuController', ['webapp','LocalStorageModule']);

shu.controller('shuNavController',['util',function(util){
  util.navConfig(1);
}]);
shu.controller('shuController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','$http',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,$http) {
  util.headerConfig('承诺书',true);
//alert(1)

//	aboutService.aboutUs(2).success(function(resp){
//		$scope.aboutInfo = resp.object;
////		console.log(resp);
//	});
  
}]);
