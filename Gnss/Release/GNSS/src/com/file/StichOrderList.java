package com.file;

import java.io.*;

/**
 * Created by Joshua on 2016/6/22.
 */
public class StichOrderList {
    /**
     * Joshua Create on 2016/6/22
     * orderList 函数用于操作数据文件中的内容,将内容移动到orderList.txt中
     * 然后会删除.\File\Date文件夹下的文件数据
     */
    public void orderList(String DataDir, String OrderDir){
        /**
         * Joshua create on 2016/6/22
         * 获取DataDir目录中所有txt文件,先获取第一个文件,dir数组中已经存储了路径
         */
        String[] dirCome = GetFileInfo.getFileNames(DataDir);
        String[] dir = new String[dirCome.length];
        /**
         * Joshua create on 2016/6/23
         * 完成的txt定位的工作,现在就要实现拼接的任务
         * 打开一个文件并且将所有内容复制到OrderLIst.txt
         */
        for (int i = 0; i < dir.length; i++) {
            dir[i] = DataDir + "\\" + dirCome[i];//完成路径的拼接
            FileWriter fileWriter = null;
            BufferedReader bufferedReader = null;
            try {
                fileWriter = new FileWriter(OrderDir, true);//FileWriter(OrderDir, true)这里是目的文件名
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(dir[i]), "GB2312"));//FileInputStream(DataDir)这个地方是源文件所在文件夹
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.print("文件内容:" + line);
                    fileWriter.write(line + "\r\n");
                    fileWriter.flush();
                }
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Joshua Create on 2016/6/23
     * manageOrderList 函数用于构成发送的命令的形式,命令的结构来自于pdf中MOT命令的格式
     * 当我看到现在的文件大小(12M)的时候,我会觉得这个文件读写的时候会非常慢,应该在读写文件的
     * 时候直接就对指令操作
     */

    /**
     * Joshua create on 2016/6/23
     * 附件中是30秒时长的1ms间隔的轨道数据文件。其中SatCoord_G01.txt、SatCoord_G02.txt分别
     * 代表GPS卫星1号星和2号星的轨道坐标文件，SatCoord_LEO.txt代表LEO（低轨卫星）的轨道坐标
     * 文件。
     *格式为：
     * GPS周  周内秒   卫星号    X(m)   Y(m)   Z(m)     VX(m/s)     VY(m/s)    VY(m/s)    ACCX(m/s^2)     ACCY(m/s^2)    ACCY(m/s^2)    dT(s)
     *  1839  522000.0720   2  -14632406.7627  -6352899.6034  -20927767.0998  360.1371  -2719.9477  518.5324  00.0000  00.0000  00.0000  00.00055900889
     *  以上是源文件格式
     *  转化成为目标文件格式:
     *  <timestamp>,MOT,<veh_mot>,
     *  <x>,<y>,<z>,
     *  <vel_x>,<vel_y>,<vel_z>,
     *  <acc_x>,<acc_y>,<acc_z>,
     *  <jerk_x>,<jerk_y>,<jerk_z>,
     *
     *  当我看到Use the literal "-"instead of a time format to action the command as soon as it is received.
     *  这里的时候,明白了如果是想按照原定的格式的话时间戳应该是[<d>Δ]<hh>:<mm>:<ss>[.<ms>]但是如果想让模拟器立刻执行
     *  就应该直接使用"-"符号,所以timestamp应该为 "-", 第二个字段应该是"MOT"指令, 第三个指令要根据判断变成V(飞行器号码)_M1
     *
     * 这样manageOrderList的函数功能就明白了,读取文件中的一行后,先加入"-","MOT"字段,然后根据空格判断位置,当到达卫星号
     * 将卫星号字段加入vn_m1,启动n代表了卫星号,剩下的字段都是用","进行隔开
     *
     */

    public void manageOrderList(String dir, String dir2){
        /**
         *  Joshua create on 2016/6/23
         *  首先读入一行数据,然后按照上面的所需要的格式处理现有数据
         *  现在不确定的内容是:
         *  第一:按照它的时间(卫星周),如何转换成为现有的timestamp数据格式,这个是我比较担心的
         *  第二:需不需要初始化模拟器等命令,还是直接操作
         */
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(dir), "GB2312"));
            PrintStream printStream = new PrintStream(new FileOutputStream(dir2));
            String line = null;
            String[] firstSplit = null;
            String[] secondSplit = null;
            String temp = null;
            String out = "-,";
            while((line = bufferedReader.readLine())!=null){
                /**
                 * Joshua Create on 2016/6/25
                 * 完成字符串的拼接功能
                 */
                line = line.replaceAll("  ", ",");
                firstSplit = line.split(" ");
                firstSplit[0] = "MOT,";
                secondSplit = firstSplit[1].split(",");
                temp = "v"+secondSplit[0] + "_m1,";
                out = out + firstSplit[0] + temp;
                for (int i = 1; i < secondSplit.length-1; i++){
                    out = out + secondSplit[i] + ",";
                }
                /**
                 * 补齐剩下的数字位
                 */
                for (int i = 0; i < 3; i++){
                    out = out + "00.0000,";
                }
                out = out.substring(0, out.length()-1);
                /**
                 * 写入文件中
                 */
                printStream.print(out + "\r\n");
                out = "-,";
            }
            bufferedReader.close();
            printStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
