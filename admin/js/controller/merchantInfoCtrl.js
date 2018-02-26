/**
 * Created by Administrator on 2016/8/11.
 */

app.controller('merchantInfoCtrl', ['$scope', '$state', 'qbhttp', function($scope, $state, qbhttp) {
    var aid=sessionStorage.getItem("aid");
    $scope.ngreadonly=true;
    $scope.btnShow=true;
    var switchName={
        "门店照片":"mainImage",
        "身份证正面照":"idcardImage1",
        "身份证反面照":"idcardImage2",
        "营业执照":"licenceImage"

    }
    /*  $scope.infoOpration=["门店照片","身份证正面照","身份证反面照","营业执照"];*/
    $scope.changeImg=function (name,e) {
        $(e.target).siblings(".fileUpload").trigger("click");
    }
    var infoData=sessionStorage.getItem("info")
     var merchatInfo=infoData ?JSON.parse(infoData):undefined;


    $scope.init=function (data) {
        if(!data){
            $scope.merchantInfoDetail={
                aid:aid,
                uid:0,
                areaId:"",
                provinceId:"",
                cityId :"",
                districtId:"",
                mctName:"",
                addr:"",
                mobile:"",
                shopHours:"",
                mainImage:"",
                images:"",
                types:"",
                ownerRealName:"",
                ownerIdcard:"",
                idcardImage1:"",
                idcardImage2:"",
                licenceType:"",
                licenceStatus:"",
                licenceImage:"",
                longitude:"",
                latitude:"",
                consumerption:"",
                profits:"",
                rebateType:""

            }
            var data=$scope.merchantInfoDetail;
            var d=JSON.stringify(data);
            $scope.merchant=JSON.parse(d);
            $scope.mapOpts = {
                apiKey: '696e6574253d1eb36c1c4b705c504b20',
                center: {
                    longitude: "121.388642",
                    latitude: "31.097438"
                },
              /*  http://oss51shenga.51shenga.com/imgageInfoidcardImage1*/
                zoom: 17,
                enableScrollWheelZoom: true,
                enableMapClick: false,
                onMapLoadFild: function () {
                    $scope.log('百度地图加载失败');
                }
            };
            $scope.infoOpration=[{name:"门店照片",imag:data.mainImage},{name:"身份证正面照",imag:data.idcardImage1},{name:"身份证反面照",imag:data.idcardImage2},{name:"营业执照",imag:data.licenceImage}];
        }else {
            $scope.mapOpts = {
                apiKey: '696e6574253d1eb36c1c4b705c504b20',
                center: {
                   /* longitude: data.longitude,
                    latitude: data.latitude*/
                    longitude: "121.388642",
                    latitude: "31.097438"
                },
                zoom: 17,
                enableScrollWheelZoom: true,
                enableMapClick: false,
                onMapLoadFild: function () {
                    $scope.log('百度地图加载失败');
                }
            };
                $scope.merchantInfoDetail=data;
                var d=JSON.stringify(data);
            $scope.merchant=JSON.parse(d);
                $scope.infoOpration=[{name:"门店照片",imag:data.mainImage},{name:"身份证正面照",imag:data.idcardImage1},{name:"身份证反面照",imag:data.idcardImage2},{name:"营业执照",imag:data.licenceImage}];

        }
    }
    $scope.init(merchatInfo);


    $scope.dateFormat=function(d) {
        //var dt = d.replace(/[^0-9]+/g, '');
        if(!d){
            return;
        }
        var t = new Date(Number(d)).format("yyyy-MM-dd hh:mm:ss");
        return t;
    },

    /*$scope.merchantInfoDetail={
        aid:aid,
         uid:0,
       areaId:"",
       provinceId:"",
       cityId :"",
        districtId:"",
        mctName:"",
        addr:"",
        mobile:"",
        shopHours:"",
        mainImage:"",
        images:"",
        types:"",
        ownerRealName:"",
        ownerIdcard:"",
        ownerIdcard:"",
        ownerIdcard:"",
        licenceType:"",
        licenceStatus:"",
        licenceImage:"",
        longitude:{},
        latitude:"",
        consumerption:"",
        profits:"",
        rebateType:""

    }*/

    $scope.fileNameChanged=function (e) {
        var $file = $(e.target);
        var fileObj = $file[0];

        var windowURL = window.URL || window.webkitURL;
        var dataURL;
        var $img =$file.siblings(".small-box").find("img");

        dataURL = windowURL.createObjectURL(fileObj.files[0]);
        var variable=switchName[e.target.name];
        var path1='imgageInfo/'+variable+(new Date()).getTime()+".png";
        var baseURL = "http://api.51shenga.com";
        $img.prop("src",dataURL)
        var data = new FormData();
        data.append('file',fileObj.files[0]);
        data.append('path',path1);
        var path2= "/api/common/uploadFile";
        var sign=hex_md5(aid+path2);
        $.ajax({
            header: {sign: sign},
            url: baseURL + path2,
            method: 'POST',
            data: data,
            contentType: false, // 注意这里应设为false
            processData: false,
            cache: false,
            success: function (data) {
                $scope.$apply(function () {

                    $scope.merchant[variable]=data.object;

                })

            }
        })
    }

    $scope.subInformation=function () {
       var latloPath='/api/location/getLocationInfo';
        qbhttp.post(latloPath,  $scope.mapOpts.center, function(res) {
            var typestr="";
            for(var i=0,type;type=$scope.merchantInfoDetail.types[i++];){
                typestr+=type.id+'|';
            }
           var mer=JSON.stringify($scope.merchant)
            $scope.merchantInfoDetail=JSON.parse(mer);
            $scope.merchantInfoDetail.types=typestr.substring(0,typestr.length-1);
            $scope.merchantInfoDetail.aid=aid;
            $scope.merchantInfoDetail.mid= $scope.merchantInfoDetail.id;
            $scope.merchantInfoDetail.longitude=$scope.mapOpts.center.longitude;
            $scope.merchantInfoDetail.latitude=$scope.mapOpts.center.latitude;
            $scope.merchantInfoDetail.mctName=$scope.merchantInfoDetail.nickname;
            $scope.merchantInfoDetail.provinceId=res.object.province.tId;
            $scope.merchantInfoDetail.cityId = res.object.city.tId;
            if( typeof $scope.merchantInfoDetail.agentName=="object"){
                $scope.merchantInfoDetail.agentId= $scope.merchantInfoDetail.agentName.id;
                $scope.merchantInfoDetail.agentName= $scope.merchantInfoDetail.agentName.agentInfo;

            }
            $scope.merchantInfoDetail.areaId = res.object.province.areaId;
            $scope.merchantInfoDetail.districtId=res.object.district.tId;
            console.log($scope.merchantInfoDetail);
            var addpath=merchatInfo?'/api/admin/mct/modifyMerchantByAdmin':'/api/admin/mct/createMerchant';
            qbhttp.post(addpath, $scope.merchantInfoDetail, function(res) {
                layer.msg("添加成功!",{icon:"6"});
            })
        })
    //    return layer.alert("暂时没做好!",{icon:"6"});

        /* $scope.merchantInfoDetail.longitude=*/


    }
    $scope.rebateType=function (num) {
        return num==1?"全部返利":"部分返利";
    }
    $scope.licenceType=function (num) {
        return num==1?"企业法人":"个体工商";
    }

    $scope.licenceStatus=function (num) {
        return num==1?"已经办理":"暂无法提供";
    }
    $scope.agentChange=function (agent) {
        if(typeof agent =="object"){
            return agent.agentInfo;
        }else{
            return agent;
        }
    }
    $scope.confirm=function () {
        var phoneReg=/^((13[0-9])|(14[0-9])|(15([0-9]))|(18[0-9]))\d{8}$/;
        var idReg=/^\d{18}$/;
        if(!phoneReg.test($scope.merchant.mobile)) {
            return alert("手机号格式不正确");
        };

        if(!idReg.test($scope.merchant.ownerIdcard)) {
            return alert("身份证格式不正确");
        };
        var info=JSON.stringify($scope.merchant);
        $scope.merchantInfoDetail=JSON.parse(info);

        $scope.ngreadonly=true;
        $scope.btnShow=true;

    }
    $scope.cancel=function () {
        var info=JSON.stringify($scope.merchantInfoDetail);
        $scope.merchant=JSON.parse(info);
        $scope.ngreadonly=true;
        $scope.btnShow=true;
    }
    $scope.modifyInformation=(function(){

        var agentList;
        var typeList;
      return  function () {
          if(typeList){

              $scope.typeList = typeList;
              $scope.ngreadonly = false;
              $scope.btnShow = false;

          }else{
              var typePath ="/api/admin/mct/mctTypeList";
              var  typeParams={
                  aid:aid,
                  status:1
              }
              qbhttp.post(typePath, typeParams, function (res) {
                  typeList = res.object;
                  $scope.typeList=typeList;
                  $scope.ngreadonly=false;
                  $scope.btnShow=false;

              })
          }



       if(agentList){

               $scope.agentList = agentList;
               $scope.ngreadonly = false;
               $scope.btnShow = false;

           return;
       }else{
        var agentPath ="/api/admin/agent/findAgentListByAdmin";
        var  agentParams={
            aid:aid,
            searchStr:''
        }
        qbhttp.post(agentPath, agentParams, function (res) {
            agentList = res.object.content;

                $scope.agentList=agentList;
                console.log( $scope.agentList);
                $scope.ngreadonly=false;
                $scope.btnShow=false;

        })
       }
    }
    })()

    /*  var longitude = 0;
      var latitude = 0;
      function getNowLocation() {

          if (navigator.geolocation) {
              navigator.geolocation.getCurrentPosition(show);
          }else {
              layer.alert("浏览器不支持定位！");
          }
      }
      function show(position) {

          latitude= position.coords.latitude
          longitude=position.coords.longitude

      }
      getNowLocation();*/
    //地图显示中心经纬度

    /*  $scope.getCoord = function(e) {
          alert('The coordinate you chose is: ' + e.point.lng + ' : ' + e.point.lat);
      };*/
    /*    $scope.mapOptions = {
            centerAndZoom: {
            longitude: longitude,
            latitude: latitude,
            zoom: 16
        },
            enableKeyboard: true,
            enableScrollWheelZoom:true
        };*/





    $scope.onMapClick = function ($event, $params) {
        $scope.mymarker.clearOverlays();
        $scope.mapOpts.center.latitude = $params[0].point.lat;
        $scope.mapOpts.center.longitude = $params[0].point.lng;
        console.log( $scope.mapOpts.center.latitude);
        // 这里是使用百度提供的API方法
        // var marker = new BMap.Marker($params[0].point);
        // marker.addEventListener("click", $scope.onMarkerClick);
        // $scope.myMap.addOverlay(marker);

        //这里是自定义的一个方法，传一个点击回调方法
        $scope.mymarker.addMarker(new BMap.Marker($params[0].point), $scope.onMarkerClick);
    };
    $scope.initLat=function () {
        $scope.mapOpts.center.latitude= $scope.merchantInfoDetail?$scope.merchantInfoDetail.latitude:"39";
        $scope.mapOpts.center.longitude = $scope.merchantInfoDetail?$scope.merchantInfoDetail.longitude:"127";
    }


}]);