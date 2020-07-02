package com.study.nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * 设置buffer只读
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {
        //创建bytebuffer
        IntBuffer allocate = IntBuffer.allocate(10);
        //设置buffer值
        int limit = allocate.limit();
        int position = allocate.position();
        for (int i=0;i<limit-position;i++){
            allocate.put(i*2);
        }
        allocate.flip();
        //设置buffer只读
        IntBuffer intBuffer = allocate.asReadOnlyBuffer();
        // 在该处设置了 buffer是只读，如果再进行写操作的话会出现   java.nio.ReadOnlyBufferException
        //allocate buffer 还是可以读写的
       // intBuffer.put(0);
        //position < limit  如果position往下移会出现数据丢失
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
