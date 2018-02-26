/**
 * Created by Administrator on 2017/9/13.
 */
var unassess = angular.module('unassessController', ['webapp','LocalStorageModule','merchantService','orderService']);

unassess.controller('unassessNavController',['util',function(util){
  util.navConfig(1);
}]);
unassess.controller('unassessController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','merchantService','orderService','$http',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,merchantService,orderService,$http) {
  util.headerConfig('未评价订单',true);

//获取订单列表
	$scope.order=orderService;
	console.log($scope.order.orderArray);
	
//跳转评价
	var uid=localStorageService.uid;
	$scope.ping=function(mid,id){
		var objid={};
		objid.id=id;
		sessionStorage.setItem("cunid",JSON.stringify(objid));
		event.stopPropagation();
		var url = "/user/grade/"+mid;
		util.toUrl(url);
//console.log(url)
	}

//跳转商家详情
	$scope.xiang=function(mid){
		var url = "/user/details/"+mid;
		util.toUrl(url);
//console.log(url)
	}
	
  	
//无评价订单
  	orderService.unnum().success(function(resp){
			if(resp.object=="0"){
				var be = document.querySelector(".bei");
				angular.element(be).css("display","block");
			}
  	})
  

}]);
