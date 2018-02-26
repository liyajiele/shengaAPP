/**
 * Created by Administrator on 2017/9/13.
 */

var whatfennum = angular.module('whatfennumController', ['webapp','LocalStorageModule']);

whatfennum.controller('whatfennumNavController',['util',function(util){
  util.navConfig(1);
}]);
whatfennum.controller('whatfennumController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','$http',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,$http) {
  util.headerConfig('如何查看粉丝数量？',true);
//alert(1)

  
}]);
