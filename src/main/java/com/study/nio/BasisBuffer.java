package com.study.nio;

import java.nio.IntBuffer;

/**
 *
 *
 */
public class BasisBuffer {

    public static void main(String[] args) {
        //创建一个容量为5的intbuffer   position 表示指针指向的下标 limit 表示能添加几位  capacity 容量
        IntBuffer allocate = IntBuffer.allocate(5);
        allocate.limit(3);//长度为多少 ,如果设置
        allocate.position(1);//可以设置角标是多少
        //根据容量大小插入数据

        int size=allocate.limit()-allocate.position();
        for (int i=1;i<=size;i++){
            allocate.put(i*2);
            System.out.println("插入几个数据开始出错了=");
        }

        //如果需要读取数据需要进行反转，因为每次添加时，指针会往下移动
        allocate.flip();

        while (allocate.hasRemaining()){
            int i = allocate.get();
            System.out.println(i);
        }

    }


}
