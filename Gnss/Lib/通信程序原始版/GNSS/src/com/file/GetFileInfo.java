package com.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Harry on 2016/4/26.
 */
public class GetFileInfo {
    public static String[] getFileNames(String directory) {
        //获得某个目录下的所有文件路径
        //ArrayList 和 数组 中都存储着 这个文件夹下的txt文件，使用时需要拼接文件夹的前缀~~~
        File path = new File(directory);
        ArrayList<String> al_names = new ArrayList<String>();
        String filename = "";
        String[] str_array = {"txt"};
        String[] names = {};
        Iterator it = FileUtils.iterateFiles(path, str_array, true);
        while (it.hasNext()) {
            //FileUtils.moveFileToDirectory((File) it.next(),new File(to),true);

            filename = ((File) it.next()).getName();
            al_names.add(filename);
        }
        int size = al_names.size();
        names = (String[])al_names.toArray(new String[size]);
        System.out.println("路径 "+directory+" 下的文件："+al_names);
        //System.out.println("222222222"+names[0]+names[1]);
        return names;
    }
}