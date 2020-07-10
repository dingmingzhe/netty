package com.study.nio.groupchat;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: dingmingzhe
 * \* Date: 2020-07-10
 * \* Time: 09:51
 * \
 * 群聊服务端
 */
public class GroupChatServer {
    //定义属性
    private Selector selector;//选择器
    private ServerSocketChannel serverSocketChannel;
    //初始化端口
    private static final int PORT=6667;

    //构造器初始化信息
    public GroupChatServer() {
        try {
            //打开selector选择器
            selector = Selector.open();
            //打开渠道
            serverSocketChannel=ServerSocketChannel.open();

            //绑定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));

            //设置非阻塞
            serverSocketChannel.configureBlocking(false);
            //注册选择器(监听连接事件)
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    //监听
    public void listen(){
        System.out.println("监听线程:"+Thread.currentThread().getName());

        try {
            while (true) {
                //判断是否有请求接收(大于0证明有请求信息)
                int select = selector.select();
                if (select>0){
                    //获取所以事件，进行迭代(不可使用selector.keys方法进行获取,删除会出现问题）
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()){
                        //进行遍历信息,获取当前数据
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()){
                            //接收请求
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            //设置为非阻塞
                            socketChannel.configureBlocking(false);
                            //注册选择器（关注读时间）
                            socketChannel.register(selector,SelectionKey.OP_READ);

                        }else if (selectionKey.isReadable()){//通道发生read事件，即通道是可读的
                            //读取数据
                            readData(selectionKey);
                        }
                        //删除事件，防止重复提交
                       iterator.remove();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();

        }finally {

        }
    }


    //读取客户端的信息
    private void readData(SelectionKey selectionKey) {
        //根据selectionkey获取到channel信息
        SocketChannel channel =(SocketChannel) selectionKey.channel();
        try {
            //创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (true){
                //读取数据
                int read = channel.read(byteBuffer);
                if(read>0) {
                    byte[] array = byteBuffer.array();
                    String msg = new String(array, 0, read);
                    //输出信息
                    System.out.println("form 客户端" + msg);
                    //向其它客户端转发消息
                    sendInfoToOtherClients(msg, channel);

                    //清空信息
                    byteBuffer.clear();

                }else {
                    break;
                }
        }
        }catch (Exception e){
            try {
                //关闭信息,取消注册
                selectionKey.channel();
                channel.close();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
    //向其它客户端转发消息
    private void sendInfoToOtherClients(String msg, SocketChannel channel) throws  Exception{
        System.out.println("服务器转发消息中。。。");
        System.out.println("服务器转发数据给客户端线程:"+Thread.currentThread().getName());

        //获取所有的selectionKey
        Iterator<SelectionKey> iterator = selector.keys().iterator();
        while (iterator.hasNext()){
            SelectionKey selectionKey = iterator.next();
            Channel targetChannel = selectionKey.channel();
            if (targetChannel instanceof SocketChannel&& targetChannel!=channel){
                SocketChannel destChannel=(SocketChannel)  targetChannel;
                destChannel.write(ByteBuffer.wrap(msg.getBytes()));
            }
        }
    }


    public static void main(String[] args) {
        //开始程序准备监听
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();

    }
}