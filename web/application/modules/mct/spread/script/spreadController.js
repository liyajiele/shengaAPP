var spread = angular.module('spreadController', ['webapp','LocalStorageModule']);

spread.controller('spreadNavController',['util',function(util){
    util.navConfig(1);
}]);
spread.controller('spreadHeaderController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
    util.headerConfig('附近商家',true);
}]);
