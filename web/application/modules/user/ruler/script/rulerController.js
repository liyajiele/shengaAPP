/**
 * Created by Administrator on 2017/9/13.
 */
var ruler = angular.module('rulerController', ['webapp','LocalStorageModule']);

ruler.controller('rulerNavController',['util',function(util){
  util.navConfig(1);
}]);
ruler.controller('rulerController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
  util.headerConfig('提现规则',true);
}]);
