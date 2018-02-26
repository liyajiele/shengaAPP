/**
 * Created by Administrator on 2017/9/13.
 */

var gong = angular.module('gongController', ['webapp','LocalStorageModule']);

gong.controller('gongNavController',['util',function(util){
  util.navConfig(1);
}]);
gong.controller('gongController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','$http',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,$http) {
  util.headerConfig('省啊攻略',true);

  //跳转
    $scope.toUrl = function(url){
        util.toUrl(url);
    }
}]);
