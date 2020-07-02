package com.study.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 可以让文件直接在内存（堆外的内存）中进行修改， 而如何同步到文件由NIO 来完成.
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws  Exception {
        File file =new File("/Users/dingmingzhe/Desktop/报销/1.txt");
        //RandomAccessFile 可以想指定位置进行读取和写入  seek 表示定位到文件的某个位置
        RandomAccessFile RandomAccessFile=new RandomAccessFile(file,"rw");
        //根据randomAccessFile 获取fileChannel
        FileChannel channel = RandomAccessFile.getChannel();

        /**
         * 设置模式为读写模式 位置从0开始 总共长度为5
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE,0,5);

        mappedByteBuffer.put(0,(byte)'h');//设置第一个字节为h   一个汉子在这边等于3个字节

        mappedByteBuffer.put(1,(byte)'o');
        mappedByteBuffer.put(2,(byte)'w');
        //如果把一个文件在mac的一个汉子只修改一个字节会出问题
        /*mappedByteBuffer.put(3,(byte)'w');*/

        channel.close();

    }

}
