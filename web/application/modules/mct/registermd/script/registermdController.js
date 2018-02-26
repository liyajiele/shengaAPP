/**
 * Created by miss on 2015/11/19.
 */
var registermd = angular.module('registermdController',['webapp','LocalStorageModule','userService','addressService']);

registermd.controller('registermdController',['util',function(util){
    util.headerConfig("注册",true);
}]);
registermd.controller('registermdController',['$location','$scope','$timeout','$rootScope','util','$http','localStorageService','userService','addressService',function($location,$scope,$timeout,$rootScope,util,$http,localStorageService,userService,addressService){

    $scope.rmm = "../images/login/you.png";
    
    var uid = localStorageService.get('uid');
    
//商家类型
    userService.mcttype().success(function(resp){
//		console.log(resp);
  		$scope.mcttype=resp.object;		
  	})
    var cuna =JSON.parse(localStorage.getItem("cuna"));
    if(!cuna){
    	console.log("填写信息");

    }else{
    	var cuna =JSON.parse(localStorage.getItem("cuna"));
	    console.log(cuna)
	    $scope.shmc=cuna.shmc;
	    $scope.dizhi=cuna.dizhi;
	    $scope.typename=cuna.typename;
	    $scope.code=cuna.code;
	    $scope.timerange=cuna.timerange;
	    $scope.image=cuna.image;
    }
    
    
    //选择类型
	    $scope.xuan=true;
	  	$scope.type=function () {
	    	$scope.xuan=!$scope.xuan;
			var aa=document.querySelector(".type");
			angular.element(aa).css("display","block");
			$scope.xuan=false;
		}
	  	$scope.qu=function () {
			var aa=document.querySelector(".type");
			angular.element(aa).css("display","none");
		}
    	$scope.typename="选择商户类型";
	    //点击每一个li
	    $scope.chosedIndexARR=[];  
	    $scope.typeid=[];
	    var flag=false;
	    $scope.dian=function(typeName,typeid,index){  		
	    	if(!~$scope.chosedIndexARR.indexOf(typeName)){     //添加
				if(index!=4){
					$scope.chosedIndexARR.push(typeName);
					$scope.typeid.push(index+1);
	//				$scope.typeid+=index+1+"|";
					console.log($scope.typeid);
				    console.log($scope.chosedIndexARR);    //输出数组
				    var aa=document.querySelectorAll(".con")[index];   
					angular.element(aa).css({background:"#1C82D4",color: "#FFFFFF"});
				}else{     //全选
					if(flag==false){      
						flag=true;
		    			$scope.chosedIndexARR=[];   
		    			$scope.typeid=[1,2,3,4];
	//	    			$scope.typeid="";
	//	    			$scope.typeid="1|2|3|4|5";
		    			console.log($scope.typeid);
						$scope.chosedIndexARR.push($scope.mcttype[0].typeName,$scope.mcttype[1].typeName,$scope.mcttype[2].typeName,$scope.mcttype[3].typeName);
					    console.log($scope.chosedIndexARR);    //输出数组
					    var aa=document.querySelectorAll(".con");   
						angular.element(aa).css({background:"#1C82D4",color: "#FFFFFF"});
					}else{    //全删
						flag=false;
						$scope.chosedIndexARR=[];//取消选中
	//					$scope.typeid="";
						$scope.typeid=[];
						var aa=document.querySelectorAll(".con");   
				    	angular.element(aa).css({background:"#fff",color: "#353535"});
	//				    console.log($scope.chosedIndexARR);    //输出数组	    				
					}
				}	    	
			}else{    //删除
		    	$scope.chosedIndexARR.splice($scope.chosedIndexARR.indexOf(typeName),1);//取消选中
		    	console.log($scope.typeid.indexOf(index+1));
		    	$scope.typeid.splice($scope.typeid.indexOf(index+1),1);//取消选中
	//	    	console.log($scope.typeid.indexOf(index));
	//	    	$scope.typeid=$scope.typeid.slice($scope.typeid.indexOf(index),1);
		    	console.log($scope.typeid);
	//	    	$scope.id=$scope.typeid.join("|");
				var aa=document.querySelectorAll(".con")[index];   
		    	angular.element(aa).css({background:"#fff",color: "#353535"});
		    	var bb=document.querySelectorAll(".con")[4];   
				angular.element(bb).css({background:"#fff",color: "#353535"});
			    console.log($scope.chosedIndexARR);    //输出数组	  
	//		    console.log($scope.typeid);    //输出数组	 
	//		    console.log($scope.id);
			}
	
			
			
			$scope.que=function(){    //确定
	    		var aa=document.querySelector(".type");
				angular.element(aa).css("display","none");
				$scope.typename=$scope.chosedIndexARR;
	//			$scope.typeid=$scope.
			}
	    }
	     // 选择时间    
	    $scope.timerange="请添加营业时间";
	    document.getElementById('addtime').addEventListener('click', function () {
        	picker.show();
	    });
	    var $hour = [],$minute=[];
	    for (var i=0;i<12;i++){
	        var h = i<10 ? '0'+i : i;
	        $hour.push({
	            text: h,
	            value: h
	        })
	    }
	    for (var i=0;i<60;i++){
	        var m = i<10 ? '0'+i : i;
	        $minute.push({
	            text: m,
	            value: m
	        })
	    }
	    var picker = new Picker({
	        data: [
	            $hour,
	            [{text: '时',value: 0}],
	            $minute,
	            [{text: '分',value: 0}],
	            [{text: '-',value: 0}],
	            $hour,
	            [{text: '时',value: 0}],
	            $minute,
	            [{text: '分',value: 0}]
	        ],
	        selectedIndex: [0,0,0,0,0,0,0,0,0],
	    });
	
	    picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
	        var $time = selectedVal[0] + '：' + selectedVal[2] + ' - ' + selectedVal[5] + '：' + selectedVal[7];
	        $('#timeBox').append('<li><span class="selectTime-l">分时段</span><div class="selectTime-c">' + $time +'</div><i class="iconfont icon-shanchu selectTime-r"></i></li>')
	    });

//	    $scope.shi=true;
//		$scope.time=function () {
//	    	$scope.shi=!$scope.shi;
//			var bb=document.querySelector(".time");
//			angular.element(bb).css("display","block");
//	    	$scope.shi=false;
//	    	
////	    	$timeout(function(){
//	    		$(function(){
//					$('.range-slider').jRange({
//						from: 0, to: 1440, step:30,
//						scale: ['00:00','04:00','08:00','12:00','16:00','20:00','24:00'],
//						format: '%s',
//						width: 300,
//						showLabels: true,
//						isRange : true,
//						marginLeft:50,
//					});
//					$scope.queque=function(){
//			    		var aa=document.querySelector(".time");
//						angular.element(aa).css("display","none");
//						var bb=$(".pointer-label").eq(0).html();
//	//					console.log(bb)
//						var cc=$(".pointer-label").eq(1).html();
//	//					console.log(cc)
//						$scope.timerange=bb+"-"+cc;
//					}
//				});
////	    	},5000)
//	    	
//		}
//		$scope.time();
//		
//	   
//		$scope.ququ=function () {
//			var bb=document.querySelector(".time");
//			angular.element(bb).css("display","none");
//		}
  	
  	
	
    
    
    
    
    
    
    

//	var cun =JSON.parse(localStorage.getItem("cun"));   //注册缓存
//	if(!cun){
//		
//	}else{
//	    console.log(cun)
//	    $scope.mobile=cun.mobile;
//	    $scope.password=cun.password;
//	}
   
    
    

   
    
 //百度定位
	var geolocation = new BMap.Geolocation();
	var geoc = new BMap.Geocoder();
	if(localStorage.cunc){
		var cunc =JSON.parse(localStorage.getItem("cunc"));
		console.log(cunc)
		var cuna =JSON.parse(localStorage.getItem("cuna"));
		console.log(cuna)
		$scope.tid=cunc.tid;
		$scope.areaid=cunc.areaid;
		$scope.citycity=cunc.citycity;
		$scope.cityid=cunc.cityid;
		$scope.districtid=cunc.districtid;
		$scope.proid=cunc.proid;
		$scope.proname=cunc.proname;
		$scope.lat=cunc.lat;
		$scope.lng=cunc.lng;
		console.log($scope.districtid)
		var objc={};
		objc.areaid=$scope.areaid;
		objc.tid=$scope.tid;
		objc.citycity=$scope.citycity;
		objc.cityid=$scope.cityid;		
		objc.districtid=$scope.districtid;
		objc.proid=$scope.proid;
		objc.proname=$scope.proname;
		objc.lat=$scope.lat;
		objc.lng=$scope.lng;
		localStorage.setItem("cunc",JSON.stringify(objc));
		
    }else{
	geolocation.getCurrentPosition(function (pos) {
	    $scope.lng = pos.point.lng;//当前经度
	    $scope.lat = pos.point.lat;//当前纬度
//			var objc={
//				lat:lat,
//				lng:lng,
//			}
//var objjw={};
//$scope.lng=lng;
//$scope.lat=lat;
//localStorage.setItem("cunjw",JSON.stringify(objjw));
	
	//定位到省市区
	    var point = new BMap.Point($scope.lng, $scope.lat);
	    geoc.getLocation(point, function (rs) {
	        var addComp = rs.addressComponents;
	        $scope.dizhi=addComp.province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber;
    	});
		if(typeof(Storage)!=="undefined"){
		  	localStorage.lng=$scope.lng;
		  	localStorage.lat=$scope.lat;
			userService.updateLocation($scope.lng,$scope.lat).success(function(resp){
  				//1 .修改左上角   获取商家列表							
  				addressService.location($scope.lng,$scope.lat).success(function(resp){
//console.log(resp)       //输出当前位置    省市区
					$scope.location=resp.object;
					console.log($scope.location);
					$scope.proname=$scope.location.province.name;     //省
					console.log($scope.proname)
 					$scope.areaid=$scope.location.province.areaId;    //大区id
 					$scope.proid=$scope.location.province.tId;    //省id
					$scope.citycity=$scope.location.city.name;    //当前城市姓名
					console.log($scope.citycity)
					$scope.cityid=$scope.location.city.tId;     //当前城市ID
					$scope.disname=$scope.location.district.name;    //当前区域名字
					$scope.districtid=$scope.location.district.tId;     //当前区域id
//					$scope.dizhi=$scope.proname+""+$scope.citycity;
					var objc={};
					objc.proname=$scope.proname;
					objc.areaid=$scope.areaid;
					objc.proid=$scope.proid;
					objc.cityid=$scope.cityid;
					objc.disname=$scope.disname;
					objc.citycity=$scope.citycity;
					objc.districtid=$scope.districtid;
					objc.lat=$scope.lat;
					console.log(objc.lat)
					objc.lng=$scope.lng;
					localStorage.setItem("cunc",JSON.stringify(objc));
				})
  			})
		}
	})  
}
    
	
//跳转
$scope.toUrl=function(url){
	util.toUrl(url);
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

//提交
    $scope.register = function(){
        if($scope.shmc === undefined){
            util.tip("请输入商户名称");
            return;
        }
        if($scope.dizhi === undefined){
            util.tip("请输入商户地址");
            return;
        }
        if(!(/^1[0-9][0-9]\d{4,8}$/.test($scope.code))){
            util.tip("请输入正确的商户电话");
            return;
        }
        if($scope.typename=="选择商户类型"){
        	util.tip("请选择商户类型！");
        	return;
        }
        if($scope.timerange=="请添加营业时间"){
        	util.tip("请添加营业时间");
        	return;
        }
        if($scope.image==""){
        	util.tip("请选择门店图片");
        	return;
        }
        console.log($scope.image)
        $scope.typeid=$scope.typeid.join("|");
		console.log($scope.typeid);
        var obja={};
        obja.citycity=$scope.citycity;
        obja.shmc=$scope.shmc;
        obja.dizhi=$scope.dizhi;
        obja.code=$scope.code;
        obja.mobile=$scope.mobile;
        obja.password=$scope.password;
        obja.image=$scope.image;
        obja.typename=$scope.typename;
        obja.typeid=$scope.typeid;
        obja.timerange=$scope.timerange;
        localStorage.setItem("cuna",JSON.stringify(obja));     //数据存储，对象转字符串	
        util.toUrl("/mct/registerzz");
		
    }
    


}]);