/**
 * Created by Administrator on 2017/9/13.
 */
var contacts = angular.module('contactsController', ['webapp','LocalStorageModule',"userService"]);
contacts.controller('contactsNavController',['util',function(util){
  util.navConfig(1);
}]);


contacts.controller('contactsController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','userService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,userService) {
     util.headerConfig('我的人脉',true);
  
  //获取下级列表
  $scope.child=userService;
  
  $scope.xuan=true;
  $scope.active=function (childType) {
    $scope.xuan=!$scope.xuan;
    userService.childArray=[];
    userService.childPage=0;
    userService.childBusy=false;
    userService.child(childType);   //
  }

  
  
  
  //获取下级人数
  userService.childnum().success(function(resp){
  	$scope.childnum=resp.object;
  	console.log(resp);
  })
}]);

