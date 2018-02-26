
var accountRecordService = angular.module('accountRecordService',['webapp','LocalStorageModule']);


accountRecordService.factory('accountRecordService',['util','$http','$cacheFactory','localStorageService',function(util,$http,$cacheFactory,localStorageService){

	//刷新问题
//	.state('page.xx', {  
//              url: '/xx',  
//              templateUrl: 'xxxx.html',  
//              reload:true,  
//          })  


    var AccountRecordService = function(){ 
    	
    	   this.myrecordArray=[];     //获取我的账户记录
           this.myrecordPage = 0;
           this.myrecordBusy = false;
           console.log(this.myrecordArray)
    	
    }

    AccountRecordService.prototype.initUid = function(){
        if(this.uid == undefined || this.uid==null){
            this.uid = localStorageService.get('uid');
        }
    }

//获取我的账户记录
  AccountRecordService.prototype.myrecord = function(mid,recordType,startTime,endTime){
        // this.initUid();
        var url = util.baseUrl+"/api/mct/authc/accountRecord/getMyAccountRecord";
        // this.accountRecordBusy=true;
        this.myrecordBusy=true;
        $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                mid:mid,
                recordType:recordType,
                startTime:startTime,
                endTime:endTime,
                page:this.myrecordPage,
                size:20,
            },
            method:'post'
        }).success(function (resp) {
        	console.log(resp)
            var respItems = resp.object.content;
            console.log(respItems)
            for (var i = 0; i < respItems.length; i++) {
                this.myrecordArray.push(respItems[i]);
            }
            this.myrecordPage += 1;
            this.myrecordBusy = false;
            if(resp.object.totalElements=="0"){
            	var dd=document.querySelector(".bei")
            	angular.element(dd).css("display","block");
            }else{
            	var dd=document.querySelector(".bei")
            	angular.element(dd).css("display","none");
            }
        }.bind(this));
  }


  
    return new AccountRecordService();

}]);