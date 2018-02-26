var yijianService = angular.module('yijianService',['webapp','LocalStorageModule']);

yijianService.factory('yijianService',['$http','util','$cacheFactory','localStorageService',function($http,util,$cacheFactory,localStorageService){
	
	var YijianService = function(){
		//
    };
    
    
     //意见反馈
   	  YijianService.prototype.feedback=function(fbType,fbTypeDesc,content,images){
   	    var url=util.baseUrl+"/api/common/feedback";
   	    return $http({
   	      url:url,
   	      data:{
   			fbType:fbType,
 			fbTypeDesc:fbTypeDesc,
 			content:content,
 			images:images,
   	      },
   	      method:'post'
   	    })
   	  }
  
  	//文件上传
	YijianService.prototype.upload=function(file,path,bucketName){
	    var url=util.baseUrl+"/api/common/uploadFile";
	    return $http({
	      url:url,
	      data:{
		      	file:file,
				path:path,
				bucketName:bucketName
	      },
	      method:'post'
	    })
	  }
  
	return new YijianService();
}]);