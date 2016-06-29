package com.main;

import com.file.GetFileInfo;
import com.file.Move;
import com.invoking.Invoke;

import java.io.IOException;
import java.util.Scanner;
/**
 * Created by Harry on 2016/4/25.
 * 调用程序1：代替模拟器生成.txt
 *       参数分别是：1: C:/a.exe 2:exe参数arga 3:argb 4:argc 5:生成txt文件的文件目录
 * 调用程序2: 向模拟器发送指令或者数据，然后获取相应的反馈信息
 *       参数分别是：默认值
 *                  自定义值：IP  PORT  Commands  pathTOstoreDataFromSimgen
 */
public class Start {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("接下来输入所需要的参数，依次是----\n 1: 调用程序的路径 2:exe参数arga 3:argb 4:argc 5:生成txt文件的文件目录  \n" +
                "请以回车换行为间隔，依次输入上述参数\n ------------------------------------");
        System.out.println("调用程序的绝对路径：");
        String exe_path = sc.nextLine();
        System.out.println("输入第一个参数（起始时间）：");
        String args1 = sc.nextLine();
        System.out.println("输入第二个参数（截止时间）：");
        String args2 = sc.nextLine();
        System.out.println("输入第三个参数（时间间隔）：");
        String args3 = sc.nextLine();
        System.out.println("调用程序所生成的txt文件目录的绝对路径：");
        String txt_path = sc.nextLine();

        try {
            Invoke.invokeExe(exe_path,args1,args2,args3);
        } catch (IOException e) {
            System.out.println("1.Start中调用invokeExe出错！！！");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("2.Start中调用invokeExe出错！！！");
            e.printStackTrace();
        }
        String to = "D:\\Txt_backup";
        //移动txt文件
        Move.moveFile(txt_path,to);
        //获得所有的txt文件路径，调用两个子程序时传参
        String[] txtPath_array = GetFileInfo.getFileNames(to);
        System.out.println("输出相应的txt路径");
        for (int i = 0;i<txtPath_array.length;i++){
            System.out.println(to+"\\"+txtPath_array[i]);
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~程序第一部分运行结束~~~~~~~~~~~~~~~~~");
        String path = "F:\\Done\\ConSIM\\conSim.exe";
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
