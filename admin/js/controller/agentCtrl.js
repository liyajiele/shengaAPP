/**
 * Created by wangshuang on 2016/8/3.
 */
'use strict';

app.controller('agentCtrl', ['$scope', '$state', '$http', '$rootScope', '$sce', 'qbhttp', function ($scope, $state, $http, $rootScope, $sce, qbhttp) {
    var aid=sessionStorage.getItem("aid");
$scope.addAgent={
    aid:aid,
    uid:"",
    username:"",
    password:"",
    description:"",
    districtId:"",
    startTime:"",
    endTime:"",
    zhaoshangManagerId:"",
    kehuManagerId:"",
};
    var aid = 1;
    var params =aid==1?{
        aid :aid,
        searchStr:''
    }:{aid:aid,uid:$rootScope.uid};
    var path =aid==1? '/api/admin/agent/findAgentListByAdmin':'/api/admin/agent/findAgentListByAgentUid';
    qbhttp.post(path, params, function (res) {
        var result = res.object.content;
        tableShow(result);
    });

    function tableShow(data) {
        var dom = $('#advertise-table');
        dom.bootstrapTable("destroy");
        dom.bootstrapTable("hideLoading");
        dom.bootstrapTable({
            onAll: function () {  //加载失败时执行
                $('#table').find('.no-records-found').each(function () {
                    $(this).children().remove();
                    $(this).append($('<td colspan="8">').css('vertical-align', 'inherit')
                        .append($('<span>').addClass('no-records-found-span').text('未搜索出结果')));
                });
            },
            striped: true,                      //是否显示行间隔色
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            uniqueId: "id",
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            data: data,
            columns: [{
                field: 'agentInfo',
                title: '名字',
                valign: 'middle',
                sortable: true
            },{
                field: 'mobile',
                title: '电话',
                valign: 'middle',
                sortable: true

            }, {
                field: 'district',
                title: '代理区域',
                valign: 'middle',
                sortable: true,
                formatter: function (value, row, index) {
                    return value.name;
                }
            }, {
                field: 'createTime',
                title: '代理时间',
                sortable: true,
                valign: 'middle',
                formatter: function (value, row, index) {
                    return  new Date(value).format('yyyy-MM-dd hh:mm');
                }
            }, {
                field: 'endTime',
                title: '结束时间',
                sortable: true,
                valign: 'middle',
                formatter: function (value, row, index) {
                    return  new Date(value).format('yyyy-MM-dd hh:mm');
                }

            }, {
                field: 'account',
                title: '账户余额',
                valign: 'middle',
                sortable: true,
                formatter: function (value, row, index) {
                    return value.balance+value.retateBalance+value.redBalance;
                }
            }, {
                field: 'fansNums',
                title: '用户数',
                valign: 'middle',
                sortable: true

            },{
                field: 'mctNums',
                title: '商家数',
                valign: 'middle',
                sortable: true

            },
                {
                    field: 'zhaoShangManager',
                    title: '招商经理',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        return value.user.nickname;
                    }
                },
                {
                    field: 'kuhuManager',
                    title: '客户经理',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        return value.user.nickname;
                    }
                },
                {
                    field: 'operation',
                    title: '操作',
                    valign: 'middle',
                    align:'center',
                    width:'150px',
                    formatter: function (value, row, index) {
                        return '<button class="btn past-button" id="modify">修改</button><br><button class="btn refuse-button" id="del">删除</button>'
                    },
                    events:{
                        'click #del':function (e, value, rowData, index) {

                            layer.confirm('确定删除?', {
                                title: '提示',
                                /*area: ['700px', '300px'],*/
                                btn: ['确定', '取消'], //可以无限个按钮
                                btnAlign: 'c',
                                yes: function(index, layero) {
                                    var agentPath ="/api/admin/agent/delAgentinfosById";
                                    var  agentParams={
                                        aid:aid,
                                        infoId:rowData.id
                                    }
                                    qbhttp.post(agentPath, agentParams, function(res) {
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

                        },
                        'click #modify':function (e, value, rowData, index) {
                            //招商经理
                            qbhttp.post("/api/admin/user/findAdminListByRoleId", {aid:aid,roleId:7}, function(res) {
                               $scope.zhaoshang = res.object.content;
                            })
                            //客户经理
                            qbhttp.post("/api/admin/user/findAdminListByRoleId", {aid:aid,roleId:8}, function(res) {
                                $scope.kehu = res.object.content;
                            })
                            layer.open({
                                type:1,
                                content:$("#modifyAgentInfo"),
                                btn: ['确定', '取消'],
                                btnAlign: 'c',
                                title: '修改信息',
                                shadeClose: true,
                                yes: function(index, layero) {

                               /*     var modifyAgentByUidPath = "/api/admin/agent/modifyAgentByUid";
                                    var info={
                                        aid:aid,
                                        uid:rowData.user.id,
                                        realname:$("#realname").val(),
                                        mobile:$("#mobile").val()
                                    }*/
                                    var modifyAgentByUidPath = "/api/admin/agent/modifyAgentinfosById";
                                    var info={
                                        aid:aid,
                                        uid:rowData.id,
                                        description:$("#description").val(),
                                        startTime:$("#startTime").val(),
                                        endTime:$("#startTime").val(),
                                        zhaoshangManagerId:$("#zhaoshangManagerId>option:selected").val(),
                                        kehuManagerId:$("#kehuManagerId>option:selected").val()
                                    }
                                    var startTime = Date.parse(new Date($("#startTime").val()));
                                     var endTime = Date.parse(new Date($("#endTime").val()));
                                    info.startTime=startTime;
                                    info.endTime=endTime;
                                    qbhttp.post(modifyAgentByUidPath, info, function(res) {
                                        var r=res.object;
                                        $('#advertise-table').bootstrapTable('updateByUniqueId', {
                                            id: rowData.id,
                                            /*row: {
                                                agentInfo:info.realname,
                                                mobile: info.mobile,
                                            }*/
                                            row:r
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
    $scope.province="";
    $scope.city="";
    $scope.district="";

    $scope.provinceChange=function (id) {
        //根据省获取城市
        qbhttp.post("/api/location/findCityListByProvinceId", {aid:aid,provinceId:id}, function(res) {
            $scope.cityList = res.object;
        })
    }

    $scope.cityChange=function (id) {
        //根据城市获取区域
        qbhttp.post("/api/location/findDistrictByCityId", {aid:aid,cityId:id}, function(res) {
            $scope.districtList = res.object;
        })
    }

    
    $scope.addAgentFun=function(){
        if(!$scope.zhaoshang){
        qbhttp.post("/api/admin/user/findAdminListByRoleId", {aid:aid,roleId:7}, function(res) {
            $scope.zhaoshang = res.object.content;
        })
        //客户经理
        qbhttp.post("/api/admin/user/findAdminListByRoleId", {aid:aid,roleId:8}, function(res) {
            $scope.kehu = res.object.content;
        })}

        //省
        qbhttp.post("/api/location/provinceList", {aid:aid}, function(res) {
            $scope.provinceList = res.object;
        })

        
        layer.open({
            type:1,
            content:$("#agentModal"),
            btn: ['确定', '取消'],
            btnAlign: 'c',
            title: '新增代理商',
            shadeClose: true,
            yes: function(index, layero) {
                var remarks = $("#refuseReason").val();
                var addAgentPath = "/api/admin/agent/bindAgent";
                $scope.addAgent.startTime=Date.parse(new Date($("#start").val()));
                $scope.addAgent.endTime=Date.parse(new Date($("#end").val()));
              /* $scope.addAgent.districtId=  $scope.district;*/
                qbhttp.post(addAgentPath, $scope.addAgent, function(res) {
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