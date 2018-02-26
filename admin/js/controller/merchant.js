/**
 * Created by Administrator on 2016/8/11.
 */

app.controller('MerchantCtrl', ['$scope', '$state', '$http', '$rootScope', 'qbhttp', function($scope, $state, $http, $rootScope, qbhttp) {
    var aid=sessionStorage.getItem("aid");
    $scope.status="0";
    var page=1,size=10;
/*    var params = {
        aid: aid,
        auditStatus:0,
        searchStr: ''
    };

    var path = aid == 1 ? '/api/admin/mct/findMctListByAdmin' : '/api/admin/mct/findMctListByAgent';
    qbhttp.post(path, params, function(res) {
        var result = res.object.content;
        tableShow(result);
    });*/
    var baseURL = "http://api.51shenga.com";
    var path = aid == 1 ?baseURL+ '/api/admin/mct/findMctListByAdmin' :baseURL+ '/api/admin/mct/findMctListByAgent';
    tableShow(path,page,size);
    $scope.search=function () {
     /*   var params = {
            aid: aid,
            auditStatus:$scope.status,
            searchStr: ''
        };
        var path = aid == 1 ? '/api/admin/mct/findMctListByAdmin' : '/api/admin/mct/findMctListByAgent';
        qbhttp.post(path, params, function(res) {
            var result = res.object.content;
            tableShow(result);
        });*/
        tableShow(path,page,size);
    }
    function tableShow(u,page,size) {
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
                    auditStatus:$scope.status,
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
                field: 'ownerRealName',
                title: '商户主',
                valign: 'middle',
                sortable: true
            }, {
                field: 'createTime',
                title: '注册时间',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    return new Date(Number(value)).format("yyyy-MM-dd hh:mm:ss");
                }

            },
                {
                field: 'account',
                title: '账户余额',
                sortable: true,
                valign: 'middle',
                 formatter: function(value, row, index) {
                    if(!value){
                        return "";
                    }
                        return value.balance+value.redBalance+value.retateBalance;
                    }
            }, {
                field: 'agentName',
                title: '代理商',
                sortable: true,
                valign: 'middle'

            },
                {
                    field: 'status',
                    title: '审核',
                    valign: 'middle',
                    formatter: function(value, row, index) {

                    if(value==-1){
                        return '<button class="btn past-button" id="pass">通过</button></br><button class="btn refuse-button" id="reject">拒绝</button>'

                     }
                     if( value==1){
                         return "已通过"
                     }
                        if(value==4){
                            return "<div>已拒绝</div>"
                        }

                    },
                    events: {
                        'click #pass': function(e, value, rowData, index) {
                            layer.confirm('确定通过?', {
                                btn: ['确定', '取消'],
                                btnAlign: 'c',
                                yes: function(index, layero) {
                                    var MctPath = "/api/admin/mct/auditMct";
                                    var MctPathParams = {
                                        aid: aid,
                                        mid: rowData.id,
                                        auditStatus: 1,
                                        auditDesc: ""
                                    }
                                    qbhttp.post(MctPath, MctPathParams, function(res) {

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
                                    var MctPath = "/api/admin/mct/auditMct";
                                    var MctPathParams = {
                                        aid: aid,
                                        mid: rowData.id,
                                        auditStatus: 2,
                                        auditDesc: remarks
                                    }
                                    qbhttp.post(MctPath, MctPathParams, function(res) {

                                        layer.msg('操作成功！', {
                                            icon: 6
                                        });
                                        dom.bootstrapTable('remove', {
                                            field: 'id',
                                            values: [rowData.id]
                                        });
                                        layer.close(index);
                                    });
                                }
                            });

                        }

                    }
                },
                {
                    field: 'operation',
                    title: '操作',
                    valign: 'middle',
                    align:'center',
                    width:'150px',
                    formatter: function(value, row, index) {
                        return '<button class="btn past-button" id="belong">修改归属</button></br><button class="btn refuse-button" id="info">修改信息</button>'
                    },
                    events: {
                        'click #belong': function(e, value, rowData, index) {

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

                        },
                        'click #info': function(e, value, rowData, index) {
                            rowData.createTime=new Date(rowData.createTime).format("yyyy-MM-dd hh:ss");
                            console.log(rowData);
                            sessionStorage.setItem("info",JSON.stringify(rowData));
                            $state.go("main.merchantInfo");
                        }

                    }
                }
            ]
        });
    }
    $scope.addMerchant=function(){
        sessionStorage.removeItem('info');

        $state.go("main.merchantInfo");
    }
}]);