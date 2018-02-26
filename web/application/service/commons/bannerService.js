var bannerService= angular.module('bannerService',['webapp','LocalStorageModule']);

bannerService.factory('bannerService',['$http','util','$cacheFactory','localStorageService',function($http,util,$cacheFactory,localStorageService){
	
    var BannerService = function(){

    };

    BannerService.prototype.getBanners = function(){
//      var url = 'banner.json';
		var url=util.baseUrl+'/api/common/getBanners';
        return $http({
            url:url,
            data:{

            },
            method:'post'
        });



    }


    return new BannerService();
}]);