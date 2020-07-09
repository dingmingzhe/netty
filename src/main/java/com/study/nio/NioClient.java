package com.study.nio;

import javax.print.DocFlavor;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: dingmingzhe
 * \* Date: 2020-07-09
 * \* Time: 16:29
 * \
 * nio客户端
 */
public class NioClient {

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        //设置为非阻塞
        socketChannel.configureBlocking(false);

        //设置服务端的ip和端口
        InetSocketAddress inetSocketAddress=new InetSocketAddress("127.0.0.1",6666);

        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)){
            //服务器是否连接完成
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间,客户端不会阻塞。可以进行其它工作");
            }
        }




        socketChannel.write(ByteBuffer.wrap(new String("hello 我连接了").getBytes()));

        System.in.read();
    }

}