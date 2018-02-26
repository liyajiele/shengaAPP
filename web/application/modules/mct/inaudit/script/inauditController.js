var inaudit = angular.module('inauditController', ['webapp','LocalStorageModule']);

inaudit.controller('inauditController',['util',function(util){
    util.navConfig(1);
}]);
inaudit.controller('inauditController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
    util.headerConfig('审核中',true);
    
//跳转
    $scope.toUrl=function(url){
    	util.toUrl(url)
    }
    $scope.close=function(){
    	window.close();    //关闭浏览器
    }
}]);
