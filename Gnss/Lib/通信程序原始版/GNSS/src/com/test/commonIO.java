package com.test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Harry on 2016/4/25.
 */
public class commonIO {
    public static void main(String[] args){
        File file = new File("E:\\joke");
        String[] str_array = {"txt"};
        Iterator it = FileUtils.iterateFiles(file,str_array,true);
        while (it.hasNext()){
            try {
                FileUtils.moveFileToDirectory((File) it.next(),new File("E:\\HAHA"),true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
