var index = angular.module('indexController', ['webapp','LocalStorageModule','bannerService','userService','merchantService','addressService','accountRecordService']);

index.controller('indexNavController',['util',function(util){
  util.navConfig(1);
}]);
index.controller('indexController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','$timeout','$http','localStorageService','bannerService','$http','userService','merchantService','addressService','accountRecordService',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,$timeout,$http,localStorageService,bannerService,$http,userService,merchantService,addressService,accountRecordService) {
    util.headerConfig('省啊',true);

//判断登录状态
		var uid = $stateParams.uid;
		var token = $stateParams.token;
		if(uid && token){
			localStorageService.set("uid",uid);
			localStorageService.set("token",token);
			util.toUrl("/user/index");
			return;
		}
		if(localStorageService.get("payUrl")){
			var payUrl =localStorageService.get("payUrl");
			localStorageService.remove("payUrl");
			util.toUrl(payUrl);
		}
		
//跳转
    $scope.toUrl = function(url){
        util.toUrl(url);
    }
		$scope.cai=function(){    //精彩活动
			window.location="https://common.ofo.so/newdist/?MouthCardPage=&channel=33096_1509961301045&type=2&duringType=1&subType=86305724&from=singlemessage"
		}
		$scope.duiba=function(){    //兑吧
			var time=new Date().getTime();
			console.log(time)
			userService.duiba().success(function(resp){
				console.log(resp.object)
				window.location.href=resp.object;
			})
		}

		
//头条
//		$scope.datesetData=accountRecordService;
    $scope.tou=accountRecordService;
    console.log($scope.tou.newArray);
//  $scope.$apply(function () {
//  	
//  	alert("1")
//		$timeout(function(){
// 		 $rootScope.datasetData=$scope.tou.newArray;
// 		 console.log($scope.datasetData);
// 		 
//		},1000)
//  });
// 		 console.log($rootScope.datasetData);

    $scope.datasetData = [
            {content : "小小获得返利0.1元"},
            {content : "小小获得返利0.15元"},
            {content : "小小获得返利0.58元"},
            {content : "小小获得返利0.83元"},
            {content : "静静获得返利0.1元"},
            {content : "珍爱17735579662 抢到大红包一个"}
        ]

    
//banner
		bannerService.getBanners().success(function(resp){
			$scope.aboutInfo = resp.object;
//				console.log($scope.aboutInfo);
		});
      
      
 //搜索城市
		$scope.xuan=false;
		$scope.city=function () {
	    $scope.xuan=!$scope.xuan;
	  }
		
		
//附近商家
		$scope.xiang=function(mid){
			var url = "/user/details/"+mid;
			util.toUrl(url);
		}
		merchantService.merchantArray=[];
		merchantService.merchantPage=0;
		merchantService.merchantLast=false;
		$scope.merchant=merchantService;
		console.log($scope.merchant)
	
//换一批		
		$scope.huan=function(){    //点击页数增加
				$scope.merchant.merchantPage+=0;
				console.log($scope.merchant.merchantPage);
			
				$scope.merchant.merchantArray=[];
				$scope.merchant.merchantLast=false;
				$scope.merchant.nearmerchant();
				if($scope.merchant.merchantPage==3){
					$scope.merchant.merchantPage=1;
					$scope.merchant.nearmerchant();
					
				}
		}
		
//百度定位
	var geolocation = new BMap.Geolocation();
	var geoc = new BMap.Geocoder();
    if(sessionStorage.cun){
		var cun =JSON.parse(sessionStorage.getItem("cun"));
		console.log(cun)
		var cuna =JSON.parse(sessionStorage.getItem("cuna"));
		console.log(cuna)
		$scope.tid=cun.tid;
		$scope.citycity=cun.citycity;
		$scope.cityid=cun.cityid;
		$scope.districtid=cun.districtid;
//				$scope.districtid=cuna.districtid;
		console.log($scope.districtid)
		var obj={};
		obj.tid=$scope.tid;
		obj.citycity=$scope.citycity;
		obj.cityid=$scope.cityid;
//				
//				
//				//根据城市ID获取区域列表
	  addressService.cityId($scope.cityid).success(function(resp){//获取城市区域
	    $scope.cityId=resp.object;
//			    console.log($scope.cityId)
			
			//城市点击
			$scope.diancity=function(index){
				$scope.districtid=$scope.cityId[index].tId;    //当前点击城市的tId
				
				obj.districtid=$scope.districtid;
//							console.log($scope.districtid)
				 $scope.merchant=merchantService;
					merchantService.merchantArray=[];
					merchantService.merchantPage=0;
					merchantService.merchantLast=false;
//							 console.log($scope.merchant)
				
				$timeout(function(){
				$scope.xuan=!$scope.xuan;
				},1000)
				util.tip("区域选择成功！")
				sessionStorage.setItem("cun",JSON.stringify(obj));     //数据存储，对象转字符串																					
		}
	   
			
	  })			
    }else{
			util.tip("正在为您定位！");
			geolocation.getCurrentPosition(function (pos) {
		        var lng = pos.point.lng;//当前经度
		        var lat = pos.point.lat;//当前纬度
		        $scope.merchant=merchantService;          //mct.distance
		        var merchant=$scope.merchant.merchantArray;
						var obj={
							lat:lat,
							lng:lng,
							merchant:merchant,
						}

			//定位到省市区
		        var point = new BMap.Point(lng, lat);
		        geoc.getLocation(point, function (rs) {
			        var addComp = rs.addressComponents;
			        var add=addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
			    	});
						if(typeof(Storage)!=="undefined"){
						  localStorage.lng=lng;
						  localStorage.lat=lat;
							userService.updateLocation(lng,lat).success(function(resp){
				  				//1 .修改左上角   获取商家列表							
				  				addressService.location(lng,lat).success(function(resp){
//											console.log(resp)       //输出当前位置    省市区
											$scope.location=resp.object;
											$scope.cityid=$scope.location.city.tId;     //当前城市ID
											$scope.citycity=$scope.location.city.name;    //当前城市姓名
											$scope.districtid=$scope.location.district.tId;     //当前区域id
											obj.cityid=$scope.cityid;
											obj.citycity=$scope.citycity;
											obj.districtid=$scope.districtid;
											sessionStorage.setItem("cun",JSON.stringify(obj));     //数据存储，对象转字符串		
								
//											util.tip("请在左上角选择你所在的区域")
//																
											//根据城市ID获取区域列表
										  addressService.cityId($scope.cityid).success(function(resp){
										    $scope.cityId=resp.object;
										    obj.cityId=$scope.cityId;
														$scope.diancity=function(index){
															$scope.districtid=$scope.cityId[index].tId;    //当前点击城市的tId
															console.log($scope.districtid)
																var aa={};
																aa.districtid=$scope.districtid;
																sessionStorage.setItem("cuna",JSON.stringify(aa));     //数据存储，对象转字符串				
															
															
															$timeout(function(){
						   									$scope.xuan=!$scope.xuan;
															},1000)
															util.tip("区域选择成功！")
			   											$scope.merchant=merchantService;				
			   											
														}
										  })							
									})	
							})
						} 	
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
        '<li ng-repeat="star in stars" ng-class="star" ng-click="click($index + 1)" ng-mouseover="over($index + 1)" style="font-size:0.4rem">' +
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

//小轮播
app.directive("slideFollow",function($timeout){
//			console.log("11111")
//  	$scope.tou=accountRecordService;
// 		 $scope.datasetData=$scope.tou.newArray;
		
        return {
            restrict : 'E',
            replace : true,
            scope : {
                id : "@",
                datasetData : "="
            },
            template : "<li ng-repeat = 'data in datasetData'>{{data.content}}</li>",
            link : function(scope,elem,attrs) {
                $timeout(function(){
                    var className = $("." + $(elem).parent()[0].className);
                    var i = 0,sh;
                    var liLength = className.children("li").length;
                    var liHeight = className.children("li").height() + parseInt(className.children("li").css('border-bottom-width'));
                    className.html(className.html() + className.html());

                    // 开启定时器
                    sh = setInterval(slide,4000);

                    function slide(){
                        if (parseInt(className.css("margin-top")) > (-liLength * liHeight)) {
                            i++;
                            className.animate({
                                marginTop : -liHeight * i +6+ "px"
                            },"slow");
                        } else {
                            i = 0;
                            className.css("margin-top","0px");
                        }
                    }

                    // 清除定时器
                    className.hover(function(){
                        clearInterval(sh);
                    },function(){
                        clearInterval(sh);
                        sh = setInterval(slide,4000);
                    })


                },0)

            }
        }
    })