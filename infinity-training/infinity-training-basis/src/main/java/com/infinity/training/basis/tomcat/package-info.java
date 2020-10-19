/**
 * Tomcat流程简介
 * (传输层的TCP是基于网络层的IP协议的，而应用层的HTTP协议又是基于传输层的TCP协议的，而Socket本身不算是协议，就像上面所说，它只是提供了一个针对TCP或者UDP编程的接口。socket是对端口通信开发的工具,它要更底层一些。)
 * 1. 数据传输到操作系统(TCP/IP协议确保数据的有序性、正确性，由操作系统实现)(HTTP协议确保数据内容格式，浏览器作为客户端，Tomcat作为服务端来实现HTTP协议)。
 * 2. EndPoint提供基础的网络IO服务，用来实现网络连接和控制，它是服务器对外I/O操作的接入点。主要任务是管理对外的socket连接，同时将建立好的socket连接交到合适的工作线程中去。
 * 3. tomcat解析数据组装为HttpServletRequest对象，tomcat提供了具体实现(RequestFacade)。
 * 4. 然后将Request对象传递给容器：Engine-Pipeline -> Host-Pipeline -> Context-Pipeline -> Wrapper-Pipeline -> FilterChain -> Servlet。
 * <p>
 * Tomcat热部署与热加载
 * <p>
 * 热部署和热加载是类似的，都是在不重启Tomcat的情况下，使得应用的最新代码生效。
 * 热部署表示重新部署应用，它的执行主体是Host，表示主机。
 * 热加载表示重新加载class，它的执行主体是Context，表示应用。
 * <p>
 * Tomcat针对每个应用都有一个WebApp类加载器，用于加载自己目录下的class文件，不会传递给父类加载器。它打破了双亲委派模型。
 * <p>
 * 热部署和热加载都需要监听相应的文件夹是否发生了变化，它们都是由Tomcat的后台线程触发的。
 * BackgroundProcessor就表示后台线程，每个容器都可以拥有一个BackgroundProcessor，但是默认情况下只有Engine容器会在启动的时候启动一个BackgroundProcessor线程。
 * <p>
 * s
 */
package com.infinity.training.basis.tomcat;