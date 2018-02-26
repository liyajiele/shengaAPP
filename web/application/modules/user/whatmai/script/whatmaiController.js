/**
 * Created by Administrator on 2017/9/13.
 */

var whatmai = angular.module('whatmaiController', ['webapp','LocalStorageModule']);

whatmai.controller('whatmaiNavController',['util',function(util){
  util.navConfig(1);
}]);
whatmai.controller('whatmaiController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','$http',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,$http) {
  util.headerConfig('如何使用省啊买单？',true);
//alert(1)

  
}]);
