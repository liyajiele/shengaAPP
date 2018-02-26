/**
 * Created by Administrator on 2017/9/13.
 */

var generalize = angular.module('generalizeController', ['webapp','LocalStorageModule','userService']);

generalize.controller('generalizeNavController',['util',function(util){
  util.navConfig(3);
}]);
generalize.controller('generalizeController', ['$window','$location','$rootScope','$scope','util','cache','$stateParams','localStorageService','userService','$http',function ($window,$location,$rootScope,$scope,util,cache,$stateParams,localStorageService,userService,$http) {
  util.headerConfig('推广赚钱',true);
//alert(1)
//推广赚钱的详细信息
	var uid=localStorageService.get("uid");
	console.log(uid);
	userService.people(uid).success(function(resp){
		console.log(resp)
		$scope.aboutInfo = resp.object;
		var pad = "000000";  
        $scope.codema= 'SA'+pad.substring(0, pad.length - uid.length) + uid ; 
	})
}]);
generalize.directive('qr', ['$timeout', '$window', function ($timeout, $window) {

    return {
        restrict: 'ACE',
        link: function(scope, element, attrs) {
          //字符串生成二维码
	      //  var aa=document.querySelector("#qrcode");
	       // $scope.qrcode=angular.element(aa);
	        //console.log($scope.qrcode);
	     
	        scope.$watch('aboutInfo.qrcode',function(newVal,oldVal){
	        
	            if(newVal==undefined){
	          	  return;
	            }
	        	if (newVal === oldVal) {
	        		return; 
	        	}
	       /* console.log($(element));
	         console.log($(element)[0]);
	          console.log(element);*/
			    new QRCode($("#qrcode")[0], {
			        text: scope.aboutInfo.qrcode,
			        width:140,
			        height:140
					   
			    })
	   
	    /* 	if(ele.qrcode){
	     		clearInterval(t1);
	     		 ele.qrcode({
			        text: scope.aboutInfo.qrcode,
			        width:140,
			        height:140
			   });
	     	}*/     
			});
	       
        }
	}
}])


///**
// * Created by Administrator on 2017/9/13.
// */
//var generalize = angular.module('generalizeController', ['webapp','LocalStorageModule','userService']);
//
//generalize.controller('generalizeNavController',['util',function(util){
//util.navConfig(3);
//}]);
//
//
//generalize.directive('qr', ['$timeout', '$window', function ($timeout, $window) {
//
//  return {
//      restrict: 'ACE',
//      link: function(scope, element, attrs) {
//        //字符串生成二维码
//	      //  var aa=document.querySelector("#qrcode");
//	       // $scope.qrcode=angular.element(aa);
//	        //console.log($scope.qrcode);
//	     
//	        scope.$watch('aboutInfo.qrcode',function(newVal,oldVal){
//	        
//	          if(newVal==undefined){return;}
//	        if (newVal === oldVal) { return; }
//	       /* console.log($(element));
//	         console.log($(element)[0]);
//	          console.log(element);*/
//	      new QRCode($("#qrcode")[0], {
//			        text: scope.aboutInfo.qrcode,
//			        width:140,
//			        height:140
//			   
//	      })
//	   
//	    /* 	if(ele.qrcode){
//	     		clearInterval(t1);
//	     		 ele.qrcode({
//			        text: scope.aboutInfo.qrcode,
//			        width:140,
//			        height:140
//			   });
//	     	}*/
//	       
//			
//	      
//               
//});
//	       
//      }
//}
//}])
//		
//
//
//
//generalize.controller('generalizeController', ['$window','$location','$rootScope','$scope','util','cache','$timeout','$stateParams','localStorageService','userService',function ($window,$location,$rootScope,$scope,util,cache,$timeout,$stateParams,localStorageService,userService) {
//util.headerConfig('推广赚钱',true);
////alert(1)
////推广赚钱的详细信息
//	var uid=localStorageService.get("uid");
//	console.log(uid);
//	userService.people(uid).success(function(resp){
//		console.log(resp)
//		$scope.aboutInfo = resp.object;
//		 var pad = "000000";  
//       $scope.codema= 'SA'+pad.substring(0, pad.length - uid.length) + uid ; 
//		
//		var img = $scope.aboutInfo.avatar;
//		function getBase64Image(img) {
//			
//			var canvas = document.createElement("canvas");
//			canvas.width = img.width;
//			canvas.height = img.height;
//			var ctx = canvas.getContext("2d");
//			ctx.drawImage(img, 0, 0, img.width, img.height);
//			var ext = img.src.substring(img.src.lastIndexOf(".") + 1).toLowerCase();
//			var dataURL = canvas.toDataURL("image/" + ext);
//			return dataURL;
//		}
//
//		var images = new Image();
//		images.crossOrigin = "Anonymous";
//		try{
//		images.onload = function() {
//			var base64 = getBase64Image(images);
//			document.getElementById("aa").src=base64;
//		}
//			images.src = img;
//		}catch(e){
//			alert("Img error");
//		}
///*		$timeout(function(){
//        //字符串生成二维码
//	        var aa=document.querySelector("#qrcode");
//	        console.log(aa)
//	        $scope.qrcode=angular.element(aa);
//	        console.log($scope.qrcode);	
//	//      console.log($scope.qrcode.qrcode)
//			
//			if($scope.qrcode.qrcode=="undefined"){
//				$scope.qrcode.qrcode({
//			        text: $scope.aboutInfo.qrcode,
//			        width:140,
//			        height:140
//			   });
//			}else{
//				$scope.qrcode.qrcode({
//			        text: $scope.aboutInfo.qrcode,
//		//			text:1,
//			        width:140,
//			        height:140
//			   	});
//			}
//      },500);  */
//	});
//
//
//		
//		
//		$scope.takeScreenshot=function(){
//			html2canvas(document.body, {
//				allowTaint: true,
//				onrendered: function(canvas) {
//					try{
//						var dataurl = canvas.toDataURL('image/png');
//					}catch(e){
//						alert("toDataURL error")
//					}
//					var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
//					bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
//					while(n--){
//						u8arr[n] = bstr.charCodeAt(n);
//					}
//					var blob = new Blob([u8arr], {type:mime});
//					var fd = new FormData();
//					fd.append("upfile", blob,"image.png");
//					 var dlLink = document.createElement("a");
//					   
//					        dlLink.id='dlLink';
//					        dlLink.download = '文件名'; 
//					        document.body.appendChild(dlLink);
//					    
//					    dlLink.href = dataurl;
//					    dlLink.click();
//					    document.body.removeChild(dlLink);
//					
//			/*		
//				var blob = new Blob([e.format(fullTemplate, e.ctx)], {
//					type: "application/vnd.ms-excel"
//				});*/
//			/*	window.URL = window.URL || window.webkitURL;
//				link = window.URL.createObjectURL(blob);
//				a = document.createElement("a");
//				a.download = "QRcode";
//				a.href = link;
//
//				document.body.appendChild(a);
//
//				a.click();
//
//				document.body.removeChild(a);
//					*/
//					
//					
//					//canvas保存图片到本地 
//				/*	(function(t){
//						alert(t)
//					   
//					    
//					})(document.getElementById("#dlLink"));*/
//
//				}
//			});
//
//		}
//
//}]);
//
