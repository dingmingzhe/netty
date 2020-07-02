package com.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *使用 FileChannel(通道) 和 方法  read , write，完成文件的拷贝
 *
 * 拷贝一个文本文件 1.txt  , 放在项目下即可
 */
public class NIOFileChannel03 {

    public static void main(String[] args) throws Exception{
        //创建文件输入流
        FileInputStream fileInputStream=new FileInputStream("/Users/dingmingzhe/Desktop/报销/1.txt");
       //根据文件获取输入渠道
        FileChannel channel = fileInputStream.getChannel();


        FileOutputStream fileOutputStream=new FileOutputStream("/Users/dingmingzhe/Desktop/报销/2.txt");
        FileChannel channel1 = fileOutputStream.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate(1024);


    }
}
