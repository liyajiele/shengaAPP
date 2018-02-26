/**
 * Created by Administrator on 2017/9/13.
 */
var collect = angular.module('collectController', ['webapp','LocalStorageModule','userService','merchantService']);

collect.controller('collectNavController',['util',function(util){
  util.navConfig(1);
}]);
collect.controller('collectController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','userService','merchantService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,userService,merchantService) {
  util.headerConfig('我的收藏',true);

var uid=localStorageService.get("uid");
console.log(uid)

  //收藏列表
  $scope.collects=userService;   
  $scope.xuan=true;
  $scope.display=function (id) {
  	console.log(id)
  	event.stopPropagation();
    $scope.xuan=!$scope.xuan;
    $scope.type=0;
    userService.collect(id,$scope.type).success(function (resp) {
	    console.log(resp);
	    if($scope.type==0){
	    	util.tip("取消店铺成功!");
 			$scope.collects=userService;   
 			
// 			userService.collectsArray=[];     //全部清空
//			userService.collectsPage=0;
//			userService.collectsBusy=false;	    
			
	    }	   
	})
  }

//跳转商家详情
	var mid = $stateParams.mid;
	$scope.xiang=function(mid){
		var url = "/user/details/"+mid;
		util.toUrl(url);
	}

//星星
  $scope.max = 5;
  $scope.ratingVal = 3;
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

//星星
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