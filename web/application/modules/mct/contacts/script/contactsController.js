/**
 * Created by Administrator on 2017/9/13.
 */
var contacts = angular.module('contactsController', ['webapp','LocalStorageModule',"userService"]);
contacts.controller('contactsNavController',['util',function(util){
  util.navConfig(1);
}]);


contacts.controller('contactsController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','userService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,userService) {
     util.headerConfig('我的人脉',true);
     
    var mid=localStorageService.get("mid");
    $scope.mid=mid;
     
//获取门店信息
userService.mct().success(function(resp){
	console.log(resp)
	$scope.name=resp.object.content[0].ownerRealName;
	$scope.mid=resp.object.content[0].id;
	console.log($scope.mid);
})

//获取下级列表
  	$scope.child=userService;
    userService.childArray=[];
    userService.childPage=0;
    userService.childBusy=false;
    
    
    
    
}]);

