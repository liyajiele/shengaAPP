app.controller('accountShowCtrl', ['$scope', '$state', '$http', '$rootScope', '$sce', 'qbhttp', function ($scope, $state, $http, $rootScope, $sce, qbhttp) {
    var aid=sessionStorage.getItem("aid");
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
var params={
    aid:aid,
    startTime:Date.parse("2017-12-01"),
    endTime:Date.parse(new Date())
}

$scope.account={};
    qbhttp.post('/api/admin/order/dashboardByAgent', params, function (res) {
        var result = res.object;
        $scope.account=result;
      var   option = {
            tooltip : {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: {
                data:['用户数','盈利额']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data :result.dateArr
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'用户数',
                    type:'line',
                    areaStyle: {normal: {}},
                    data:result.userArr
                },
                {
                    name:'盈利额',
                    type:'line',
                    areaStyle: {normal: {}},
                    data:result.earnsArr
                }

            ]
        };
        myChart.setOption(option)
    });
$scope.queryData=function () {
    var start= Date.parse($("#start").val());
    var end= Date.parse($("#end").val());

    var data={
        aid:aid,
        startTime:start,
        endTime:end
    }
    qbhttp.post('/api/admin/order/dashboardByAgent', data, function (res) {
        var result = res.object;

        $scope.account=result;
        var   option = {
            tooltip : {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: {
                data:['用户数','盈利额']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data :result.dateArr
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'用户数',
                    type:'line',
                    areaStyle: {normal: {}},
                    data:result.userArr
                },
                {
                    name:'盈利额',
                    type:'line',
                    areaStyle: {normal: {}},
                    data:result.earnsArr
                }

            ]
        };
        myChart.setOption(option)
    });
}
    $scope.modifyPsd=function () {

        layer.open({
            type: 1,
            content: '<div style="padding: 5px;"><div><label>用户名:</label><input id="userName" type="text"></div>'+
            '<div><label>旧密码:</label><input id="oldPass" type="password" ></div>'+
            '<div><label>新密码:</label><input id="newPassword" type="password" ></div></div>',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            title: '修改密码',
            shadeClose: true,
            yes: function (index, layero) {

                var modifyPasswordPath = "/api/admin/user/modifyPassword";
                var modifyPasswordParams = {
                    aid: aid,
                    username:$("#userName").val(),
                    oldPass:$("#oldPass").val(),
                    newPassword:$("#newPassword").val()
                }
                qbhttp.post(modifyPasswordPath, modifyPasswordParams, function(res) {
                    layer.msg('修改成功！', {
                        icon: 6
                    });
                    layer.close(index);
                })

            }
        })

    }
}])