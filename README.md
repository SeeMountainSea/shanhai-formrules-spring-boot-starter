<div align="center">
  <p>
    <img src="logo.jpg"  height="200px" />
  </p>
  <p>山海FormRules- 基于SpringBoot 的通用请求参数校验组件</p>
  <p>ShanHaiFormRules-based SpringBoot Parameter Rules Check Component</p>
  <p>
    <a href="https://github.com/SeeMountainSea/shanhai-formrules-spring-boot-starter
/releases/latest"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SeeMountainSea/shanhai-formrules-spring-boot-starter"/></a>
    <a href="https://github.com/SeeMountainSea/shanhai-formrules-spring-boot-starter
/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SeeMountainSea/shanhai-formrules-spring-boot-starter?color=009688"/></a>
    <a href="https://github.com/topics/java"><img alt="GitHub top language" src="https://img.shields.io/github/languages/top/SeeMountainSea/shanhai-formrules-spring-boot-starter?color=eb8031"/></a>
    <br>
    <a href="https://github.com/SeeMountainSea/shanhai-formrules-spring-boot-starter
/find/master"><img alt="GitHub Code Size" src="https://img.shields.io/github/languages/code-size/SeeMountainSea/shanhai-formrules-spring-boot-starter?color=795548"/></a>
    <a href="https://github.com/SeeMountainSea/shanhai-formrules-spring-boot-starter
/find/master"><img alt="GitHub Code Lines" src="https://img.shields.io/tokei/lines/github/SeeMountainSea/shanhai-formrules-spring-boot-starter?color=37474F"/></a>
    <a href="https://github.com/SeeMountainSea/shanhai-formrules-spring-boot-starter
r/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SeeMountainSea/shanhai-formrules-spring-boot-starter?color=534BAE"/></a>
  </p>
</div>

ShanHiFormRules 主要提供以下能力：

- 支持@RequestFormRules注解方式执行参数检验
- 其他特性正在逐步更新

# 1.引入依赖

```xml
      <dependency>
            <groupId>com.wangshanhai.formrules</groupId>
            <artifactId>shanhai-formrules-spring-boot-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
```

# 2.启用ShanHiFormRules 组件

使用注解@EnableShanHiFormRules即可启用ShanHiFormRules 组件

```java
@Configuration
@EnableShanHiFormRules
public class AppConfig {
}

```

# 3.@ShanHiFormRules 详解

使用@RequestFormRules即可进行参数校验规则编写，相关定义如下：

```java
public @interface RequestFormRules {
    /**
     * 校验对象
     * @return
     */
   String [] target();
    /**
     * 规则清单(多个使用;分割)
     * @return
     */
    String rule() default "" ;
    /**
     * 是否启用
     * @return
     */
   boolean enable() default true;
}
```



使用方式样例如下：

```java

    @RequestMapping("/restRules")
    @RequestFormRules(target = {"rulesForm"},rule="shf.notEmpty(rulesForm,'1001','参数不能为空！');")
    public String restRules(@RequestBody RulesForm rulesForm){
       return "success";
    }
```



# 4.内置校验函数说明

待更新

# 5.路线图

- 适配常用规则
- 规则新增文件配置支持
- 规则新增配置中心支持
- 规则自定义事件处理支持
- ……