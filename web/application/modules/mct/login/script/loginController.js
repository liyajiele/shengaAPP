/**
 * Created by miss on 2015/11/19.
 */
var login = angular.module('loginController',['webapp','userService','LocalStorageModule']);

login.controller('loginHeaderController',['util',function(util){
    util.headerConfig("登录",true);
}]);
login.controller('loginController',['$location','$scope','$rootScope','util','userService','localStorageService',function($location,$scope,$rootScope,util,userService,localStorageService){

//跳转
	$scope.toUrl = function(url){
       util.toUrl(url)
   }
	
//登录
$scope.login=function(){		
	//判断格式
	if(!(/^1[0-9][0-9]\d{4,8}$/.test($scope.mobile))){
        util.tip("请输入正确的手机号");
        return;
    }
    if($scope.password==undefined ||$scope.password.lenth<6  ){
        util.tip("密码至少6位");
        return;
    }
	userService.login($scope.mobile,$scope.password).success(function(resp){
		console.log(resp);
	})
}


//  $scope.login = function(){
//      var userService = new UserService();
//      userService.login($scope.mobile,$scope.password)
//          .success(function(resp){
//              if(resp.code=="1"){
//                  localStorageService.set("uid",resp.object.id);
//                  localStorageService.set("token",resp.object.token);
//
//                  //跳转到登录前的页面
//                  var preLoginUrl = localStorageService.get("preLoginUrl")
//                  $location.replace(preLoginUrl);
//                  $location.path(preLoginUrl);
//                  localStorageService.remove('preLoginUrl');
//              }
//          });
//  }


    $scope.remeberPwd = function(){
        if($scope.rmm == '../images/login/you.png'){
            $scope.rmm = '../images/login/mei.png';
        }else{
            $scope.rmm = '../images/login/you.png';
        }
    }
    
//获取审核信息
//userService.shen().success(function(resp){
//	console.log(resp)
//	$scope.sheng=resp.object;
//	if($scope.sheng.auditStatus==1){
//		util.toUrl("/mct/auditsuccess");     //审核通过
//	}else if($scope.sheng.auditStatus==2){
//		util.toUrl("/mct/auditfailure");     //审核失败	
//	}else if($scope.sheng.auditStatus==0){
//		util.toUrl("/mct/inaudit");     //审核中
//	}
//})
}]);