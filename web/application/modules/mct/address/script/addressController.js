var address = angular.module('addressController', ['webapp','LocalStorageModule']);

address.controller('addressController',['util',function(util){
    util.navConfig(1);
}]);
address.controller('addressController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
    util.headerConfig('门店地址',true);
    
	var cunad =JSON.parse(sessionStorage.getItem("cunad"));   //注册缓存
	$scope.addr=cunad.addr;

	$scope.bian = function() {
		console.log($scope.addr); 
		$scope.bao=function(){
			var objad={};
			objad.addr=$scope.addr;
			console.log(objad.addr)
			sessionStorage.setItem("cunad",JSON.stringify(objad));
			util.tip("新地址保存成功！");
			util.toUrl("/mct/details");
		}
    };
	
    
    
}]);
