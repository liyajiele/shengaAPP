/**
 * Created by Administrator on 2017/9/13.
 */
var details = angular.module('detailsController', ['webapp','LocalStorageModule','userService']);

details.controller('detailsNavController',['util',function(util){
  util.navConfig(1);
}]);
details.controller('detailsController', ['$window','$location','$rootScope','$scope','$http','$filter','util','cache','$stateParams','localStorageService','userService',function ($window,$location,$rootScope,$scope,$http,$filter,util,cache,$stateParams,localStorageService,userService) {
  util.headerConfig('详情',true);

var uid=localStorageService.get("uid");
var mid=localStorageService.get("mid");
$scope.uid=uid;
$scope.mid=mid;
console.log($scope.uid,$scope.mid)


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
        var path='mct/mainImage'+localStorageService.get('uid')+'.png';//以下为像后台提交图片数据
		$scope.image='http:\/\/oss51shenga.51shenga.com\/'+path;
   		
        data.append('file', files[0]);
        data.append('path',path);
        return $http({
            method: 'post',
            url: util.baseUrl+"/api/common/uploadFile",
            data:data,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity,
        }).success(function(resp) {        	
        	var add=document.querySelector(".adda");
        	angular.element(add).css("display","none");
   			console.log("图片上传成功！");
		})
       }



//返利信息
$scope.records = [
      {
        "Name" : "全返",
        "id":"1"
      },{
        "Name" : "部分返利",
        "id":"2"
      }
    ]

	$scope.chosed = 0;//默认是0使第一个有样式
    $scope.isShow=false;
    $scope.goul = function(index){
      	$scope.chosed = index;   //保存点击的li位置
     	$scope.isShow=index;
    	$scope.type = !$scope.type;   
//  	console.log(index)
    	$scope.rebateType=index+1;
    }

//返利额度
	$scope.fan = [
	{
		"Name" : "10%",
		"profits":"0.1",
        "id":"1"
    },{
		"Name" : "15%",
		"profits":"0.15",
        "id":"2"
    },{
		"Name" : "20%",
		"profits":"0.2",
        "id":"3"
    },{
		"Name" :  "25%",
		"profits":"0.25",
        "id":"4"
    },{
		"Name" :  "30%",
		"profits":"0.3",
        "id":"5"
    },{
		"Name" : "35%",
		"profits":"0.35",
        "id":"6"
    },{
		"Name" : "40%",
		"profits":"0.4",
        "id":"7"
    },{
		"Name" :  "45%",
		"profits":"0.45",
        "id":"8"
    },{
		"Name" : "自定义额度",
        "id":"9"
    },
]
	
 //点击每一个li
    $scope.dian=function(index){
    	$scope.chosedIndex=index;
//  	console.log($scope.chosedIndex)   //当前下标
    	var aa=document.querySelector(".con")[index];
    	angular.element(aa).addClass('biana');
    	console.log(angular.element(aa));
    	console.log($scope.fan[index].Name);
    	console.log($scope.fan[index].profits);
    	if(index==8){
    		angular.element(".detailsa").css({"display":"block"});  
    	}

    }
    
//自定义额度判断
$scope.que=function(){
	if(!$scope.min){
		util.tip("请输入自定义额度！")
	}else if($scope.min<10){
		util.tip("自定义额度最低为10%")
	}else{
		angular.element(".detailsa").css({"display":"none"});
		console.log($scope.fan[8].Name);
		$scope.fan[8].Name=$scope.min+"%(自定义)";
		var ba=document.querySelectorAll(".con")[8];
		angular.element(ba).css("font-size","0.24rem");
		$scope.profits=$scope.min.replace(/%/, "")/100;
		console.log($scope.min.replace(/%/, "")/100);
	}
}
    
//自定义额度取消
$scope.quxiao=function(){
	angular.element(".detailsa").css({"display":"none"})
}
    
    //店铺信息
    userService.mct().success(function(resp){
    	console.log(resp);
    	$scope.mct=resp.object.content[0];
    	console.log($scope.mct);
    	

//  	$scope.agentId=$scope.mct.agentId;
    	$scope.areaId=$scope.mct.areaId;
    	$scope.provinceId=$scope.mct.provinceId;
    	$scope.cityId=$scope.mct.cityId;
    	$scope.district=$scope.mct.district;
    	$scope.mctName=$scope.mct.nickname;
    	console.log($scope.mctName)
    	$scope.addr=$scope.mct.addr;    //地址
    	$scope.mobile=$scope.mct.mobile;
    	$scope.shopHours=$scope.mct.shopHours;    //营业时间
    	$scope.image=$scope.mct.mainImage;   //主图
    	$scope.images=$scope.mct.images;
    	$scope.types=$scope.mct.types;
    	$scope.ownerRealName=$scope.mct.ownerRealName;
    	$scope.ownerIdcard=$scope.mct.ownerIdcard;
    	$scope.idcardImage1=$scope.mct.idcardImage1;
    	$scope.idcardImage2=$scope.mct.idcardImage2;
    	$scope.licenceType=$scope.mct.licenceType;
    	$scope.licenceStatus=$scope.mct.licenceStatus;
    	$scope.licenceImage=$scope.mct.licenceImage;
    	$scope.longitude=$scope.mct.longitude;
    	$scope.latitude=$scope.mct.latitude;
    	$scope.consumerption=$scope.mct.consumerption;
    	$scope.profits=$scope.mct.profits;     //返利
    	$scope.rebateType=$scope.mct.rebateType;    //返利类型
    	
    	$scope.commentNum=$scope.mct.commentNum;
    	$scope.createTime=$scope.mct.createTime;
    	$scope.id=$scope.mct.id;
    	$scope.orderNum=$scope.mct.orderNum;
    	$scope.stars=parseInt($scope.mct.stars);
    	console.log($scope.stars);
    	
    	
    	$scope.tags=resp.object.content[0].tags;
    	if(!$scope.tags){
    		
    	}else{
    		$scope.arr=$scope.tags.split("|"); 
			console.log($scope.arr)
    	}
		
		var cunad =JSON.parse(sessionStorage.getItem("cunad"));   //注册缓存
		if(!cunad){
			$scope.addr=$scope.mct.addr;
			var objad={};
			objad.addr=$scope.addr;
			sessionStorage.setItem("cunad",JSON.stringify(objad));
		}else{
			$scope.addr=cunad.addr;	
		}
		
		// 选择时间
	    $scope.timerange="请添加营业时间";
	    $scope.shi=true;
		$scope.time=function () {
	    	$scope.shi=!$scope.shi;
			var bb=document.querySelector(".time");
			angular.element(bb).css("display","block");
	    	$scope.shi=false;
	    	
	    	
	    	$(function(){
				$('.range-slider').jRange({
					from: 0, to: 1440, step:30,
					scale: ['00:00','04:00','08:00','12:00','16:00','20:00','24:00'],
					format: '%s',
					width: 300,
					showLabels: true,
					isRange : true,
					marginLeft:50,
				});
				$scope.queque=function(){
		    		var aa=document.querySelector(".time");
					angular.element(aa).css("display","none");
					var bb=$(".pointer-label").eq(0).html();
					console.log(bb)
					var cc=$(".pointer-label").eq(1).html();
					console.log(cc)
					$scope.shopHours=bb+"-"+cc;
				}
			});
		}
	   
		$scope.ququ=function () {
			var bb=document.querySelector(".time");
			angular.element(bb).css("display","none");
		}
    })
    
//跳转
	$scope.toUrl = function(url){
       util.toUrl(url)
    }
$scope.xiu=function(){
	console.log($scope.mctName);
	console.log($scope.types);
	console.log($scope.addr);
    console.log($scope.image);	
    console.log($scope.shopHours);
    console.log($scope.rebateType);
    console.log($scope.profits)
	//修改信息
    userService.xiu($scope.uid,$scope.mid,$scope.areaId,$scope.provinceId,$scope.cityId,
    	$scope.districtId,$scope.mctName,$scope.addr,$scope.mobile,$scope.shopHours,$scope.mainImage,$scope.images,
    	$scope.types,$scope.ownerRealName,$scope.ownerIdcard,$scope.idcardImage1,$scope.idcardImage2,$scope.licenceType,
    	$scope.licenceStatus,$scope.licenceImage,$scope.longitude,$scope.latitude,$scope.consumerption,$scope.profits,$scope.rebateType).success(function(resp){
			console.log(resp);
			
			
//			util.toUrl("/mct/inaudit")
   		})
}
   












//星星
  $scope.max = 5;
  $scope.ratingVal = 3;
  $scope.readonly = true;
  $scope.onHover = function(val){
    $scope.hoverVal = val;
  };
  $scope.onLeave = function(){
    $scope.hoverVal = null;
  }
  $scope.onChange = function(val){
    $scope.ratingVal = val;
  }

}]);

//星星
app.directive('star', function () {
  return {
    template: '<ul class="rating" ng-mouseleave="leave()">' +
        '<li ng-repeat="star in stars" ng-class="star" ng-click="click($index + 1)" ng-mouseover="over($index + 1)">' +
        '<i class="iconfont icon-xing"></i>' +
        '</li>' +
        '</ul>',
    scope: {
      ratingValue: '=',
      max: '=',
      readonly: '@',
      onHover: '=',
      onLeave: '='
    },
    controller: function($scope){
      $scope.ratingValue = $scope.ratingValue || 0;
      $scope.max = $scope.max || 5;
      $scope.click = function(val){
        if ($scope.readonly && $scope.readonly === 'true') {
          return;
        }
        $scope.ratingValue = val;
      };
      $scope.over = function(val){
        $scope.onHover(val);
      };
      $scope.leave = function(){
        $scope.onLeave();
      }
    },
    link: function (scope, elem, attrs) {
      elem.css("text-align", "center");
      var updateStars = function () {
        scope.stars = [];
        for (var i = 0; i < scope.max; i++) {
          scope.stars.push({
            filled: i < scope.ratingValue
          });
        }
      };
      updateStars();
 
      scope.$watch('ratingValue', function (oldVal, newVal) {
        if (newVal) {
          updateStars();
        }
      });
      scope.$watch('max', function (oldVal, newVal) {
        if (newVal) {
          updateStars();
        }
      });
    }
  };
});