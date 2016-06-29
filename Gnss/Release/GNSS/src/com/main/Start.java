package com.main;

import com.file.GetFileInfo;
import com.file.Move;
import com.invoking.Invoke;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
/**
 * Created by Harry on 2016/4/25.
 * 调用程序1：代替模拟器生成.txt
 *       参数分别是：1: C:/a.exe 2:exe参数arga 3:argb 4:argc 5:生成txt文件的文件目录
 * 调用程序2: 向模拟器发送指令或者数据，然后获取相应的反馈信息
 *       参数分别是：默认值
 *                  自定义值：IP  PORT  Commands  pathTOstoreDataFromSimgen
 */

/**
 * Joshua Created on 2016/6/21
 *  框架结构如下:
 *                      总程序
 *  1, 文件处理  2, 时间控制  3, 调用C++模拟器部分  4, 命令拼接存储部分
 *
 *  1, 文件处理使用java实现, 完成主要功能为, copy原始数据文件,分别进入log和file文件中, log中数据作为备份数据
 *
 *  2, 时间控制,郭朋师兄已经写完.
 *
 *  3, 调用C++ 模拟器进行通讯,主要的任务是通过之前的order.txt文件构造出来所需要用的命令行,然后发送给模拟器进行执行
 *
 *  4, 命令拼接存储,是为了从1中获得的文件操作拼接成一个order.txt的文件,order.txt的使用是使用java从file中读取一次的文件
 *  然后存到order.txt中,c++的文件从order.txt的文件中一次读取一条,然后发给模拟器
 *
 */
public class Start {
    public static void main(String[] args){


        /**
         * Joshua create on 2016/6/21
         * 获取当前文件路径
         */
        System.out.println("***********获取程序路径**********");
        File directory = new File("");
        String courseFileDir = "";
        try {
            courseFileDir = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(courseFileDir);
        System.out.println("********************************");

        /**
         * Joshua Create on 2016/6/21
         * 读写Log进行log操作
         */
        String logDir = courseFileDir + "\\Log\\Op\\Log.txt";
        System.out.println(logDir);
        FileWriter fileWriter = null;
        try {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String initTime =  simpleDateFormat.format(date);
            fileWriter = new FileWriter(logDir, true);
            fileWriter.write("Log has been initial Sucussfully on " + initTime + "\r\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("加载日志出错!");
        }
//        FileOutputStream fileOutputStream = new FileOutputStream(logDir);
//        BufferedWriter bufferedWriter = new BufferedWriter(fileOutputStream);

        /**
         * Joshua create on 2016/6/21
         * 首先将要进行处理的文件夹移动到Log和File文件夹中,Log中是历史记录,File文件夹是为了对文件中的命令进行操作
         *
         */
        String logData = ".\\Log\\Data";
        String fileOrder = ".\\File\\Data";
        String txt_path = ".\\Work";
        try {
            Move.copyFile(txt_path, logData);
            Move.moveFile(txt_path, fileOrder);
        } catch (IOException e){
            System.out.println("处理原始数据出错!请检查日志获取具体问题!");
            Date now = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String wrongTime = simpleDateFormat.format(now);
            try {
                fileWriter = new FileWriter(logDir, true);
                fileWriter.write("原始数据移动出错,请检查Work文件夹下的数据文件是否完整,错误发生时间为:");
                fileWriter.write(wrongTime);
                fileWriter.write("\r\n");
                fileWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        /**
         * 数据成功移动后进行记录以及显示
         */
        {
            System.out.println("处理原始数据成功!");
            Date now = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String sucTime = simpleDateFormat.format(now);
            try {
                fileWriter = new FileWriter(logDir, true);
                fileWriter.write("移动数据成功,发生时间为:");
                fileWriter.write(sucTime);
                fileWriter.write("\r\n");
                fileWriter.close();
            } catch (IOException el){
                el.printStackTrace();
            }
        }

        /**
         * Joshua Create on 2016/6/21
         * 调用conSIM程序进行传输看数据处理,传入参数进行时间控制
         *
         */
        Scanner sc = new Scanner(System.in);
        System.out.println("接下来输入所需要的参数，依次是----\n" +
                "1: 调用程序的路径 2:exe参数arga 3:argb 4:argc 5:生成txt文件的文件目录  \n" +
                "请以回车换行为间隔，依次输入上述参数\n ------------------------------------");
        System.out.println("simGEN.exe所在路径："); //需要修改成为当前文件下的
        String exe_path = sc.nextLine();
        System.out.println("输入第一个参数（起始时间）：");
        String args1 = sc.nextLine();
        System.out.println("输入第二个参数（截止时间）：");
        String args2 = sc.nextLine();
        System.out.println("输入第三个参数（时间间隔）：");
        String args3 = sc.nextLine();

        System.out.println("************************");
        System.out.println("调用程序开始执行");
        try {
            Invoke.invokeExe(exe_path,args1,args2,args3);
        } catch (IOException e) {
            System.out.println("1.Start中调用invokeExe出错！！！");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("2.Start中调用invokeExe出错！！！");
            e.printStackTrace();
        }


        /**
         * Joshua create on 2016/6/21
         * 下面这个是为了将模拟器传输的文件进行传输到两个子程序中进行计算,暂时不用写
         */
        //获得所有的txt文件路径，调用两个子程序时传参
        String[] txtPath_array = GetFileInfo.getFileNames(logData);
        System.out.println("输出相应的txt路径");
        for (int i = 0;i<txtPath_array.length;i++){
            System.out.println(logData+"\\"+txtPath_array[i]);
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~程序第一部分运行结束~~~~~~~~~~~~~~~~~");


        /**
         * Joshua Create on 2016/6/21
         * 下面这部分程序使为了调用模拟器进行通信
         */
        String path = "..\\Exec\\ConSIM\\conSIM.exe";
        try {
            Invoke.invokeExe(path);
        } catch (IOException e) {
            System.out.println("1程序第二部分调用出现错误！！！");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("2程序第二部分调用出现错误！！！");
            e.printStackTrace();
        }
    }
}
