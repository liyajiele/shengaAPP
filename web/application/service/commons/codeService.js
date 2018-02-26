var codeService = angular.module('codeService',['webapp','LocalStorageModule']);

codeService.factory('codeService',['$http','util','$cacheFactory','localStorageService',function($http,util,$cacheFactory,localStorageService){
	
	var CodeService = function(){
		//
    };
    //短信验证码
    CodeService.prototype.code=function(mobile,smsType){
    	var url=util.baseUrl+"/api/common/getSmsCode";
    	return $http({
    		url:url,
    		data:{
    			mobile:mobile,
    			smsType:smsType
    		},
    		method:'post'
    	})
    }
    
  
	return new CodeService();
}]);