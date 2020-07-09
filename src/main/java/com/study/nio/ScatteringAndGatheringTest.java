package com.study.nio;

import jdk.nashorn.internal.runtime.JSONFunctions;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 *都是通过一个Buffer 完成的，NIO 还支持
 * 通过多个Buffer (即 Buffer 数组) 完成读写操作，即 Scattering 和 Gathering
 */
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws Exception{

        //1.使用serverSocketChannel.open获取serverSocketChannel渠道
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();

        //2.使用serversocket绑定地址
        //2.1创建inetsocketAdress地址对象合作端口为8000
        InetSocketAddress inetSocketAddress=new InetSocketAddress(8000);
        //2.2使用渠道绑定地址并启动(serverSocket才可以进行地址的绑定)
        serverSocketChannel.socket().bind(inetSocketAddress);

        //3创建buffer数组(使用两个buffer进行循环读取数据)
        ByteBuffer[] byteBuffers=new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);//创建为5的bytebuffer
        byteBuffers[1]= ByteBuffer.allocate(3);//创建大小为3的byteffer


        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength=8;

        while (true){
            int byteRead=0;
            while (byteRead<messageLength){
                System.out.println("byteRead"+byteRead);
                long read = socketChannel.read(byteBuffers);
                byteRead+=read;
                System.out.println("byteRead读取数据为:"+byteRead);


                //System.out::println等价于buffer-> System.out.println(buffer)
                Arrays.asList(byteBuffers).stream().map(buffer->"position是:"+buffer.position()+"limit是:"+buffer.limit()).forEach(buffer-> System.out.println(buffer));

            }
            //把所有的buffer进行反转
            Arrays.asList(byteBuffers).forEach(buffer->buffer.flip());

            long bytewrite=0;
            while (bytewrite<messageLength){
                long write = socketChannel.write(byteBuffers);
                bytewrite+=write;

            }

            //将所有的buffer进行clear操作  改操作在读之前也是可以的

            Arrays.asList(byteBuffers).forEach(byteffer->{byteffer.clear();});


            System.out.println("byteRead数量为:="+byteRead+",byteWrite="+bytewrite);



        }


    }

}
