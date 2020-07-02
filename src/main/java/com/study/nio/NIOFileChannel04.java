package com.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 使用 FileChannel(通道) 和 方法  transferFrom ，完成文件的拷贝
 *
 * 拷贝一张图片
 */
public class NIOFileChannel04 {

    public static void main(String[] args) throws  Exception{



        //创建文件输入流
        FileInputStream fileInputStream=new FileInputStream("/Users/dingmingzhe/Desktop/报销/1.png");
        //根据文件获取输入渠道
        FileChannel souceChannel = fileInputStream.getChannel();


        FileOutputStream fileOutputStream=new FileOutputStream("/Users/dingmingzhe/Desktop/报销/2.png");
        FileChannel tarChannel = fileOutputStream.getChannel();

        tarChannel.transferFrom(souceChannel,0,souceChannel.size());

        //关闭渠道
        souceChannel.close();
        tarChannel.close();

        fileInputStream.close();
        fileOutputStream.close();


    }
}
