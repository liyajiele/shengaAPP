
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
//         this.myrecordStart=1507948957059;     //时间
           this.myrecordType=null;     //类型    没有全部
           this.totalElements=0;
           this.myrocordLast=false;
//         console.log(this.myrecordArray)
    	
    	
    	  
           this.newArray=[];     //获取最新动态
           this.newPage = 0;
           this.newBusy = false;
           this.newLast=false;
        
    }



    AccountRecordService.prototype.initUid = function(){
        if(localStorageService.get('uid') == undefined || localStorageService.get('uid')==null){
            localStorageService.get('uid') = localStorageService.get('uid');
        }
    }

//获取我的账户记录     财务记录	
  	AccountRecordService.prototype.myrecord = function(recordType,startTime,endTime){
        // this.initUid();
        var url = util.baseUrl+"/api/user/accountRecord/authc/getMyAccountRecord";
        // this.accountRecordBusy=true;
        this.myrecordBusy=true;
        $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                recordType:this.myrecordType,
                startTime:startTime,
                endTime:endTime,
                page:this.myrecordPage,
                size:20,
            },
            method:'post'
        }).success(function (resp) {
//      	console.log(resp)
			if(!this.myercordLast){
	        	this.totalElements=resp.object.totalElements;
	            var respItems = resp.object.content;
	            console.log(respItems)
//	            console.log(this.totalElements)
	            for (var i = 0; i < respItems.length; i++) {
	                this.myrecordArray.push(respItems[i]);
	            }
	            this.myrecordPage += 1;
	            this.myrecordBusy = false;
				this.myrecordLast=resp.object.last;
			}
            if(resp.object.totalElements=="0"){
            	var dd=document.querySelector(".bei")
            	angular.element(dd).css("display","block");
            }else{
            	var dd=document.querySelector(".bei")
            	angular.element(dd).css("display","none");
            }
        }.bind(this));
}


  //获取最新动态
  AccountRecordService.prototype.new = function(){
    // this.initUid();
    var url = util.baseUrl+"/api/user/accountRecord/getNewsInfoByPage";
    // this.accountRecordBusy=true;
    this.newBusy=true;
        $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                page:this.newPage,
                size:50,
            },
            method:'post'
        })
        .success(function (resp) {
        	if(!this.newLast){
//      	console.log(1)
	            if(resp.object && resp.object.content){
	            	var respItems = resp.object.content;
//	            		console.log(respItems)
		            for (var i = 0; i < respItems.length; i++) {
		                this.newArray.push(respItems[i]);
		            }
		            this.newPage += 1;
		            this.newBusy = false;
		            this.newLast=resp.object.Last;
	            }
	            
	        }
        }.bind(this));
  }



  //获取红包动态    分页
  AccountRecordService.prototype.pack = function(){
    // this.initUid();
    var url = util.baseUrl+"/api/user/accountRecord/getRedBagInfos";
    // this.accountRecordBusy=true;
    return $http({
      url: url,
      data: {
        uid: localStorageService.get('uid'),

      },
      method: 'post',
    })
  }

    return new AccountRecordService();

}]);