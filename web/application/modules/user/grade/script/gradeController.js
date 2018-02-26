/**
 * Created by Administrator on 2017/9/13.
 */
var grade = angular.module('gradeController', ['webapp','LocalStorageModule','userService','orderService']);
grade.controller('gradeNavController',['util',function(util){
util.navConfig(1);
}]);


grade.controller('gradeController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','userService','orderService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,userService,orderService) {
   util.headerConfig('评分',true);

	$scope.toUrl = function(url){
       util.toUrl(url)
   	}
	
//获取当前订单ID
	var cunid =JSON.parse(sessionStorage.getItem("cunid"));   //注册缓存
	$scope.id=cunid.id;
	console.log($scope.id)
	
//评价星星
	
    $scope.readonly = false;
    $scope.onHover = function(val){
      $scope.hoverVal = val;
    };
    $scope.onLeave = function(){
      $scope.hoverVal = null;
    }
    $scope.onChange = function(val){
      $scope.ratingVal = val;
      console.log($scope.ratingVal)
    }
    
    	
//评价订单
	$scope.fa=function(){
		$scope.max = 5;
//	$scope.ratingValue=1;
//	$scope.retaStar=0;
		orderService.evalorder($scope.id,1,"z",$scope.retaStar).success(function (resp) {
			console.log(resp);
		    util.tip("评价成功！")
		    util.toUrl('/user/completedOrders')
		})
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
        $scope.ratingValue = val;     //星星数量
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
        if(oldVal || newVal == 0){
          updateStars();
        }
      });
      scope.$watch('max', function (oldVal, newVal) {
        if (oldVal) {
          updateStars();
        }
      });
    }
  };
});