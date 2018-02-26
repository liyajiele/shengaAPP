/**
 * Created by miss on 2015/11/19.
 */
var registerzz = angular.module('registerzzController',['webapp','userService','LocalStorageModule']);

registerzz.controller('registerzzHeaderController',['util',function(util){
    util.headerConfig("注册",true);
}]);
registerzz.controller('registerzzController',['$location','$scope','$rootScope','util','userService','localStorageService',function($location,$scope,$rootScope,util,userService,localStorageService){

    $scope.rmm = "../images/login/you.png";
    
    //跳转
   	$scope.toUrl=function(url){
		util.toUrl(url);
	}
   	$scope.fan=function(){
   		history.go(-1);
   	}
   	
   	//姓名以及身份证号
   	var xin={};
   	xin.username=$scope.username;
	xin.idnumber=$scope.idnumber;
	localStorage.setItem("cunxin",JSON.stringify(xin));
	var cunxin =JSON.parse(localStorage.getItem("cunxin"));
   	$scope.username=xin.username;
	$scope.idnumber=xin.idnumber;
//radio
   	$scope.type = [    //状态
      {
        Name : "已办理好",
        id:1
      },{
        Name : "暂时无法提供",
        id:2
      }
    ]
   	$scope.typea = [    //类型
      {
        Name : "企业法人",
        id:1
      },{
        Name : "个体工商户",
        id:2
      }
    ]
   	$scope.dian=function(id){
// 		console.log(id)
   		$rootScope.licenceType=id;
   		console.log($rootScope.licenceType)
   		
   		$scope.diana=function(id){
	// 		console.log(id)
	   		$scope.licenceStatus=id;
	   		console.log($scope.licenceStatus)
	   			var zz={};
			   	zz.licenceType=$scope.licenceType;
			   	zz.licenceStatus=$scope.licenceStatus;
				localStorage.setItem("cunzz",JSON.stringify(zz));	
				var cunzz =JSON.parse(localStorage.getItem("cunzz"));
				console.log(cunzz);
				$scope.licenceType=cunzz.licenceType;
				$scope.licenceStatus=cunzz.licenceStatus;
	   	}
   		
   	}
    

//授权同意书
$scope.myclick=function(){
	$scope.mychecked=!$scope.mychecked;
		console.log($scope.mychecked);
	if($scope.mychecked==false){
		util.tip("请同意《经营权授权书》");
		return;
	}
}
   	
   		
//身份证	
var cunz =JSON.parse(localStorage.getItem("cunz"));
console.log(cunz);
var cunf =JSON.parse(localStorage.getItem("cunf"));
console.log(cunf);
if(!cunz||!cunf){
	console.log("没有cunz");
	$scope.zi="未上传";
}else{
	console.log(cunz);
	$scope.imagez=cunz.image;	
	console.log(cunf);
	$scope.imagef=cunz.imagea;	
	if(!cunz&&!cunf){
		$scope.zi="未上传";
	}else{
		$scope.zi="已上传";
	}
}
	
	
//营业执照
var cunl =JSON.parse(localStorage.getItem("cunl"));
console.log(cunl);
if(!cunl){
	console.log("没有cunl");
	$scope.wen="未上传";
}else{
	console.log(cunl);
	$scope.licenceImage=cunl.image;	
	if(!cunl){
		$scope.wen="未上传";
	}else{
		$scope.wen="已上传";
	}
}
	
	
	

//	console.log($scope.licenceType)    //不能输出
    //基本信息 	
		var cuna =JSON.parse(localStorage.getItem("cuna"));
		console.log(cuna)
		$scope.shmc=cuna.shmc;    //商铺名称
		$scope.dizhi=cuna.dizhi;   //商铺地址
		$scope.mobile=cuna.mobile;     //手机号
		$scope.password=cuna.password;     //密码
		$scope.code=cuna.code;     //商铺电话
		$scope.image=cuna.image;    //门店图片
		$scope.timerange=cuna.timerange;    //营业时间
		$scope.typeid=cuna.typeid;     //类型id
		$scope.typename=cuna.typename;     //类型名字
 	
 	
 	var cunc =JSON.parse(localStorage.getItem("cunc"));
		console.log(cunc)
//注册
    $scope.register = function(){
	//基本信息 	
		var cuna =JSON.parse(localStorage.getItem("cuna"));
		console.log(cuna)
		$scope.shmc=cuna.shmc;    //商铺名称
		$scope.dizhi=cuna.dizhi;   //商铺地址
		$scope.mobile=cuna.mobile;     //手机号
		$scope.password=cuna.password;     //密码
		$scope.code=cuna.code;     //商铺电话
		$scope.image=cuna.image;    //门店图片
    	$scope.images=0;
		
		$scope.timerange=cuna.timerange;    //营业时间
		$scope.typeid=cuna.typeid;     //类型id
		$scope.typename=cuna.typename;     //类型名字
		
		
	//位置
		var cunc =JSON.parse(localStorage.getItem("cunc"));
		console.log(cunc)
		$scope.areaid=0;    //大区id
		$scope.proid=cunc.proid;   //省份id
		$scope.cityid=cunc.cityid;    //城市id
		$scope.districtid=cunc.districtid;
	//	$scope.areaid=cunc.areaid;
        console.log($scope.proid);
        console.log($scope.cityid);
        console.log($scope.districtid)
		$scope.lat=cunc.lat;
		$scope.lng=cunc.lng;
    	//判断正则    身份证号码
	   	if(!(/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|[xX])$/.test($scope.idnumber))){
	        util.tip("请输入正确的省份证号码");
	        return;
	    }
	   	
	   	console.log($rootScope.licenceType)
var cunz =JSON.parse(localStorage.getItem("cunz"));
console.log(cunz);
$scope.imagez=cunz.image;	
console.log($scope.imagez)
var cunf =JSON.parse(localStorage.getItem("cunf"));
console.log(cunf);
$scope.imagef=cunf.imagea;	
console.log($scope.imagef)
var cunl =JSON.parse(localStorage.getItem("cunl"));
console.log(cunl);
$scope.imagel=cunl.image;	
console.log($scope.imagel);
        //创建门店信息

		userService.create($scope.areaid,$scope.proid,$scope.cityid,$scope.districtid,$scope.shmc,$scope.dizhi,$scope.code,$scope.timerange,$scope.image,$scope.images,$scope.typeid,$scope.username,$scope.idnumber,$scope.imagez,$scope.imagef,$scope.licenceType,$scope.licenceStatus,$scope.imagel,$scope.lng,$scope.lat).success(function(resp){
			console.log(resp)
			
			
			util.toUrl("/mct/inaudit")
   		})
	}
    

}]);