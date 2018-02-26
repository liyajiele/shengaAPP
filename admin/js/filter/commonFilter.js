/**
 * Created by Administrator on 2016/8/28.
 */
app.filter('getYear', function () {
    //传入时间格式字符串就可以:"2016-08-23"
    return function (str) {
        return new Date(str).getFullYear();
    }
});
app.filter('getMonDay', function () {
    //传入时间格式符串就可以:"2016-08-23"
    return function (str) {
        return (new Date(str).getMonth() + 1) + '-' + new Date(str).getDate();
    }
});