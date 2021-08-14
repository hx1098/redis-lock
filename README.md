[TOC]

# 背景

​	最近滴滴的app在appstore里搜不到, 不过小程序呀, 高德里面还是可以搜到的, 我就在想, 抢单的大概功能是如何来实现的呢? 它的怎样演化的呢? 本文主要以redis锁为基础, 探究一下司机抢单的过程, 缅怀一下吧哈哈哈!



# 用到的软件
   idea, postman, Jmeter, chrome 基本就折磨多了吧, 用到的话再说

# 抢单状态
## 1.无锁状态
这个肯定是会出问题id, 但是我还是要演示一下, 实现类**Grab_01_Nolocks.calss**, 同时使用Jmeter进行设置10个司机进行抢单,

![image-20210812225306134](https://cdn.jsdelivr.net/gh/hx1098/redis-lock@master/img/image-20210812225147015.png?raw=true)

![image-20210812225330681](https://cdn.jsdelivr.net/gh/hx1098/redis-lock@master/img/image-20210812225306134.png)

![image-20210812225147015](https://cdn.jsdelivr.net/gh/hx1098/redis-lock@master/img/image-20210812225330681.png)



可以看到这10个司机都抢单成功了, 那这不是乱套了吗? 到底该谁去接乘客哪? 显然不可能吧乘客分成10分的, 大家都是成年人, 而且都是法制社会, 也不可能为了一个乘客大打出手, 显然这是程序的锅.




# 2.synchronized

使用synchronize的话, 分两个场景, 第一种是单个实例的节点, 第二种是多节点,

如果是用的是单个节点是完全没有问题的, 但是第二种如果是多个节点的, 就会在使用上有问题了, 这里不再截图过多赘述了, 下面的这个截图是单个示例的截图 

![img](https://cdn.jsdelivr.net/gh/hx1098/redis-lock@master/img20210814092018.png)







