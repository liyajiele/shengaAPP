/**
 * Created by Administrator on 2016/8/22.
 */

app.directive('ssModalDirective', ['$interval', '$parse', '$window', 'modalService', function($interval, $parse, $window, modalService) {

	return {
		restrict: 'C',
		replace: true,
		scope: {
			//传入的模态框数据
			//          data: "=modalData",
            addFirstOrSecond: '=modalAdd',
			firstLevel:"=modalFirst",
			cancelOrconfirm: '=cancelmm'
		},
		templateUrl: "tpl/modal.html",
		link: function(scope, ele, attr, contr) {
			scope.firsttext="1231";
			$(".comfirm").bind("click", function() {
				scope.$apply(function() {
					if($('input[name="Menu"]').length!=0) {
						if($("input[name='Menu']:checked")) {
							var val = $("input[name='Menu']:checked").val();
							 if(val==''){
							 	console.log("请选择");return;
							 }
							 
                            if(val=="一级菜单"){
                            	var name=$("#firstMenuName").val();
                            	var code=$("#firstMenuCode").val();
                            	scope.addFirstOrSecond="addFirst-"+name+"-"+code;
                            	
//                          	执行增加一级菜单函数
                            }
                            if(val=="二级菜单"){
                            	var name=$("#secondMenuName").val();
                            	scope.addFirstOrSecond="addSecond-"+name;
                            	/*执行增加二级菜单函数*/
                            }
						}

					}else{
						scope.cancelOrconfirm = true;
					}

					modalService.close();
					
				});
			});
			$(".cancel").bind("click", function() {
				scope.$apply(function() {
					modalService.close();
					scope.cancelOrconfirm = false;
				});
			})

			//          var oBox = $(".ss-modal-dialog")[0];
			//调用此函数使其可以拖动，参数为你要拖动的对象
			//          startDrag(oBox, oBox);
		}
	}

}]);