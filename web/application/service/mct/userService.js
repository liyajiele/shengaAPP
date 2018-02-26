/**
 * Created by miss on 2015/11/2.
 */
var userService = angular.module('userService',['webapp','LocalStorageModule']);

userService.factory('userService',['util','$http','$cacheFactory','localStorageService',function(util,$http,$cacheFactory,localStorageService){

    var UserService = function(){
        this.mctArray=[];
		this.mctPage=0;
		this.mctBusy=false;
        this.uid = localStorageService.get('uid');
        this.mid = localStorageService.get('mid');
    	
    	this.childArray=[];
		this.childPage=0;
		this.childBusy=false;
		this.childLast=false;
    };

    /**
     * 用户登录
     */
    UserService.prototype.login = function(username,password){
        var url = util.baseUrl+"/api/mct/merchant/login";
        return $http({
            url:url,
            data:{
                uid:localStorageService.get("uid"),
                username:username,
                password:password,

            },
            method:'post'
        });
    };



    /**
     *用户忘记登陆密码修改
     */
    UserService.prototype.newlogin = function(username,code,newPassword){
        var url = util.baseUrl+"/api/mct/merchant/forgetPassword";
        return $http({
            url:url,
            data:{
                uid:localStorageService.get("uid"),
                username:username,
                code:code,
                newPassword:newPassword,
            },
            method:'post'
        });
    };


    
    /**
     * 修改密码
     * @param oldPass
     * @param newPass
     * @returns 
     */
//  UserService.prototype.updPass = function(oldPass,newPass){
//      var url = util.baseUrl+"/user/updatePassword";
//      return $http({
//          url:url,
//          data:{
//              uid:this.uid,
//              oldPass:oldPass,
//              newPass:newPass
//          },
//          method:'post'
//      });
//  }
    /**
     * 充值
     */
//  UserService.prototype.recharge = function(money,rechargeType){
//      var url = util.baseUrl+"/account/recharge";
//      return $http({
//          url:url,
//          data:{
//              uid:this.uid,
//              money:money,
//              rechargeType:rechargeType
//          },
//          method:'post'
//      });
//  }
//  
    
 
       //创建门店
       UserService.prototype.create=function(areaId,provinceId,cityId,districtId,mctName,addr,mobile,shopHours,mainImage,images,types,ownerRealName,ownerIdcard,idcardImage1,idcardImage2,licenceType,licenceStatus,licenceImage,longitude,latitude){
       	var url=util.baseUrl+"/api/mct/merchant/authc/createMerchant";
       	return $http({
       		url:url,
       		data:{
                uid:localStorageService.get("uid"),
   	            areaId:areaId,
   	            provinceId:provinceId,
   	            cityId:cityId,
   	            districtId:districtId,
   	            mctName:mctName,
   	            addr:addr,
   	            mobile:mobile,
   	            shopHours:shopHours,
   	            mainImage:mainImage,
   	            images:images,
   	            types:types,
   	            ownerRealName:ownerRealName,
   	            ownerIdcard:ownerIdcard,
   	            idcardImage1:idcardImage1,
   	            idcardImage2:idcardImage2,
   	            licenceType:licenceType,
   	            licenceStatus:licenceStatus,
   	            licenceImage:licenceImage,
   	            longitude:longitude,
   	            latitude:latitude
       		},
       		method:'post'
       	})
       }


//修改门店信息
UserService.prototype.xiu=function(mid,areaId,provinceId,cityId,districtId,mctName,addr,mobile,shopHours,mainImage,images,types,ownerRealNamem,ownerIdcard,idcardImage1,idcardImage2,licenceType,licenceStatus,licenceImage,longitude,latitude,consumerption,profits,rebateType){
       	var url=util.baseUrl+"/api/mct/merchant/authc/updateMerchantInfos";
       	return $http({
       		url:url,
       		data:{
       			uid:localStorageService.get("uid"),
                mid:localStorageService.get("mid"),
                areaId:areaId,
                provinceId:provinceId,
                cityId:cityId,
                districtId:districtId,
                mctName:mctName,
                addr:addr,
                mobile:mobile,
                shopHours:shopHours,
                mainImage:mainImage,
                images:images,
                types:types,
                ownerRealNamem:ownerRealNamem,
                ownerIdcard:ownerIdcard,
                idcardImage1:idcardImage1,
                idcardImage2:idcardImage2,
                licenceType:licenceType,
                licenceStatus:licenceStatus,
                licenceImage:licenceImage,
                longitude:longitude,
                latitude:latitude,
                consumerption:consumerption,
                profits:profits,
                rebateType:rebateType,
       		},
       		method:'post'
    
       	})
	
}




       //商户类型
     UserService.prototype.mcttype=function(){
         var url=util.baseUrl+"/api/mct/merchant/allMerchantTypes";
         return $http({
           url:url,
           data:{
                uid:localStorageService.get('uid'),
   			
           },
           method:'post'
         })
       }
     
     
         
    /**
     * 更新用户位置
     */
    UserService.prototype.updateLocation = function(longitude,latitude){
        var url = util.baseUrl+"/api/user/user/authc/updateLocation";
        return $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                longitude:longitude,
                latitude:latitude,
            },
            method:'post'
        });
    }

   //获取粉丝
   UserService.prototype.child=function(mid){
      var url=util.baseUrl+"/api/mct/merchant/authc/getMyFans";
      this.childBusy=true;
        $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                mid:mid,
            },
            method:'post'
        }).success(function (resp) {
//      	console.log(7)
			if(!this.childLast){
	            var respItems = resp.object.content;
	//          console.log(respItems)
	            for (var i = 0; i < respItems.length; i++) {
	                this.childArray.push(respItems[i]);
	            }
	            this.childPage += 1;
	            this.childBusy = false;
			 	this.childLast=resp.object.Last;          
			}
        }.bind(this));
    }
     //分页获取门店
//   UserService.prototype.mct=function(){
//       var url=util.baseUrl+"/api/mct/merchant/authc/getMyMerchant";
//       this.mctBusy=true;
//       $http({
//             url:url,
//             data:{
//                 uid:this.uid,
//                 page:this.mctPage,
//                 size:20,
//             },
//             method:'post'
//         }).success(function (resp) {
//         	console.log(7)
//             var respItems = resp.object.content;
//             console.log(respItems)
//             for (var i = 0; i < respItems.length; i++) {
//                 this.mctArray.push(respItems[i]);
//             }
//             this.mctPage += 1;
//             this.mctBusy = false;
//         }.bind(this));
//     }

//分页获取店铺
    UserService.prototype.mct=function(){
   	    var url=util.baseUrl+"/api/mct/merchant/authc/getMyMerchant";
   	    return $http({
   	      url:url,
   	      data:{
   			uid:localStorageService.get('uid'),
   	      },
   	      method:'post'
   	    })
   	}

       /**
        * 获取账户信息
        */
       UserService.prototype.accountInfo = function(mid){
           var url = util.baseUrl+"/api/mct/merchant/authc/accountInfo";
           return $http({
               url:url,
               data:{
                   uid:localStorageService.get('uid'),
                   mid:mid,
               },
               method:'post'
           });
       }
//
//    //获取下级列表
//    UserService.prototype.child=function(childType){
//       var url=util.baseUrl+"/api/user/user/authc/getMyChild";
//       this.childBusy=true;
//         $http({
//             url:url,
//             data:{
//                 uid:this.uid,
//                 childType:childType,
//                 page:this.childPage,
//                 size:20,
//             },
//             method:'post'
//         }).success(function (resp) {
// //      	console.log(7)
//             var respItems = resp.object.content;
// //          console.log(respItems)
//             for (var i = 0; i < respItems.length; i++) {
//                 this.childArray.push(respItems[i]);
//             }
//             this.childPage += 1;
//             this.childBusy = false;
//         }.bind(this));
//     }
//
       //获取审核信息
     UserService.prototype.shen=function(){
   	    var url=util.baseUrl+"/api/mct/merchant/authc/getMyAuditInfo";
   	    return $http({
   	      url:url,
   	      data:{
   			    uid:localStorageService.get('uid'),
   	      },
   	      method:'post'
   	    })
   	}
    
    
    return new UserService();


}]);