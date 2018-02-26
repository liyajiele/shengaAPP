var storeprogress = angular.module('storeprogressController', ['webapp','LocalStorageModule']);

storeprogress.controller('storeprogressController',['util',function(util){
    util.navConfig(1);
}]);
storeprogress.controller('storeprogressController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
    util.headerConfig('附近商家',true);
}]);
