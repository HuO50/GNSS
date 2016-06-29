package com.invoking;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Harry on 2016/4/25.
 */
public class Invoke {
    public static void invokeExe(String exe_path,String arg1,String arg2,String arg3) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(exe_path+" "+arg1+" "+arg2+" "+arg3);//

        /**
         * 擦!读别人的代码真是一个麻烦的事情,在完成这个工作的时候,师兄写了一个带参数的句子,
         * 就像这个Process process = Runtime.getRuntime().exec(exe_path+" "+arg1+" "+arg2+" "+arg3);
         * 把参数传递给一个叫做A.cpp的,并且存储在argv[]的参数数组中,这真是坑死我了,这个代码真的好麻烦,但是尝试一下
         * 我还以为师兄写完了时间控制,这样的话,我还得重新写,不过没有关系,我更明白这坑爹的逻辑了
         *Joshua Create on 2016/6/21
         */

        //获取c++程序的输出
        BufferedReader addResult = new BufferedReader(new InputStreamReader(process.getInputStream(),"GB2312"));
        String line;
        System.out.println("---------输出C++连接SimGEN程序反馈信息------");
        while((line=addResult.readLine())!=null)
        {
            System.out.println(line);
        }
        int exitcode = process.waitFor();
        System.out.println("执行完毕之后的状态码："+exitcode);
        if (exitcode==0){
            System.out.println("调用程序《《   "+exe_path+"》》   执行成功，并且调用完毕已关闭-----");
        }
    }

    public static void invokeExe(String exe_path) throws IOException, InterruptedException {
        System.out.println("调用程序连接SimGEN");
        Process process = Runtime.getRuntime().exec(exe_path);
        //获取c++程序的输出
        BufferedReader addResult = new BufferedReader(new InputStreamReader(process.getInputStream(),"GB2312"));
        String line;
        System.out.println("---------输出C++连接SimGEN程序反馈信息------");
        while((line=addResult.readLine())!=null)
        {
            System.out.println(line);
        }
        int exitcode = process.waitFor();
        System.out.println("执行完毕之后的状态码："+exitcode);
        if (exitcode==0){
            System.out.println("调用程序《《   "+exe_path+"》》   执行成功，并且调用完毕已关闭-----");
        }
    }

    public String[] getCommands(String program_name,String[] args){
        String[] pro = {program_name};
        ArrayList al_pro = new ArrayList(Arrays.asList(pro));
        ArrayList al_args = new ArrayList(Arrays.asList(args));
        al_pro.addAll(al_args);
        String[] commands = (String[]) al_pro.toArray();
        System.out.println("整合后的命令数组："+commands);
        return commands;
    }
    public String[] getCommands(String[] args){
        System.out.println("整合后的命令数组："+args);
        return args;

    }
}

    /*public static void main(String[] args) throws IOException, InterruptedException {
        String[] commands = {"WeChat.exe"};
        //File file = new File(path);
        Process process = Runtime.getRuntime().exec("F:\\WorkStation\\CFree\\c_test1\\mingw5\\c_test1.exe aa bb cc dd ee");
        InputStream fis = process.getInputStream();
        //用一个读输出流类去读
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        //逐行读取输出到控制台
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        int exitcode = process.waitFor();
        System.out.println("执行完毕之后的状态码："+exitcode);

    }*/