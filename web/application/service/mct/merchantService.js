
var merchantService = angular.module('merchantService',['webapp','LocalStorageModule']);


merchantService.factory('merchantService',['util','$http','$cacheFactory','localStorageService',function(util,$http,$cacheFactory,localStorageService){
	
	var MerchantService=function(){
		
		
		this.commonslistArray=[];        //评论列表
		this.commonslistPage=0;
		this.commonslistBusy=false;
		this.commonslistLast=false;
	}

//查看商家评论列表
	MerchantService.prototype.commonslist = function(mid){
	    var url = util.baseUrl+"/api/user/merchant/getMerchantCommons";
	    this.commonslistBusy=true;
	        $http({
	            url:url,
	            data:{
	                uid:localStorageService.get('uid'),
	                mid:mid,
	                page:this.commonslistPage,
	                size:20,
	            },
	            method:'post'
	        }).success(function (resp) {
	        	// console.log(6)
	        	if(!this.commonslistLast){
		        	var respItem=resp.object;
		            var respItems = resp.object.commont.content;
		            for (var i = 0; i < respItems.length; i++) {
		                this.commonslistArray.push(respItems[i]);
		            }
		            this.commonslistPage += 1;
		            this.commonslistBusy = false;
		            this.commonslistLast = resp.object.last;
	            }
	        	
	        }.bind(this));
	}
  

  
  return new MerchantService();
}])