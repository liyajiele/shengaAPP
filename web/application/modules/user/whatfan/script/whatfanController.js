/**
 * Created by Administrator on 2017/9/13.
 */

var whatfan = angular.module('whatfanController', ['webapp','LocalStorageModule']);

whatfan.controller('whatfanNavController',['util',function(util){
  util.navConfig(1);
}]);
whatfan.controller('whatfanController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','$http',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,$http) {
  util.headerConfig('如何查看自己及粉丝的消费返利？',true);
//alert(1)

  
}]);
