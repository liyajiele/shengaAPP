var IDcard = angular.module('IDcardController', ['webapp','LocalStorageModule']);

IDcard.controller('IDcardController',['util',function(util){
    util.navConfig(1);
}]);
IDcard.controller('IDcardController', ['$window','$location','$rootScope','$scope','util','$http','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,$http,cache,$stateParams,localStorageService) {
    util.headerConfig('身份证上传',true);
    
//身份证正面    
    $scope.display=function(){
    	var zheng=document.querySelector(".lia");
    	angular.element(zheng).css("display","block");
    }
    $scope.displaya=function(){
    	var zheng=document.querySelector(".lia");
    	angular.element(zheng).css("display","none");
    }
    
//身份证反面
    $scope.displayb=function(){
    	var zheng=document.querySelector(".lib");
    	angular.element(zheng).css("display","block");
    }
    $scope.displayc=function(){
    	var zheng=document.querySelector(".lib");
    	angular.element(zheng).css("display","none");
    }
    

//上传图片
//点击添加图片
	$scope.reader = new FileReader();   //创建一个FileReader接口
    $scope.form = {     //用于绑定提交内容，图片或其他数据
        image:{},
        imagea:{},
    };
	$scope.images="";
	$scope.imagess="";
    $scope.img_upload = function(files) {       //单次提交图片的函数
//      $scope.guid = (new Date()).valueOf();   //通过时间戳创建一个随机数，作为键名使用
        $scope.reader.readAsDataURL(files[0]);  //FileReader的方法，把图片转成base64
        $scope.reader.onload = function(ev) {
            $scope.$applyAsync(function(){
                imgSrc:ev.target.result  //接收base64
            });
        };
        var data = new FormData();
        var path='mct/IDcardzheng'+localStorageService.get('uid')+'.png';//以下为像后台提交图片数据
		$scope.image='http:\/\/oss51shenga.51shenga.com\/'+path;
   		console.log($scope.image)
		var objz={};
		objz.image=$scope.image;
		localStorage.setItem("cunz",JSON.stringify(objz));     //数据存储，对象转字符串	
		var cunz =JSON.parse(localStorage.getItem("cunz"));
		console.log(cunz)
        data.append('file', files[0]);
        data.append('path',path);
        return $http({
            method: 'post',
            url: util.baseUrl+"/api/common/uploadFile",
            data:data,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity,
        }).success(function(resp) {        	
        	var add=document.querySelector(".add");
        	angular.element(add).css("display","none");
   			console.log("图片上传成功！");
		})
       }
    $scope.img_uploada = function(files) {       //单次提交图片的函数
//      $scope.guid = (new Date()).valueOf();   //通过时间戳创建一个随机数，作为键名使用
        $scope.reader.readAsDataURL(files[0]);  //FileReader的方法，把图片转成base64
        $scope.reader.onload = function(ev) {
            $scope.$applyAsync(function(){
                imgSrc:ev.target.result  //接收base64
            });
        };
        var data = new FormData();
        var path='mct/IDcardfan'+localStorageService.get('uid')+'.png';//以下为像后台提交图片数据
		$scope.imagea='http:\/\/oss51shenga.51shenga.com\/'+path;
   		
        data.append('file', files[0]);
        data.append('path',path);
        return $http({
            method: 'post',
            url: util.baseUrl+"/api/common/uploadFile",
            data:data,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity,
        }).success(function(resp) {        	
        	var adda=document.querySelector(".adda");
        	angular.element(adda).css("display","none");
   			console.log("图片上传成功！");
			console.log($scope.imagea)
   			var objf={};
			objf.imagea=$scope.imagea;
			localStorage.setItem("cunf",JSON.stringify(objf));     //数据存储，对象转字符串	
		})
       }
		
//保存图片
	$scope.bao=function(){
		var img=document.querySelector("img");
		var aa=angular.element(img);
		console.log(aa)
		if(!aa[0].src){
			util.tip("请上传身份证照片！");
		}else{
			
			util.toUrl("/mct/registerzz");
		}
	}
		
		
		
    
    
    
    
    
    
    
    
    
    
    
    
    
}]);
