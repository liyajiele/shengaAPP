/**
 * Created by Administrator on 2016/8/11.
 */

app.controller('loginCtrl', ['$scope', '$state', '$http', '$rootScope', 'qbhttp', function($scope, $state, $http, $rootScope, qbhttp) {
    $(".login").on("click",function () {
        var u=$("#name").val().trim();
        var p=$("#psw").val();
        var baseURL = "http://api.51shenga.com";
        var loginObj={
            username:u,
            password:p
        }
        $.ajax({
                url:baseURL+"/api/admin/user/login",
                type:"post",
                data:loginObj,
                success:function (res) {
                    if(res.code==1){
                        sessionStorage.Active=-1;
                        $rootScope.token=res.object.token;
                        $rootScope.uid=res.object.user.id;
                        $rootScope.aid=res.object.id;
                        sessionStorage.setItem("token",res.object.token);
                        sessionStorage.setItem("uid",res.object.user.id);
                        sessionStorage.setItem("aid",res.object.id);
                        $state.go("main.panel");
                    }else{
                        alert("账户或密码错误！");
                    }
                }
            }
        )
    })
}]);