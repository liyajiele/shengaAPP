/**
 * Created by Administrator on 2017/9/13.
 */
var payti = angular.module('paytiController', ['webapp','LocalStorageModule','paytiService','userService','accountService']);

payti.controller('paytiNavController',['util',function(util){
  util.navConfig(1);
}]);
payti.controller('paytiController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','paytiService','userService','accountService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,paytiService,userService,accountService) {
  util.headerConfig('用户提现',true);
//   alert(1)
  $scope.toUrl = function(url){
       util.toUrl(url)
   }
//localStorageService.add("uid",1);
  
//	paytiService.payti(uid,drawNum,drawCId).success(function(resp){
//		$scope.aboutInfo = resp.object;
//		console.log(resp)
//	});

	
//	console.log($scope.name);

  
//账户信息
	accountService.getAccountInfo().success(function(resp){
        $scope.userInfo = resp.object;
        $scope.money=$scope.userInfo.balance+$scope.userInfo.redBalance+$scope.userInfo.retateBalance;
        console.log($scope.money)
    	console.log($scope.userInfo)
  });

//显示用户所有的提现渠道
//	paytiService.alldraw().success(function(resp){
//		console.log(resp);
//	})
// 	var drawNum = $scope.drawNum;
//   var alipay = $scope.alipay;
//   var concatUser = $scope.concatUser;

//全部提现
  $scope.allti=function () {
    $scope.drawNums=$scope.money;
    $scope.drawNum=Math.floor($scope.drawNums);    //保留小数点两位
    console.log($scope.drawNum);
    
  }
 
  
//无id提现渠道
	$scope.tixian = function () {
		$scope.aa=angular.element(".concatUser").val();
		$scope.bb=angular.element(".aliPay").val();
		$scope.cc=angular.element(".allti").val();
		if($scope.aa==""){
			util.tip("请输入支付宝本人姓名！");
	        return;
//			console.log("请输入支付宝本人姓名！")
		}
		if($scope.bb==""){
			util.tip("请输入支付宝账号！");
			return;
//			console.log("请输入支付宝账号！")
		}
		
		if($scope.cc>$scope.drawNum){
			util.tip("提现金额大于总金额！")
			return;
		}
		if(Number.isInteger($scope.drawNum)=="false"){
			util.tip("提现金额为整数!");
			return;
		}
	 	if($scope.drawNum<10){
			util.tip("提现金额最低为10元！")
			return;
		}else{
			paytiService.noidti($scope.drawNum,$scope.alipay,$scope.concatuser).success(function(){
//					util.toUrl("/user/tisuccess");
		    }).error(function () {
		    	util.tip("申请失败！");
			   	return;
		    })
		}
 }


 

}]);
