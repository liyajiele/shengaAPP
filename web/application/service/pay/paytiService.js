var paytiService = angular.module('paytiService',['webapp','LocalStorageModule']);

paytiService.factory('paytiService',['$http','util','$cacheFactory','localStorageService',function($http,util,$cacheFactory,localStorageService){
	
	var PaytiService = function(){
//		console.log(localStorageService.get('uid'))
    };
     //申请提现
    PaytiService.prototype.payti=function(uid,drawNum,drawCId){
    	var url=util.baseUrl+"/api/user/draw/authc/appylForDraw";
    	return $http({
    		url:url,
    		data:{
    			uid:localStorageService.get('uid'),
    			drawNum: drawNum,
    			drawCId:drawCId,
    		},
    		method:'post'
    	})
    }
    
    //申请提现（无id）
    PaytiService.prototype.noidti=function(drawNum,alipay,concatUser){
    	var url=util.baseUrl+"/api/user/draw/authc/appylForDrawWithoutChannelId";
    	return $http({
    		url:url,
    		data:{
    			uid:localStorageService.get('uid'),
    			drawNum: drawNum,
    			alipay:alipay,
    			concatUser:concatUser,
    		},
    		method:'post'
    	})
    }
    
    //支付宝提现
    PaytiService.prototype.zhi=function(concatUser,concatMobile,aliPay){
    	var url=util.baseUrl+"/api/user/draw/authc/saveAliPayDrawChannel";
    	return $http({
    		url:url,
    		data:{
    			uid:localStorageService.get('uid'),
    			concatUser:concatUser,
    			concatMobile: concatMobile,
    			aliPay:aliPay,
    		},
    		method:'post',
//  		async:false,
    	})
    }
    
    //显示用户所有的提现渠道
    PaytiService.prototype.alldraw=function(){
    	var url=util.baseUrl+"/api/user/draw/authc/queryUserAllDrawChannel";
    	return $http({
    		url:url,
    		data:{
    			uid:localStorageService.get('uid'),
    			
    		},
    		method:'post',
    		async:false,
    })
    }
  
	return new PaytiService();
}]);