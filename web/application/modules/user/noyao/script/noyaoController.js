/**
 * Created by Administrator on 2017/9/13.
 */
var noyao = angular.module('noyaoController', ['webapp','LocalStorageModule','accountRecordService','userService','accountService']);

noyao.controller('noyaoNavController',['util',function(util){
  util.navConfig(1);
}]);
noyao.controller('noyaoController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','accountRecordService','userService','accountService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,accountRecordService,userService,accountService) {
  util.headerConfig('摇红包',true);

	var uid=localStorageService.get("uid");
	$scope.uid=uid;
//	accountRecordService.myrecordArray=[];     //获取我的账户记录
//  accountRecordService.myrecordPage = 0;
//  accountRecordService.myrecordBusy = false;
//当天时间戳
	$scope.end=new Date(new Date().toLocaleDateString()).getTime()+24*60*60*1000-1;
	$scope.start=new Date(new Date().toLocaleDateString()).getTime();
	console.log($scope.start)
	console.log($scope.end)
//自己获取红包的记录
//	accountRecordService.myrecordArray=[];     //获取我的账户记录
//	accountRecordService.myrecordPage = 0;
//  accountRecordService.myrecordBusy = false; 
	$scope.myrecord=accountRecordService;
  	console.log($scope.myrecord)
	
	
//头部滚动
  	accountRecordService.pack().success(function (resp) {
	    $scope.new=resp.object.content;
//		console.log($scope.new)
  	})



//红包次数
	accountService.getAccountInfo().success(function(resp){
		$scope.money=resp.object;
		console.log($scope.money);
		if($scope.money.canGetRebNum==0){
			util.tip("红包次数已用完！");
			return;
			var aa=document.querySelector(".packbig");
			angular.element(aa).css("display","none");
			var bb=document.querySelector(".tou");
			angular.element(bb).css("display","none");
			var dd=document.querySelector(".packno");
			angular.element(dd).css("display","none");
		}else{
				//运动事件监听
			    if (window.DeviceMotionEvent) {
			        window.addEventListener('devicemotion',deviceMotionHandler,false);
			    }
			
			    //获取加速度信息
			    //通过监听上一步获取到的x, y, z 值在一定时间范围内的变化率，进行设备是否有进行晃动的判断。
			    //而为了防止正常移动的误判，需要给该变化率设置一个合适的临界值。
			    var SHAKE_THRESHOLD = 4000;
			    var last_update = 0;
			    var x, y, z, last_x = 0, last_y = 0, last_z = 0;
			    function deviceMotionHandler(eventData) {
			        var acceleration =eventData.accelerationIncludingGravity;
			        var curTime = new Date().getTime();
			        if ((curTime-last_update)> 20) {
			            var diffTime = curTime -last_update;
			            last_update = curTime;
			            x = acceleration.x;
			            y = acceleration.y;
			            z = acceleration.z;
			            var speed = Math.abs(x +y + z - last_x - last_y - last_z) / diffTime * 10000;
			            if (speed > SHAKE_THRESHOLD) {
							accountService.redpack().success(function(resp){    //获取红包
								console.log(resp.object);
								$scope.redmoney=resp.object;
								if($scope.redmoney.amount=="0.00"){	
									var aa=document.querySelector(".packbig");
									angular.element(aa).css("display","none");
									var bb=document.querySelector(".tou");
									angular.element(bb).css("display","block");
									var dd=document.querySelector(".packno");
									angular.element(dd).css("display","block");
									
									$scope.qu=function(){
										var bb=document.querySelector(".tou");
										angular.element(bb).css("display","none");
										var dd=document.querySelector(".packno");
										angular.element(dd).css("display","none");
									}
								}else{
									$scope.redmoneya=$scope.redmoney.amount;
									var aa=document.querySelector(".packbig");
									var bb=document.querySelector(".tou");
									var dd=document.querySelector(".packno");
									angular.element(dd).css("display","none");
									angular.element(aa).css("display","block");
									angular.element(bb).css("display","block");
									$scope.ling=function(){
										var aa=document.querySelector(".packbig");
										angular.element(aa).css("display","none");
										var bb=document.querySelector(".tou");
										angular.element(bb).css("display","none");
											$scope.end=new Date(new Date().toLocaleDateString()).getTime()+24*60*60*1000-1;
											$scope.start=new Date(new Date().toLocaleDateString()).getTime();
											accountRecordService.myrecordArray=[];     //获取我的账户记录
											accountRecordService.myrecordPage = 0;
										    accountRecordService.myrecordBusy = false; 
											accountRecordService.myrecord($scope.uid,$scope.start,$scope.end);      //手动执行
	//										$scope.myrecord=accountRecordService;
										util.tip("领取成功！");
										accountService.getAccountInfo().success(function(resp){
											$scope.money=resp.object;
											console.log($scope.money);
											if($scope.money.canGetRebNum==0){
												util.tip("红包次数已用完！");
												return;
												var aa=document.querySelector(".packbig");
												angular.element(aa).css("display","none");
												var bb=document.querySelector(".tou");
												angular.element(bb).css("display","none");
												var dd=document.querySelector(".packno");
												angular.element(dd).css("display","none");
											}
										})
										return;
										
									}
								}
								
							})
						}
			        last_x = x;
		            last_y = y;
		            last_z = z;
		            }
		            
		        }
		    }

			
		    
		
	})
	

//页面跳转
   $scope.toUrl = function(url){
       util.toUrl(url)
   }

}]);
//app.factory('readJSON',['$http','$q', function ($http,$q) {
//      return {
//          query: function () {
//              var deferred=$q.defer();
//              $http({
//                  method:'GET',
////                  url:'img.json'
//              }).success(function (data, status, header, config) {
//                  deferred.resolve(data);
//              }).error(function (data, status, header, config) {
//                  deferred.reject(data);
//              });
//              return deferred.promise;
//          }
//      }
//  }]);
//
//app.directive('lunbo',['readJSON', function (readJSON) {
//      return{
//          restrict:'EA',
//          templateUrl:'lunbo.html',
//          scope:{},
//          link: function (scope, element, attr) {
//              var promise=readJSON.query();
////              var step=0;
////              scope.flag=false;
////              promise.then(function (data) {
////                  console.log(data);
////                  scope.new=data;
////              });
////              setInterval(function () {
////                  element.find("li").css({"display":"none","opacity":0});
////                  step++;
////                  step=step%5;
////                  element.find("li").eq(step).css({"display":"block","opacity":1});
////              },1000)
//          }
//      }
//  }]);
