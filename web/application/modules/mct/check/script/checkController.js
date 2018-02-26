var check = angular.module('checkController', ['webapp','LocalStorageModule']);

check.controller('checkController',['util',function(util){
    util.navConfig(1);
}]);
check.controller('checkController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
    util.headerConfig('附近商家',true);
}]);
