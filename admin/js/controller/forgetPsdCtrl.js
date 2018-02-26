/**
 * Created by Administrator on 2016/8/11.
 */

app.controller('forgetPsdCtrl', ['$scope', '$state', '$http', '$rootScope', 'qbhttp', function($scope, $state, $http, $rootScope, qbhttp) {
 var aid=1;
    $scope.info={
     aid:aid,
     username:"",
     code:"",
     newPassword:""

 };

 $scope.validCode=function () {
     var code={
         aid:aid,
         mobile:$("#phone").val(),
         smsType:2
     }
     qbhttp.post('/api/common/getSmsCode',code, function (res) {
        console.log(res);
     });
 }
  $scope.changePsd=function () {
      qbhttp.post('/api/admin/user/forgetPassword',$scope.info, function (res) {
          $state.go("login");
      });
  }
}]);