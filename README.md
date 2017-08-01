# Breakpoint-http

![wen icon](https://fourwenwen.github.io/fww/image/logo100_100.jpg)

## 目录
* [背景介绍](#背景介绍)
* [项目介绍](#项目介绍)
* [使用说明](#使用说明)
  * [获取代码](#获取代码)
  * [需要知识点](#需要知识点)
  * [启动项目](#启动项目)
  * [项目示范](#项目示范) 
* [核心讲解](#功能分析)
  * [重要原理](#重要原理)
* [功能分析](#功能分析)
  * [分块上传](#分块上传)
  * [秒传功能](#秒传功能)
  * [断点续传](#断点续传)
* [总结](#总结)

<a name="背景介绍"></a>
## 背景介绍

&emsp;&emsp;*Breakpoint-http*，是不是觉得这个名字有点low，break point断点。这是一个大文件上传的一种实现。因为本来很久没写过前端了，本来想自己好好写一番js，可惜
因为种种原因而作罢了。该项目是基于一款百度开源的前端上传控件：WebUploader（百度开源的东西文档一如既往的差，哈哈。或者是我理解能力差）。<br/>

&emsp;&emsp;*Breakpoint-http*，当初想实现这一块web大文件上传，是因为有一天同时询问我这方面的知识，我发现好像在实战中没写过这类的代码啊。既然知道了自己不足那
肯定要狠狠补一下。所以才有了这个项目。  

&emsp;&emsp;对了这个项目是gradle+Spring Boot可能有部分人还没接触过这两个东西，这里就不进行讲解了，毕竟这不是重点，把gradle当成maven吧，虽然它还有更出色的功能。
Spring Boot用来简化Spring应用的初始搭建以及开发过程，一个约定大于规范的框架。

<a name="项目介绍"></a>
## 项目介绍

&emsp;&emsp;*Breakpoint-http* 是一个基于大文件上传，并参考网盘上传文件，而基于web的大文件上传实现项目。web中上传大文件没有桌面软件那么容易，还好现在是身处于
一个html5的时代。我们web端上传文件常用的做法就是用表单上传了，一旦上传的文件大小较大，一旦带宽跟不上，那用户只能在哪里一直等着，不能做刷新页面的操作，
并且一旦产生网络波动，那么用户所做的一切就白费了。  
&emsp;&emsp;*Breakpoint-http*就是为了保证在web端上传大文件能达到基本的可靠性的一种方案，方法多种，可能的方案会更出色，欢迎讨论。要让大文件上传能达到可用性，我们需要做到怎么样的程度呢？
* **断点续传** 最主要的功能之一，在断网或者在暂停的情况下，能够在上传断点中继续上传。
* **分块上传** 也是断点续传的基础之一，把大文件通过前端分块，然后后台在组在一起。
* **文件妙传** 这个相信大家在网盘中见过不少了，就是服务中已经有人上传过得文件，其他人再上传这个文件就秒上传到服务中去。
* **其他功能** 把下面这些功能归类到其他，是因为它们基本都是通过WebUploader(<http://fex.baidu.com/webuploader>)来实现的，很简单。
     * *多线程上传* 多个线程上传不同的块文件。
     * *文件进度显示* 显示文件的上传完成情况。
     * UI等等。   
     
<a name="使用说明"></a>
## 使用说明
<a name="获取代码"></a>
### 获取代码
* GitHub:<https://github.com/Fourwenwen/Breakpoint-http.git>
* OSChina项目主页: <https://git.oschina.net/Fourwenwen/breakpoint-http.git><br>
持续更新。

<a name="需要知识点"></a>
### 需要知识点
- 基于spring boot开发的。
- WebUploader，WebUploader是由Baidu WebFE(FEX)团队开发的一个简单的以HTML5为主，FLASH为辅的现代文件上传组件。
- redis,key-value存储系统，在这里我把redis用作存储文件路径来使用。
- Gradle，Gradle是一个基于JVM的构建工具。这里我用Gradle顶替了Maven。嗯，多学点东西。

<a name="启动项目"></a>
### 启动项目

1. main方法直接运行：  
(1)找到App启动类（win.pangniu.learn包下）  
(2)执行main方法。  
(3)然后用浏览器访问：<http://localhost:9090>
     
2. tomcat运行：  
(1)执行命令gradle war。  
(2)在out目录下找到bphttp.war包。  
(3)拷贝到tomcat，然后运行tomcat。  
(4)然后用浏览器访问：<http://localhost:9090>
   
<a name="项目示范"></a>
### 项目示范

1. 上传完后的页面
![duan1](https://fourwenwen.github.io/fww/image/bphttp/duan1.jpg)
2. 妙传功能演示页面
![duan1](https://fourwenwen.github.io/fww/image/bphttp/duan1.jpg)
详情自己运行就知道。

<a name="核心讲解"></a>
## 核心讲解
<a name="核心原理"></a>
### 核心原理
&emsp;&emsp;该项目核心就是文件分块上传。前后端要高度配合，需要双方约定好一些数据，才能完成大文件分块，我们在项目中要重点解决的以下问题。  
&emsp;&emsp;* 如何分片；  
&emsp;&emsp;* 如何合成一个文件；  
&emsp;&emsp;* 中断了从哪个分片开始。  
&emsp;&emsp;如何分，利用强大的js库，来减轻我们的工作，市场上已经能有关于大文件分块的轮子，虽然程序员的天性曾迫使我重新造轮子。但是因为时间的关系还有工作的关系，我只能罢休了。最后我选择了百度的WebUploader来实现前端所需。  
&emsp;&emsp;如何合，在合之前，我们还得先解决一个问题，我们如何区分分块所属那个文件的。刚开始的时候，我是采用了前端生成了唯一uuid来做文件的标志，在每个分片请求上带上。不过后来在做秒传的时候我放弃了，采用了Md5来维护分块和文件关系。  
&emsp;&emsp;在服务端合并文件，和记录分块的问题，在这方面其实行业已经给了很好的解决方案了。参考迅雷，你会发现，每次下载中的时候，都会有两个文件，一个文件主体，另外一个就是文件临时文件，临时文件存储着每个分块对应字节位的状态。
这些都是需要前后端密切联系才能做好，前端需要根据固定大小对文件进行分片，并且请求中要带上分片序号和大小。前端发送请求顺利到达后台后，服务器只需要按照请求数据中给的分片序号和每片分块大小（分片大小是固定且一样的）算出开始位置，与读取到的文件片段数据，写入文件即可。


<a name="功能分析"></a>
## 功能分析

<a name="分块上传"></a>
### 分块上传
&emsp;&emsp;分块上传可以说是我们整个项目的基础，像断点续传、暂停这些都是需要用到分块。
分块这块相对来说比较简单。前端是采用了webuploader，分块等基础功能已经封装起来，使用方便。
借助webUpload提供给我们的文件API,前端就显得异常简单。



```
    // 实例化wu
    var uploader = WebUploader.create({
        pick: {
            id: '#picker',
            label: '点击选择文件'
        },
        formData: {
            uid: 0,
            md5: '',
            chunkSize: chunkSize
        },
        //dnd: '#dndArea',
        //paste: '#uploader',
        swf: 'js/Uploader.swf',
        chunked: true,
        chunkSize: chunkSize, // 字节 1M分块
        threads: 3,
        server: 'index/fileUpload',
        auto: false,

        // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
        disableGlobalDnd: true,
        fileNumLimit: 1024,
        fileSizeLimit: 1024 * 1024 * 1024,    // 200 M
        fileSingleSizeLimit: 1024 * 1024 * 1024    // 50 M
    });
```
&emsp;&emsp;分则必合。把大文件分片了，但是分片了就没有原本文件功能，所以我们要把分片合成为原本的文件。我们只需要把分片按原本位置写入到文件中去。因为前面原理那一部我们已经讲到了，我们知道分块大小和分块序号，我就可以知道该分块在文件中的起始位置。所以这里使用RandomAccessFile是明智的，RandomAccessFile能在文件里面前后移动。但是在andomAccessFile的绝大多数功能，已经被JDK1.4的NIO的“内存映射文件(memory-mapped files)”取代了。我在该项目中分别写了使用RandomAccessFile与MappedByteBuffer来合成文件。分别对应的方法是uploadFileRandomAccessFile和uploadFileByMappedByteBuffer。两个方法代码如下。

```
 public void uploadFileRandomAccessFile(MultipartFileParam param) throws IOException {
        String fileName = param.getName();
        String tempDirPath = finalDirPath + param.getMd5();
        String tempFileName = fileName + "_tmp";
        File tmpDir = new File(tempDirPath);
        File tmpFile = new File(tempDirPath, tempFileName);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }

        RandomAccessFile accessTmpFile = new RandomAccessFile(tmpFile, "rw");
        long offset = CHUNK_SIZE * param.getChunk();
        //定位到该分片的偏移量
        accessTmpFile.seek(offset);
        //写入该分片数据
        accessTmpFile.write(param.getFile().getBytes());
        // 释放
        accessTmpFile.close();

        boolean isOk = checkAndSetUploadProgress(param, tempDirPath);
        if (isOk) {
            boolean flag = renameFile(tmpFile, fileName);
            System.out.println("upload complete !!" + flag + " name=" + fileName);
        }
    }
    
    public void uploadFileByMappedByteBuffer(MultipartFileParam param) throws IOException {
            String fileName = param.getName();
            String uploadDirPath = finalDirPath + param.getMd5();
            String tempFileName = fileName + "_tmp";
            File tmpDir = new File(uploadDirPath);
            File tmpFile = new File(uploadDirPath, tempFileName);
            if (!tmpDir.exists()) {
                tmpDir.mkdirs();
            }
    
            RandomAccessFile tempRaf = new RandomAccessFile(tmpFile, "rw");
            FileChannel fileChannel = tempRaf.getChannel();
    
            //写入该分片数据
            long offset = CHUNK_SIZE * param.getChunk();
            byte[] fileData = param.getFile().getBytes();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
            mappedByteBuffer.put(fileData);
            // 释放
            FileMD5Util.freedMappedByteBuffer(mappedByteBuffer);
            fileChannel.close();
    
            boolean isOk = checkAndSetUploadProgress(param, uploadDirPath);
            if (isOk) {
                boolean flag = renameFile(tmpFile, fileName);
                System.out.println("upload complete !!" + flag + " name=" + fileName);
            }
        }
```
   
<a name="秒传功能"></a>
### 秒传功能

&emsp;&emsp;秒传功能，相信大家都体现过了，网盘上传的时候，发现上传的文件秒传了。其实原理稍微有研究过的同学应该知道，其实就是检验文件MD5，记录下上传到系统的文件的MD5,在一个文件上传前先获取文件内容MD5值或者部分取值MD5，然后在匹配系统上的数据。  
&emsp;&emsp;*Breakpoint-http*实现秒传原理，客户端选择文件之后，点击上传的时候触发获取文件MD5值，获取MD5后调用系统一个接口（/index/checkFileMd5），查询该MD5是否已经存在（我在该项目中用redis来存储数据，用文件MD5值来作key，value是文件存储的地址。）接口返回检查状态，然后再进行下一步的操作。相信大家看代码就能明白了。  
&emsp;&emsp;嗯，前端的MD5取值也是用了webuploader自带的功能，这还是个不错的工具。

<a name="断点续传"></a>
### 断点续传
&emsp;&emsp;断点续传，就是在文件上传的过程中发生了中断，人为因素（暂停）或者不可抗力（断网或者网络差）导致了文件上传到一半失败了。然后在环境恢复的时候，重新上传该文件，而不至于是从新开始上传的。  
&emsp;&emsp;前面也已经讲过，断点续传的功能是基于分块上传来实现的，把一个大文件分成很多个小块，服务端能够把每个上传成功的分块都落地下来，客户端在上传文件开始时调用接口快速验证，条件选择跳过某个分块。  
&emsp;&emsp;实现原理，就是在每个文件上传前，就获取到文件MD5取值，在上传文件前调用接口（/index/checkFileMd5，没错也是秒传的检验接口）如果获取的文件状态是未完成，则返回所有的还没上传的分块的编号，然后前端进行条件筛算出哪些没上传的分块，然后进行上传。  
```
/**
     * 秒传判断，断点判断
     *
     * @return
     */
    @RequestMapping(value = "checkFileMd5", method = RequestMethod.POST)
    @ResponseBody
    public Object checkFileMd5(String md5) throws IOException {
        Object processingObj = stringRedisTemplate.opsForHash().get(Constants.FILE_UPLOAD_STATUS, md5);
        if (processingObj == null) {
            return new ResultVo(ResultStatus.NO_HAVE);
        }
        String processingStr = processingObj.toString();
        boolean processing = Boolean.parseBoolean(processingStr);
        String value = stringRedisTemplate.opsForValue().get(Constants.FILE_MD5_KEY + md5);
        if (processing) {
            return new ResultVo(ResultStatus.IS_HAVE, value);
        } else {
            File confFile = new File(value);
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            List<String> missChunkList = new LinkedList<>();
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    missChunkList.add(i + "");
                }
            }
            return new ResultVo<>(ResultStatus.ING_HAVE, missChunkList);
        }
    }
```    

<a name="总结"></a>
## 总结
&emsp;&emsp;身为一个具有拖延症的程序猿，写个文档及其不容易，这方面还是优待加强，写代码时间都还没写这个文档长，并且写了那么久还那么烂的文档。实在抱歉，望谅解。  
&emsp;&emsp;项目的Bug和改进点，可在评论去留言或者在GitHub或者OSChina上以issue的方式直接提交给我，谢谢大家。

##参考文献

[1]http://fex.baidu.com/webuploader/
[2]http://www.zuidaima.com/blog/2819949848316928.htm  
[3]https://my.oschina.net/feichexia/blog/212318