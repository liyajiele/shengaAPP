var nearby = angular.module('nearbyController', ['webapp','LocalStorageModule']);

nearby.controller('nearbyNavController',['util',function(util){
    util.navConfig(1);
}]);
nearby.controller('nearbyHeaderController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
    util.headerConfig('附近商家',true);
}]);
