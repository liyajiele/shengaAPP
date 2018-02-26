angular.module("modalModule",[]).factory('modalService',function(){ 
	
	 /* var modal={
	  title: "提示",
		icon: "icon_warn", //信息前面的icon  分别为提醒："icon_warn",错误："icon_error",询问："icon_inquiry",成功："icon_success"
		content: "您确定要删除吗？",
		btn: ["确定", "取消"], //多个按钮的btn:["确定","取消",...,...]
		size:{width:"400px",height:"217px"} 
	};*/
	var modal={
		head:"请选择",
		body:'<span class="icon_warn"></span>'+
           '<span class="text">您确定要删除吗？</span>',
		footer:["确定", "取消"],
		size:{width:"500px",height:"300px"}
	}
	modal.open=function(options){
			$(".ss-modal-dialog").css("width",options.size.width);
			$(".ss-modal-dialog").css("height",options.size.height);
			$(".ss-modal-title").text(options.head);
			$(".ss-modal-body-content").html(options.body);
			$(".ss-modal").show();
			
	
	}
	modal.close=function(){
		$(".ss-modal").hide();
	}
	
	
		
	
	return modal;
	
})
