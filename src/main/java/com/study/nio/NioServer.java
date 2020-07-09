package com.study.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: dingmingzhe
 * \* Date: 2020-07-09
 * \* Time: 15:34
 * nio服务端
 */
public class NioServer {

    public static void main(String[] args)throws Exception {
        //创建serverSocketChannel对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //创建selector对象(创建选择器)
        Selector selector = Selector.open();

        //为ServerSocketChannel绑定一个端口并开启服务
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置渠道为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serversocketChannel注册到selector选择器(关注连接的时间)
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("注册后选择器selectionkey数量是"+selector.keys().size());


        //等待客户端连接
        while (true){
            //设置阻塞1s   selector.select();表示一直阻塞       selector.wakeup() 唤醒阻塞线程selector.selectNow()表示不阻塞


            if (selector.select(1000)==0){
                System.out.println("服务器等待了1s，无连接。");
                continue;
            }
            //如果select.select >0表示selectKey集合
            //1.如果返回的>0,表示已经获取到相关的事件
            //2.selector.selectkeys()返回关注事件的集合
            //通过selectkey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            System.out.println("selectionKeys 数量="+selectionKeys.size());


            //遍历selectionKey 获取相关事件
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()){
                //获取selectionkey
                SelectionKey selectionKey = iterator.next();
                //根据key，判断对应通道发生的事件做相应的处理
                if (selectionKey.isAcceptable()){//如果是接收事件,表示有新的客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生产了一个socketchannel"+socketChannel.hashCode());
                    //将连接设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketchannel注册到selecter，关注事件为 op_read 同时给socketChannel

                    //关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接后,注册的selectionkey 数量="+selector.keys().size());
                }else if (selectionKey.isReadable()){
                    //如果发生读数据
                    SocketChannel channel = (SocketChannel)selectionKey.channel();

                    //获取到该channel关联buffer
                    ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();

                    int read = channel.read(byteBuffer);
                    byte[] array = byteBuffer.array();
                    System.out.println("from 客户端"+new String(array,0,read));

                }


                //手动从集合中移除当前selectionkey,防止重复操作
                iterator.remove();



            }



        }



    }




}