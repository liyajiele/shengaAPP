/**
 * Created by Administrator on 2017/9/13.
 */
var payti = angular.module('paytiController', ['webapp','LocalStorageModule','paytiService','userService']);

payti.controller('paytiNavController',['util',function(util){
  util.navConfig(1);
}]);
payti.controller('paytiController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','paytiService','userService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,paytiService,userService) {
  util.headerConfig('用户提现',true);
//   alert(1)


    var mid=localStorageService.get("mid");
    
  $scope.toUrl = function(url){
       util.toUrl(url)
   }

	
	//获取账户信息
	userService.accountInfo(mid).success(function(resp){
		$scope.timoney=resp.object.balance+resp.object.redBalance+resp.object.retateBalance;   
		$scope.canDrawNum=resp.object.canDrawNum;
		console.log($scope.timoney)
	})
	
	


//全部提现
  $scope.allti=function () {
    $scope.drawNums=$scope.timoney;
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
					util.tip("申请成功！");
			        return;
		    }).error(function () {
		    	util.tip("申请失败！");
			   	return;
		    })
		}
 }


 

}]);
