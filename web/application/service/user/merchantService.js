
var merchantService = angular.module('merchantService',['webapp','LocalStorageModule']);


merchantService.factory('merchantService',['util','$http','$cacheFactory','localStorageService',function(util,$http,$cacheFactory,localStorageService){
	
	var MerchantService=function(){
		this.merchantArray=[];         //附近商家信息
		this.merchantPage=0;
		this.merchantBusy=false;
		this.merchantLast=false;
		
		
		this.commonslistArray=[];        //评论列表
		this.commonslistPage=0;
		this.commonslistBusy=false;
		this.commonslistLast=false;

	    this.tuiArray=[];        //评论列表
	    this.tuiPage=0;
	    this.tuiBusy=false;
	    this.tuiLast=false;
//		console.log(this.merchantArray);   
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
  
//查看商家详情       推荐商家
  MerchantService.prototype.details = function(mid){
    var url = util.baseUrl+"/api/user/merchant/getMerchantDetail";
    return $http({
      url:url,
      data:{
		uid:localStorageService.get('uid'),
		mid:mid,

      },
      method:'post'
    });
  }
  
//查询所有商家类型
	MerchantService.prototype.type = function(){
	    var url = util.baseUrl+"/api/user/merchant/allMerchantTypes";
	    return $http({
	      url:url,
	      data:{
			
	
	      },
	      method:'post'
	    });
	  }

//附近商户
	MerchantService.prototype.nearmerchant = function(districtId,typeId,searchStr){
	    var url = util.baseUrl+"/api/user/merchant/nearByMerchant";
	    this.merchantBusy=true;
        $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                page:this.merchantPage,
                size:10,
                districtId:districtId,
                typeId:typeId,
                searchStr:searchStr,
            },
            method:'post'
        }).success(function (resp) {
            var respItems = resp.object.content; 
            if(!this.merchantLast){
	            for (var i = 0; i < respItems.length; i++) {
	                this.merchantArray.push(respItems[i]);
	            }
	            this.merchantPage += 1;
	            this.merchantBusy = false;
	            
	            this.merchantLast = resp.object.last;
            }
            
            
        }.bind(this));
	}   
	
	
	//搜索商户
MerchantService.prototype.searchmct = function(districtId,typeId,searchStr){
	    var url = util.baseUrl+"/api/user/merchant/nearByMerchant";
        return $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                districtId:districtId,
                typeId:typeId,
                searchStr:searchStr,
            },
            method:'post'
        })
	}   
  
  return new MerchantService();
}])