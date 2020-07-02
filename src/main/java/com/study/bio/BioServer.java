package com.study.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟bio服务(同步阻塞)
 */
public class BioServer {
    public static void main(String[] args)throws Exception {

        //线程池机制

        //思路
        //1. 创建一个线程池
        //2. 如果有客户端连接，就创建一个线程，与之通讯(单独写一个方法)
        ExecutorService executorService = Executors.newCachedThreadPool();

        //创建serversocket 绑定端口
        ServerSocket serverSocket=new ServerSocket(10086);
        System.out.println("服务器启动了=======");
       while (true){
           //接收一个请求
           System.out.println("有线程开始请求了。线程名称是"+Thread.currentThread().getName());
           final Socket accept = serverSocket.accept();
           System.out.println("接收到一个客户端的请求。");
           executorService.execute(new Runnable() {
               public void run() {
                  handle(accept);
               }
           });
       }


    }
    public static void handle(Socket socket){
        try {
            //根据socket获取输入流
            InputStream inputStream = socket.getInputStream();

            byte[] bytes=new byte[1024];
            //通过while 循环进行读取
            while (true){
                int read = inputStream.read();
                if (read!=-1){//读取完毕

                    System.out.println("接收到的信息是:"+new String(bytes,0,read));
                }else {
                    break;
                }

             }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
