var authentication = angular.module('authenticationController', ['webapp','LocalStorageModule']);

authentication.controller('authenticationNavController',['util',function(util){
    util.navConfig(1);
}]);
authentication.controller('authenticationController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
    util.headerConfig('申请商户认证',true);
   
//跳转
$scope.toUrl=function(url){
	util.toUrl(url);
}
    
}]);
