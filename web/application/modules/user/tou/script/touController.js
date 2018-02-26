/**
 * Created by Administrator on 2017/9/13.
 */
var tou = angular.module('touController', ['webapp','LocalStorageModule','accountRecordService']);

tou.controller('touNavController',['util',function(util){
  util.navConfig(1);
}]);
tou.controller('touController', ['$window','$location','$rootScope','$scope','$filter','util','cache','$stateParams','localStorageService','accountRecordService',function ($window,$location,$rootScope,$scope,$filter,util,cache,$stateParams,localStorageService,accountRecordService) {
  util.headerConfig('省啊头条',true);
  accountRecordService.newArray=[];
accountRecordService.newPage=0;
accountRecordService.newLast=false;
  //获取最新动态
  $scope.new=accountRecordService;
  console.log($scope.new)


	$scope.setcolor=function(newsType){
		var color="";
		var background="";
		if(1==newsType){     //红色
			background= "#fbe8eb";
      		color= "#e4657a";
		}else if(2==newsType){    // 蓝色
			background= "#ddecf9";
      		color= "#1c82d4";
		}else if(3==newsType){      //紫色
			background= "#f3e8fb";
      		color= "#ad65e4";
		}else if(4==newsType){     //绿色
			background= "#dff6d9";
      		color= "#29c200";
		}
		return {"background-color":background,"color":color}
	}








}]);


  