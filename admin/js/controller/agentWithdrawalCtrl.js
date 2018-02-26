/**
 * Created by Administrator on 2016/8/11.
 */

app.controller('agentWithdrawalCtrl', ['$scope', '$state', '$http', '$rootScope', 'qbhttp', function($scope, $state, $http, $rootScope, qbhttp) {

    var aid=sessionStorage.getItem("aid");
    $scope.status='1';
    var tType= 3;  //1.用户 2.商户 3.代理商
    var params = {
        aid: aid,
        tType:tType,
        drawStatus:$scope.status,
        searchStr: ''
    };
    var path ='/api/admin/draw/findDrawListByAdmin'
    qbhttp.post(path, params, function(res) {
        var result = res.object.content;
        tableShow(result);
    });
    function tableShow(data) {
        var dom = $('#draw-table');
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
            striped: true, //是否显示行间隔色
            uniqueId: "id",
            sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1, //初始化加载第一页，默认第一页
            pageSize: 10, //每页的记录行数（*）
            pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
            search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            data: data,
            columns: [{
                field: 'drawChannel',
                title: '信息',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    return value.user.nickname;
                }
            }, {
                field: 'createTime',
                title: '提现时间',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    return new Date(Number(value)).format("yyyy-MM-dd hh:mm:ss");
                }

            },
                {
                    field: 'drawChannel',
                    title: '提现账户',
                    sortable: true,
                    valign: 'middle',
                    formatter: function(value, row, index) {
                        return value.aliPay;
                    }
                }, {
                    field: 'orderNum',
                    title: '手机号',
                    sortable: true,
                    valign: 'middle'

                },
                {
                    field: 'tradeStatus',
                    title: '账户余额',
                    valign: 'middle'


                },
                {
                    field: 'drawNum',
                    title: '提现金额',
                    valign: 'middle'
                },
                {
                    field: 'drawStatus',
                    title: '状态',
                    valign: 'middle',
                    formatter: function(value, row, index) {
                        if(value=="1"){
                            return '已提现'
                        }else if(value=="2"){
                            return " 拒绝"
                        }else if(value=="3"){
                            return " 通过"
                        }
                    }
                },
                {
                    field: 'drawStatus',
                    title: '审核',
                    valign: 'middle',
                    align:'center',
                    width:'150px',
                    formatter: function(value, row, index) {
                        if(value=="1"){
                            return '<button class="btn past-button" id="pass">通过</button></br><button class="btn refuse-button" id="reject">拒绝</button>'
                        }else {
                            return "--"
                        }
                    },
                    events: {
                        'click #pass': function(e, value, rowData, index) {
                            layer.confirm('确定通过?', {
                                btn: ['确定', '取消'],
                                btnAlign: 'c',
                                yes: function(index, layero) {
                                    var DrawPath = "/api/admin/draw/modifyDrawStatus";
                                    var  DrawPathParams = {
                                        aid: aid,
                                        drawId: rowData.id,
                                        drawStatus: 3,
                                        rejectDescr: ""
                                    }
                                    qbhttp.post(DrawPath, DrawPathParams, function(res) {
                                        dom.bootstrapTable('remove', {
                                            field: 'id',
                                            values: [rowData.id]
                                        });
                                        layer.msg('操作成功！', {
                                            icon: 6
                                        });
                                        layer.close(index);
                                    });

                                },
                                title: '提示',
                                shadeClose: true
                            });

                        },
                        "click #reject": function(e, value, rowData, index) {

                            layer.confirm('确定拒绝?', {
                                title: '拒绝理由',
                                /*area: ['700px', '300px'],*/
                                btn: ['确定', '取消'], //可以无限个按钮
                                btnAlign: 'c',
                                content: '<div><label></label>拒绝理由<textarea id="refuseReason" class="refuse-textarea"></textarea></div>',
                                yes: function(index, layero) {
                                    var remarks = $("#refuseReason").val();
                                    var DrawRejectPath = "/api/admin/draw/modifyDrawStatus";
                                    var  DrawRejectPathParams = {
                                        aid: aid,
                                        drawId: rowData.id,
                                        drawStatus:2,
                                        rejectDescr:remarks
                                    }
                                    qbhttp.post(DrawRejectPath, DrawRejectPathParams, function(res) {

                                        dom.bootstrapTable('remove', {
                                            field: 'id',
                                            values: [rowData.id]
                                        });
                                        layer.msg('操作成功！', {
                                            icon: 6
                                        });
                                        layer.close(index);
                                    });
                                }
                            });

                        }

                    }
                },
            ]
        });
    }
    $scope.search=function () {

        var params = {
            aid: aid,
            tType:tType,
            drawStatus:$scope.status,
            searchStr: ''

        };

        var path ='/api/admin/draw/findDrawListByAdmin';
        qbhttp.post(path, params, function(res) {
            var result = res.object.content;
            tableShow(result);
        });
    }
}]);