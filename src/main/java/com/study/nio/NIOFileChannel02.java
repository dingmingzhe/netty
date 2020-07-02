package com.study.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用前面学习后的ByteBuffer(缓冲) 和 FileChannel(通道)，
 * 将 file01.txt 中的数据读入到程序，并显示在控制台屏幕
 *
 * 假定文件已经存在
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception{
        File file=new File("/Users/dingmingzhe/Desktop/报销/1.txt");
        //创建文件输入流
        FileInputStream fileInputStream=new FileInputStream(file);

        //根据文件输入流获取fileChannel
        FileChannel channel = fileInputStream.getChannel();

        //创建bytebuffer
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);

        while (true){
            int read = channel.read(byteBuffer);
            if (read ==-1){
                break;
            }
            //直接使用byteBuffer.get进行数据的截取还是会获取到后面的空格 多出乱码信息
            //  System.out.println(new String(byteBuffer.get(byteBuffer.array(),0,read).array()));
            byte[] array = byteBuffer.array();
            System.out.println(new String(array,0,read));

            byteBuffer.flip();

        }
        //关闭读写渠道
        fileInputStream.close();


    }
}
