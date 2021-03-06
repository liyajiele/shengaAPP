/**
 * Created by Administrator on 2017/9/13.
 */
var code = angular.module('codeController', ['webapp','LocalStorageModule','merchantService']);

code.controller('codeNavController',['util',function(util){
  util.navConfig(1);
}]);
code.controller('codeController', ['$window','$location','$rootScope','$scope','$filter','util','cache','$stateParams','localStorageService','merchantService',function ($window,$location,$rootScope,$scope,$filter,util,cache,$stateParams,localStorageService,merchantService) {
  util.headerConfig('网友打分',true);

//评论列表

    var mid=localStorageService.get("mid");
  $scope.mid=mid;      //局域mid
  $scope.commonslist=merchantService;
  
//返回商家详情
$scope.back=function(){
	var url = "/mct/mystore";
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