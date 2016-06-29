package com.test;

import com.file.GetFileInfo;

/**
 * Created by Harry on 2016/4/26.
 */
public class GetfileinfoTest {
    public static void main(String[] args){
        String[] a = GetFileInfo.getFileNames("E:\\HAHA");
        System.out.println("---------------------------");
        System.out.println(a);
    }

}
