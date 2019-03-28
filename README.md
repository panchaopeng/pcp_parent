# IDEA后台微服务架构:

- ### 基于SpringBoot+SpringCloud+SpringMVC+SpringDataJPA的微服务Demo说明  
  > - 开发环境：IDEA、jdk 1.8、mysql 5.7、maven、CentOS 7、docker、Postman
  > - 如源码所示，每个文件夹都是一个微服务

##

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


























