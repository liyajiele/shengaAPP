/**
 * Created by Administrator on 2017/9/14.
 */
var search = angular.module('searchController', ['webapp','LocalStorageModule','addressService','merchantService']);

search.controller('searchNavController',['util',function(util){
  util.navConfig(1);
}]);
search.controller('searchController', ['$window','$location','$rootScope','$scope','$filter','util','cache','$stateParams','localStorageService','addressService','merchantService',function ($window,$location,$rootScope,$scope,$filter,util,cache,$stateParams,localStorageService,addressService,merchantService) {
  util.headerConfig('搜索',true);


//merchantService.merchantArray=[];
//merchantService.merchantPage=0;
//merchantService.merchantLast=false;


  //热门搜索
//	var chengshi =JSON.parse(sessionStorage.getItem("chengshi"));
//	console.log(chengshi)
	var cun =JSON.parse(sessionStorage.getItem("cun"));
	console.log(cun);
	$scope.districtId=cun.districtId;
	$scope.cityid=cun.cityid;
	addressService.hotsearch($scope.cityid,1).success(function (resp) {
	     console.log(resp);
//	     $scope.hot=resp.object;
	 $scope.hotsearch=resp.object.content;
	//  console.log($scope.hotsearch)
	})

  //历史记录
  addressService.searchrecord().success(function (resp) {
    $scope.searchrecord=resp.object.content;
    console.log($scope.searchrecord);
    if($scope.searchrecord.length==0){
    	var laji=document.querySelector(".laji");
		angular.element(laji).css("display","none")
    }
  })
  
  //清空搜索记录
  $scope.del=function(){
  	addressService.clear().success(function (resp) {
  		console.log(resp)
        $scope.searchrecord=resp.object.content;
	    $scope.type=resp.object;
	    if(resp.object==true){     //没有历史记录，垃圾桶消失
	    	var laji=document.querySelector(".laji");
    		angular.element(laji).css("display","none")
	    }
	  })
  }
  
//搜索
	$scope.merchant=merchantService;


//商铺跳转
$scope.search=function(searchstr){
	console.log($scope.searchstr);
	$scope.searchstr=$scope.searchstr;
	if(!$scope.searchstr){
		util.tip("请输入您想要搜索的内容！")
		return;
	}else{
//		merchantService.merchantArray=[];
//		merchantService.merchantPage=0;
//		merchantService.merchantLast=false;
//		merchantService.merchant();  
//		addressService.kuang($scope.districtId,$scope.cityId).success(function(resp){
//			console.log(resp)
//		})
		merchantService.searchmct(0,0,searchstr).success(function(resp){
//			console.log(resp)     //搜索
		})
		$scope.$watch('searchstr',function(newvalue){    //没有搜索到商铺
			$scope.merchant=merchantService;
			var filter=$filter('filter');
			if(!filter($scope.merchant.merchantArray,newvalue).length){
				$scope.isShow=true;
			}else{
				$scope.isShow=false;
			}
		})
		this.aa=function(){
			util.toUrl('/user/search');
		}
		var aa=document.querySelector(".searchqian");
		angular.element(aa).css("display","none");
		var bb=document.querySelector(".searchhou");
		angular.element(bb).css("display","block");
		$scope.xuan=function(mid){
			var url = "/user/details/"+mid;
			util.toUrl(url);
		}
	}	
}

//跳转
  $scope.toUrl = function(url){
    util.toUrl(url);
  }
  
  $scope.back=function(){
  	history.back();
  }
}]);
