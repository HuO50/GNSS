package com.file;

/**
 * Created by Harry on 2016/4/25.
 * 用于移动文件
 */
import org.apache.commons.io.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Move {

    public static void moveFile(String from,String to){
        //将文件夹下的所有文件，移动到另一个文件夹中
        File from_file = new File(from);
        String[] str_array = {"txt"};
        Iterator it = FileUtils.iterateFiles(from_file,str_array,true);
        while (it.hasNext()){
            try {
                FileUtils.moveFileToDirectory((File) it.next(),new File(to),true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void copyFile(String from,String to) throws IOException {
        //拷贝一个文件夹下的所有文件到指定文件夹下
        FileUtils.copyDirectory(new File(from),new File(to));
    }

}
