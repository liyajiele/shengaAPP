#shenga
1. 文档地址
http://api.51shenga.com:8999/swagger-ui.html

2. 修改app.js baseUrl 为 http://api.51shenga.com:8999

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