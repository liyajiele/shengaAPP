var aboutService = angular.module('aboutService',['webapp','LocalStorageModule']);

aboutService.factory('aboutService',['$http','util','$cacheFactory','localStorageService',function($http,util,$cacheFactory,localStorageService){
	
	var AboutService = function(){
		//
    };
    //关于我们
    AboutService.prototype.aboutUs=function(osType){
    	var url=util.baseUrl+"/api/common/aboutUs";
    	return $http({
    		url:url,
    		data:{
    			osType:osType
    		},
    		method:'post'
    	})
    }
    
  
	return new AboutService();
}]);