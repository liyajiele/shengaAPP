#shenga
1. 文档地址
http://api.51shenga.com/swagger-ui.html

2. 修改app.js baseUrl 为 http://api.51shenga.com

3. 接口返回格式
~~~
{
  "code": 1,    // 1.成功, 其他非正常
  "message": "成功!",  // 当非code 非1 的时候返回的错误原因
  "object": {    // 不同的接口 返回不同的格式
    "id": 1,
    "nickname": "123"
  }
}
~~~

4. 调用普通接口  
a.在对应的service.js 中加入接口调用的方法  
b.在controller 中注入service  app.js , controller注入
c. userService.login().success(function(resp){ //具体操作})

5.项目介绍
省啊APP是一个微信公众号开发项目，分为用户端以及商户端，附带后台管理系统。<br>
移动端为angular1开发。后台管理系统为boostrap框架。<br>
技术：html，css，jquery，angular,boostrap,ajax
