var licence = angular.module('licenceController', ['webapp','LocalStorageModule']);

licence.controller('licenceController',['util',function(util){
    util.navConfig(1);
}]);
licence.controller('licenceController', ['$window','$location','$rootScope','$scope','util','$http','cache','$stateParams','localStorageService',function ($window,$location,$rootScope,$scope,util,$http,cache,$stateParams,localStorageService) {
    util.headerConfig('上传营业执照',true);
    
    $scope.display=function(){
    	var zheng=document.querySelector(".li");
    	angular.element(zheng).css("display","block");
    }
    $scope.displaya=function(){
    	var zheng=document.querySelector(".li");
    	angular.element(zheng).css("display","none");
    }
    
//上传图片
//点击添加图片
	$scope.reader = new FileReader();   //创建一个FileReader接口
    $scope.form = {     //用于绑定提交内容，图片或其他数据
        image:{},
    };
	$scope.images="";
	$scope.img_upload = function(files) {       //单次提交图片的函数
//      $scope.guid = (new Date()).valueOf();   //通过时间戳创建一个随机数，作为键名使用
        $scope.reader.readAsDataURL(files[0]);  //FileReader的方法，把图片转成base64
        $scope.reader.onload = function(ev) {
            $scope.$applyAsync(function(){
                imgSrc:ev.target.result  //接收base64
            });
        };
        var data = new FormData();
        var path='mct/licence'+localStorageService.get('uid')+'.png';//以下为像后台提交图片数据
		$scope.image='http:\/\/oss51shenga.51shenga.com\/'+path;
   		console.log($scope.image)
		var objl={};
		objl.image=$scope.image;
		localStorage.setItem("cunl",JSON.stringify(objl));     //数据存储，对象转字符串	
		var cunl =JSON.parse(localStorage.getItem("cunl"));
		console.log(cunl)
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
    
    
    //保存图片
	$scope.bao=function(){
		var img=document.querySelector("img");
		var aa=angular.element(img);
		console.log(aa)
		if(!aa[0].src){
			util.tip("请上传营业执照照片！");
		}else{
			util.toUrl("/mct/registerzz");
		}
	}
    
    
    
    
}]);
