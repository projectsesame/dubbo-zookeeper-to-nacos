# 升级文档

## 注册中心从zookeeper更换到nacos

1. 去除zookeeper依赖(也可不去除)
2. 添加nacos依赖

```xml

<dependencies>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
</dependencies>

<dependencyManagement>
<dependencies>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-dependencies</artifactId>
        <version>${spring-cloud-alibaba.version}</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
</dependencies>
</dependencyManagement>
```

3. 修改注册中心地址

```yaml
dubbo:
  registry:
    address: ${REG_ADDRESS:nacos://127.0.0.1:8848}
```

注: 要在地址的协议上写上nacos(原zookeeper

4. 启动应用

启动成功之后

nacos控制台能看到注册的服务

![nacos-naming.png](img%2Fnacos-naming.png)

然后请求consumer接口
```
curl -X GET "http://localhost:8085/sayHello?name=hello
```
返回结果: say hello 则切换注册中心成功

## 配置中心从apollo更换到nacos

1. 去除apollo相关依赖以及启动项上的@EnabledApolloConfig注解(必须去除)
2. 添加nacos-config相关依赖
```xml
<dependencys>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-bootstrap</artifactId>
    </dependency>
</dependencys>

<dependencyManagement>
<dependencies>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-dependencies</artifactId>
        <version>${spring-cloud-alibaba.version}</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
</dependencies>
</dependencyManagement>
```
3. 在bootstrap.yaml文件中(没有就创建一个)添加如下配置
```yaml
spring:
  application:
    name: ${APP_NAME:provider-demo}
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
      discovery:
        enabled: false #关闭spring-cloud下的注册功能
```

4. 在nacos中创建相关配置文件

注：DataID 为 spring.application.name
![nacos-config.png](img%2Fnacos-config.png)


在 Nacos Spring Cloud 中，dataId 的完整格式如下：
**${prefix}-${spring.profiles.active}.${file-extension}**  

**prefix**: 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置。  
**spring.profiles.active**:即为当前环境对应的 profile，详情可以参考 Spring Boot文档。 注意：当 spring.profiles.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 ${prefix}.${file-extension}  
**file-exetension**:为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置  

5. 启动应用

启动成功之后
```
curl -X GET "http://localhost:8086/dynamic-config"

curl -X GET "http://localhost:8086/dynamic-config-system"
```
如果返回的值和nacos配置中心的一致说明配置成功，并且可以动态生效

## 参考文档

https://nacos.io/zh-cn/docs/v2/ecology/use-nacos-with-spring-cloud.html
