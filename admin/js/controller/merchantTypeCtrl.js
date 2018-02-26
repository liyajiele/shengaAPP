/**
 * Created by Administrator on 2016/8/11.
 */

app.controller('merchantTypeCtrl', ['$scope', '$state', '$http', '$rootScope', 'qbhttp', function($scope, $state, $http, $rootScope, qbhttp) {
    var aid=sessionStorage.getItem("aid"),status=1;
    var params = {
        aid: aid,
        status:status
    };
    /*    $scope.merchant={
            aid:aid,
            name:"",
            time:"",
            agent:""
        }*/
    var path =  '/api/admin/mct/mctTypeList';
    qbhttp.post(path, params, function(res) {
        var result = res.object;
        tableShow(result);
    });
    function tableShow(data) {
        var dom = $('#advertise-table');
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
                field: 'typeName',
                title: '名称',
                valign: 'middle',
                sortable: true
            }, {
                field: 'createTime',
                title: '时间',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    return new Date(Number(value)).format("yyyy-MM-dd hh:mm:ss");
                }

            },
                {
                    field: 'createUser',
                    title: '更改人',
                    sortable: true,
                    valign: 'middle'

                },
                {
                    field: 'opration',
                    title: '操作',
                    valign: 'middle',
                    align:'center',
                    width:'150px',
                    formatter: function(value, row, index) {
                        return '<div><button class="btn past-button" id="modifyName">更改名称</button></div><div><button class="btn refuse-button" id="del">删除</button></div>'
                    },
                    events: {
                        'click #del': function(e, value, rowData, index) {
                            layer.confirm('确定删除吗?', {
                                btn: ['删除', '取消'],
                                btnAlign: 'c',
                                yes: function(index, layero) {
                                    var MctPath = "/api/admin/mct/modifyMctType";
                                    var MctPathParams = {
                                        aid: aid,
                                        tid: rowData.id,
                                        status: 0
                                    }
                                    qbhttp.post(MctPath, MctPathParams, function(res) {
                                        $('#advertise-table').bootstrapTable('remove', {
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
                        "click #modifyName": function(e, value, rowData, index) {

                            layer.confirm('提示', {
                                title: '更改名称',
                                /*area: ['700px', '300px'],*/
                                btn: ['确定', '取消'], //可以无限个按钮
                                btnAlign: 'c',
                                content: '<label>分类名称:</label><input id="typeName" type="text"  value='+rowData.typeName+'>',
                                yes: function(index, layero) {
                                    var remarks = $("#refuseReason").val();
                                    var MctPath = "/api/admin/mct/modifyMctType";
                                    var MctPathParams = {
                                        aid: aid,
                                        tid: rowData.id,
                                        typeName:$("#typeName").val(),
                                        status: 1
                                    }
                                    qbhttp.post(MctPath, MctPathParams, function(res) {
                                        $('#advertise-table').bootstrapTable('updateByUniqueId', {
                                            id:  res.object.id,
                                            row: {
                                                typeName: res.object.typeName

                                            }
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
                }

            ]
        });
    }
    $scope.addMerchantType=function(){
        layer.confirm('新增?', {
            title: '提示',
            /*area: ['700px', '300px'],*/
            btn: ['确定', '取消'], //可以无限个按钮
            btnAlign: 'c',
            content: '<div><label>分类名称:</label><input id="typeName" type="text"></div>'+
            '<div><label>分类图标:</label><input id="typeIcon" type="text" ></div>',
            yes: function(index, layero) {
                var remarks = $("#refuseReason").val();
                var addMctTpePath = "/api/admin/mct/addMctType";
                var addMctTypeParams = {
                    aid: aid,
                    typeName:$("#typeName").val(),
                    typeImage:$("#typeIcon").val()
                }
                qbhttp.post(addMctTpePath, addMctTypeParams, function(res) {
                    var r=res.object;
                    $('#advertise-table').bootstrapTable('insertRow', {
                        index: 0,
                        row: {
                            id:r.id,
                            typeName:$("#typeName").val(),
                            createTime:r.createTime,
                            createUser:r.createUser,

                        }
                    })
                    layer.msg('操作成功！', {
                        icon: 6
                    });
                    layer.close(index);
                });
            }
        });
    }
}]);