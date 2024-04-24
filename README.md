## 本项目分为三个模块
consumer 消费端  
provider 服务端   
interface 共享API模块

## 启动
1. 先启动provider  

测试
```
curl -X GET "http://localhost:8086/health"

返回结果: success
```
2. 再启动consumer
测试
```
curl -X GET "http://localhost:8085/sayHello?name=hello

返回结果: say hello
```