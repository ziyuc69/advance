# Docker入门

## 一、Docker安装

### Docker的基本组成

> 三个概念

**镜像（image）：**

镜像可以类比虚拟机的镜像，或者可以理解为是一款软件的安装包。例如：Redis镜像、Nginx镜像、Ubuntu镜像等。镜像是创建Docker的基础。开发者可以在网络上下载获取到已经设置完好的镜像来直接应用开发。

最直观的理解就是，镜像就像是软件安装包，软件安装后对软件安装包没有任何影响，换一台机器这个软件安装包依旧可以使用。

**容器（container）：**

容器类似于一个轻量级的沙箱，Docker利用容器来运行和隔离应用。容器是从镜像中创建出来的应用实例，类似于Java中的类和通过类实例化对象之间的关系。每个容器类似Java类实例出来的对象一样，各自有各自的操作周期等，是彼此相互隔离、互不可见的。

**镜像本身是只读的，不可以对其操作修改。容器从镜像启动时，其实会在镜像的最上一层生成一个可读写层**

最直观的理解就是，容器就像是已经安装好的软件，可以随时启动使用，也可以关闭停止使用。

**仓库（repository）:**

仓库就是用来放很多东西的地方，那么Docker仓库就是集中存放镜像文件的场所。每个Docker仓库中存放着一类Docker镜像的集合，它们之间通过 `tag`来进行区分。Docker仓库的概念和 `Git` 思路大致相同，因为Docker在很多地方设计就是参见Git来进行构建开发的，这就让开发者们对Docker的使用更加得心应手、触类旁通。

![Docker Architecture Diagram](https://docs.docker.com/engine/images/architecture.svg)

### 安装docker

> 环境说明

使用Linux系统的CentOS7.x版本，可以自己安装虚拟机，也可以买阿里云ECS云服务，因为公司生产环境大部分是CentOS版本，所以推荐。

> 安装步骤

```shell
# 1.卸载旧版本docker（也可能系统上没有，不过无所谓）
sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
# 2.安装yum-utils工具包
sudo yum install -y yum-utils
# 3.设备镜像仓库
sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo  （这个是国外仓库，不推荐使用）

yum-config-manager \
    --add-repo \
    https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo  （这是阿里云镜像仓库，推荐使用）

# 4.更新yum软件包索引
yum makecache fast
# 5.安装docker的相关ce （ce是社区版）
sudo yum install docker-ce docker-ce-cli containerd.io
# 6.启动docker
sudo systemctl start docker
# 7.查看docker安装是否成功
docker version
# 8.hello-worl测试一下
docker run hell-world
```

> 阿里云镜像加速

1.阿里云进入控制台

![image-20201220124821496](.\image-20201220124821496.png)

3.复制阿里云镜像加速地址

![image-20201220125126383](.\image-20201220125126383.png)

## 二、Docker常用命令

#### 基础命令

```shell
docker version         # 查看docker的版本信息
docker info            # 查看docker的系统信息，包括镜像和容器的数量
docker [命令] --help    # 帮助命令
```

#### 镜像命令

```shell
docker images   		  # 列举出本机所有镜像
docker search 镜像名  		# 搜索镜像
docker pull 镜像名     	# 拉取镜像
docker rmi 镜像名or镜像id   # 删除镜像
```

> docker image 查看镜像的所有命令

```shell
Commands:
  build       Build an image from a Dockerfile
  history     Show the history of an image
  import      Import the contents from a tarball to create a filesystem image
  inspect     Display detailed information on one or more images
  load        Load an image from a tar archive or STDIN
  ls          List images
  prune       Remove unused images
  pull        Pull an image or a repository from a registry
  push        Push an image or a repository to a registry
  rm          Remove one or more images
  save        Save one or more images to a tar archive (streamed to STDOUT by default)
  tag         Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE
```

#### 容器命令

```shell
# 1.容器基本操作命令
docker run 镜像id			 # 创建新容器并启动
docker ps 				  # 列举出运行的容器
docker rm 容器id			 # 删除指定容器
docker start 容器id	     # 启动指定容器
docker restart 容器id		 # 重启指定容器
docker stop 容器id         # 停止指定容器
docker kill 容器id         # 强制停止指定容器
docker exec               # 进入容器 
docker attach             # 进入容器
docker commit             # 根据当前容器创建一个新的镜像
docker logs				  # 查看日志

# 2.容器进程信息
docker top 容器id
docker stats 容器id

# 3.查看容器的元数据
docker inspect 容器id
```

#### 一些重要的命令

> docker run 启动一个容器

```shell
docker run -d --name 新容器名 -p 8090::80 镜像名:版本号  # 一定注意不要忘了版本号（除非你拉取的是最新版本，可以不要）
```

> docker run限制容器使用内容资源

```shell
# -e ES_JAVA_OPTS="xms128m -xmx128m" 限制ES堆宿主机内存的占用，最大不超过128M 
docker run -d --name elasticsearch --net somenetwork -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e ES_JAVA_OPTS="xms128m -xmx128m" elasticsearch:7.6.2
```

> docker cp 拷贝容器内容

```
docker cp 容器id:容器内路径  主机目的路径
```

> docker exec 进入容器

```shell
# 交互模式进入容器（如果容器没启动，第一次进入然后退出，容器可能会停止）
docker exec -it 容器id /bin/bash
```

> docker attach 进入容器

```shell
docker attach 容器id 

# docker attach和docker exec的区别？
# docker exec #进入当前容器后开启一个新的终端，可以在里面操作。（常用）
# docker attach # 进入容器正在执行的终端
```

> docker container 查看容器的所有命令

```
Commands:
  attach      Attach local standard input, output, and error streams to a running container
  commit      Create a new image from a container's changes
  cp          Copy files/folders between a container and the local filesystem
  create      Create a new container
  diff        Inspect changes to files or directories on a container's filesystem
  exec        Run a command in a running container
  export      Export a container's filesystem as a tar archive
  inspect     Display detailed information on one or more containers
  kill        Kill one or more running containers
  logs        Fetch the logs of a container
  ls          List containers
  pause       Pause all processes within one or more containers
  port        List port mappings or a specific mapping for the container
  prune       Remove all stopped containers
  rename      Rename a container
  restart     Restart one or more containers
  rm          Remove one or more containers
  run         Run a command in a new container
  start       Start one or more stopped containers
  stats       Display a live stream of container(s) resource usage statistics
  stop        Stop one or more running containers
  top         Display the running processes of a container
  unpause     Unpause all processes within one or more containers
  update      Update configuration of one or more containers
  wait        Block until one or more containers stop, then print their exit codes
```

#### Docker常用命令总结：

![image-20201220132549247](.\image-20201220132549247.png)

## 三、Docker镜像原理

### 镜像是什么

镜像是一种轻量级、可执行的独立软件保，用来打包软件运行环境和基于运行环境开发的软件，他包含运行某个软件所需的所有内容，包括代码、运行时库、环境变量和配置文件

### Docker 镜像加载原理

UnionFs（联合文件系统）：Union文件系统（UnionFs）是一种分层、轻量级并且高性能的文件系统，他支持对文件系统的修改作为一次提交来一层层的叠加，同时可以将不同目录挂载到同一个虚拟文件系统下（ unite several directories into a single virtual filesystem)。Union文件系统是 Docker镜像的基础。镜像可以通过分层来进行继承，基于基础镜像（没有父镜像），可以制作各种具体的应用镜像
特性：一次同时加载多个文件系统，但从外面看起来，只能看到一个文件系统，联合加载会把各层文件系统叠加起来，这样最终的文件系统会包含所有底层的文件和目录

docker的镜像实际上由一层一层的文件系统组成，这种层级的文件系统UnionFS。
boots(boot file system）主要包含 bootloader和 Kernel, bootloader主要是引导加 kernel, Linux刚启动时会加bootfs文件系统，在 Docker镜像的最底层是 boots。这一层与我们典型的Linux/Unix系统是一样的，包含boot加載器和内核。当boot加载完成之后整个内核就都在内存中了，此时内存的使用权已由 bootfs转交给内核，此时系统也会卸载bootfs。
rootfs（root file system),在 bootfs之上。包含的就是典型 Linux系统中的/dev,/proc,/bin,/etc等标准目录和文件。 rootfs就是各种不同的操作系统发行版，比如 Ubuntu, Centos等等

![](.\image-20201220132549345.png)

对于个精简的OS,rootfs可以很小，只需要包合最基本的命令，工具和程序库就可以了，因为底层直接用Host的kernel，自己只需要提供rootfs就可以了。由此可见对于不同的Linux发行版， boots基本是一致的， rootfs会有差別，因此不同的发行版可以公用bootfs.

**虚拟机是分钟级别，容器是秒级！**

### Docker分层技术

通过`docker pull`下载镜像时发现，docker是一层一层的去下载的，使用`docker image inspect`命令可以看到镜像分层。

```shell
[root@linux ~]# docker pull redis
```

![image-20201220150454996](.\image-20201220150454996.png)

```shell
[root@linux ~]# docker image inspect redis
```

![image-20201220150236600](.\image-20201220150236600.png)

最大的好处，我觉得莫过于资源共享了！比如有多个镜像都从相同的Base镜像构建而来，那么宿主机只需在磁盘上保留一份base镜像，同时内存中也只需要加载一份base镜像，这样就可以为所有的容器服务了，而且镜像的每一层都可以被共享。

### commit镜像

> docker commit 镜像 提交容器成为一个新副本(镜像)

```shell
# -a="提交者" -m="提交的内容注释"  tomcat02:1.0 ->  新镜像名称:新镜像版本号
docker commit -a="ziyu" -m="add webapps app" 容器id tomcat02:1.0
```

> 实战测试

```shell
# 1.下载tomcat镜像
docker pull tomcat

# 2.启动一个默认的tomcat
docker run -d -p 8090:8080 tomcat

# 3.进入容器，发现这个默认的tomcat 是没有webapps应用，官方的镜像默认webapps下面是没有文件的
docker exec -it 容器id /bin/bash

# 4.修改已启动的tomcat内容,拷贝/usr/local/tomcat/webapps.dist下的内容到/usr/local/tomcat/webapps
cd /usr/local/tomcat/
cp -r webapps.dist/* webapps/

# 5.将修改后的容器通过commt提交为一个新的镜像
docker commit -a="ziyu" -m="add webapps app" 容器id 新镜像名:新镜像版本号
```

### 容器数据卷

> 容器数据卷技术

docker的理念，是将数据和环境打包成一个镜像，这样的话如果数据在容器中，我们把容器删除了，数据就会丢失。而实际在生产环境中，我们是不希望数据丢失的。因此需要将数据持久化存储在本地(宿主机)。

容器和本地之间的一种数据共享的技术，也就是容器中产生的数据，同步到本地，这中技术叫做容器数据卷技术。说白了就是将容器中的目录，挂载在Linux系统的目录上，保证了容器中的数据在Linux系统的目录中同步保存。

**总结：容器的持久化和同步操作，容器间也是可以数据共享的！**

#### 使用数据卷

> 方式一：直接使用命令来挂载 -v

```shell
docker run -it -v 主机目录:容器目录 centos /bin/bash

# 测试
docker run -it -v /home/ceshi:/home centos /bin/bash

# 启动起来的时候，可以通过以下命令查看挂载关系
docker inspect 容器id
```

![image-20201220172543525](.\image-20201220172543525.png)

**说明：1. 就算容器停止了，Linux系统中的文件被修改了；然后容器重新启动，依旧可以同步到被修改的内容；**

​            **2.容器如果被删除了，挂载在Linux系统上目录上的数据，依旧不会丢失。**

#### 具名和匿名挂载

```shell
# 匿名挂载
docker run -d -P --name nginx01 -v /etc/nginx nginx

# 查看所有volume的情况
docker volume ls

# 查看挂载情况
docker inspect 容器id
```

```shell
# 具名挂载
docker run -d -P --name nginx02 -v juming /etc/nginx niginx

# 查看所有volume的情况
docker volume ls

# 查看挂载情况
docker volume inspect jumingg-ninx
```

```shell
# 如何确定是具名还是匿名挂载，还是指定路径挂载
-v 容器内路径                # 匿名挂载
-v 卷名:容器内路径            # 具名挂载
-v /宿主机路径:容器内路径      # 指定路径挂载

docker run -d -P --name nginx02 -v juming-nginx:/etc/nginx:ro nginx  # 只读
docker run -d -P --name nginx02 -v juming-nginx:/etc/nginx:rw nginx  # 读写

# ro 只要看到ro就说明这路径只能通过宿主机来操作，容器内部无法操作
```

### DockerFile

Dockerfile就是用来构建docker镜像的构建文件。

通过这个脚本可以生成镜像，镜像是一层一层的，脚本一个个的命令，每个命令都是一层

```shell
# 创建一个dockerfile文件，文件名最好带上dockerfile
FROM centos

VOLUME ["volume01","volume02"]

CMD echo "---end---"

CMD /bin/bash
```

```shell
# dockerfile构建命令
docker build -f /home/docker-test-volume/dockerfile1 -t ziyu/centos .
```

![image-20201220181304250](.\image-20201220181304250.png)

**说明：注意划红线地方的写法，前面不能有`/`，后面`. `不能少。**

```shell
# 运行自己创建的镜像
docker run -it 容器id /bin/bash
```

![image-20201220182307029](.\image-20201220182307029.png)

```shell
VOLUME ["volume01","volume02"] # 这个是匿名挂载
```

#  

```shell
# 查看一下卷挂载的路径 
docker inspect 容器id
```

![image-20201220182810722](.\image-20201220182810722.png)

**说明：这种方式用的比较多，因为我们通常会构建自己的镜像；**

​           **假设构建镜像的时候没有挂在卷，要手动给镜像挂载卷 `-v 卷名:容器内路径`**

```shell
# docker02挂载在docker01上；docker02可以同步docker01的数据
docker run -it --name docker02 --volumes-from docker01 ziyu/centos

# 容器卷技术，数据是互相备份拷贝的，而不是共享的
```

#### Dockerfile构建步骤

1. 编写一个dockerfile文件

2. docker build构建成为一个镜像

3. docker run运行镜像

4. docker push发布镜像（DockerHub，阿里云镜像仓库）

**dockerfile是面向开发的，我们发布项目一般都是做一个镜像，就是编写一个dockerfile文件。**

#### 公司开发，部署流程：

1. dockerfile：构建文件，定义了一切的步骤（源代码）
2. dockerImages：通过dockerifle构建生成的镜像，最终发布和运行的产品
3. docker容器：容器就是镜像运行起来提供服务的。

#### Dockerfile的指令

```shell
FROM            # 基础镜像，一切从这里开始
MAINTAINER      # 镜像是谁写的，姓名 + 邮箱
RUN             # 镜像构建的时候需要运行的命令
ADD             # 步骤：
WORKDIR         # 镜像的工作目录
VOLUME          # 挂载的目录
EXPOST          # 暴露的端口配置
CMD				# 指定容器启动的时候要云信的命令，只有最后一个会生效，可被替代
ENTRYPOINT      # 指定这个容器启动的时候要运行的命令，可以追加命令
ONBUILD         # 当构建一个被继承Dockerfile 这个时候就会运行ONBUILD的命令。触发指令。
COPY            # 类型ADD，将我们的文件拷贝到镜像中
ENV             # 构建的时候设置环境变量

例如：ls -a       docker run -l  
CMD命令则会替换为   -l，执行会失败
ENTRYPOINT命令则追加为： ls -al 
```

#### 实战测试

> 创建自己的centos

```shell
# 1.编写dockerfile文件
[root@linux dockerfile]# cat mydockerfile 
FROM centos
MAINTAINER ziyu<ziyu@163.com>

ENV MYPATH /usr/local
WORKDIR $MYPATH

RUN yum -y install vim
RUN yum -y install net-tools

EXPOSE 80

CMD echo $MYPATH
CMD echo "----end----"
CMD /bin/bash

# 2.通过文件构建镜像
# 命令 docker build -f dockerfile文件路径 -t 新镜像名:镜像版本号 .
docker build -f mydockerfile -t mycentos:0.1 . 

# 3.测试运行
docker run -it mycentos
```

我们自己制作的镜像，运行起来的效果：

![image-20201220191509269](.\image-20201220191509269.png)

查看镜像构建历史

```shell
docker history 镜像id
```

![image-20201220191820558](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20201220191820558.png)

> CMD 和 ENTRYPOINT 区别

```shell
CMD                              # 指定这个容器启动的时候要运行的命令，只有最后一个会生效，可被替代
ENTRYPOINT						 # 指定这个容器启动的时候要运行的命令，可以追加
```

 测试CMD

![image-20201220193022406](.\image-20201220193022406.png)

 测试ENTRYPOINT

**说明，就不用测试了，这个命令构建的镜像，执行上面最后那个命令就可以正常运行。**

#### 发布到远程仓库

公司内部一般用阿里云，发布流程参考阿里云



#### docker常见问题

1. Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running

解答：忘记启动docker服务了，启动命令：service docker start