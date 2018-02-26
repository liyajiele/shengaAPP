/**
 * Created by Administrator on 2017/9/13.
 */
var fanli = angular.module('fanliController', ['webapp','LocalStorageModule','orderService','userService']);
fanli.controller('fanliNavController',['util',function(util){
  util.navConfig(1);
}]);


fanli.controller('fanliController', ['$window','$location','$rootScope','$scope','$timeout','util','cache','$stateParams','localStorageService','orderService','userService',function ($window,$location,$rootScope,$scope,$timeout,util,cache,$stateParams,localStorageService,orderService,userService) {
     util.headerConfig('订单管理',true);
     
	var uid=localStorageService.get("uid");
	var mid=localStorageService.get("mid");
	console.log(mid)
	console.log(uid)
	$scope.mid=mid;
		$scope.fanli=orderService;
		orderService.fanliArray=[];
	    orderService.fanliPage=0;
	    orderService.fanliBusy=false;

	//获取门店信息
//userService.mct().success(function(resp){
//	$scope.name=resp.object.content[0].ownerRealName;
//	$scope.tumover=resp.object.content[0].tumover;
//	$scope.orderNum=resp.object.content[0]. orderNum;
//	$scope.mid=resp.object.content[0].id;
//	console.log($scope.mid);
//})	
	
	
	
	

  $scope.xuan=true;
  $scope.active=function () {
    $scope.xuan=!$scope.xuan;
  }
  
  //时间选择完成
  $scope.active1=function () {
  	 //转换时间戳
  	var start=$(" input[ name='start_date'] ").val();
//	console.log(start)
    $scope.start=new Date(start).getTime();
$scope.starta=start;
    console.log($scope.start);
	var end=$(" input[ name='end_date'] ").val();
$scope.enda=end;
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
	    orderService.fanli($scope.mid,4,$scope.start,$scope.end);  
    }
  	
  	
    

  }
}]);
