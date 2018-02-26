
var accountService = angular.module('accountService',['webapp','LocalStorageModule']);


accountService.factory('accountService',['util','$http','$cacheFactory','localStorageService',function(util,$http,$cacheFactory,localStorageService){

	//刷新问题
//	.state('page.xx', {  
//              url: '/xx',  
//              templateUrl: 'xxxx.html',  
//              reload:true,  
//          })  


    var AccountService = function(){ 
//      this.accountRecordArray=[];
//      this.accountRecordPage = 0;
//      this.accountRecordBusy = false;
    }



//  accountService.prototype.initUid = function(){
//      if(localStorageService.get('uid') == undefined || localStorageService.get('uid')==null){
//          localStorageService.get('uid') = localStorageService.get('uid');
//      }
//  }

//获取账户信息
    AccountService.prototype.getAccountInfo = function(){
        var url = util.baseUrl+"/api/user/account/authc/accountInfo";
        return $http({
            url:url,
            data:{
                uid:localStorageService.get('uid')
            },
            method:'post'
        });
    }


//获取红包
	AccountService.prototype.redpack = function(){
        var url = util.baseUrl+"/api/user/account/authc/getRedBag";
        return $http({
            url:url,
            data:{
                uid:localStorageService.get('uid')
            },
            method:'post'
        });
    }



    return new AccountService();
    
    
    


}]);