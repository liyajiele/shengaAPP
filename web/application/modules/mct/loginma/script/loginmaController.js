/**
 * Created by miss on 2015/11/19.
 */
var login = angular.module('loginmaController',['webapp','userService','LocalStorageModule']);

login.controller('loginHeaderController',['util',function(util){
    util.headerConfig("找回密码",true);
}]);
login.controller('loginmaController',['$location','$scope','$rootScope','util','userService','localStorageService',function($location,$scope,$rootScope,util,userService,localStorageService){

    $scope.login = function(){
        userService.newlogin($scope.mobile,$scope.code,$scope.newPassword).success(function(resp){
        	console.log(resp)
        })
    }

    $scope.clearUsername = function(){
        $scope.mobile = '';
    }
    $scope.clearPwd = function(){
        $scope.password = '';
    }

    $scope.remeberPwd = function(){
        if($scope.rmm == '../images/login/you.png'){
            $scope.rmm = '../images/login/mei.png';
        }else{
            $scope.rmm = '../images/login/you.png';
        }
    }
}]);