var business = angular.module('businessController', ['webapp','LocalStorageModule']);

business.controller('businessController',['util',function(util){
    util.navConfig(1);
}]);
business.controller('businessController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService) {
    util.headerConfig('附近商家',true);
}]);
