# IDEA后台微服务架构:

- ### 基于SpringBoot+SpringCloud+SpringMVC+SpringDataJPA的微服务Demo说明  
  > - 开发环境：IDEA、jdk 1.8、mysql 5.7、maven、CentOS 7、docker、Postman
  > - 如源码所示，每个文件夹都是一个微服务

##


# 目录
- [0.准备后台开发文档](#0准备后台开发文档)
- [1.关于后台公共模块](#1关于后台公共模块)
-- [1-1.全局的返回结果实体类](#1-1全局的返回结果实体类)
-- [1-2.全部模块采用SpringMVC模式](#1-2.全部模块采用SpringMVC模式)
-- [1-3.基于twitter的snowflake（雪花）算法-分布式ID生成器](1-3.基于twitter的snowflake（雪花）算法-分布式ID生成器)
- [2.Spring Data JPA](#2.Spring-Data-JPA)
-- [2-1.SpringDataJPA依赖与配置](#2-1.SpringDataJPA依赖与配置)
-- [2-2.SpringDataJPA使用](#2-2.SpringDataJPA使用)
- [3.Spring Data Redis](#3.Spring-Data-Redis)
-- [3-1.SpringDataRedis依赖与配置](#3-1.SpringDataRedis依赖与配置)
-- [3-2.SpringDataRedis使用](#3-2.SpringDataRedis使用)
- [4.SpringDataMongoDB](#4.SpringDataMongoDB)
-- [4-1.SpringDataMongoDB依赖与配置](#4-1.SpringDataMongoDB依赖与配置)
-- [4-2.SpringDataMongoDB使用](#4-2.SpringDataMongoDB使用)
- [5.SpringDataElasticsearch](#5.SpringDataElasticsearch)
-- [5-1.SpringDataElasticsearch依赖与配置](#5-1.SpringDataElasticsearch依赖与配置)
-- [5-2.SpringDataElasticsearch使用](#5-2.SpringDataElasticsearch使用)
-- [5-3.SpringDataElasticsearch搜索例子](#5-3.SpringDataElasticsearch搜索例子)
- [6.RabbitMQ](#6.RabbitMQ)
-- [6-1.RabbitMQ依赖与配置](#6-1.RabbitMQ依赖与配置)
-- [6-2.RabbitMQ-直接模式(Direct)](#6-2.RabbitMQ-直接模式(Direct))
-- [6-3.RabbitMQ-分列模式(Fanout)](#6-3.RabbitMQ-分列模式(Fanout))
-- [6-4.RabbitMQ-主题模式(Topic)](#6-4.RabbitMQ-主题模式(Topic))
- [7.spring security](#7.spring-security)
-- [7-1.spring security依赖与配置](#7-1.spring-security依赖与配置)
- [8.JWT的Token认证](#8.JWT的Token认证)
-- [8-1.Java的JJWT实现JWT](#8-1.Java的JJWT实现JWT)
-- [8-2.登录时签发Token](#8-2.登录时签发Token)
-- [8-3.客户端请求时解析Token](#8-3.客户端请求时解析Token)
- [9.Spring Cloud系列](#9.Spring-Cloud系列)
-- [9-1.Eureka(服务发现组件)](#9-1.Eureka(服务发现组件))
--- [9-1-1.Eureka Server](#9-1-1.Eureka-Server)
--- [9-1-2.Eureka Client](#9-1-2.Eureka-Client)
-- [9-2.Feign(实现服务间的调用)](#9-2.Feign(实现服务间的调用))
-- [9-3.Hystrix(熔断器)](#9-3.Hystrix(熔断器))
-- [9-4.Zuul(微服务网关)](9-4.Zuul(微服务网关))
--- [9-4-1.前后台微服务网关](#9-4-1.前后台微服务网关)
-- [9-5.SpringCloudConfig(分布式配置中心)与SpringCloudBus(消息总线组件)](#9-5.SpringCloudConfig(分布式配置中心)与SpringCloudBus(消息总线组件))
--- [9-5-1.config server依赖与配置](#9-5-1.config-server依赖与配置)
--- [9-5-2.config client依赖与配置](#9-5-2.config-client依赖与配置)
--- [9-5-3.自定义配置的读取](#9-5-3.自定义配置的读取)
- [10.Docker私有仓库搭建与配置](#10.Docker私有仓库搭建与配置)
- [11.如何将微服务自动部署并打包成镜像？](#11.如何将微服务自动部署并打包成镜像？)
- [12.持续集成](#12.持续集成)
-- [12-1.Gogs安装与配置](#12-1.Gogs安装与配置)
-- [12-2.Jenkins(实现持续集成)](#12-2.Jenkins(实现持续集成))
- [13.容器管理与容器监控](#13.容器管理与容器监控)
-- [13-1.Rancher(容器管理工具)](#13-1.Rancher(容器管理工具))
-- [13-2.微服务扩容与缩容](#13-2.微服务扩容与缩容)
-- [13-3.influxDB(分布式时间序列数据库)](#13-3.influxDB(分布式时间序列数据库))
-- [13-4.cAdvisor(监控Docker容器)](#13-4.cAdvisor(监控Docker容器))
-- [13-5.Grafana(可视化面板,查看容器参数)](#13-5.Grafana(可视化面板,查看容器参数))
-- [13-6.预警通知设置](#13-6.预警通知设置)




## 0.准备后台开发文档

> - 前后端分离开发前，需要准备好 **后台开发文档**。  
> - 前端根据开发文档的URL开发前端，后台根据开发文档的URL开发后台。

|后台文档技术选型 |说明 |
|:-----|:------|
| [swaggerEditor](https://swagger.io/tools/swagger-editor/)+[swaggerUI](https://swagger.io/tools/swagger-ui/)+[nginx](http://nginx.org/en/download.html) | 开源，但是代码入侵性太强 |
| [YAPI](http://yapi.demo.qunar.com/)(**推荐**) | 开源，图形化界面编写，简单易用 |
| [apiPost](https://www.apipost.cn/)、[apiDoc](http://apidocjs.com/)等第三方 | 网站付费、个人GitHub开源等 |

##

## 1.关于后台公共模块

### 1-1.全局的返回结果实体类

|实体类|说明|示意图|
|:------:|:------:|:------:|
|[Result](https://github.com/panchaopeng/pcp_parent/tree/master/pcp_common/src/main/java/entity/Result.java)|全局返回结果类|![Result](https://github.com/panchaopeng/pcp_parent/blob/master/img/common/1.common.result.png)|
|[PageResult](https://github.com/panchaopeng/pcp_parent/tree/master/pcp_common/src/main/java/entity/PageResult.java)|通用的分页结果类|![PageResult](https://github.com/panchaopeng/pcp_parent/blob/master/img/common/1.common.PageResult.png)|
|[StatusCode](https://github.com/panchaopeng/pcp_parent/tree/master/pcp_common/src/main/java/entity/StatusCode.java)|返回码常量类|![StatusCode](https://github.com/panchaopeng/pcp_parent/blob/master/img/common/1.common.StatusCode.png)|  

### 1-2.全部模块采用SpringMVC模式

> - SpringMVC模式的好处，分工明确  
> - Controller注入Service，Service注入Dao，Dao持久化PoJo  

|SpringMVC层级|说明|示意图|
|:------:|:----------:|:--------:|
|SpringMVC|结构图|![Result](https://github.com/panchaopeng/pcp_parent/blob/master/img/common/springMVC.png)||
|xxxController|控制层注入Service| ![Result](https://github.com/panchaopeng/pcp_parent/blob/master/img/common/1.common.Controller.png	)|
|xxxService|服务层注入Dao| ![Result](https://github.com/panchaopeng/pcp_parent/blob/master/img/common/1.common.Service.png)|
|xxxDao|dao使用JPA持久化PoJo| ![Result](https://github.com/panchaopeng/pcp_parent/blob/master/img/common/1.common.dao.png)|
|xxxPoJo|某实体类| ![Result](https://github.com/panchaopeng/pcp_parent/blob/master/img/common/1.common.pojo.png)|
|BaseExceptionHandler|模块的统一异常处理|![BaseExceptionHandler](https://github.com/panchaopeng/pcp_parent/blob/master/img/common/1.common.exception.png)|  

### 1-3.基于twitter的snowflake（雪花）算法-分布式ID生成器

> - 为什么要使用**分布式ID生成器**？为什么不使用数据库的本身的自增ID？
>> - 因为微服务大多数都触及分布式开发，需要确保整个分布式系统内不产生重复的ID
> - 分布式ID生成器**优点**:
>> - 生成ID时不依赖数据库，完全在内存生成，高性能高可用
>> - ID呈趋势递增,插入索引树的时候性能较好

#### snowflake
> - 首字节为0，无用。  
> - 41字节时间戳，能容纳69年
> - 10字节工作机器id，能容纳1024节点  
> - 12字节序列号，每毫秒每节点能自增4096个id  

|名称|说明|示意图|
|:---------:|:--------:|:--------:|
|snowflake（雪花）算法|java版分布式ID生成器每毫秒能生成26万多个id,能使用69年|![snowflake](https://github.com/panchaopeng/pcp_parent/blob/master/img/common/snowflake.png)|

##

## 2.Spring Data JPA

> - SpringDataJPA简化了实体类的CRUD操作。   

### 2-1.SpringDataJPA依赖与配置
```
	<!-- Spring Data JPA依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
	<!-- mysql依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.14</version>
        </dependency>
```

|application.yml配置|
|:----:|
|![JPAyml](https://github.com/panchaopeng/pcp_parent/blob/master/img/jpa/yml.png)|  

### 2-2.SpringDataJPA使用

|说明|示意图或备注|
|:-------:|:--------:|
|接口关系|![Result](https://github.com/panchaopeng/pcp_parent/blob/master/img/jpa/jpa.png)|
|复杂查询接口|JpaSpecificationExecutor|
|解析方法名创建查询|一般不好维护，建议用原生sql|
|原生SQL|![Result](https://github.com/panchaopeng/pcp_parent/blob/master/img/jpa/sql.png)|  

##

## 3.Spring Data Redis

> - RedisTemplate提供了redis各种操作。  

### 3-1.SpringDataRedis依赖与配置

```
	<!-- SpringDataRedis依赖 -->
    	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-redis</artifactId>
	</dependency>
	
```

|application.yml配置|
|:----:|
|![Redisyml](https://github.com/panchaopeng/pcp_parent/blob/master/img/redis/yml.png)|  

### 3-2.SpringDataRedis使用

|说明|示意图或备注|
|:-------:|:--------:|
|常用的使用方法|[SpringDataRedis](https://github.com/panchaopeng/pcp_parent/blob/master/doc笔记/3.SpringDataRedis使用说明.txt)|
|使用前注入RedisTemplate|![RedisTemplate](https://github.com/panchaopeng/pcp_parent/blob/master/img/redis/redisTemplate.png)|
|查询时缓存|![查询](https://github.com/panchaopeng/pcp_parent/blob/master/img/redis/use.png)|
|修改或删除后清除缓存|![update_delete](https://github.com/panchaopeng/pcp_parent/blob/master/img/redis/update_or_delete.png)|  

##  

## 4.SpringDataMongoDB
> - 操作MongoDB的持久层框架，封装了底层的mongodb-driver
> - 介于关系数据库和非关系数据库之间，非关系数据库当中最像关系数据库的产品
> - 适合 数据量大 写入操作频繁 价值较低 的评论功能场景  

|对比图|
|:--------:|
|![对比图](https://github.com/panchaopeng/pcp_parent/blob/master/img/mongodb/Mongodb.png)|  

### 4-1.SpringDataMongoDB依赖与配置

```
	<!-- SpringDataMongoDB依赖 -->
	<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring‐boot‐starter‐data‐mongodb</artifactId>
        </dependency>
```

|application.yml配置|
|:----:|
|![MongoDByml](https://github.com/panchaopeng/pcp_parent/blob/master/img/mongodb/yml.png)|  
  
### 4-2.SpringDataMongoDB使用

> - SpringDataMongoDB的*CRUD*使用与SpringDataJPA的*CRUD*使用几乎没有区别  

|区别|示意图|
|:------:|:------:|
|使用前注入MongoTemplate|![mongoTemplate](https://github.com/panchaopeng/pcp_parent/blob/master/img/mongodb/mongoTemplate.png)|
|MongoDB实体类中id(必须为_id)|![id](https://github.com/panchaopeng/pcp_parent/blob/master/img/mongodb/id.png)|
|MongoDB的DAO中继承MongoRepository|![MongoRepository](https://github.com/panchaopeng/pcp_parent/blob/master/img/mongodb/pojo.png)|  

##

## 5.SpringDataElasticsearch

> - 具体配置查看[2.配置容器.docx](https://github.com/panchaopeng/pcp_parent/tree/master/doc笔记/2.配置容器.docx)中的Elasticsearch内容
> - 实时的分布式搜索和分析引擎
> - 使用[logstash](https://www.elastic.co/downloads/logstash)完成mysql与Elasticsearch的同步工作
> - [Head插件](https://github.com/mobz/elasticsearch-head),图形化界面来实现Elasticsearch的日常管理
> - [IK分词器](https://github.com/medcl/elasticsearch-analysis-ik/releases),符合中文语法的简单分词器

|对比图|
|:--------:|
|![对比图](https://github.com/panchaopeng/pcp_parent/blob/master/img/elasticsearch/compare.png)|  

### 5-1.SpringDataElasticsearch依赖与配置

```
	<!-- 使用分布式搜索服务 -->
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-elasticsearch</artifactId>
            <version>3.1.5.RELEASE</version>
        </dependency>
```  

|application.yml配置|
|:----:|
|![Elasticsearchyml](https://github.com/panchaopeng/pcp_parent/blob/master/img/elasticsearch/yml.png)|  
  
### 5-2.SpringDataElasticsearch使用

> - SpringDataElasticsearch的*CRUD*使用与SpringDataJPA的*CRUD*使用几乎没有区别  

|区别|示意图|
|:------:|:------:|
|POJO类书写,具体查看文件[Article](https://github.com/panchaopeng/pcp_parent/blob/master/pcp_search/src/main/java/com/pcp/search/pojo/Article.java)|![entity.png](https://github.com/panchaopeng/pcp_parent/blob/master/img/elasticsearch/entity.png)|
|Elasticsearch的DAO类继承ElasticsearchRepository|![ElasticsearchRepository](https://github.com/panchaopeng/pcp_parent/blob/master/img/elasticsearch/dao.png)|  

### 5-3.SpringDataElasticsearch搜索例子

|SpringMVC层|示意图|
|:------:|:------:|
|DAO层|![DAO](https://github.com/panchaopeng/pcp_parent/blob/master/img/elasticsearch/dao.png)|
|Service层|![Service](https://github.com/panchaopeng/pcp_parent/blob/master/img/elasticsearch/search_service.png)|
|Controller层|![Controlle](https://github.com/panchaopeng/pcp_parent/blob/master/img/elasticsearch/search_controller.png)|  

##

## 6.RabbitMQ

> - 消息队列中间件 异步处理 
> - RabbitMQ有四种模式，常用三种：直接模式（Direct）、分列模式(Fanout)、主题模式（Topic）
> - RabbitMQ架构：
>> - PS：**使用RabbitMQ前先创建好队列**  
>> - RabbitMQ Server:一种传输服务。维护一条从Producer到Consumer的路线，保证数据按照指定方式进行传输
>> - A B C ：消息生产者-Producer
>> - Exchange-交换器，将消息路由到一个或多个Queue中（或者丢弃）
>> - RoutingKey-指定消息的路由规则,使消息流向某个/多个队列
>> - 1 2 3 :Consumer-消息消费者  


|RabbitMQ架构图|
|:------:|
|![RabbitMQ](https://github.com/panchaopeng/pcp_parent/blob/master/img/RabbitMQ/mq_server.png)|  

### 6-1.RabbitMQ依赖与配置


```
        <!-- RabbitMQ -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
```  

|application.yml配置|
|:----:|
|![RabbitMQ](https://github.com/panchaopeng/pcp_parent/blob/master/img/RabbitMQ/yml.png)|  


### 6-2.RabbitMQ-直接模式(Direct)

> - 具体查看文件：[消息生产者](https://github.com/panchaopeng/pcp_parent/blob/master/pcp_rabbitmq/src/test/java/com/pcp/test/ProducerTest.java),[消息消费者](https://github.com/panchaopeng/pcp_parent/tree/master/pcp_rabbitmq/src/main/java/com/pcp/rabbit/com/pcp/rabbit/customer)  
> - 直接模式（Direct）：将消息发给唯一一个节点时使用这种模式
>> - 自带的Exchange：”"(该Exchange的名字为空字符串)
>> - 需要一个“RouteKey”，可以简单的理解为要发送到的队列名字
>> - 如果vhost中不存在RouteKey中指定的队列名，则该消息会被抛弃  

|说明|示意图|
|:----:|:----:|
|消息生产者|![消息生产者](https://github.com/panchaopeng/pcp_parent/blob/master/img/RabbitMQ/direct_producer.png)|
|消息消费者|![消息消费者](https://github.com/panchaopeng/pcp_parent/blob/master/img/RabbitMQ/direct_consumer.png)|  


### 6-3.RabbitMQ-分列模式(Fanout)

> - PS:此时已经新建路由ex_it，并绑定了itcast2和itcast3两个队列  
> - 分列模式(Fanout)
>> - 将消息一次发给多个队列
>> - 这种模式不需要RouteKey
>> - 需要提前将Exchange与Queue进行绑定
>> - 如果接受到消息的Exchange没有与任何Queue绑定，则消息会被抛弃


|说明|示意图|
|:----:|:----:|
|消息生产者|![消息生产者](https://github.com/panchaopeng/pcp_parent/blob/master/img/RabbitMQ/fanout_pro.png)|
|消息消费者|![消息消费者](https://github.com/panchaopeng/pcp_parent/blob/master/img/RabbitMQ/fanout_con1.png)|
|消息消费者|![消息消费者](https://github.com/panchaopeng/pcp_parent/blob/master/img/RabbitMQ/fanout_con2.png)|  


### 6-4.RabbitMQ-主题模式(Topic)

> - 主题模式(Topic)
>> - 任何发送到Topic Exchange的消息都会被转发到所有关心RouteKey中指定话题的Queue上
>> - 就是模糊匹配的意思。使用前指定binding key=usa.#/#.log.#等。然后使用时模糊匹配routingKey.
>>> - #代表匹配任意字符
>>> - \*(星号转义，不然github代表粗体)代表一个字符
>> - 这种模式需要RouteKey，也许要提前绑定Exchange与Queue
>> - 如果Exchange没有发现能够与RouteKey匹配的Queue，则会抛弃此消息

|说明|示意图|
|:----:|:----:|
|队列创建与匹配|![队列创建与匹配](https://github.com/panchaopeng/pcp_parent/blob/master/img/RabbitMQ/topic_exchange.png)|
|消息生产者|![消息生产者](https://github.com/panchaopeng/pcp_parent/blob/master/img/RabbitMQ/topic_con1.png)|
|消息消费者|![消息消费者](https://github.com/panchaopeng/pcp_parent/blob/master/img/RabbitMQ/topic_con2.png)|
|消息消费者|![消息消费者](https://github.com/panchaopeng/pcp_parent/blob/master/img/RabbitMQ/topic_con3.png)|  

##

## 7.spring security

> - BCrypt强哈希方法 每次加密的结果都不一样
> - BCrypt密码加密,BCryptPasswordEncodert强哈希方法来加密密码

### 7-1.spring security依赖与配置

> - 添加了spring security依赖后，所有的地址都被spring security所控制了
> - 所以要添加一个配置类，配置为所有地址都可以匿名访问

```
          <!-- 盐加密 -->
	  <dependency>
		  <groupId>org.springframework.boot</groupId>
		  <artifactId>spring-boot-starter-security</artifactId>
	  </dependency>
```  

|配置|示意图|
|:----:|:--------:|
|application.yml配置|无，可当作密码工具类使用|
|security安全配置类|[WebSecurityConfig](https://github.com/panchaopeng/pcp_parent/blob/master/pcp_user/src/main/java/com/pcp/user/config/WebSecurityConfig.java)|
|在Application中,配置bean|![PasswordEncoder](https://github.com/panchaopeng/pcp_parent/blob/master/img/Security/password.png)|  


### 7-1.spring security密码加密与校验

|encode加密步骤|示意图|
|:----:|:--------:|
|1.Service中注入BCryptPasswordEncoder|![encode](https://github.com/panchaopeng/pcp_parent/blob/master/img/Security/PasswordEncoder.png)|
|2.调用encode方法|![encode](https://github.com/panchaopeng/pcp_parent/blob/master/img/Security/encode.png)|  


|matches解密步骤|示意图|
|:----:|:--------:|
|1.DAO中查找该用户信息(加密后的密码)|![encode](https://github.com/panchaopeng/pcp_parent/blob/master/img/Security/findPassword.png)|
|2.Service中进行密码校验|![encode](https://github.com/panchaopeng/pcp_parent/blob/master/img/Security/matches.png)|
|3.Controller调用|![login](https://github.com/panchaopeng/pcp_parent/blob/master/img/Security/login.png)|  


## 

### 8.JWT的Token认证

> - JSON Web Token（JWT）是一个非常轻巧的规范。这个规范允许我们使用JWT在用户和服务器之间传递安全可靠的信息。
> - JWT组成：头部（Header）、载荷（playload） 、签证（signature）
>> - 头部（Header）:描述该JWT的基本信息,例如其类型以及签名所用的算法等
>>> - 比如：{"type":"JWT","alg":"HS256"},在头部指明了签名算法是HS256算法
>> - 载荷（playload）:存放有效信息的地方,这些有效信息包含三个部分
>>> - （1）标准中注册的声明（建议但不强制使用）
>>>> - iss: jwt签发者 
>>>> - sub: jwt所面向的用户 
>>>> - aud: 接收jwt的一方 
>>>> - exp: jwt的过期时间，这个过期时间必须要大于签发时间 
>>>> - nbf: 定义在什么时间之前，该jwt都是不可用的. 
>>>> - iat: jwt的签发时间 
>>>> - jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
>>> - （2）公共的声明
>>>> - 公共的声明可以添加任何的信息,不建议添加敏感信息，因为该部分在客户端可解密
>>> - （3）私有的声明
>>> - 私有声明是提供者和消费者所共同定义的声明.因为base64是对称解密的，意味着该部分信息可以归类为明文信息。
>>> - 这个指的就是自定义的claim,比如下面的name admin
>> - 定义一个payload:{"sub":"1234567890","name":"John Doe","admin":true}
>>> - 签证（signature）:由三部分组成
>>>> - header (base64后的)
>>>> - payload (base64后的)
>>>> - secret :服务端的私钥,jwt的签发和jwt的验证

### 8-1.Java的JJWT实现JWT

> - jjwt的依赖与配置
> - 在pcp_common公共模块编写工具类[JwtUtil](https://github.com/panchaopeng/pcp_parent/blob/master/pcp_common/src/main/java/util/JwtUtil.java)
>> - createJWT:Token创建
>> - parseJWT: Token解析

···
	<!-- 创建Token -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>
···

### 8-2.登录时签发Token

|使用步骤|示意图|
|:----:|:--------:|
|1.在涉及Token的微服务的yml加配置)|![yml](https://github.com/panchaopeng/pcp_parent/blob/master/img/jwt/yml.png)|
|2.Application/Service中注入JwtUtil|![JwtUtil](https://github.com/panchaopeng/pcp_parent/blob/master/img/jwt/JwtUtil.png)|
|3.登录，也即login方法签发Token|![jwt_login](https://github.com/panchaopeng/pcp_parent/blob/master/img/jwt/jwt_login.png)|  

### 8-3.客户端请求时解析Token

> - 意味着我们需要拦截请求，并解析请求中携带的Token

|拦截请求|示意图|
|:----:|:--------:|
|1.添加jwt拦截器)|[JWTInterceptor(仅供参考)](https://github.com/panchaopeng/pcp_parent/blob/master/pcp_user/src/main/java/com/pcp/user/interceptor/JWTInterceptor.java)|
|2.注册jwt拦截器|[JWTInterceptorConfig](https://github.com/panchaopeng/pcp_parent/blob/master/pcp_user/src/main/java/com/pcp/user/config/JWTInterceptorConfig.java)|
|3.Service注入HttpServletRequest,拿到Token|![HttpServletRequest](https://github.com/panchaopeng/pcp_parent/blob/master/img/jwt/HttpServletRequest.png)|
|4.Service注入JwtUtil,解析Token信息|![parse](https://github.com/panchaopeng/pcp_parent/blob/master/img/jwt/parse.png)|  

##

## 9.Spring Cloud系列

```
<!-- 父工程中引入Spring Cloud -->
<!-- dependencyManagement 表示锁定该版本。与Spring boot2.x对应 -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```


> - Spring Boot专注于快速、方便集成的单个微服务个体
> - Spring Cloud关注全局的服务治理框架

### 9-1.Eureka(服务发现组件)

> - Eureka包含两个组件：Eureka Server和Eureka Client
>> - Eureka Server提供服务注册服务
>> - Eureka Client是一个java客户端，用于简化与Eureka Server的交互

#### 9-1-1.Eureka Server

```
    <!-- Eureka服务器。提供服务注册 -->
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
    </dependencies>
```
|说明|示意图|
|:----:|:----:|
|yml配置|![yml](https://github.com/panchaopeng/pcp_parent/blob/master/img/eureka/yml.png)|
|启动类配置|![EnableEurekaServer](https://github.com/panchaopeng/pcp_parent/blob/master/img/eureka/EnableEurekaServer.png)|  

#### 9-1-2.Eureka Client

```
          <!-- Eureka客户端 -->
	  <dependency>
		  <groupId>org.springframework.cloud</groupId>
		  <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
	  </dependency>
```

|说明|示意图|
|:----:|:----:|
|yml配置|![yml](https://github.com/panchaopeng/pcp_parent/blob/master/img/eureka/yml_client.png)|
|启动类配置|![EnableEurekaServer](https://github.com/panchaopeng/pcp_parent/blob/master/img/eureka/EnableEurekaClient.png)|  


### 9-2.Feign(实现服务间的调用)

> - 为什么需要Feign?因为服务间通常需要交互，而每个微服务的端口号又都不一致，因此需要Feign通过yml的application name进行调用

```
	<!-- feign,模块间的调用，也即服务调用 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
```

> - @FeignClient注解用于指定从哪个服务中调用功能,不能包含下划线
> -  @PathVariable注解一定要指定参数名称，否则出错

|说明|示意图|
|:----:|:----:|
|yml配置|无|
|启动类配置|![feign](https://github.com/panchaopeng/pcp_parent/blob/master/img/eureka/feign.png)|
|client包下写接口(该接口必须与某个微服务的Controller方法一致)|![client](https://github.com/panchaopeng/pcp_parent/blob/master/img/eureka/client.png)|
|Controller中注入client并调用接口方法|![baseClient](https://github.com/panchaopeng/pcp_parent/blob/master/img/eureka/baseClient.png)|  


### 9-3.Hystrix(熔断器)

> - 基础服务的故障可能会导致**级联故障**，进而造成整个系统不可用的情况，这种现象被称为服务雪崩效应
> - Hystrix 能使你的系统在出现**依赖服务失效**的时候，通过**隔离系统所依赖的服务**，防止服务级联失败，同时提供失败回退机制

|说明|示意图|
|:----:|:----:|
|yml配置|![Hystrix](https://github.com/panchaopeng/pcp_parent/blob/master/img/eureka/yml_hystrix.png)|
|client下新建impl,创建熔断实现类|![impl](https://github.com/panchaopeng/pcp_parent/blob/master/img/eureka/Hystrix.png)|
|修改xClient的@FeignClient注解，添加fallback|![fallback](https://github.com/panchaopeng/pcp_parent/blob/master/img/eureka/fallback.png)|   

##

## 9-4.Zuul(微服务网关)

> - 微服务网关是介于客户端和服务器端之间的中间层，所有的外部请求都会先经过微服务网关
> - Zuul组件的核心是一系列的过滤器
> - 为什么需要微服务网关？
>> - 不同的微服务一般有不同的网络地址，客户端可能需要调用多个服务的接口才能完成一个业务需求...
>> - 身份认证和安全: 识别每一个资源的验证要求，并拒绝那些不符的请求

### 9-4-1.前后台微服务网关

> - 后台微服务网关，对应**后台ZuulFilter过滤器：ManagerFilter**,主要是判断管理员角色，然后
> - 前台微服务网关，对应**前台ZuulFilter过滤器：WebFilter**，主要是判断携带的

```
 	<!-- Eureka客户端 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- zuul 网关 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>
	<!-- 通过zuul调用eureka注册的服务（可能不用这个，也可能需要） -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
            <version>1.4.6.RELEASE</version>
        </dependency>
```

|说明|示意图|
|:----:|:----:|
|yml配置|![man_yml](https://github.com/panchaopeng/pcp_parent/blob/master/img/zuul/man_yml.png)|
|启动类配置|![man_application](https://github.com/panchaopeng/pcp_parent/blob/master/img/zuul/man_application.png)|
|后台ZuulFilter过滤器：ManagerFilter|[ManagerFilter(仅供参考)](https://github.com/panchaopeng/pcp_parent/blob/master/pcp_manager/src/main/java/com/pcp/manager/filter/ManagerFilter.java)|
|前台ZuulFilter过滤器：WebFilter|[WebFilter(仅供参考)](https://github.com/panchaopeng/pcp_parent/blob/master/pcp_web/src/main/java/com/pcp/web/filter/WebFilter.java)|  

## 

## 9-5.SpringCloudConfig(分布式配置中心)与SpringCloudBus(消息总线组件)

> - SpringCloudBus，不重启微服务的情况下更新配置(拉取码云上SpringCloudConfig的yml文件)
> - 使用Git仓库存放yml配置文件(比如[**码云**](https://gitee.com/))
> - 两个角色：
>> - config server:默认使用Git存储配置文件内容，也可以使用SVN存储，或者是本地文件存储
>> - config client:微服务在启动时会请求Config Server获取配置文件的内容，请求到后再启动容器

### 9-5-1.config server依赖与配置

> - 1.注册码云，并创建项目
> - 2.将所有微服务的yml文件上传到码云仓库,并赋值git地址备用
> - 3.所有的yml文件有一套命名规则，必须遵循：
>> - {application}-{profile}.yml或{application}-{profile}.properties
>> - application为应用名称 profile指的开发环境（用于区分开发环境，测试环境、生产环境等）

```
	<!-- config server依赖 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring‐cloud‐config‐server</artifactId>
        </dependency>
	
	<!-- SpringCloudBus的server依赖，自动更新配置文件 -->
	<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring‐cloud‐bus</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring‐cloud‐stream‐binder‐rabbit</artifactId>
        </dependency>
```

|说明|示意图|
|:-----:|:------:|
|配置启动类|![Application](https://github.com/panchaopeng/pcp_parent/blob/master/img/config/Application.png)|
|码云上的yml配置|![yml](https://github.com/panchaopeng/pcp_parent/blob/master/img/config/yml.png)|
|SpringCloudBus追加在码云的yml配置|![bus](https://github.com/panchaopeng/pcp_parent/blob/master/img/config/bus.png)|  


### 9-5-2.config client依赖与配置

```
	<!-- config client依赖 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring‐cloud‐starter‐config</artifactId>
        </dependency>
	
	<!-- SpringCloudBus的client依赖 -->
	<dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring‐cloud‐bus</artifactId>
      </dependency>
      <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring‐cloud‐stream‐binder‐rabbit</artifactId>
      </dependency>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring‐boot‐starter‐actuator</artifactId>
      </dependency>
```

|说明|示意图|
|:-----:|:------:|
|码云上yml配置|![yml](https://github.com/panchaopeng/pcp_parent/blob/master/img/config/client.png)|
|SpringCloudBus追加在码云的yml配置：rabbitMQ|![rabbitMQ](https://github.com/panchaopeng/pcp_parent/blob/master/img/config/rabbitMQ.png)|  


### 9-5-3.自定义配置的读取

> - config配置了bus后，读取yml的配置还需要加注解@RefreshScope，此注解用于刷新配置

|@RefreshScope,刷新配置|
|:---------:|
|![RefreshScope](https://github.com/panchaopeng/pcp_parent/blob/master/img/config/RefreshScope.png)|  


##


## 10.Docker私有仓库搭建与配置


|说明|备注|
|:-----:|:------:|
|拉取私有仓库镜像|docker pull registry|
|启动私有仓库容器|docker run ‐di ‐‐name=registry ‐p 5000:5000 registry|
|浏览|http://ip:5000/v2/\_catalog|
|让docker信任私有仓库地址|vi /etc/docker/daemon.json,保存并退出:{"insecure‐registries":["ip:5000"]}|
|重启docker 服务|systemctl restart docker|  


##

## 11.如何将微服务自动部署并打包成镜像？

> - 利用DockerMaven插件生成镜像并上传镜像到Docker私有仓库

|说明|备注|
|:-----:|:------:|
|修改宿主机的docker配置，让其可以远程访问|vi /lib/systemd/system/docker.service，其中ExecStart=后添加配置 ‐H tcp://0.0.0.0:2375 ‐H unix:///var/run/docker.sock|
|刷新配置，重启服务|systemctl daemon‐reload、systemctl restart docker、docker start registry|  

> - 以上配置会自动生成Dockerfile
>> - FROM jdk8
>> - ADD app.jar /
>> - ENTRYPOINT ["java","‐jar","/app.jar"]

```
<!-- MavenPlugin 使微服务生成镜像并上传到私有仓库 -->
    <build>
        <finalName>app</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <!-- docker的maven插件，官网：
            https://github.com/spotify/docker-maven-plugin -->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.4.13</version>
                <configuration>
		    <!-- docker私有仓库地址 -->
                    <imageName>192.168.200.129:5000/${project.artifactId}:${project.version}</imageName>
		    <!-- 打包前必须要存在基础镜像：jdk8镜像，因为微服务都是依赖jdk的 -->
                    <baseImage>jdk8</baseImage>
                    <entryPoint>["java","‐jar","/${project.build.finalName}.jar"]</entryPoint>
                    <resources>                   
                        <resource>                        
                            <targetPath>/</targetPath>             
                            <directory>${project.build.directory}</directory>                    
                            <include>${project.build.finalName}.jar</include>
                        </resource>             
                    </resources>
                    <dockerHost>http://192.168.200.129:2375</dockerHost>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

> - 最后，进入要生成镜像的微服务，在Terminal输入命令：mvn clean package docker:build  ‐DpushImage
> - 上面的打包方式还是太繁琐了，因为还要手动敲命令，下面会讲解持续集成来生成镜像



##

## 12.持续集成

> - 自动化的周期性的集成测试过程

### 12-1.Gogs安装与配置

> - 搭建自助 Git 服务
> - 目的是将idea代码上传到Gogs git

|说明|备注|
|:-----:|:------:|
|下载镜像|docker pull gogs/gogs|
|创建容器|docker run ‐d ‐‐name=gogs ‐p 10022:22 ‐p 3000:3000 ‐v /var/gogsdata:/data gogs/gogs|
|浏览|http://ip:3000|  


### 12-2.Jenkins(实现持续集成)

> - 以图形化界面连接Gogs的git代码，并将某个微服务生成镜像并上传到docker私有仓库
> - 必须保证系统有jdk

|说明|备注|
|:-----:|:------:|
|下载jenkins|wget https://pkg.jenkins.io/redhat/jenkins‐2.83‐1.1.noarch.rpm|
|配置jenkins|vi /etc/sysconfig/jenkins|
|修改用户和端口|JENKINS_USER="root"、JENKINS_PORT="8888"|
|启动服务|systemctl start jenkins|
|访问链接|http://ip:8888|
|进入界面-系统管理-管理插件-可选插件|安装Maven Integration和Git插件|
|进入界面-系统管理-全局工具配置|安装Maven与本地仓库(将开发时的Maven仓库传上来)以及JDK|
|这样就可以构建Maven项目了||
|1.回到首页，点击新建按钮|![newMaven](https://github.com/panchaopeng/pcp_parent/blob/master/img/jenkins/newMaven.png)|
|2.源码管理，选择Git|![git](https://github.com/panchaopeng/pcp_parent/blob/master/img/jenkins/git.png)|
|3.Build|![newMaven](https://github.com/panchaopeng/pcp_parent/blob/master/img/jenkins/newMaven.png)|
|PS:|clean package docker:build ‐DpushImage 用于清除、打包，构建docker镜像,最后保存|
|4.返回首页，创建的任务|点击右边的绿色箭头按钮，即可执行此任务|
|5.查看docker私有仓库|http://ip:5000/v2/\_catalog,发现该微服务已经生成了镜像了|   


## 

## 13.容器管理与容器监控

> - 14节我们已经在图形化界面生成了镜像，同理，我们也可以在图形化界面启动容器并管理
> - Rancher:管理容器的start/stop/delete
> - influxDB:存储cAdvisor采集的容器数据
> - cAdvisor：采集容器数据，将其存储到influxDB
> - Grafana：以图形化方式查看容器数据


### 13-1.Rancher(容器管理工具)

> - 容器部署及管理平台,一键式应用部署和管理

|说明|备注|
|:-----:|:------:|
|下载Rancher 镜像|docker pull rancher/server|
|创建Rancher容器|docker run ‐d ‐‐name=rancher ‐‐restart=always ‐p 9090:8080 rancher/server|
|访问链接|http://ip:9090|
|左上角的Default -->环境管理|创建各种环境:开发、测试、生产|
|基础架构-镜像库|custom-地址：填写ip|
|基础架构-主机|添加主机-拷贝脚本-并在服务器上运行该脚本|
|选择创建的环境-添加应用|也即以图形化的界面配置镜像并启动容器了|  


### 13-2.微服务扩容与缩容

|说明|备注|
|:-----:|:------:|
|1.创建微服务的时候，不设置端口映射||
|2.回到Rancher界面,API-WebHooks-添加接收器|填写名称、类型(扩/缩容)、目标服务、步长。|
|3.复制触发地址，备用||  


### 13-3.influxDB(分布式时间序列数据库)

> - 存储cAdvisor组件所提供的监控信息

|说明|备注|
|:-----:|:------:|
|1.下载镜像|docker pull tutum/influxdb|
|2.创建容器|docker run -di -p 8083:8083 ‐p 8086:8086 --expose 8090 --expose 8099 --name=influxsrv tutum/influxdb|
|PS：|端口概述：8083端口:web访问端口、8086:数据写入端口|
|访问|http://ip:8083/|  

|常用操作,右上角有快捷选项|备注|
|:-----:|:------:|
|1.创建数据库|CREATE DATABASE "cadvisor" /  SHOW DATABASES|
|2.创建用户并授权|CREATE USER "cadvisor" WITH PASSWORD 'cadvisor' WITH ALL PRIVILEGES / SHOW USRES|
|3.用户授权|grant all privileges on cadvisor to cadvisor 、grant WRITE on cadvisor to cadvisor 、grant READ on cadvisor to cadvisor|
|4.查看采集的数据|切换到cadvisor数据库,SHOW MEASUREMENTS.由于没有安装cAdvisor，所以看不到数据|  


### 13-4.cAdvisor(监控Docker容器)

> - 采集Docker容器的数据，尤其是使用内存

|说明|备注|
|:-----:|:------:|
|1.下载镜像|docker pull google/cadvisor|
|2.创建容器|docker run ‐‐volume=/:/rootfs:ro ‐‐volume=/var/run:/var/run:rw ‐‐volume=/sys:/sys:ro ‐-volume=/var/lib/docker/:/var/lib/docker:ro ‐‐publish=8080:8080 ‐‐detach=true ‐‐link influxsrv:influxsrv ‐‐name=cadvisor google/cadvisor ‐storage_driver=influxdb ‐storage_driver_db=cadvisor ‐storage_driver_host=influxsrv:8086|
|PS：|influxsrv是influxdb容器，cadvisor是influxsrv容器创建的数据库|
|访问|http://ip:8080/containers/|  


### 13-5.Grafana(可视化面板,查看容器参数)

> - 以图形化的方式查看cAdvisor采集到的容器数据

|说明|备注|
|:-----:|:------:|
|1.下载镜像|docker pull google/cadvisor|
|2.创建容器|docker run ‐d ‐p 3001:3000  ‐e INFLUXDB_HOST=influxsrv ‐e INFLUXDB_PORT=8086 ‐e INFLUXDB_NAME=cadvisor ‐e INFLUXDB_USER=cadvisor ‐e INFLUXDB_PASS=cadvisor ‐‐link influxsrv:influxsrv ‐‐name grafana grafana/grafana|
|PS：|influxsrv是influxdb容器，cadvisor是influxsrv容器创建的数据库,还有同名的user/pass|
|访问|http://ip:3001/containers/|
|使用||
|1.添加数据源,点击设置，Data Sources-Add data source|![source](https://github.com/panchaopeng/pcp_parent/blob/master/img/graph/source.png)![detail](https://github.com/panchaopeng/pcp_parent/blob/master/img/graph/detail.png)|
|2.添加仪表盘，Manage-Dashboadr-Add-Graph-Panel Title-Edit,查询内存，选择容器名称，保存|![general](https://github.com/panchaopeng/pcp_parent/blob/master/img/graph/general.png)![metrics](https://github.com/panchaopeng/pcp_parent/blob/master/img/graph/metrics.png)|  


### 13-6.预警通知设置
|说明|备注|
|:-----:|:------:|
|1.alerting-Notification channels-Add channel|![notification](https://github.com/panchaopeng/pcp_parent/blob/master/img/graph/notification.png)|
|PS:|url地址是[15-2.微服务扩容与缩容]所备用的扩/缩容地址|
|2.仪表盘预警设置,也即多少M后触发扩/缩容|![alert](https://github.com/panchaopeng/pcp_parent/blob/master/img/graph/alert.png)![notify](https://github.com/panchaopeng/pcp_parent/blob/master/img/graph/notify.png)|  

##



















