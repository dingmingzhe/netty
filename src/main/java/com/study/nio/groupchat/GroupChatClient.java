package com.study.nio.groupchat;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: dingmingzhe
 * \* Date: 2020-07-10
 * \* Time: 11:07
 * \
 * 群聊客户端
 */
public class GroupChatClient {
    //设置初始化属性信息
    private final String HOST="127.0.0.1";
    private final int PORT=6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    //构造器初始化信息
    public GroupChatClient() {
        try {
            //初始化选择器
            selector = Selector.open();
            //初始化socketChannel
            socketChannel=SocketChannel.open(new InetSocketAddress(HOST,PORT));
            //设置为非阻塞
            socketChannel.configureBlocking(false);
            //绑定到selector上
            socketChannel.register(selector,SelectionKey.OP_WRITE);

            //得到用户姓名
            userName = socketChannel.getLocalAddress().toString();
            System.out.println(userName+"已经准备好连接了===");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //想服务器发生消息
    public  void sendInfo(String info){
        info=userName+"说:"+info;
        try{
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //从服务端回复的消息
    public void readInfo(){
        try {
            int select = selector.select();
            if (select>0){//有事件发生
                //根据selector获取selectionkey
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    //判断事件是否是读取操作
                    if (selectionKey.isReadable()){//如果是读取事件读取信息，打印到控制台
                        SocketChannel channel = (SocketChannel)selectionKey.channel();
                        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
                        while (true){
                            int read = channel.read(byteBuffer);
                            if (read>0){
                                byte[] array = byteBuffer.array();
                                System.out.println("客服端收到数据是:"+new String(array,0,read));
                                byteBuffer.clear();
                            }else {
                                break;
                            }
                        }
                    }else{

                        System.out.println("客户端发生了其它事件。");
                    }
                }
            }
        }catch (Exception e){
            //打印错误信息
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        GroupChatClient groupChatClient=new GroupChatClient();
        //启动一个线程读取数据
        new Thread(){
            @Override
            public void run() {
                while (true){
                    groupChatClient.readInfo();
                }
            }
        }.start();

        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s=scanner.nextLine();
            groupChatClient.sendInfo(s);
        }
    }


}