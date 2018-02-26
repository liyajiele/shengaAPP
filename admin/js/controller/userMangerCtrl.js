/**
 * Created by wangshuang on 2016/8/3.
 */
'use strict';

app.controller('userMangerCtrl', ['$scope', '$state', '$http', '$rootScope', '$sce', 'qbhttp', function ($scope, $state, $http, $rootScope, $sce, qbhttp) {

    var aid=sessionStorage.getItem("aid");
    var params ={
        aid :aid,
        searchStr:''
    };
    var path =aid==1? '/api/admin/user/findUserListByAdmin':'/api/admin/user/findUserListByAgent';
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
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            data: data,
            columns: [{
                field: 'id',
                title: '用户编号',
                valign: 'middle',
                sortable: true
            },{
                field: 'nickname',
                title: '用户名',
                valign: 'middle',
                sortable: true

            }, {
                field: 'createTime',
                title: '创建时间',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    return new Date(Number(value)).format("yyyy-MM-dd hh:mm:ss");
                }
            }, {
                field: 'gender',
                title: '性别',
                sortable: true,
                valign: 'middle'
            }, {
                field: 'locationInfos',
                title: '城市',
                sortable: true,
                valign: 'middle',
                formatter: function (value, row, index) {
                    return value.city;
                }

            }, {
                field: 'locationInfos',
                title: '地区',
                valign: 'middle',
                sortable: true,
                formatter: function (value, row, index) {
                    return value.district;
                }
            }, {
                field: 'balance',
                title: '账户余额',
                valign: 'middle',
                sortable: true

            },{
                field: 'agentInfos',
                title: '代理商',
                valign: 'middle',
                sortable: true

            },
           {
            field: 'operation',
            title: '操作',
            valign: 'middle',
               align:'center',
               width:'150px',
            formatter: function (value, row, index) {
                   return '<button class="btn past-button" id="modifyBelong">修改归属</button>'
               },
            events:{
                'click #modifyBelong':function (e, value, rowData, index) {
                    var agentPath ="/api/admin/agent/findAgentListByAdmin";
                    var  agentParams={
                        aid:aid,
                        searchStr:''
                    }
                    qbhttp.post(agentPath, agentParams, function (res) {
                        var result = res.object.content;
                        var optionList="";
                       for(var i=0,obj;obj=result[i++];){
                           optionList="<option value="+obj.account.id+">"+obj.agentInfo+"</option>";
                       }
                        layer.open({
                            content: '<div><label>修改归属：</label><select id="agentUid">'+optionList+'</select></div>',
                            btn: ['确定', '取消'],
                            btnAlign: 'c',
                            yes: function(index, layero) {
                                //按钮【按钮一】的回调/api/admin/banner/agentAddBanner


                                var path ="/api/admin/user/modifyUserAgentByAdmin";
                                console.log($("#agentUid option:selected").val())
                                var  params={
                                    aid:aid,
                                    uid:rowData.id,
                                    agentUid:$("#agentUid option:selected").val()
                                };
                                qbhttp.post(path, params, function (res) {
                                    layer.close(index);
                                });

                            },
                            title: '修改归属',
                            shadeClose: true
                        });
                    });

                }

            }
            }
            ]
        });
    }

}]);