/**
 * Created by miss on 2015/11/2.
 */
var userService = angular.module('userService',['webapp','LocalStorageModule']);

userService.factory('userService',['util','$http','$cacheFactory','localStorageService',function(util,$http,$cacheFactory,localStorageService){

    var UserService = function(){
    	this.collectsArray=[];
		this.collectsPage=0;
		this.collectsBusy=false;
		this.collectsLast=false;
        
        
        this.childArray=[];
        this.childPage=0;
        this.childBusy=false;
        this.childLast=false;
    };




    /**
     * 用户登录
     */
//  UserService.prototype.login = function(username,password){
//      var url = util.baseUrl+"/user/login";
//      return $http({
//          url:url,
//          data:{
//              username:username,
//              password:password,
//              oid:localStorageService.get("oid")
//          },
//          method:'post'
//      });
//  };



    /**
     * 用户注册
     */
//  UserService.prototype.register = function(username,password,code){
//      var url = util.baseUrl+"/user/register";
//      return $http({
//          url:url,
//          data:{
//              username:username,
//              password:password,
//              code:code,
//              oid:localStorageService.get("oid")
//          },
//          method:'post'
//      });
//  };


    
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
     * 兑吧
     */
    UserService.prototype.duiba = function(redirectUrl){
        var url = util.baseUrl+"/api/user/user/authc/duibaLogin";
        return $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                redirectUrl:redirectUrl,
            },
            method:'post'
        });
    }
    
    
 
    //收藏与取消收藏
    UserService.prototype.collect=function(mid,type){
    	var url=util.baseUrl+"/api/user/user/authc/collectOrCancel";
    	return $http({
    		url:url,
    		data:{
 			    uid:localStorageService.get('uid'),
	            mid:mid,
	            type:type,
    		},
    		method:'post'
    	})
    }

    //收藏的店铺数量
  	UserService.prototype.collectnums=function(){
      var url=util.baseUrl+"/api/user/user/authc/getCollectNums";
      return $http({
        url:url,
        data:{
			uid:localStorageService.get('uid'),
        },
        method:'post'
      })
    }

  //分页获取收藏列表
  UserService.prototype.collects=function(){
      var url=util.baseUrl+"/api/user/user/authc/getCollects";
      this.collectsBusy=true;
        $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                page:this.collectsPage,
                size:20,
            },
            method:'post'
        }).success(function (resp) {
//      	console.log(1)
			if(!this.collectsLast){
	            var respItems = resp.object.content;
	            for (var i = 0; i < respItems.length; i++) {
	                this.collectsArray.push(respItems[i]);
	            }
	            this.collectsPage += 1;
	            this.collectsBusy = false;
	            this.collectsLast=resp.object.Last;
            }
        }.bind(this));
    }
    
    //推广赚钱    用户自己
    UserService.prototype.people=function(){
	    var url=util.baseUrl+"/api/user/user/authc/getUserInfo";
	    return $http({
	      url:url,
	      data:{
	      	timeout:1,
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

   //获取下级列表
   UserService.prototype.child=function(childType){
      var url=util.baseUrl+"/api/user/user/authc/getMyChild";
      this.childBusy=true;
        $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                childType:childType,
                page:this.childPage,
                size:20,
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
    
    //获取下级人数
    UserService.prototype.childnum=function(){
	    var url=util.baseUrl+"/api/user/user/authc/getMyChildNums";
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