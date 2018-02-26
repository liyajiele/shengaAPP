/**
 * Created by Administrator on 2016/8/11.
 */

app.controller('authorityCtrl', ['$scope', '$state', '$http', '$rootScope', 'qbhttp', function($scope, $state, $http, $rootScope, qbhttp) {
    var aid=sessionStorage.getItem("aid"),status=1;
    $scope.author={
        aid: aid,
        roleId:"",
        role:"",
        description:"",
        priority:""
    }
    var params = {
        aid: aid
    };
    /*    $scope.merchant={
            aid:aid,
            name:"",
            time:"",
            agent:""
        }*/
    var path =  '/api/admin/user/findAdminList';
    qbhttp.post(path, params, function(res) {
        var result = res.object.content;
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
                field: 'user',
                title: '账号',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    return value.username;
                },
            }, {
                field: 'user',
                title: '用户ID',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    return value.id;
                }
            },{
                field: 'user',
                title: '真实姓名',
                valign: 'middle',
                sortable: true,
                formatter: function(value, row, index) {
                    return value.realname;
                }
            },
                {
                    field: 'roles',
                    title: '角色',
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
                       /* return '<span class="past-button" id="modifyName">更改权限</span><span class="refuse-button" id="del">删除</span>'*/
                        return '<div><button class="btn past-button" id="modifyName">更改权限</button></div><div><button class="btn refuse-button" id="del">删除</button></div>'
                    },
                    events: {
                        'click #del': function(e, value, rowData, index) {
                            layer.confirm('确定删除吗?', {
                                btn: ['删除', '取消'],
                                btnAlign: 'c',
                                yes: function(index, layero) {
                                    var delAdminPath = "/api/admin/user/delAdmin";
                                    var delAdminParams = {
                                        aid: aid,
                                        tAid: rowData.id
                                    }
                                    qbhttp.post(delAdminPath, delAdminParams, function(res) {
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
                            $scope.taid=rowData.id;

                            qbhttp.post("/api/admin/user/roleList",{aid:aid}, function(res) {
                                var r=res.object;
                                for(var i=0,len=r.length;i<len;i++){
                                    if(rowData.roles.indexOf(r[i].description)>=0){
                                        r[i].isChecked=true;
                                    }
                                }
                                    $scope.roleList=r;



                            });


                            layer.open({
                                type:1,
                                content:$("#roleList"),
                                btn: ['确定', '取消'],
                                btnAlign: 'c',
                                title: '更改权限',
                                shadeClose: true,
                                yes: function(index, layero) {
                                    layer.close(index);
                                    location.reload();
                                /*    var authorityPath = "/api/admin/user/modifyRole";
                                    $scope.author.roleId=rowData.id;
                                    $scope.author.aid=aid;
                                    qbhttp.post(authorityPath, $scope.author, function(res) {
                                        $('#advertise-table').bootstrapTable('updateByUniqueId', {
                                            id:  res.object.id,
                                            row: $scope.author
                                        });

                                        layer.msg('操作成功！', {
                                            icon: 6
                                        });

                                    });*/
                                }
                            });

                        }

                    }
                }

            ]
        });
    }
    $scope.changeUserRole=function (e,l) {
        var  param={
            aid:aid,
            taid:$scope.taid,
            roleId:l.id,
        }
        var path=  e.target.checked?"/api/admin/user/addAdminRole":"/api/admin/user/delAdminRole";
        qbhttp.post(path, param, function(res) {

           /* layer.close(index);*/
        });

    }
    $scope.roleMange=function () {
        qbhttp.post("/api/admin/user/roleList",{aid:aid}, function(res) {
            var r=res.object;
            $scope.roleList=r;
        });
        layer.open({
            type:1,
            content:$("#roleMange"),
            btn: ['确定', '取消'],
            btnAlign: 'c',
            title: '角色管理',
            shadeClose: true,
            yes: function(index, layero) {

            }
        });
    }

    $scope.isCheck=function () {
        $scope.author.description=[];
        for(var i=0,c;c=$(".role input[type='checkbox']:checked")[i++];){
            $scope.author.description.push(c.getAttribute("data-description"));
        }
        $scope.author.description=$scope.author.description.toString();
    }
    $scope.role={
        aid:aid,
        role:"",
        description:"",
        priority:""
    }
    $scope.addRole=function () {
        layer.open({
            type:1,
            content:$("#AddRole"),
            btn: ['确定', '取消'],
            btnAlign: 'c',
            title: '新增角色',
            shadeClose: true,
            yes: function(index, layero) {
                var addRolePath = "/api/admin/user/addRole";

                qbhttp.post(addRolePath, $scope.role, function(res) {
                    var r=res.object;
                    $scope.roleList.push(r);
                    layer.msg('操作成功！', {
                        icon: 6
                    });
                    layer.close(index);
                });
            }
        });
    }
    $scope.delRole=function (id,num) {
        layer.confirm('确定删除?', {icon: 3, title:'提示'}, function(index){
            var delRolePath = "/api/admin/user/delRole";

            qbhttp.post(delRolePath, {aid:aid,roleId:id}, function(res) {
                $scope.roleList.splice(num,1);
                layer.msg('操作成功！', {
                    icon: 6
                });
                layer.close(index);
            });

            layer.close(index);
        });
    }

    $scope.modifyRole=function (role,num) {
        var rolestr=JSON.stringify(role);
        $scope.role=  JSON.parse(rolestr);
        layer.open({
            type:1,
            content:$("#AddRole"),
            btn: ['确定', '取消'],
            btnAlign: 'c',
            title: '更改角色',
            shadeClose: true,
            yes: function(index, layero) {
                var modifyRolePath = "/api/admin/user/modifyRole";
                $scope.role.roleId=role.id;
                $scope.role.aid=aid;
                qbhttp.post(modifyRolePath, $scope.role, function(res) {
                    var r=res.object;
                    $scope.roleList.splice(num,1,r);
                    layer.msg('操作成功！', {
                        icon: 6
                    });
                    layer.close(index);
                });
            }
        });
    }

    $scope.resourceOfrole=function (id) {
        $scope.roleId=id;
        qbhttp.post("/api/admin/user/resourceList",{},function(res) {
            var r = res.object;
            $scope.resourceList = r;
        })
        qbhttp.post("/api/admin/user/findResourceByRole",{aid:aid,roleId:id},function(res) {
            var r=res.object;
            $scope.resourceListOfRole=r;
            layer.open({
                type:1,
                content:$("#resourceListOfRole"),
                area:["auto","50vh"],
                btn: ['确定', '取消'],
                btnAlign: 'c',
                title: '角色已有资源',
                shadeClose: true,
                yes: function(index, layero) {

                }
            });
        });
    }
    $scope.resourceMange=function () {
        qbhttp.post("/api/admin/user/resourceList",{},function(res) {
            var r=res.object;
            $scope.resourceList=r;
            layer.open({
                type:1,
                content:$("#resourceMange"),
                area:["auto","50vh"],
                btn: ['确定', '取消'],
                btnAlign: 'c',
                title: '角色管理',
                shadeClose: true,
                yes: function(index, layero) {

                }
            });
        });

    }
    $scope.resource={
        aid:aid,
        resourceName:"",
        permission:"",
        url:"",
        parentResourceId:"",
        priority:""
    }

    $scope.addResourceForRole=function () {


        layer.confirm('确定为角色增加此资源?', {icon: 3, title:'提示'}, function(index){
            var addRoleResourcePath = "/api/admin/user/addRoleResource";
            qbhttp.post(addRoleResourcePath, {aid:aid,resourceId:$scope.selectOption.id,roleId:$scope.roleId}, function(res) {
                $scope.resourceListOfRole.push($scope.selectOption);
                layer.msg('操作成功！', {
                    icon: 6
                });
                layer.close(index);
            });
        });

    }
    $scope.addResource=function () {
        layer.open({
            type:1,
            content:$("#AddResource"),
            btn: ['确定', '取消'],
            btnAlign: 'c',
            title: '新增资源',
            shadeClose: true,
            yes: function(index, layero) {
                $scope.resource.aid=aid;
                var addResourcePath = "/api/admin/user/addResource";

                qbhttp.post(addResourcePath, $scope.resource, function(res) {
                    var r=res.object;
                    $scope.resourceList.push(r);
                    layer.msg('操作成功！', {
                        icon: 6
                    });
                    layer.close(index);
                });
            }
        });
    }

    $scope.delResourceForRole=function (id,num) {
        layer.confirm('确定删除?', {icon: 3, title:'提示'}, function(index){
            var delRoleResourcePath = "/api/admin/user/delRoleResource";

            qbhttp.post(delRoleResourcePath, {aid:aid,resourceId:id,roleId:$scope.roleId}, function(res) {
                $scope.resourceListOfRole.splice(num,1);

                layer.msg('操作成功！', {
                    icon: 6
                });
                layer.close(index);
            });

            layer.close(index);
        });
    }

    $scope.delResource=function (id,num) {
        layer.confirm('确定删除?', {icon: 3, title:'提示'}, function(index){
            var delResourcePath = "/api/admin/user/delResource";

            qbhttp.post(delResourcePath, {aid:aid,resourceId:id}, function(res) {
                $scope.resourceList.splice(num,1);
                layer.msg('操作成功！', {
                    icon: 6
                });
                layer.close(index);
            });

            layer.close(index);
        });
    }
    $scope.modifyResource=function (role,num) {
        var rolestr=JSON.stringify(role);
        $scope.resource=  JSON.parse(rolestr);
        layer.open({
            type:1,
            content:$("#AddResource"),
            btn: ['确定', '取消'],
            btnAlign: 'c',
            title: '更改资源',
            shadeClose: true,
            yes: function(index, layero) {
                var modifyResourcePath = "/api/admin/user/modifyResource";
                $scope.resource.resourceId=role.id;
                $scope.resource.aid=aid;
                qbhttp.post(modifyResourcePath, $scope.resource, function(res) {
                    var r=res.object;
                    $scope.resourceList.splice(num,1,r);
                    layer.msg('操作成功！', {
                        icon: 6
                    });
                    layer.close(index);
                });
            }
        });
    }

    $scope.addMember=function(){
        $scope.author={
            aid: aid,
            uid:"",
            username:"",
            password:"",
            description:[],
            roleIds:[]

        };
        qbhttp.post("/api/admin/user/roleList",{aid:aid}, function(res) {
            var r=res.object;
            $scope.roleList=r;
        });
        layer.open({
            type:1,
            content:$("#Addauthority"),
            btn: ['确定', '取消'],
            btnAlign: 'c',
            title: '新增用户',
            shadeClose: true,
            yes: function(index, layero) {

                $scope.author.roleIds=[];
                for(var i=0,c;c=$(".role input[type='checkbox']:checked")[i++];){

                    $scope.author.roleIds.push(c.getAttribute("data-id"));
                }

                $scope.author.roleIds=$scope.author.roleIds.toString();
                var addRolePath = "/api/admin/user/bindAdmin";

                qbhttp.post(addRolePath, $scope.author, function(res) {
                    var r=res.object;
                    $('#advertise-table').bootstrapTable('insertRow', {
                        index: 0,
                        row: r
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