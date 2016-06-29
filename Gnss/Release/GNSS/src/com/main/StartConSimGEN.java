package com.main;

import com.invoking.Invoke;

import java.io.IOException;

/**
 * Created by Harry on 2016/5/6.
 *
 */
public class StartConSimGEN {
    public static void main(String[] args) throws IOException, InterruptedException {
        String path = "F:\\Done\\ConSIM\\conSim.exe";   //这个地址是调用conSIM程序的,conSIM程序也需要改
        Invoke.invokeExe(path); //调用程序
    }
}
