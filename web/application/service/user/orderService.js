/**
 * Created by miss on 2015/11/30.
 */

var orderService = angular.module('orderService',['webapp','LocalStorageModule']);

orderService.factory('orderService',['util','$http','$cacheFactory','localStorageService',function(util,$http,$cacheFactory,localStorageService){
    var OrderService = function(){
        this.orderArray=[];      
        this.orderPage = 0;
        this.orderBusy = false;
        this.orderLast=false;
        
        
        this.finishArray=[];
        this.finishPage = 0;
        this.finishBusy = false;
        this.finishLast=false;

//      console.log(this.finishArray)
        
        
        this.fanliArray=[];
        this.fanliPage = 0;
        this.fanliBusy = false;
        this.start=1;
        this.end=2143065600000;    //2037.11.29年的时间戳
        this.finishnum=0;
        this.finishBalancenum=0;
        this.fanliLast=false;
//          console.log(this.uid)
    }
//  OrderService.prototype.initUid = function(){
//      if(this.uid == undefined || this.uid==null){
//          this.uid = localStorageService.get('uid');
//          console.log(this.uid)
//      }
//  }
    /**
     * 评价订单
     */
    OrderService.prototype.evalorder = function(orderId,retaType,retaContretaCont,retaStar){
        var url = util.baseUrl+"/api/user/order/authc/evalorder";
        return $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                orderId:orderId,
                retaType:retaType,
                retaContretaCont:retaContretaCont,
                retaStar:retaStar
            },
            method:'post'
        });
    }
    /**
     * 获取已完成订单列表
     */
    OrderService.prototype.finish = function(){
//      this.initUid();
        var url = util.baseUrl+"/api/user/order/authc/getMineFinishedOrders";
        this.finishBusy=true;
        $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                page:this.finishPage,
                size:20
            },
            method:'post'
        }).success(function (resp) {
        	if(!this.finishLast){
        		//无已完成订单
      			var ordersnum=resp.object.ordersNum;
			  	if(ordersnum==0){
					var be = document.querySelector(".bei");
					angular.element(be).css("display","block");
				}
	            var respItems = resp.object.orders.content;
	            for (var i = 0; i < respItems.length; i++) {
	                this.finishArray.push(respItems[i]);
	            }
	            this.finishPage += 1;
	            this.finishBusy = false;
	            this.finishLast=resp.object.Last;
            }
        }.bind(this));
    }
    
//    获取返利订单
	OrderService.prototype.fanli=function(){
	    var url=util.baseUrl+"/api/user/order/authc/getMineRebetaOrders";
	    this.fanliBusy=true;
        $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                startTime:this.start,
                endTime:this.end,
                page:this.fanliPage,
                size:20
            },
            method:'post'
        }).success(function (resp) {
        	if(!this.fanliLast){
//      	  console.log(3)
	            var respItems = resp.object.orders.content;
	//          console.log(resp)
	            this.finishnum=resp.object.ordersNum;
	            this.finishBalancenum=resp.object.rebateNum;
	            for (var i = 0; i < respItems.length; i++) {
	                this.fanliArray.push(respItems[i]);
	            }
	            this.fanliPage += 1;
	            this.fanliBusy = false;
	            this.fanliLast=resp.object.Last;
           }
            //暂无返利订单
            if(resp.object.ordersNum==0){
            	var bei = angular.element(".bei");
            	bei.css("display","block");
            }else{
            	var bei = angular.element(".bei");
            	bei.css("display","none");
            }
        }.bind(this));
	}

    //待评价订单列表
	OrderService.prototype.order=function(){
	    var url=util.baseUrl+"/api/user/order/authc/getMineOrderToBeEvaluated";
	    this.orderBusy=true;
        $http({
            url:url,
            data:{
                uid:localStorageService.get('uid'),
                page:this.orderPage,
                size:20
            },
            method:'post'
        }).success(function (resp) {
//      	console.log(5)
			if(!this.orderLast){
	            var respItems = resp.object.content;
//	            console.log(respItems)
	            for (var i = 0; i < respItems.length; i++) {
	                this.orderArray.push(respItems[i]);
	            }
	            this.orderPage += 1;
	            this.orderBusy = false;
	            this.orderLast=resp.object.Last;
            }
        }.bind(this));
	}

  //待评价订单数量
  OrderService.prototype.unnum=function(){
    var url=util.baseUrl+"/api/user/order/authc/getMineOrderToBeEvaluatedNums";
    return $http({
      url:url,
      data:{
		uid:localStorageService.get('uid'),
		
      },
      method:'post'
    })
  }
  
  
//下单支付
// this.balanceAmount=accountService.object.balance;
 OrderService.prototype.pay=function(mid,payType,totalAmount,rebateAmount,balanceAmount,longitude,latitude){
    var url=util.baseUrl+"/api/user/order/pay";
    return $http({
      url:url,
      data:{
		uid:localStorageService.get('uid'),
		mid:mid,
		payType:"balance_pay",
		totalAmount:totalAmount,
		rebateAmount:rebateAmount,
		balanceAmount:balanceAmount,
		longitude:longitude,
		latitude:latitude
      },
      method:'post'
    })
  }
  
    return new OrderService();
}]);