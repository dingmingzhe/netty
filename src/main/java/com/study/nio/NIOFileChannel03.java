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


        while (true){
            //使用完需要进行数据的清空，不然之前的buffer数据会一直存在
            allocate.clear();//重复使用一个buffer进行读取的时候，一定要进行清空，不然会是一个死循环


            //根据channel读取buffer内容
            int read = channel.read(allocate);
            if(read==-1){
                break;
            }
            //如果有数据写入到输出流(乱码)
            //channel1.write(allocate);

            //翻转信息 指针指向开始
            allocate.flip();

            channel1.write(allocate);
        }
      //关闭输入输出流
        fileInputStream.close();
        fileInputStream.close();


    }
}
