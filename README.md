# IDEA后台微服务架构:

- ### 基于SpringBoot+SpringCloud+SpringMVC+SpringDataJPA的微服务Demo说明  
  > - 开发环境：IDEA、jdk 1.8、mysql 5.7、maven、CentOS 7、docker、Postman
  > - 如源码所示，每个文件夹都是一个微服务
  
## 0.准备后台开发文档  

> - 前后端分离开发前，需要准备好 **后台开发文档**。  
> - 前端根据开发文档的URL开发前端，后台根据开发文档的URL开发后台。

|后台文档技术选型 |说明 |
|:-----|:------|
| [swaggerEditor](https://swagger.io/tools/swagger-editor/)+[swaggerUI](https://swagger.io/tools/swagger-ui/)+[nginx](http://nginx.org/en/download.html) | 开源，但是代码入侵性太强 |
| [YAPI](http://yapi.demo.qunar.com/)(**推荐**) | 开源，图形化界面编写，简单易用 |
| [apiPost](https://www.apipost.cn/)、[apiDoc](http://apidocjs.com/)等第三方 | 网站付费、个人GitHub开源等 |

## 1.关于后台公共模块

### 1-1. 全局的返回结果实体类
|实体类|作用|示意图|
|:------:|:------:|:------:|
|[Result](https://github.com/panchaopeng/pcp_parent/tree/master/pcp_common/src/main/java/entity/Result)|123||
|[PageResult](https://github.com/panchaopeng/pcp_parent/tree/master/pcp_common/src/main/java/entity/PageResult)|||
|[StatusCode](https://github.com/panchaopeng/pcp_parent/tree/master/pcp_common/src/main/java/entity/StatusCode)|||



### 1-2. 全部模块采用SpringMVC模式
### 1-3. RESTful风格的CRUD操作与模块的统一异常处理
### 1-4. 基于twitter的snowflake（雪花）算法-分布式ID生成器







