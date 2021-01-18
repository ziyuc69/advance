# Docker进阶

## 1. Docker Compose

> 1.下载

```shell
# github下载地址
sudo curl -L "https://github.com/docker/compose/releases/download/1.27.4/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

# 国内镜像下载地址
sudo curl -L https://get.daocloud.io/docker/compose/releases/download/1.24.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose

# 另一种下载方式
https://github.com/docker/compose/releases/download/1.25.5/docker-compose-Linux-x86_64
要下载的链接直接复制到迅雷里面，直接下载压缩包。
```



> 2.赋予权限

```shell
sudo chmod +x /usr/local/bin/docker-compose
```

> 3.查看是否安装成功

```shell
docker-compose --version
```

## 2. Docker swarm

> 1.初始化docker swarm

```shell
# 初始化docker swarm并指定manager地址
docker swarm init --advertise-addr 192.168.1.3

# 生成一个命令，用户在worker节点执行，来加入docker swarm; 但是这个新加入的节点被指定为了manaer节点
docker swarm join-token manager

# 生成一个命令，用户在worker节点执行，来加入docker swarm
docker swarm join-token worker
```

> 2.给docker swarm添加一个work节点

```shell
# 在docker swarm初始化的时候生成如下命令，在从节点执行（即添加从节点到manager上）
docker swarm join --token SWMTKN-1-5ymgzznyo3l15c9uikjdjk74rv1bmcg8do0g3fx4zips56fspr-55ce2xn3rxpkh3uzwybh7z66v 192.168.1.3:2377
```

> 3.集群上启动一个节点

```shell
# docker集群上启动一个节点
docker service create -p 8888:80 --name my-ninx nginx

# docker集群指定relipcas数量
docker service update --relipcas 2 my-nginx
```





