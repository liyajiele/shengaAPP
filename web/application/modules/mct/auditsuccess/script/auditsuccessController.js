var auditsuccess = angular.module('auditsuccessController', ['webapp','LocalStorageModule']);

auditsuccess.controller('auditsuccessController',['util',function(util){
    util.navConfig(1);
}]);
auditsuccess.controller('auditsuccessController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
    util.headerConfig('申请成功',true);
    
    
    //跳转
    $scope.toUrl=function(url){
    	util.toUrl(url)
    }
}]);
