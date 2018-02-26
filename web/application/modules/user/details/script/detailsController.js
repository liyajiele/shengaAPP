/**
 * Created by Administrator on 2017/9/13.
 */
var details = angular.module('detailsController', ['webapp','LocalStorageModule','merchantService','userService']);

details.controller('detailsNavController',['util',function(util){
  util.navConfig(1);
}]);
details.controller('detailsController', ['$window','$location','$rootScope','$scope','$filter','util','cache','$stateParams','localStorageService','merchantService','userService',function ($window,$location,$rootScope,$scope,$filter,util,cache,$stateParams,localStorageService,merchantService,userService) {
  util.headerConfig('详情',true);
  
var mid=$stateParams.mid;	
$scope.mid=mid;       //局域mid
var uid=localStorageService.get("uid");
console.log(uid)
//商家详情
merchantService.details(mid).success(function (resp) {
	console.log(resp)
	$scope.details=resp.object;
	$scope.detailsstars=parseInt($scope.details.stars);

	console.log($scope.detailsstars)
	console.log($scope.details.hasCollect);
	
	if($scope.details.hasCollect==0){		  //判断是否收藏
		//收藏与取消收藏
		$scope.xuan=false;
		$scope.display=function () {
	    	$scope.xuan=!$scope.xuan;
		    if($scope.xuan){
		    	$scope.type=1;
		    	console.log($scope.type)
		    }else{
		    	$scope.type=0;
		    	console.log($scope.type)
		    }    
		    userService.collect(mid,$scope.type).success(function (resp) {
			    console.log(resp);
			    if($scope.type==1){
			    	 util.tip("收藏店铺成功!")
			    }else{
			    	 util.tip("取消店铺成功!")		    	
			    }
			   
			})
		}
	}else{
		$scope.xuan=true;
	}
//	console.log($scope.details.commentNum)
})

//评论列表
merchantService.commonslistArray=[];        //评论列表
merchantService.commonslistPage=0;
merchantService.commonslistBusy=false;
$scope.commonslist=merchantService;

//网友打分
$scope.dafen=function(){
	var url = "/user/code/"+mid;
	util.toUrl(url);
}

//付款
$scope.fukuan=function(){
	var url = "/user/pay/"+mid;
	util.toUrl(url);
}

//打电话
$scope.callPhone = function (mobilePhone) {
//console.log("拨打:" + mobilePhone);
  $window.location.href = "tel:" + mobilePhone;
};


//推荐商家点击
$scope.tuijian=function(mid){
	var url = "/user/details/"+mid;
	util.toUrl(url);
}


	

//星星
$scope.max = 5;
//$scope.ratingVal = 3;
$scope.readonly = true;
$scope.onHover = function(val){
    $scope.hoverVal = val;
};
$scope.onLeave = function(){
    $scope.hoverVal = null;
}
$scope.onChange = function(val){
    $scope.ratingVal = val;
}

}]);
app.directive('star', function () {
  return {
    template: '<ul class="rating" ng-mouseleave="leave()">' +
        '<li ng-repeat="star in stars" ng-class="star" ng-click="click($index + 1)" ng-mouseover="over($index + 1)">' +
        '<i class="iconfont icon-xing"></i>' +
        '</li>' +
        '</ul>',
    scope: {
      ratingValue: '=',
      max: '=',
      readonly: '@',
      onHover: '=',
      onLeave: '='
    },
    controller: function($scope){
      $scope.ratingValue = $scope.ratingValue || 0;
      $scope.max = $scope.max || 5;
      $scope.click = function(val){
        if ($scope.readonly && $scope.readonly === 'true') {
          return;
        }
        $scope.ratingValue = val;
      };
      $scope.over = function(val){
        $scope.onHover(val);
      };
      $scope.leave = function(){
        $scope.onLeave();
      }
    },
    link: function (scope, elem, attrs) {
      elem.css("text-align", "center");
      var updateStars = function () {
        scope.stars = [];
        for (var i = 0; i < scope.max; i++) {
          scope.stars.push({
            filled: i < scope.ratingValue
          });
        }
      };
      updateStars();
 
      scope.$watch('ratingValue', function (oldVal, newVal) {
        if (newVal) {
          updateStars();
        }
      });
      scope.$watch('max', function (oldVal, newVal) {
        if (newVal) {
          updateStars();
        }
      });
    }
  };
});