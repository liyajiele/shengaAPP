var storecertification = angular.module('storecertificationController', ['webapp','LocalStorageModule']);

storecertification.controller('storecertificationController',['util',function(util){
    util.navConfig(1);
}]);
storecertification.controller('storecertificationController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
    util.headerConfig('附近商家',true);
    
    //跳转
    $scope.toUrl = function(url){
       util.toUrl(url)
   }

}]);
