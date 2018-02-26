/**
 * Created by Administrator on 2017/9/13.
 */
var finance = angular.module('financeController', ['webapp','LocalStorageModule','accountRecordService']);

finance.controller('financeNavController',['util',function(util){
  util.navConfig(1);
}]);
finance.controller('financeController', ['$window','$location','$rootScope','$scope','$filter','$timeout','util','cache','$stateParams','localStorageService','accountRecordService',function ($window,$location,$rootScope,$scope,$filter,$timeout,util,cache,$stateParams,localStorageService,accountRecordService) {
    util.headerConfig('财务记录',true);
  
	var mid=localStorageService.get("mid");
    $scope.mid=mid;
  console.log($scope.mid);
  
  
  //获取账户记录   财务记录
	$scope.myrecord=accountRecordService;
	console.log($scope.myrecord.myrecordArray);
  	
  	
  	$scope.type = false;
  	$scope.typea=false;
  	$scope.toggle = function() {     //类型
	    $scope.type = !$scope.type;
	    $scope.records = [
	      {
	        "Name" : "购物返利",
	        "recordType":"1",
	      },{
	        "Name" : "粉丝购物返利",
	        "recordType":"2",
	      },{
	        "Name" : "红包",
	        "recordType":"3",
	      },{
	        "Name" : "消费",
	        "recordType":"4",
	      },{
	        "Name" : "提现",
	       	"recordType":"5",
	      }
	    ]
	    $scope.chosedIndex =$scope.isShow;
	    $scope.isShow=$scope.chosedIndex;
    	$scope.goul = function(index){
	      	$scope.chosedIndex = index;//保存点击的li位置
	     	$scope.isShow=$scope.chosedIndex;

	      	accountRecordService.myrecordArray=[];
		    accountRecordService.myrecordPage=0;
		    accountRecordService.myrecordBusy=false;
		    accountRecordService.start=$scope.start;      //时间
		    
	    	accountRecordService.myrecordType=$scope.records[index].recordType;     //类型
	    	$scope.typeindex=accountRecordService.myrecordType;
	    	console.log($scope.typeindex)
	    	var mid=localStorageService.get("mid");
    		$scope.mid=mid;
	    	accountRecordService.myrecord($scope.mid,$scope.typeindex);     //手动执行
	  		$scope.type = !$scope.type;    

	    }

	    
  	};
  
  
  $scope.togglea=function () {    //时间
    $scope.typea = !$scope.typea;
  }


//颜色选择
$scope.setcolor=function(recordType){
		var color="";
		if(recordType==1){     //绿
      		color= "#22c01a";
		}else if(recordType==2){     
      		color= "#22c01a";
		}else if(recordType==3){
      		color= "#22c01a";
		}else if(recordType==4){
      		color= "red";
		}else if(recordType==5){
			color="red";
		}
		return {"color":color}
	}
}]);


//账户对应类型
finance.filter('typeFilter',function(){
	return function(recordType){
	    if(recordType==1){
	      return "购物返利";
	    }else if(recordType==2){
	      return "粉丝消费返利";
	    }else if(recordType==3){
	      return "红包";
	    }else if(recordType==4){
	      return "消费";
	    }else if(recordType==5){
	      return "提现";
	    }else if(recordType==6){
	      return "平台奖励";
	    }
	};
});


//账户对应符号
finance.filter('fu',function(){
	return function(recordType){
	    if(recordType==1){
	      return "+";
	    }else if(recordType==2){
	      return "+";
	    }else if(recordType==3){
	      return "+";
	    }else if(recordType==4){
	      return "-";
	    }else if(recordType==5){
	      return "-";
	    }else if(recordType==6){
	      return "+";
	    }
	};
});