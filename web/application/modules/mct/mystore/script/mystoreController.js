var mystore = angular.module('mystoreController', ['webapp','LocalStorageModule','userService']);

mystore.controller('mystoreController',['util',function(util){
    util.navConfig(1);
}]);
mystore.controller('mystoreController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','userService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,userService) {
    util.headerConfig('我的门店',true);
    
//判断登录状态
	var uid=$stateParams.uid;
	var mid = $stateParams.mid;
	var token = $stateParams.token;
	if(uid && mid && token){
		localStorageService.set("uid",uid);
		localStorageService.set("mid",mid);
		localStorageService.set("token",token);
		if(mid==-1){
			util.toUrl("/mct/authentication");
		}else{
			util.toUrl("/mct/mystore");
			return;
		}
	}
	//获取审核信息
		var cunstate =JSON.parse(localStorage.getItem("cunstate"));
		console.log(cunstate);
		if(cunstate){
			util.toUrl("/mct/mystore");
		}else{
			userService.shen().success(function(resp){
	//			console.log(resp)
				$scope.sheng=resp.object;
				console.log($scope.sheng[0].auditStatus);
				if($scope.sheng[0].auditStatus==1){
					util.toUrl("/mct/auditsuccess");     //审核通过
					var state={};
					state.status=$scope.sheng[0].auditStatus;
					localStorage.setItem("cunstate",JSON.stringify(state));
					return;
				}else if($scope.sheng[0].auditStatus==2){
					util.toUrl("/mct/auditfailure");     //审核失败	
					return;
				}else if($scope.sheng[0].auditStatus==0){
					util.toUrl("/mct/inaudit");     //审核中
					return;
				}
			})
		}
		


    
//跳转
	$scope.toUrl = function(url){
       util.toUrl(url)
    }
	
//获取门店信息
userService.mct().success(function(resp){
	console.log(resp)
	$scope.name=resp.object.content[0].ownerRealName;
	$scope.tumover=resp.object.content[0].tumover;
	$scope.orderNum=resp.object.content[0]. orderNum;
	$scope.mid=resp.object.content[0].id;
	console.log($scope.mid);
	  localStorageService.set("mid",$scope.mid);
	//获取账户信息
	userService.accountInfo($scope.mid).success(function(resp){
		$scope.timoney=resp.object.balance+resp.object.redBalance+resp.object.retateBalance;   
		console.log($scope.timoney)
	})
//	
//	
})
//$scope.mct=userService;
//console.log($scope.mct)
//console.log($scope.mct.mctArray);
















}]);
