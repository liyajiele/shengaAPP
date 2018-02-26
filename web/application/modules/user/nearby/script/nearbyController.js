var nearby = angular.module('nearbyController', ['webapp','LocalStorageModule','merchantService']);

nearby.controller('nearbyNavController',['util',function(util){
  util.navConfig(2);
}]);

nearby.controller('nearbyController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','merchantService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,merchantService) {
    util.headerConfig('附近',true);
		

merchantService.merchantArray=[];
merchantService.merchantPage=0;
merchantService.merchantLast=false;
$scope.near=merchantService;
console.log($scope.near.merchantArray)
	
	// 获取页面 A 访问的 url
	var ur =location.href;
	// 获取页面 A 访问的 url 的 = 后面的 数字 （对应着这个页面的 id）
	var wen = "?";
// 判断字符串A中字符串B方法：
if(ur.indexOf(wen) != -1) { // 如果indexOf() 返回-1，表示不包含
     var type=ur.split('?')[1].split("=")[1];
     console.log(type)
      $scope.isActive=type;     
			$scope.typeId=$scope.isActive;
			$scope.changeGood=function(typeId){     //tab点击切换
	    $scope.isActive=typeId;     
	    $scope.typeId=$scope.isActive;
	    merchantService.merchantArray=[];
			merchantService.merchantPage=0;
			merchantService.merchantLast=false;
			
			if(typeId==4){
				merchantService.nearmerchant(null);
			}else{
				merchantService.nearmerchant(typeId+1);
			}
			}
		 console.log(type)
} else {
	    $scope.isActive=0;     
    	$scope.changeGood=function(typeId){     //tab点击切换
	    $scope.isActive=typeId;     
	    $scope.typeId=$scope.isActive;
	    merchantService.merchantArray=[];
			merchantService.merchantPage=0;
			merchantService.merchantLast=false;
			
			if(typeId==4){
				merchantService.nearmerchant(null);
			}else{
				merchantService.nearmerchant(typeId+1);
			}
		}
}
	

	
//	// 选项卡
//	$('input').click(function(event) {
//	$('input').removeClass('btn');
//	$(this).addClass('btn');
//	$('#box1 div').css({'display':'none'});
//	$('#box1 div').eq($(this).index()).css({'display':'block'});
//	});
	// 使用传过来的 id 来控制该选项卡的切换
//	$('.seller_list').css({'display':'none'});
//	$('.nav li').removeClass('active');
//	$('.nav li').eq(type-1).addClass('active');
//	$('.seller_list').eq(type-1).css({'display':'block'});
	
	
		
   
    
//跳转商家详情
	var mid = $stateParams.mid;
	$scope.xiang=function(mid){
		var url = "/user/details/"+mid;
		util.toUrl(url);
	}
	
	
//五大类型
	merchantService.type().success(function(resp){      
		$scope.type=resp.object;
	  	console.log($scope.type);    	
	})
    
//跳转
  $scope.toUrl = function(url){
       util.toUrl(url)
   }
    
 //坐上角城市
 var cun =JSON.parse(sessionStorage.getItem("cun"));
 console.log(cun)
 $scope.citycity=cun.citycity;
 $scope.cityid=cun.cityid;
 var cuna =JSON.parse(sessionStorage.getItem("cuna"));
 console.log(cuna)
 if(cuna!=undefined){
 	$scope.districtid=cuna.districtid;
 	console.log($scope.districtid)
 }else{
 	$scope.districtid=cun.districtid;
 }
// var city=$stateParams.city;
    
    
    
    
    
    
    
    
    
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
        '<li ng-repeat="star in stars" ng-class="star" ng-click="click($index + 1)" ng-mouseover="over($index + 1)" style="font-size:0.4rem">' +
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
