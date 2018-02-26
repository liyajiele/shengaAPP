/**
 * Created by Administrator on 2016/8/11.
 */

app.controller('mainCtrl', ['$scope', '$state', '$http', '$rootScope', 'qbhttp', function($scope, $state, $http, $rootScope, qbhttp) {
    var aid=sessionStorage.getItem("aid");
    qbhttp.post('/api/admin/user/menuList', {aid:aid}, function(res) {
        $scope.menuList=res.object
    });

    $scope.Active=sessionStorage.Active==-1?1:sessionStorage.Active;
    console.log($scope.Active);
    $scope.addActiveClass=function (index) {
        sessionStorage.Active=index;
        $scope.Active=index;
    }
}]);