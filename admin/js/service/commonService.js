/**
 * Created by Administrator on 2016/8/28.
 */
angular.module('qbhttpFactory', ['ui.router']).factory('qbhttp', ['$rootScope', '$http', function ($rootScope, $http) {
    var obj = {};
    obj.post = function (path, params, callback,type) {
        var baseURL = "http://api.51shenga.com";
        var sign=hex_md5(params.aid+path);
        var type = type ? type : "post";
        $http({
            headers:{sign:sign},
            url: baseURL + path,
            method: type,
            params: params
        }).success(function (res) {
            if(res.code == 1){
                if(typeof callback == 'function'){
                    callback(res);
                }
            }else{
                layer.alert(res.message);
            }
        })
    };
    return obj;
}]);
