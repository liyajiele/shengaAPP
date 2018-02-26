/**
 * Created by Administrator on 2017/9/13.
 */
var yijian = angular.module('yijianController', ['webapp','LocalStorageModule','yijianService']);

yijian.controller('yijianNavController',['util',function(util){
  util.navConfig(1);
}]);
yijian.controller('yijianController', ['$window','$location','$rootScope','$scope','$sce','$compile','$http','util','cache','$stateParams','localStorageService','yijianService',function ($window,$location,$rootScope,$scope,$sce,$compile,$http,util,cache,$stateParams,localStorageService,yijianService) {
  util.headerConfig('意见反馈',true);
  // alert(1)
    
//类型
  $scope.records = [
    {
      "Name" : "产品问题",
      "fbType":"1",
      "fbTypeDesc":"1"

    },{
      "Name" : "账号异常",
      "fbType":"2",
      "fbTypeDesc":"2"
      
		
    },{
      "Name" : "其他",
      "fbType":"3",
      "fbTypeDesc":"3"
      
		
    }
  ]
  $scope.chosedIndex = 0;//默认是0使第一个有样式
  $scope.isShow=false;
  $scope.gou= function(index){
    //保存点击的li位置
    $scope.chosedIndex = index;
    // alert($scope.chosedIndex)
    $scope.isShow=index;
    
    
    $scope.fbType=index+1;	//反馈类型
    $scope.fbTypeDesc=index+1;	//反馈类型描述
//  alert($scope.fbType);     
//  alert($scope.fbTypeDesc);     
    
  }
  	//textarea字数限制
		$scope.count = 300;
		$scope.zi=0;
		$scope.checkText = function () {
			console.log($scope.text.length);
	　　　　 $scope.count = 300 - $scope.text.length;
			$scope.zi=$scope.text.length;
		    if ($scope.text.length > 300) {
		        util.tip("只能输入300个字！")
		        $scope.text = $scope.text.substr(0, 300);
		    }
		};
	
	
//点击添加图片
	$scope.reader = new FileReader();   //创建一个FileReader接口
    $scope.form = {     //用于绑定提交内容，图片或其他数据
        image:{},
    };
//  $scope.thumb = {};      //用于存放图片的base64
//  $scope.thumb_default = {    //用于循环默认的‘加号’添加图片的框
//      1111:{}
//  };
$scope.images="";
		
    $scope.img_upload = function(files) {       //单次提交图片的函数
        $scope.guid = (new Date()).valueOf();   //通过时间戳创建一个随机数，作为键名使用
        $scope.reader.readAsDataURL(files[0]);  //FileReader的方法，把图片转成base64
        $scope.reader.onload = function(ev) {
            $scope.$applyAsync(function(){
                imgSrc:ev.target.result  //接收base64
            });
        };
        var data = new FormData();
        var path='feedbackmct/fb'+localStorageService.get("uid")+'-'+$scope.guid+'.png';//以下为像后台提交图片数据
		var image='http:\/\/oss51shenga.51shenga.com\/'+path;
   		
        data.append('file', files[0]);
        data.append('path',path);
        return $http({
            method: 'post',
            url: util.baseUrl+"/api/common/uploadFile",
            data:data,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity,
        }).success(function(resp) {        
            $scope.str = '<li class="yiimglist"><div class="add"></div><input type="file" id="one-input" accept="image/*" file-model="images" onchange="angular.element(this).scope().img_upload(this.files)" name="file" class="file" /><img src="'+'http://oss51shenga.51shenga.com/feedback/fb'+localStorageService.get("uid")+'-'+$scope.guid+'.png" alt="" /></li>';
			angular.element($(".yiimg")).prepend($scope.str);     //解析
			if(angular.element($(".yiimg li")).length==7){
				util.tip("最多可上传6张图片！");
				var cc=angular.element(".yiimglist")[6];
				angular.element(cc).css("display","none");
			}
			
			
   			$scope.images+=image+",";
   			
   			
			$scope.ti=function(){
			   	yijianService.feedback($scope.fbType,$scope.fbTypeDesc,$scope.text,$scope.images).success(function(resp){
//				 	console.log(resp)
					if(resp.code==1){
						util.tip("提交成功！")
					}
				})
		   	}
		})
       }
        

   	
   
}]);
