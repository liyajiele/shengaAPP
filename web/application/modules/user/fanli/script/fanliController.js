/**
 * Created by Administrator on 2017/9/13.
 */
var fanli = angular.module('fanliController', ['webapp','LocalStorageModule','orderService']);
fanli.controller('fanliNavController',['util',function(util){
  util.navConfig(1);
}]);


fanli.controller('fanliController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','orderService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,orderService) {
    util.headerConfig('返利订单',true);
    
 
	var uid=localStorageService.get("uid")
	$scope.fanli=orderService;
	
	orderService.fanliArray=[];
    orderService.fanliPage=0;
    orderService.fanliBusy=false;

	console.log($scope.fanli)

  $scope.xuan=true;
  $scope.active=function () {
    $scope.xuan=!$scope.xuan;
  }
  
  //时间选择完成
  $scope.active1=function () {
  	 //转换时间戳
  	var start=$(" input[ name='start_date'] ").val();
  	console.log(start)
    $scope.start=new Date(start).getTime();
    
	var end=$(" input[ name='end_date'] ").val();
    $scope.end=new Date(end).getTime();
    if(start==""){
    	util.tip("请输入开始时间")
    	return;
    }
    if(end==""){
    	util.tip("请输入结束时间")
    	return;
    	
    }
    if($scope.start>=$scope.end){
    	util.tip("结束时间不可小于开始时间！")
    }else{
    	$scope.xuan=!$scope.xuan;
	    orderService.fanliArray=[];
	    orderService.fanliPage=0;
	    orderService.fanliBusy=false;
	    console.log($scope.start)
	    orderService.start=$scope.start;      //时间
	    orderService.end=$scope.end;        //时间
	    this.finishnum=0;
	    this.finishBalancenum=0;
	    orderService.fanli();  
    }
  	
  	
    

  }

}]);
