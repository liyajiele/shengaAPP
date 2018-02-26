/**
 * Created by Administrator on 2017/9/13.
 */
var pay = angular.module('payController', ['webapp','LocalStorageModule','merchantService','orderService']);

pay.controller('payNavController',['util',function(util){
  util.navConfig(1);
}]);
pay.controller('payController', ['cache','$window','$location','$interval','$rootScope','$scope','util','cache','$stateParams','$timeout','$compile','localStorageService','merchantService','orderService','accountService',function (cache,$window,$location,$interval,$rootScope,$scope,util,cache,$stateParams,$timeout,$compile,localStorageService,merchantService,orderService,accountService) {
  util.headerConfig('支付',true);

    //判断是否有uid
var uid=localStorageService.get("uid");
var mid = $stateParams.mid;
if(!uid){
	localStorageService.set("payUrl","/user/pay/"+mid);
	window.location.href="http://api.51shenga.com/wx/user/index";
//	window.location.href = "http://127.0.0.1:8020/shenga/web/index.html#/user/index/24/token";
	return;
}
		
   //商家详情
	merchantService.details(mid).success(function(resp){
//	  	console.log(resp);
	    $scope.details=resp.object;
	    
	})	
	
	//账户信息
	accountService.getAccountInfo().success(function(resp){
        $scope.userInfo = resp.object;
        $scope.money=$scope.userInfo.balance+$scope.userInfo.redBalance+$scope.userInfo.retateBalance;
        console.log($scope.money)
//  	console.log($scope.userInfo)
		if($scope.money=="0"){     //如果账户没钱，余额支付消失
			var aa=document.querySelector(".yupaytop");
			angular.element(aa).css("display","none")			
		}
  	});
  //小键盘
  //总金额
		$scope.Arr=[1,2,3,4,5,6,7,8,9,'x',0,'.'];
        $scope.totalamount="";
        $scope.valClick = function (val) {
            if($scope.totalamount === ''){
                $scope.totalamount=val;
            }else{
                $scope.totalamount = $scope.totalamount+"" + val;
//              console.log($scope.totalamount);
            }
        }
        $scope.valClick1 = function () {
			var len = $scope.totalamount.length;
			$scope.totalamount = $scope.totalamount.substring(0,len-1);
        }
        $scope.valClick2 = function () {
        	var back=document.querySelector(".true");   //按钮变色
			angular.element(back).css("background","#1C82D4");				
			var dazhe=document.querySelector(".dazhe");
			angular.element(dazhe).css("display","block");
            var jianpan=document.querySelector(".jianpand");
			angular.element(jianpan).css("display","none");
        }
    //不参与打折金额
        $scope.brr=[1,2,3,4,5,6,7,8,9,'x',0,'.'];
        $scope.nozhe="";
        $scope.valClickx = function (val) {
            if($scope.nozhe === ''){
                $scope.nozhe=val;
            }else{
                $scope.nozhe = $scope.nozhe+"" + val;
                console.log($scope.nozhe);
            }
        }
        $scope.valClick1x = function () {
			var len = $scope.nozhe.length;
			$scope.nozhe = $scope.nozhe.substring(0,len-1);
        }
        $scope.valClick2x = function () {
            var jianpan=document.querySelector(".jianpanx");
			angular.element(jianpan).css("display","none");
        }
        
        




  //聚焦时样式改变
	$scope.xuana=false;   
	$scope.display=function(){  
		$scope.xuana=!$scope.xuana;
		$scope.xuana=true;
		
		var hui=document.querySelector(".hui");
		angular.element(hui).css("display","none");
		var hei=document.querySelector(".hei");
		angular.element(hei).css("display","block");
		var inputa=document.querySelector(".zhipay");
		angular.element(inputa).css("color","#000");
		var jianpan=document.querySelector(".jianpand");
		angular.element(jianpan).css("display","block");  
		
//		if($scope.totalamount==""){
//			$scope.xuana=false;
//			var hui=document.querySelector(".hui");
//			angular.element(hui).css("display","block");
//			var hei=document.querySelector(".hei");
//			angular.element(hei).css("display","none");
//			var inputa=document.querySelector(".zhipay");
//			angular.element(inputa).css("color","#ccc");
//			
//		}else{
//			$scope.xuana=true;
//			var hui=document.querySelector(".hui");
//			angular.element(hui).css("display","none");
//			var hei=document.querySelector(".hei");
//			angular.element(hei).css("display","block");
//			var inputa=document.querySelector(".zhipay");
//			angular.element(inputa).css("color","#000");
//		}
	}
  	$scope.displayx=function(){
	    var jianpan=document.querySelector(".jianpanx");
		angular.element(jianpan).css("display","block");
  	}
  	
  	//radio点击
  	$scope.myclick=function(){
		$scope.mychecked=!$scope.mychecked;
//		console.log($scope.mychecked);
	}
  	
  	//不参与打折金额
  	$scope.xuan=true;
	$scope.dian=function () {
	    $scope.xuan=!$scope.xuan;	
	    if($scope.xuan){
	    	var jia=document.querySelector(".jia");
	    	angular.element(jia).css("display","none");
	    	var cha=document.querySelector(".cha");
	    	angular.element(cha).css("display","block");
	    }else{
	    	var jia=document.querySelector(".jia");
	    	angular.element(jia).css("display","block");
	    	var cha=document.querySelector(".cha");
	    	angular.element(cha).css("display","none");
	    }    
	}
	$scope.toUrl = function(){
       util.toUrl()
   }



	//确认支付
	$scope.truepay=function(){
		$scope.aa=angular.element(".zhipay").val();
		if($scope.aa==""){
			util.tip("请输入支付金额！");
	        return;
		}

		$scope.bb=angular.element(".zhepay").val();
		if($scope.bb==""){
			$scope.nozhe=0;
		}
		
		$scope.rebateAmount=$scope.totalamount-$scope.nozhe;    //打折金额
		console.log($scope.rebateAmount)
		$scope.damoney=$scope.rebateAmount*$scope.details.profits*$scope.details.rebate;		
		console.log($scope.damoney);
		
		$timeout(function(){
			if(util.iswx){
				if($scope.mychecked==true){
	//				余额支付
					//balance_pay    api  -> if(code==1)  toUrl(paySuccess)
					if(parseInt($scope.totalamount)<parseInt($scope.nozhe)){
						util.tip("不参与打折金额不可大于总金额！");
					}else if($scope.totalamount<=0){
						util.tip("请输入正确的支付金额！");
					}else if($scope.nozhe<0){
						util.tip("请输入正确的不参与打折金额！");
					}else if($scope.totalamount>$scope.money){
						util.tip("余额不足！");
					}else{
						orderService.pay(mid,$scope.payType,$scope.totalamount,$scope.rebateAmount,$scope.totalamount,$scope.longitude,$scope.latitude).success(function(resp){
		//					console.log(resp)			
							if(resp.code==1){
								if(resp.object.tradeStatus==4){
//									return;
									var url = "/user/success/"+mid;
									util.toUrl(url);
		//							var  orderRst = cache.get("orderRst");
				 					cache.put("orderRst",resp.object)
								}else{
									util.tip("支付失败！")
								}
								
							}else{
								util.tip('余额为0,不可用余额支付！')
							}
				
						}).error(function () {
					    	util.tip("支付失败！");
						   	return;
					    })
					}
				}else{
					console.log(uid);
					console.log(mid);
	//				console.log($scope.totalamount);
	//				console.log($scope.rebateAmount);
	//				console.log($scope.balanceAmount);    //为0
					if($scope.nozhe>$scope.totalamount){
						util.tip("不打折金额不可大于总金额！");
					}else if($scope.totalamount<=0){
						util.tip("请输入正确的支付金额！");
					}else if($scope.nozhe<0){
						util.tip("请输入正确的不参与打折金额！");
					}else{
					window.location.href='http://api.51shenga.com/wxpay.html?uid='+uid+'&mid='+mid+'&payType=wx_pay&totalamount='+$scope.totalamount+'&rebateAmount='+$scope.rebateAmount+'&balanceAmount=0&longitude=0&latitude=0 ';
					}
				}
			}
		},1000)
	}




}]);

pay.run(['$rootScope', '$location', function($rootScope, $location) {    //监测路由变化，小键盘消失
    /* 监听路由的状态变化 */
//  $rootScope.$on('$routeChangeStart', function(evt, next, current){
//   console.log('route begin change');
//  }); 
//console.log($location)
    $rootScope.$on('$stateChangeSuccess', function(evt, current, previous) {
//   console.log('route have already changed ：'+$location.path());
     var jian=document.querySelector(".ngcalculator_area");
     angular.element(jian).css("display","none");
    }); 
   }])

//pay.directive('calculator', ['$compile',function($compile) {
//  return {
//      restrict : 'A',
//      replace : true,
//      transclude : true,
//      template:'<input/>',
//
//      link : function(scope, element, attrs) {
//          var keylist=[1,2,3,4,5,6,7,8,9,'x',0,'.'];
//          var calculator = '<div class="ngcalculator_area"><div class="bg"></div>'
//              +'<div class="calculator">'
//              +'<div class="title close">'+attrs.title+'</div><div class="inputarea">'
//              +'<input type="text" id="text" ng-tap="getInput()" class="'+attrs.class+'" ng-model="' +attrs.ngModel+'">'
//              +'</div><div class="con">'
//              +'<div class="left">';
//          $.each(keylist,function(k,v){
//              calculator += '<div class="keyboard num" value="'+v+'">'+v+'</div>';
//          });
//
//          calculator += '</div>'
//              +'<div class="right">'
//              +'<div class="keyboard blueIcon backstep"><img src="images/pay/del.png"></div>'
//              +'<div class="keyboard ensure ensure">完成</div>'
//              +'</div>'
//              +'</div>'
//              +'</div>'
//              +'</div>';
//          calculator = $compile(calculator)(scope);
//          element.bind('focus',function(){
//              document.body.appendChild(calculator[0]);
//              document.activeElement.blur();
//          });
//
//          $(calculator[0]).find("input").focus(function(){
//              document.activeElement.blur();
//          });
//          //关闭模态框
//          $(calculator[0]).find(".close").click(function(){
//              calculator[0].remove();
//              var callback = attrs.callback;
//              if(typeof callback!="undefined"){
//                  scope[callback]();
//              }
//          });
//          $(calculator[0]).find(".bg").click(function(){
//              calculator[0].remove();
//          });
//          //退格
//          $(calculator[0]).find(".backstep").click(function(){
//              if(typeof $(calculator[0]).find("input").val()=="undefined"){
//                  $(calculator[0]).find("input").val("");
//              }
//              $(calculator[0]).find("input").val($(calculator[0]).find("input").val().substring(0,$(calculator[0]).find("input").val().length-1)).trigger('change');
//          });
//          //清空
//          $(calculator[0]).find(".cleanup").click(function(){
//              $(calculator[0]).find("input").val("").trigger('change');
//          });
//          //点击数字
//          $(calculator[0]).find(".num").click(function(){
//              var val = $(calculator[0]).find("input").val();
//              console.log(val)
//              var filter = attrs.filter;
//              if(typeof filter!="undefined"){
//                  val = scope[filter](val,$(this).attr("value"));
//              }else{
//                  val = val+''+$(this).attr("value");
//              }
//              $(calculator[0]).find("input").val(val).trigger('change');
//          });
//          //确认
//          $(calculator[0]).find(".ensure").click(function(){ 
//          	$(".true").css("background-color","#1C82D4");//按钮变色
//          	$(".dazhe").css("display","block");
//              calculator[0].remove();
//              var callback = attrs.callback;
//              if(typeof callback!="undefined"){
//                  scope[callback]();
//              }
//             
//          });
//          //点击效果
//          $(calculator[0]).find(".keyboard").click(function(){
//              $(this).addClass("keydown");
//              var that = this;
//              setTimeout(function(){
//                  $(that).removeClass("keydown");
//              },100)
//          });
//          var position = {
//              startX:0,
//              startY:0
//          };
//          calculator[0].getElementsByClassName("title")[0].addEventListener('touchstart', function(e) {
//              e.preventDefault();
//              var transform = $(calculator[0]).find(".calculator").css("transform").match(/translate\((.*),(.*)\)/);
//              if(transform==null){
//                  position.startX = e.targetTouches[0].clientX;
//                  position.startY = e.targetTouches[0].clientY;
//              }else{
//                  position.startX = e.targetTouches[0].clientX-parseInt(transform[1]);
//                  position.startY = e.targetTouches[0].clientY-parseInt(transform[2]);
//              }
//          }, false);
//          calculator[0].getElementsByClassName("title")[0].addEventListener('touchmove', function(e) {
//              e.preventDefault();
//              var moveX = e.targetTouches[0].clientX-position.startX;
//              var moveY = e.targetTouches[0].clientY-position.startY;
//              $(calculator[0]).find(".calculator").css("transform","translate("+moveX+"px,"+moveY+"px)");
//          }, false);
//      }
//  };
//}]);


//pay.directive('calculators', ['$compile',function($compile) {
//  return {
//      restrict : 'A',
//      replace : true,
//      transclude : true,
//      template:'<input/>',
//
//      link : function(scope, element, attrs) {
//          var keylist=[1,2,3,4,5,6,7,8,9,'x',0,'.'];
//          var calculators = '<div class="ngcalculator_area"><div class="bg"></div>'
//              +'<div class="calculator">'
//              +'<div class="title close">'+attrs.title+'</div><div class="inputarea">'
//              +'<input type="text" id="text" ng-tap="getInput()" class="'+attrs.class+'" ng-model="' +attrs.ngModel+'">'
//              +'</div><div class="con">'
//              +'<div class="left">';
//          $.each(keylist,function(k,v){
//              calculators += '<div class="keyboard num" value="'+v+'">'+v+'</div>';
//          });
//
//          calculators += '</div>'
//              +'<div class="right">'
//              +'<div class="keyboard blueIcon backstep"><img src="images/pay/del.png"></div>'
//              +'<div class="keyboard ensure ensure">完成</div>'
//              +'</div>'
//              +'</div>'
//              +'</div>'
//              +'</div>';
//          calculators = $compile(calculators)(scope);
//          element.bind('focus',function(){
//              document.body.appendChild(calculators[0]);
//              document.activeElement.blur();
//          });
//
//          $(calculators[0]).find("input").focus(function(){
//              document.activeElement.blur();
//          });
//          //关闭模态框
//          $(calculators[0]).find(".close").click(function(){
//              calculators[0].remove();
//              var callback = attrs.callback;
//              if(typeof callback!="undefined"){
//                  scope[callback]();
//              }
//          });
//          $(calculators[0]).find(".bg").click(function(){
//              calculators[0].remove();
//          });
//          //退格
//          $(calculators[0]).find(".backstep").click(function(){
//              if(typeof $(calculators[0]).find("input").val()=="undefined"){
//                  $(calculators[0]).find("input").val("");
//              }
//              $(calculators[0]).find("input").val($(calculators[0]).find("input").val().substring(0,$(calculators[0]).find("input").val().length-1)).trigger('change');
//          });
//          //清空
//          $(calculators[0]).find(".cleanup").click(function(){
//              $(calculators[0]).find("input").val("").trigger('change');
//          });
//          //点击数字
//          $(calculators[0]).find(".num").click(function(){
//              var val = $(calculators[0]).find("input").val();
//              var filter = attrs.filter;
//              if(typeof filter!="undefined"){
//                  val = scope[filter](val,$(this).attr("value"));
//              }else{
//                  val = val+''+$(this).attr("value");
//              }
//              $(calculators[0]).find("input").val(val).trigger('change');
//          });
//          //确认
//          $(calculators[0]).find(".ensure").click(function(){
//          	$(".true").css("background-color","#1C82D4");//按钮变色
//          	$(".dazhe").css("display","block");
//              calculators[0].remove();
//              var callback = attrs.callback;
//              if(typeof callback!="undefined"){
//                  scope[callback]();
//              }
//          });
//          //点击效果
//          $(calculators[0]).find(".keyboard").click(function(){
//              $(this).addClass("keydown");
//              var that = this;
//              setTimeout(function(){
//                  $(that).removeClass("keydown");
//              },100)
//          });
//          var position = {
//              startX:0,
//              startY:0
//          };
//          calculators[0].getElementsByClassName("title")[0].addEventListener('touchstart', function(e) {
//              e.preventDefault();
//              var transform = $(calculators[0]).find(".calculators").css("transform").match(/translate\((.*),(.*)\)/);
//              if(transform==null){
//                  position.startX = e.targetTouches[0].clientX;
//                  position.startY = e.targetTouches[0].clientY;
//              }else{
//                  position.startX = e.targetTouches[0].clientX-parseInt(transform[1]);
//                  position.startY = e.targetTouches[0].clientY-parseInt(transform[2]);
//              }
//          }, false);
//          calculators[0].getElementsByClassName("title")[0].addEventListener('touchmove', function(e) {
//              e.preventDefault();
//              var moveX = e.targetTouches[0].clientX-position.startX;
//              var moveY = e.targetTouches[0].clientY-position.startY;
//              $(calculators[0]).find(".calculators").css("transform","translate("+moveX+"px,"+moveY+"px)");
//          }, false);
//      }
//  };
//}]);