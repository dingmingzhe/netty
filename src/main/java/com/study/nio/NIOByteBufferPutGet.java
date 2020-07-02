package com.study.nio;

import java.nio.ByteBuffer;

/**
 * byteButter测试
 */
public class NIOByteBufferPutGet {

    public static void main(String[] args) {
        //创建一个Buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);

        //类型化方式放入数据
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('尚');
        buffer.putShort((short) 4);

        //取出
        buffer.flip();

        System.out.println();
        //指针在指向的时候和使用什么函数进行获取是有关系的，如果不按照顺序进行随便获取会出现 java.nio.BufferUnderflowException
        //超出了原有的长度
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getInt());



    }
}
