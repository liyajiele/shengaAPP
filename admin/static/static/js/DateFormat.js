Date.prototype.format = function(format) {

    var o = {

        "M+": this.getMonth() + 1, //month

        "d+": this.getDate(), //day

        "h+": this.getHours(), //hour

        "m+": this.getMinutes(), //minute

        "s+": this.getSeconds(), //second

        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter

        "S": this.getMilliseconds() //millisecond

    }

    if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));

    for (var k in o) if (new RegExp("(" + k + ")").test(format)) format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));

    return format;

}



//第二个时间函数
function GetDateStr(AddDayCount) {   
   var dd = new Date();  
   dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期  
   var y = dd.getFullYear();   
   var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0  
   var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0  
   return y+"-"+m+"-"+d;   
}  

function ConvertDate(da)
{
	var dt = da.replace(/[^0-9]+/g, '');
	var t = new Date(Number(dt)).format("yyyy-MM-dd hh:mm:ss");
	return t;
}

/*
console.log("半年前："+GetDateStr(-180));  
console.log("三月前："+GetDateStr(-90));  
console.log("一月前："+GetDateStr(-30));  
console.log("昨天："+GetDateStr(-1));  
console.log("今天："+GetDateStr(0));  
console.log("明天："+GetDateStr(1));  
console.log("后天："+GetDateStr(2));  
console.log("一月后："+GetDateStr(30));  
console.log("三月后："+GetDateStr(90));  
console.log("半年后："+GetDateStr(180));  */ 

