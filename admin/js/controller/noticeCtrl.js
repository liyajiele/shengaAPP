/**
 * Created by wangshuang on 2016/8/30.
 */
'use strict';

app.controller('noticeCtrl', ['$scope', '$state', '$http', '$rootScope', '$sce', '$location', 'qbhttp', function ($scope, $state, $http, $rootScope, $sce, $location, qbhttp) {
    var aid=sessionStorage.getItem("aid");
    $scope.status=1;
    var baseURL = "http://api.51shenga.com";
    var newNoticeId = -1;

   $scope.addable=true;//是否可以新增公共
       $scope.noticeList=[];//公告数组数据



    $scope.oprationFinish=(function () {
        return  {
            showNoticeList :function(data){
                $scope.$apply(function () {
                    $scope.noticeList = data.object.content
                });

            },
            saveFinish: function(data) {
                $scope.addable = true;
                var noticeId = data.object.id;
                for(var i = 0, len = $scope.noticeList.length; i < len; i++) {
                    if($scope.noticeList[i].id == noticeId) {
                        $scope.$apply(function () {
                            $scope.noticeList.splice(i, 1, data.object);
                        })
                        $("#click2edit" + data.object.id).removeClass("no-padding");
                        $('.click2edit' + data.object.id).addClass("normal").destroy();
                        $(".info .text-primary").show();
                        layer.msg('操作成功！', {
                            icon: 6
                        });
                        return;
                    }
                }
            },
            saveNewFinish: function(data) {
                $scope.$apply(function () {
                    $scope.addable = true;
                    $scope.noticeList.splice(-1, 1, data.object)
                });
                $("#click2edit" + newNoticeId).removeClass("no-padding");
                $('.click2edit' + newNoticeId).addClass("normal").destroy();
                $(".info .text-primary").show();
                layer.msg('操作成功！', {
                    icon: 6
                });
            },
            delFinish: function(data) {
                $scope.addable = true;
                var noticeId = data.object.id;
                for(var i = 0, len = $scope.noticeList.length; i < len; i++) {

                    if($scope.noticeList[i].id == noticeId) {
                        $scope.$apply(function () {
                            $scope.noticeList.splice(i, 1);
                        })
                        return;
                    }
                }
                //this.noticeList.splice(i, 1);
            },
            showAllInfo: function(id, e) {
                $('.click2edit'+id).removeClass("normal");
                $(e.target).hide();
            },
            dateFormat: function(d) {
                //var dt = d.replace(/[^0-9]+/g, '');
                if(!d){
                    return;
                }
                var t = new Date(Number(d)).format("yyyy-MM-dd hh:mm:ss");
                return t;
            },

            getSatusTxt: function(status) {
                switch(status) {
                    case -1:
                        return "保存";
                    case 1:
                        return "发布";
                    default:
                        return "删除";
                        break;
                }
            },
            addNotice: function() {
                if(!$scope.addable) {
                    layer.alert("请先完成新增公告的操作！",{icon:"6"});
                    return;
                }

                    $scope.addable = false;
                    $scope.noticeList.push({
                        id: newNoticeId,
                        content: ""
                    });


                setTimeout(function() {
                    $scope.oprationFinish.edit(newNoticeId);
                }, 200)

            },
            edit: function(id) {
                $(".info .text-primary").hide();

                $('#click2edit'+id).addClass("no-padding");
                $('.click2edit'+id).summernote({
                    lang: 'zh-CN',
                    focus: true
                });
            },
            save: function(noticeId, content) {
                var title = "",
                    u = "";
                if(noticeId == newNoticeId) {
                    var data = DataFormat(aid, content, 1, -1),
                        Url = baseURL + "/api/admin/notice/addNotice",
                        sign = hex_md5(aid + "/api/admin/notice/addNotice");
                    getData(Url, data, sign, $scope.oprationFinish.saveNewFinish);
                } else {
                    var data = DataFormat(aid, content, 1, -1, noticeId),
                        Url = baseURL + "/api/admin/notice/modifyNotice",
                        sign = hex_md5(aid + "/api/admin/notice/modifyNotice");
                    getData(Url, data, sign, $scope.oprationFinish.saveFinish)
                }
            },
            release: function(noticeId) {

                var content = $('.click2edit' + noticeId).code();
                if(noticeId == newNoticeId) {
                    var data = DataFormat(aid, content, 1, 1),
                        Url = baseURL + "/api/admin/notice/addNotice",
                        sign = hex_md5(aid + "/api/admin/notice/addNotice");
                    getData(Url, data, sign,$scope.oprationFinish.saveNewFinish)
                } else {
                    var data = DataFormat(aid, content, 1, 1, noticeId),
                        Url = baseURL + "/api/admin/notice/modifyNotice",
                        sign = hex_md5(aid + "/api/admin/notice/modifyNotice");
                    getData(Url, data, sign, $scope.oprationFinish.saveFinish)
                }
            },
            del: function(noticeId) {

                layer.confirm('确定删除?', {
                    title: '提示',
                    btn: ['确定', '取消'], //可以无限个按钮
                    btnAlign: 'c',
                    yes: function(index, layero) {
                        if(noticeId == newNoticeId) {
                            $scope.$apply(function () {
                                $scope.addable = true;
                                $scope.noticeList.splice(-1, 1);
                            })

                        }
                        var data = DataFormat(aid, "del", 1, 0, noticeId),
                            Url = baseURL + "/api/admin/notice/modifyNotice",
                            sign = hex_md5(aid + "/api/admin/notice/addNotice");
                        getData(Url, data, sign, $scope.oprationFinish.delFinish)
                        layer.close(index);
                    }
                });

            }
        }
    })()
    //初始化方法
    $scope.init = function () {
        var u = baseURL + "/api/admin/notice/findNoticeListByAdmin",
            sgn = hex_md5(aid + "/api/admin/notice/findNoticeListByAdmin");
        getData(u, {
            status:  $scope.status
        }, sgn, $scope.oprationFinish.showNoticeList)
    };

    //全选事件
$scope.TrustDangerousSnippet=function (content) {
    return $sce.trustAsHtml(content);
}

    $scope.init();
    //菜单点击删除按钮
    $scope.del = function (id) {
        layer.confirm('确定删除?', {
            title: '提示',
            btn: ['确定', '取消'], //可以无限个按钮
            btnAlign: 'c',
            yes: function(index, layero) {
                if(noticeId == newNoticeId) {
                    $scope.addable = true;
                    $scope.noticeList.splice(-1, 1);
                    return;
                }
                var data = DataFormat(aid, "del", 1, 0, noticeId),
                    Url = baseURL + "/api/admin/notice/modifyNotice",
                    sign = hex_md5(aid + "/api/admin/notice/addNotice");
                getData(Url, data, sign,$scope.oprationFinish.delFinish)
                layer.close(index);
            }
        });


        delVersionRecord(id);
    };
    $scope.search=function () {
        $scope.init();
    }

}]);