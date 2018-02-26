/**
 * Created by Administrator on 2017/9/13.
 */
var redruler = angular.module('redrulerController', ['webapp','LocalStorageModule']);

redruler.controller('redrulerNavController',['util',function(util){
  util.navConfig(1);
}]);
redruler.controller('redrulerController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
  util.headerConfig('摇红包规则',true);
}]);
