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

> - SpringDataMongoDB的使用与SpringDataJPA的使用几乎没有区别  

|区别|示意图|
|:------:|:------:|
|MongoDB实体类中id(必须为_id)|![MongoDByml](https://github.com/panchaopeng/pcp_parent/blob/master/img/mongodb/id.png)|
|MongoDB的POJO中继承MongoRepository|![MongoDByml](https://github.com/panchaopeng/pcp_parent/blob/master/img/mongodb/pojo.png)|





























