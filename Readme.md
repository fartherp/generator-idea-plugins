# IntelliJ IDEA 自动生成代码插件

## 加载
1. 首先加载Component的构造函数。
2. 如果组件实现JDOMExternalizable接口，将会调用readExternal函数。
3. 接下来initComponent函数将被调用。
4. 如果project或module component，projectOpened函数将会被调用。如果时module component，moduleAdded函数会被调用。

## 卸载
1. 如果组件实现JDOMExternalizable接口，component的状态将会通过调用writeExternal函数保存到XML文件中。
2. 如果project或module component，projectClosed将会被调用。
3. 接下来disposeComponent函数将被调用。

## 使用指南
1. 配置需要生成的代码配置信息
[![配置信息](./resources/imgs/1.png)](配置信息)
2. 可选择不同的代码模板
[![模板信息](./resources/imgs/2.png)](模板信息)
3. 选择需要生成代码的表名
[![表名信息](./resources/imgs/3.png)](表名信息)
4. 生成代码
[![生成代码](./resources/imgs/4.png)](生成代码)
5. 代码结果
[![代码结果](./resources/imgs/5.png)](代码结果)
