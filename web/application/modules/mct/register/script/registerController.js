/**
 * Created by miss on 2015/11/19.
 */
var register = angular.module('registerController',['webapp','userService','LocalStorageModule','codeService']);

register.controller('registerHeaderController',['util',function(util){
    util.headerConfig("注册",true);
}]);
register.controller('registerController',['$location','$scope','$rootScope','util','userService','localStorageService','codeService',function($location,$scope,$rootScope,util,userService,localStorageService,codeService){

    $scope.rmm = "../images/login/you.png";

	$scope.facode=function(){
		codeService.code($scope.mobile,1).success(function(resp){
			console.log(resp)
		})
	}
	
    $scope.register = function(){
        if(!(/^1[0-9][0-9]\d{4,8}$/.test($scope.mobile))){
            util.tip("请输入正确的手机号");
            return;
        }
        if($scope.code.lenth<6){
            util.tip("输入正确的验证码！");
            return;
        }
        if($scope.password==undefined ||$scope.password.lenth<6  ){
            util.tip("密码至少6位");
            return;
        }
        var obj={};
        obj.mobile=$scope.mobile;
        obj.password=$scope.password;
        localStorage.setItem("cun",JSON.stringify(obj));     //数据存储，对象转字符串	
        util.toUrl("/mct/registermd");
    }
    
//  
//	userService.create($scope.mobile,222222,$scope.password).success(function(resp){
//      localStorageService.set("uid",resp.object.id);
//      localStorageService.set("token",resp.object.token);
//      //跳转到登录前的页面
//      var preLoginUrl = localStorageService.get("preLoginUrl")
//      $location.replace(preLoginUrl);
//      $location.path(preLoginUrl);
//      localStorageService.remove('preLoginUrl');
//  });

}]);