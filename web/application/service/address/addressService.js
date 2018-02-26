/**
 * Created by Administrator on 2017/10/14.
 */
var addressService = angular.module('addressService',['webapp','LocalStorageModule']);

addressService.factory('addressService',['$http','util','$cacheFactory','localStorageService',function($http,util,$cacheFactory,localStorageService){

  var AddressService = function(){
  	
  };
  //查询我的搜索记录
  AddressService.prototype.searchrecord=function(){
    var url=util.baseUrl+"/api/location/authc/mySearchRecord";
    return $http({
      url:url,
      data:{
        uid:localStorageService.get('uid'),
      },
      method:'post'
    })
  }

  //热门搜索
  AddressService.prototype.hotsearch=function(cityId,type){
    var url=util.baseUrl+"/api/location/nearByHotSearch";
    return $http({
      url:url,
      data:{
        cityId:cityId,
        type:type,
      },
      method:'post'
    })
  }
  
  //搜索框
  AddressService.prototype.kuang=function(districtId,cityId){
    var url=util.baseUrl+"/api/location/hotSearch";
    return $http({
      url:url,
      data:{
      	districtId:districtId,
        cityId:cityId,
      },
      method:'post'
    })
  }

  //热门城市
  AddressService.prototype.hotcity=function(){
    var url=util.baseUrl+"/api/location/hotCity";
    return $http({
      url:url,
      data:{
        uid:localStorageService.get('uid'),
      },
      method:'post'
    })
  }


  //根据省id获取城市列表
  AddressService.prototype.provinceId=function (provinceId) {
    var url=util.baseUrl+"/api/location/findCityListByProvinceId";
    return $http({
      url:url,
      data:{
        uid:localStorageService.get('uid'),
        provinceId:provinceId,
      },
      method:'post'
    })
  }

//根据城市id获取区域列表
  AddressService.prototype.cityId=function (cityId) {
    var url=util.baseUrl+"/api/location/findDistrictByCityId";
    return $http({
      url:url,
      data:{
        uid:localStorageService.get('uid'),
        cityId:cityId,
      },
      method:'post'
    })
  }


//清空搜索记录
  AddressService.prototype.clear=function(){
	  var url=util.baseUrl+"/api/location/authc/cleanMySearchRecord";
	    return $http({
	      url:url,
	      data:{
	        uid:localStorageService.get('uid'),
	      },
	      method:'post'
	    })
	 }
  
  //获取城市列表
  AddressService.prototype.citylist=function(){
	  var url=util.baseUrl+"/api/location/cityList";
	    return $http({
	      url:url,
	      data:{

	      },
	      method:'post'
	    })
	 }
  
  //获取location信息
  AddressService.prototype.location=function(longitude,latitude){
	  var url=util.baseUrl+"/api/location/getLocationInfo";
	    return $http({
	      url:url,
	      data:{
			longitude:longitude,
			latitude:latitude,
	      },
	      method:'post'
	    })
	 }

  return new AddressService();
}]);