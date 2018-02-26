/**
 * Created by Administrator on 2017/9/13.
 */

var aboutour = angular.module('aboutourController', ['webapp','LocalStorageModule','aboutService']);

aboutour.controller('aboutourNavController',['util',function(util){
  util.navConfig(1);
}]);
aboutour.controller('aboutourController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','aboutService','$http',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,aboutService,$http) {
  util.headerConfig('关于我们',true);
//alert(1)

	aboutService.aboutUs(2).success(function(resp){
		$scope.aboutInfo = resp.object;
//		console.log(resp);
	});
  
}]);
