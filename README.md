<div align="center">
  <p>
    <img src="logo.jpg"  height="200px" />
  </p>
  <p>山海FormRules- 基于SpringBoot 的参数校验组件（告别HibernateValidator，享受私家定制）</p>
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

- 支持注解方式执行参数检验（类似ElementUI-validator风格）。

- 提供较为常用的规则实现，同时提供了自定义规则扩展能力，

  告别Hibernate validator 分组方式无法高度定制多API共享入参的情况。

- 更多规则可以自己提PR

# 1.引入依赖

```xml
      <dependency>
            <groupId>com.wangshanhai.formrules</groupId>
            <artifactId>shanhai-formrules-spring-boot-starter</artifactId>
            <version>1.0.1</version>
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

# 3.校验组件详细说明

| 注解         | 用途                       | 备注                                                         |
| ------------ | -------------------------- | ------------------------------------------------------------ |
| @ShanHaiForm | 标记该方法需要进行参数校验 |                                                              |
| @FormRule    | 标记需要校验的对象         | target：对象名称，即方法中的变量名 <br/>rules:规则集合<br/>enable: 是否启用该规则（默认启用） |
| @Rule        | 规则定义                   | ruleType 支持：<br/> 非空校验  : RuleCollect.NOT_EMPTY <br/> 正则校验: RuleCollect.REG_EXP; <br/> 字符串长度校验: RuleCollect.STR_LENTH  <br/> 枚举校验: RuleCollect.ENUM; |



## 3.1 非空校验（RuleCollect.NOT_EMPTY）

```java
   @ShanHaiForm({
            @FormRule(target = "rulesForm",rules = {
                    @Rule(ruleType = RuleCollect.NOT_EMPTY,scanFields = "*",errorCode = 1001,message = "参数不能为空！")
            })
    })
    public String restRules(@RequestBody RulesForm rulesForm) {
        return "success";
    }
```

scanFields ：支持* 或者某个字段或者嵌套对象的字段，例如（form.zhangsan）。嵌套对象必须有明确的类型定义，否则无法支持嵌套对象的输入校验。

errorCode：异常编码

message：异常提示

对于存在异常的，会抛出如下异常：

```java
throw new HttpFormRulesException(rule.errorCode(),field+":"+rule.message())
```

自己可以自行进行解析和输出。

## 3.2 正则校验（RuleCollect.REG_EXP）

```java
   @ShanHaiForm({
            @FormRule(target = "rulesForm",rules = {
                    @Rule(ruleType = RuleCollect.REG_EXP,scanFields = "projectType", regExp = RegExpCollect.NUMBER,errorCode = 1002,message = "不符合格式要求！"
            })
    })
    public String restRules(@RequestBody RulesForm rulesForm) {
        return "success";
    }
```

regExp: 正则表达式，可以使用内置的一些常用正则，例如：RegExpCollect.NUMBER，也可以自己写符合自己需要的正则表达式。

## 3.3 字符串长度校验（RuleCollect.STR_LENTH）

```java
   @ShanHaiForm({
            @FormRule(target = "rulesForm",rules = {
                    @Rule(ruleType = RuleCollect.STR_LENTH,scanFields = "projectType", min = 1,max = 30,errorCode = 1002,message = "不符合长度要求！")
            })
    })
    public String restRules(@RequestBody RulesForm rulesForm) {
        return "success";
    }
```

min: 字符串最小长度

max:字符串最大长度

len: 字符串固定长度

## 3.4 枚举校验（RuleCollect.ENUM）

```java
     @ShanHaiForm({
            @FormRule(target = "rulesForm",rules = {
                    @Rule(ruleType = RuleCollect.ENUM,scanFields = "projectType", enums = {"张三","李四"},errorCode = 1002,message = "不符合格式要求！")
            })
    })
    public String restRules(@RequestBody RulesForm rulesForm) {
        return "success";
    }
```

enums：枚举白名单

## 3.5 扩展自己的校验方法

通过实现RuleScanService，并注册自己的规则到Spring容器中，即可实现自定义校验规则。

```
@Service
public class XXXRule implements RuleScanService{
    /**
     * 注册规则定义
     * @return
     */
    @Override
    public String getScanType() {
        //TODO 注册自己的校验类型（可参考RuleCollect）
        return XXXCollect.XXX; 
    }
    /**
     *  扫描目标对象
     * @param target 目标对象实例
     * @param scanFields 目标对象扫描字段 
     * @param execTarget 目标方法信息（跟踪日志使用）
     * @param rule 规则定义
     */
    @Override
    public void scanByScope(Object target, List<String> scanFields, String execTarget, Rule rule) {
       //TODO 实现自己的校验方法
       //当不符合预期时，使用throw new HttpFormRulesException()方式抛出异常
    }
}
```

## 3.6    @Rule 参数scanFields详解

| scanFields字段范围          | 支持情况 | 备注                                                   |
| --------------------------- | -------- | ------------------------------------------------------ |
| *                           | 支持     | 支持自动推导当前对象以及当前对象的嵌套对象内的全部字段 |
| currentObjField             | 支持     | 支持自动获取当前对象的全部字段信息                     |
| currentObjField.child       | 支持     | 支持自动解析嵌套对象的全部字段信息                     |
| currentObjField.child.child | 支持     | 支持自动解析嵌套对象的嵌套对象的全部字段信息           |

**注：如果参数为Map/JSONObject等没有明确字段定义的，不适用于本组件。**

scanFields 如果为多个，使用,做分隔即可。如果scanFields中定义的字段在Object target 中不存在，则会被直接丢弃。

获取当前对象的某个字段的值，示例如下：

```
Object value= ObjectUtils.getFieldValueByName(target,field);
```

