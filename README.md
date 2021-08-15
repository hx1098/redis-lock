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




## 2.synchronized

使用synchronize的话, 分两个场景, 第一种是单个实例的节点, 第二种是多节点,

第一种单个实例的:

![image-20210815095103758](https://cdn.jsdelivr.net/gh/hx1098/redis-lock@master/img/20210815095103.png)



第二种: 8004, 8005的实例

![image-20210815095125821](https://cdn.jsdelivr.net/gh/hx1098/redis-lock@master/img/20210815095125.png)



![image-20210815095143404](https://cdn.jsdelivr.net/gh/hx1098/redis-lock@master/img/20210815095143.png)

综上所述: 如果是用的是单个节点是完全没有问题的, 但是第二种如果是多个节点的, 就会在使用上有问题了, 两台服务器都有有一个抢单成功的, 这就产生脏数据了, 而且也不符合真是的业务场景, jvm锁仅适用于那些单实例的节点, 这就引入了下面所要说的分布式锁



## 3.mysql 锁

这种方式也可以, 不过感觉代码貌似有点问题, 可以保证只有一台服务器抢到锁.,  后续在补上, 



## 4.redis单机的锁

redis锁实现起来比较简单, 基本就一个RedissonClient lock(), unlock()完事了, 但是需要注意的点还是有的

- unlock时候一定要在**finally**中, 否则可能无法进行解锁
- 在使用redis命令的时候,生产环境中一定要加上一个超时时间, 防止队友重启redis时候,无法释放锁, 同时也防止redis日渐肥胖,导致资源耗尽
- 加超时时间会有加不上的情况, 这个时候要看自己的命令是按照几行代码执行的, 一般来说这种情况很少会遇到, 但你既然用到了分布式系统, 想必你的系统也已经有了一定的用户量, 所以, 一定在执行的时候, 可以变成一行的命令一定要变成一行(**Grab_04_RedisLockImpl**.java这里有说明)

![image-20210815093435615](https://cdn.jsdelivr.net/gh/hx1098/redis-lock@master/img/20210815093435.png)



![image-20210815093453265](https://cdn.jsdelivr.net/gh/hx1098/redis-lock@master/img/20210815093453.png)





## 4.redis-RedLock

准备工作

```bash
#分别启动三个redis, 6379, 6380, 6381
.\redis-server.exe .\redis.windows.conf
.\redis-server.exe .\redis.windows6380.conf
.\redis-server.exe .\redis.windows6381.conf
```

关键代码:

```java
        String lockKey = (RedisKeyConstant.GRAB_LOCK_ORDER_KEY_PRE + orderId).intern();

        //红锁 redisson
        RLock   rLock1 = redissonRed1.getLock(lockKey);
        RLock   rLock2 = redissonRed2.getLock(lockKey);
        RLock   rLock3 = redissonRed3.getLock(lockKey);
        RedissonRedLock rLock = new RedissonRedLock(rLock1, rLock2, rLock3);
        try {
            rLock.lock();
           /* try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            boolean b = tblOrderService.grab(orderId, driverId);
            if(b) {
                System.out.println("司机:"+driverId+" 抢单成功");
            }else {
                System.out.println("司机:"+driverId+" 抢单失败");
            }

        } finally {
            rLock.unlock();
        }

        return "";
```

![image-20210815135838738](https://cdn.jsdelivr.net/gh/hx1098/redis-lock@master/img/20210815135838.png)

![image-20210815135900243](https://cdn.jsdelivr.net/gh/hx1098/redis-lock@master/img/20210815135900.png)





