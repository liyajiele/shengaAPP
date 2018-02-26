/**
 * Created by wangshuang on 2016/8/3.
 */
'use strict';

app.controller('advertisementCtrl', ['$scope', '$state', '$http', '$rootScope', '$sce', 'qbhttp', function ($scope, $state, $http, $rootScope, $sce, qbhttp) {
    var aid=sessionStorage.getItem("aid");
  var params ={
    aid :aid,
    status:1,
    districtId:''
  };
    qbhttp.post('/api/admin/banner/adminFindBannerList', params, function (res) {
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
      uniqueId: "id",                     //每一行的唯一标识，一般为主键列
      data: data,
      columns: [{
        field: 'id',
        title: 'Id',
        valign: 'middle',
        sortable: true,
      },{
        field: 'picture',
        title: '广告图片',
        valign: 'middle',
        sortable: true,
        formatter: function (value, row, index) {
          return '<img src="'+row.image+'" style="width: 101px;height: 75px">';
        }
      }, {
        field: 'image',
        title: '广告链接',
        valign: 'middle',
        sortable: true,
      }, {
        field: 'content',
        title: '备注',
        sortable: true,
        valign: 'middle'
      }, {
        field: 'carouselTime',
        title: '广告类型',
        sortable: true,
        valign: 'middle'
      }, {
        field: 'createTime',
        title: '时间',
        valign: 'middle',
        sortable: true,
      }, {
        field: 'caller',
        title: '审核',
        valign: 'middle',
        sortable: true,
        align:'center',
        width:'150px',
        formatter: function (value, row, index) {
          return '<div><button class="btn past-button">通过</button></div><div><button class="btn refuse-button">拒绝</button></div>'
        },
        events: {
          'click .past-button': function (e, value, row, index) {
            bannerModify(row,1);
          },
          'click .refuse-button':function (e, value, row, index) {
            bannerModify(row,2);
          }
        }
      },{
        field: 'is_follower',
        title: '操作',
        valign: 'middle',
        sortable: true,
        formatter: function (value, row, index) {
            return '<span  class="bannerEdit glyphicon glyphicon-edit" id="' + row.id + '" style="font-size: 16px;color:#AEAEAE;"></span><span  class="bannerRemove fa fa-trash" id="' + row.id + '" style="font-size: 16px;color:#AEAEAE;margin-left:10px"></span>';
          },
        events:{
          'click .bannerRemove': function (e, value, row, index) {
            bannerModify(row,0);
          },
          'click .bannerEdit': function (e, value, row, index) {
            bannerEdit(row);
          }
        }
        }
      ]
    });
  }

  function bannerEdit(row) {
    $("#addAdvertise").find("#content").val(row.content);
    $("#addAdvertise").find("#imgurl").val(row.image);
    $("#addAdvertise").find("#time").val(row.carouselTime);
    $("#addAdvertise").find("#pr").val("0");
    layer.open({
      type:1,
      content:$("#addAdvertise"),
      btn: ['修改', '取消'],
      btnAlign: 'c',
      yes: function(index, layero) {
        //按钮【按钮一】的回调/api/admin/banner/agentAddBanner
        var file  = $("#img")[0].files[0];
        if(file == undefined){
          addBannerParmas(row,index,row.image);
        }else{
          var path1='banner1'+ new Date().getTime();
          var baseURL = "http://api.51shenga.com";
          var data = new FormData();
          data.append('file',file);
          data.append('path',path1);
          var path2= "/api/common/uploadFile";
          var sign=hex_md5(aid+path2);
          $.ajax({
            header:{sign:sign},
            url: baseURL + path2,
            method: 'POST',
            data: data,
            contentType: false, // 注意这里应设为false
            processData: false,
            cache: false,
            success: function (data) {
              if(data.code == 1){
                addBannerParmas(row,index,data.object);
              }
            }
          })
        }

      },
      title: '修改广告',
      shadeClose: true
    })

  }

  function addBannerParmas(row,index,img) {
    var add_parmas = {
      aid :aid,
      bannerId:row.id,
      image:img,
      url:'',
      status:1,
      content:$("#content").val(),
      carouselTime:$("#time").val(),
      priority:$("#pr").val()
    }
    qbhttp.post('/api/admin/banner/adminModifyBanner', add_parmas, function (res) {
      var da = res.object;
      $('#advertise-table').bootstrapTable('updateByUniqueId', {
        id: row.id,
        row: {
          picture: da.image,
          image: da.image,
          content:da.content,
          carouselTime:da.carouselTime,
          createTime:da.createTime
        }
      });

      layer.close(index);
      layer.msg('修改成功！', {icon: 6});
    });
  }

  function bannerModify(row,status) {
    var dom = $('#advertise-table');
    var banner_params ={
      aid:aid,
      bannerId:row.id,
      image:row.image,
      url:'',
      content:row.content,
      status:status,
      carouselTime:row.carouselTime,
    }

    if(status == 2){
      layer.confirm('确定拒绝?', {
        title: '拒绝理由',
        /*area: ['700px', '300px'],*/
        btn: ['确定', '取消'], //可以无限个按钮
        btnAlign: 'c',
        content:'<div><label></label>拒绝理由<textarea id="refuseReason" class="refuse-textarea"></textarea></div>',
        yes: function(index, layero) {
          var remarks = $("#refuseReason").text();
          banner_params.remarks = remarks;
          qbhttp.post('/api/admin/banner/adminModifyBanner', banner_params, function (res) {
            dom.bootstrapTable('remove', {
              field: 'id',
              values: [row.id]
            });
            layer.msg('操作成功！', {icon: 6});
          });
          layer.close(index);
        }
      });
    }else{
      var m = status == 1 ? "确定通过?":'确定删除?';
      layer.confirm(m, {
        title: '提示',
        btn: ['确定', '取消'], //可以无限个按钮
        btnAlign: 'c',
        content:'<div>'+m+'</div>',
        yes: function(index, layero) {
          qbhttp.post('/api/admin/banner/adminModifyBanner', banner_params, function (res) {
            if(status != 1){
              dom.bootstrapTable('remove', {
                field: 'id',
                values: [row.id]
              });
            }
            layer.msg('操作成功！', {icon: 6});
          });
          layer.close(index);
        }
      });
    }
  }

  $scope.addBanner = function () {
    layer.open({
      type:1,
      content:$("#addAdvertise"),
      btn: ['发布', '取消'],
      btnAlign: 'c',
      yes: function(index, layero) {
        //按钮【按钮一】的回调/api/admin/banner/agentAddBanner


        var file  = $("#img")[0].files[0];
        var path1='banner1'+ new Date().getTime();

        var baseURL = "http://api.51shenga.com";
        var data = new FormData();
        data.append('file',file);
        data.append('path',path1);
        var path2= "/api/common/uploadFile";
        var sign=hex_md5(aid+path2);
        $.ajax({
          header:{sign:sign},
          url: baseURL + path2,
          method: 'POST',
          data: data,
          contentType: false, // 注意这里应设为false
          processData: false,
          cache: false,
          success: function (data) {
            if(data.code == 1){
              var add_parmas = {
                aid :aid,
                image:data.object,
                url:'',
                content:$("#content").val(),
                status:1,
                carouselTime:$("#time").val(),
                priority:$("#pr").val()
              }
              qbhttp.post('/api/admin/banner/adminAddBanner', add_parmas, function (res) {
                var da = res.object;
               $('#advertise-table').bootstrapTable('insertRow', {
                 index: 0,
                 row: {
                   id: da.id,
                   picture: da.image,
                   image: da.image,
                   content:da.content,
                   carouselTime:da.carouselTime,
                   createTime:da.createTime
                 }
               });

                layer.close(index);
                 layer.msg('发布成功！', {icon: 6});
              });
            }
          }
        })
/*        $http({
          headers:{sign:sign},
          url: baseURL + path2,
          method: "post",
          params: data,
          contentType: false, // 注意这里应设为false
          processData: false
        }).success(function (res) {
          console.log(res);
        })*/




      },
      title: '新增广告',
      shadeClose: true
    })

  }


}]);
