var auditfailure = angular.module('auditfailureController', ['webapp','LocalStorageModule']);

auditfailure.controller('auditfailureController',['util',function(util){
    util.navConfig(1);
}]);
auditfailure.controller('auditfailureController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
    util.headerConfig('申请失败',true);
    //跳转
    $scope.toUrl=function(url){
    	util.toUrl(url)
    }
    
    //重新申请
    $scope.chong=function(){
    	 localStorage.clear();
    	 util.toUrl("/mct/registermd");
    }
}]);
