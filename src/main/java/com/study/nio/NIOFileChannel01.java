package com.study.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * nio渠道读取文件
 *
 * 使用 FileChannel(通道) 和 方法  read , write，完成文件的拷贝
 *
 * 拷贝一个文本文件 1.txt  , 放在项目下即可
 */
public class NIOFileChannel01 {


    public static void main(String[] args) throws Exception{
        String out="我是一个测试的输出内容";
        FileOutputStream fileOutputStream=new FileOutputStream("/Users/dingmingzhe/Desktop/报销/1.txt");


        //通过文件输出流获取filechannel
        //filechannel实现类是FileChannelImpl
        FileChannel channel = fileOutputStream.getChannel();

        //创建字节缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(1024);

        //设置输入内容
        allocate.put(out.getBytes());

        //翻转信息（pos重新指向头）
        allocate.flip();

        //使用渠道进行buffer内容进行输出

        channel.write(allocate);
        //关闭流
        fileOutputStream.close();

    }
}
