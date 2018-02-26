/**
 * Created by Administrator on 2017/9/13.
 */
var city = angular.module('cityController', ['webapp','LocalStorageModule','addressService','userService']);

city.controller('cityNavController',['util',function(util){
  util.navConfig(1);
}]);
city.controller('cityController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','addressService','userService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,addressService,userService) {
  util.headerConfig('城市选择',true);
  
  $scope.toUrl = function(url){
       util.toUrl(url)
   }
 //返回上级
 $scope.back=function(){
 	history.back();
 }

//历史记录
  addressService.searchrecord().success(function (resp) {
    $scope.searchrecord=resp.object.content;
//  console.log($scope.searchrecord)
  })

  //热门城市
  addressService.hotcity().success(function(resp){
    $scope.hotcity=resp.object;
//  console.log(resp.object);
  })

//城市列表
  addressService.citylist().success(function(resp){
  	$scope.citylist=resp.object;
//	console.log($scope.citylist);
  	$scope.pin=resp.object[0].pinyinArea;
//	console.log($scope.pin)
  })
  




//改变城市
var cunc =JSON.parse(localStorage.getItem("cunc"));   //注册缓存
console.log(cunc)
$scope.citycity=cunc.citycity;
	$scope.city=function(tId){
		for (var i=0;i<$scope.citylist.length;i++) {
			if($scope.citylist[i].tId==tId){
				$scope.citycity=document.querySelector(".ding").innerHTML;
				$scope.citycity=$scope.citylist[i].name;
				$scope.cityid=tId;
				console.log(tId)
				console.log($scope.cityid)
				util.tip("切换城市成功！")
				var objc={};
				objc.citycity=$scope.citycity;
				objc.cityid=$scope.cityid;
	//			console.log(objc.citycity)
				localStorage.setItem("cunc",JSON.stringify(objc)); 
				util.toUrl("/mct/registermd")
				
			}
		}
	}
	
	$scope.hotcitydian=function(recity,aa){
	//	alert("1")
		console.log(recity)
		console.log(aa)
		$scope.citycity=document.querySelector(".ding").innerHTML;
		console.log($scope.citycity)
		$scope.citycity=recity.city.name;
		$scope.cityid=recity.city.tId;
		util.tip("切换城市成功！")
		var objc={};
		objc.citycity=$scope.citycity;
		objc.cityid=$scope.cityid;
	//			console.log(objc.citycity)
		localStorage.setItem("cunc",JSON.stringify(objc)); 
		util.toUrl("/mct/registermd")
				
		
	}
























}]);
