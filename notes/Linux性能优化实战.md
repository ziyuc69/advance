# Linux性能优化实战

## 一、排查第一步

一般遇到性能问题排查基本思路：

1. 首先通过uptime或top查看系统整体负载情况，可以实时监控负载发展趋势；主要定位是cpu、内存、磁盘、网络等那块出了问题。
2. 平均负载过高，不一定全是cpu的问题，内存或io也会导出平均负载过高
3. 如果cpu负载过高，可以通过mpstat查看各个进行cpu使用率，也可以通过pidstat定位具体是那个进行cpu使用率过高

```shell
# 查看平均负载
uptime

# 查看linux系统线程数量
grep 'model name' /proc/cpuinfo | wc -l

# 平均负载监控，每2秒更新一次
watch -d uptime

# 监控所有cpu使用率，每5秒更新一次
mpstat -P ALL 5

# 等待5秒输出一次，进程对cpu的使用率
pidstat -u 5 1
```























