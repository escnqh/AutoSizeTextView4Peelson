# 动态字体（2018-05-29-21-00）

## 需求
（如）在某些title中需要在某个范围加载一行文字，文字需要动态地确定字体大小来适应

## 预计完成时间
2018-06-02-12-00

## 思路
仿造Autosize特性

源码分析：https://juejin.im/post/5a5cbe90518825732d7f9606

考虑实际问题：
1. 是否要固定宽度
2. 初始字体大小是否要固定，达到有预先效果，但是增加文字到边界之后再调整字体大小。
3. 怎么使用？达到工具化可复用（再考虑稳定，健壮）
4. **好的代码**

### 关键算法
- 测量文字宽度，对于某个精度和大致的范围中进行折半查找，找到合适宽度的textsize

- 对高度做同样适配，取小值，此时textsize是最合适的

### 绘制

触发时机：

- 初始化过程中onMeasure()对view大小进行测量时

- 当文字改变onTextChanged()

- 当view尺寸发生变化之时

### 重要问题
取文字宽度（高度）的方法：
```java
//        mTextViewWidth = this.getWidth();
//        mTextViewHeight = this.getHeight();
//        这么写拿不到值，原因是在onCreate()方法中控件还没有计算自己的参数所以没办法取到
    mTextViewWidth = textLength - this.getPaddingRight() - this.getPaddingLeft();
```

