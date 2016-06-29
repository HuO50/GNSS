package com.test;

import com.file.GetFileInfo;
import com.file.StichOrderList;

/**
 * Created by Joshua on 2016/6/22.
 */
public class StichOrderListTest {

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        System.out.println("程序开始");
        String DataDir = "G:\\项目\\空间所\\空间所程序\\Gnss\\File\\Data";
        String OrderDir = "G:\\项目\\空间所\\空间所程序\\Gnss\\File\\Order\\OrderList_temp.txt";
        String test = "G:\\项目\\空间所\\空间所程序\\Gnss\\File\\Order\\test.txt";
//        System.out.println(DataDir);
//        System.out.println(OrderDir);

//        GetFileInfo getFileInfo = new GetFileInfo();
//        getFileInfo.getFileNames("G:\\项目\\空间所\\空间所程序\\Gnss\\File\\Data");

        StichOrderList stichOrderList = new StichOrderList();
//        stichOrderList.orderList(DataDir, OrderDir);
        System.out.println(test);
        stichOrderList.manageOrderList(test, OrderDir);
        System.out.println("程序执行结束");
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间:" + (endTime - startTime) + "ms");
    }
}
