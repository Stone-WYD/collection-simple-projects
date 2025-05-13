# 提高对接第三方系统效率

### 提效文档：

详细内容可阅读语雀文档:

* [对接第三方系统提效](https://www.yuque.com/wangyudong-stone/kb/rgqa9ofurqp033cp?singleDoc#)

### 问题：

当前项目有个问题，在此记录一下：

`项目中使用到了Knife4j，它是swagger的增强版，页面会更美观一些。其中引用了Springfox（实际上是swagger2用到了），Springfox对jackson有高度依赖，所以无法将其替换为gson。尝试多种方法后无果，迫于无奈，通过配置，我在开启Knife4j时使用jackson，不开启时切换为gson。`

### 可复用内容：

1. sqlite的使用

2. caffeine 本地缓存
3. 



