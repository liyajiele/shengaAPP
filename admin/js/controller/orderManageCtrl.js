/**
 * Created by Administrator on 2016/8/11.
 */

app.controller('orderManageCtrl', ['$scope', '$state', '$http', '$rootScope', 'qbhttp', function($scope, $state, $http, $rootScope, qbhttp) {
    var aid=sessionStorage.getItem("aid");
    var params = {
        aid: aid,
        searchStr: ''
    };
    var page=1,size=10;
    var baseURL = "http://api.51shenga.com";
    var path = aid == 1 ? baseURL+'/api/admin/order/findOrderListByAdmin' : baseURL+'/api/admin/order/findOrderListByAgent';
/*    qbhttp.post(path, params, function(res) {
        var result = res.object.content;
        tableShow(result);
    });*/

    tableShow(path,page,size);
    function tableShow(u,page,size) {
        var dom = $('#order-table');
        dom.bootstrapTable("destroy");
        dom.bootstrapTable("hideLoading");
        dom.bootstrapTable({
            onAll: function() { //加载失败时执行
                $('#table').find('.no-records-found').each(function() {
                    $(this).children().remove();
                    $(this).append($('<td colspan="8">').css('vertical-align', 'inherit')
                        .append($('<span>').addClass('no-records-found-span').text('未搜索出结果')));
                });
            },
            method: 'post',
            uniqueId: "id",
            striped: true,                      //是否显示行间隔色
            pagination: true, //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: page,                       //初始化加载第一页，默认第一页
            pageSize: size,                       //每页的记录行数（*）
            pageList: [10,20,30,50,100],        //可供选择的每页的行数（*）
            url:u,
            queryParamsType:'',
            queryParams:function(params) {
                var a=(params.pageNumber-1)<0?0:(params.pageNumber-1);
                console.log(params.pageNumber-1);
                return{
                    page:(params.pageNumber-1)<0?0:(params.pageNumber-1),
                    size:params.pageSize,
                    aid: aid,
                    searchStr: ''
                }
            },
            contentType: "application/x-www-form-urlencoded",
            responseHandler: function(res) {
                return {
                    "total": res.object.totalElements,//总页数
                    "rows": res.object.content   //数据
                };
            },
            columns: [{
                field: 'user',
                title: '用户名',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    return value.nickname;
                }
            }, {
                field: 'merchant',
                title: '服务者',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    return value.ownerRealName;
                }

            },
                {
                    field: 'createTime',
                    title: '创建时间',
                    sortable: true,
                    valign: 'middle',
                    formatter: function(value, row, index) {
                        return new Date(Number(value)).format("yyyy-MM-dd hh:mm:ss");
                        if(!value){
                            return "";
                        }
                        return value.balance+value.redBalance+value.retateBalance;
                    }
                }, {
                    field: 'orderNum',
                    title: '订单号',
                    sortable: true,
                    valign: 'middle'
                },
                {
                    field: 'tradeStatus',
                    title: '订单状态',
                    valign: 'middle'
                },
                {
                    field: 'totalAmount',
                    title: '订单金额',
                    valign: 'middle'
                },
                {
                    field: 'rebate',
                    title: '返现金额',
                    valign: 'middle'

                },
                {
                    field: 'agent',
                    title: '代理商分成',
                    valign: 'middle',
                    formatter: function(value, row, index) {
                        return   row.rebate*row.merchant.retateToDistrictAgent*row.merchant.profits;
                    }
                },
                {
                    field: 'operation',
                    title: '平台分成',
                    valign: 'middle',
                    formatter: function(value, row, index) {
                        return   row.rebate*row.merchant.retateToDistrictAgent*row.merchant.profits;
                    }
                }
            ]
        });
    }
    $scope.addMerchant=function(){
        sessionStorage.removeItem('info');

        $state.go("merchantInfo");
    }
}]);