/**
 * Created by Administrator on 2017/9/13.
 */

var whatfen = angular.module('whatfenController', ['webapp','LocalStorageModule']);

whatfen.controller('whatfenNavController',['util',function(util){
  util.navConfig(1);
}]);
whatfen.controller('whatfenController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','$http',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,$http) {
  util.headerConfig('如何发展粉丝？',true);
//alert(1)

  
}]);
