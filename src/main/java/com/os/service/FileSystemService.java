package com.os.service;

import com.os.pojo.FCB;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * @author: lj
 * @desc
 * @create: 2024.03.03
 **/
public interface FileSystemService {
    //创建文件/文件夹
    Object createFileOrFolder(boolean is_folder,String name,FCB parent_folder);
    //获取文件/文件夹的路径
    String getPath(boolean is_folder, FCB target_folder, FCB target_file);
    //通过id找文件
    Object findById(String id);
    FCB findFileOrFolderByName(String name,FCB parent_node);
    int updateFile(String file_name,String content);
    Object readFile(String file_name);
    int deleteFile(String file_name);
    int renameFile(String old_name, String new_name);
    Object pathToOP(String path, HashMap IR);
    Object pathToList(String path, HashMap IR);
}
