package com.os.service.impl;

import com.os.pojo.FCB;
import com.os.service.FileSystemService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: lj
 * @desc
 * @create: 2024.03.03
 **/
@Service
public class FileSystemServiceImpl implements FileSystemService {

    /**
     * @param is_folder     是否为文件夹
     * @param name          文件/文件夹名称
     * @param parent_folder 父文件夹
     * @return 文件/文件夹对象FCB；重名返回-1；名称为root返回-2；磁盘待定
     */
    @Override
    public Object createFileOrFolder(boolean is_folder, String name, FCB parent_folder) {
        if (is_folder) {
            if (parent_folder != null) {
                for (FCB child_node : parent_folder.child_nodes) {
                    if (child_node.fname.equals(name))
                        return -1;
                }
            }
            FCB new_folder = new FCB(FCB.FOLDER, name, parent_folder, FCB.READONLY, false);
            if (!"root".equals(name)) {
                FCB.File_Table.add(new_folder);
                parent_folder.child_nodes.add(new_folder);
            } else return -2;
            return new_folder;

        } else {
            for (FCB child_node : parent_folder.child_nodes) {
                if (child_node.fname.equals(name))
                    return -1;
            }
            FCB new_file = new FCB(FCB.FILE, name, parent_folder, FCB.READ_AND_WRITE, false);
            if (!"root".equals(name)) {
                FCB.File_Table.add(new_file);
                parent_folder.child_nodes.add(new_file);
            } else return -2;
            //磁盘分配待定
            return new_file;
        }
    }


    /**
     * 获取文件/文件夹路径
     *
     * @param is_folder     文件/文件夹标志位
     * @param target_folder 母的文件夹
     * @param target_file   目的文件
     * @return 路径字符串
     */
    @Override
    public String getPath(boolean is_folder, FCB target_folder, FCB target_file) {
        if (is_folder) {
            if (target_folder.fname.equals("root")) {
                return "/root";
            }
        }
        String curr_path;
        FCB parent_node;
        if (!is_folder) {
            curr_path = target_file.fname;
            parent_node = target_file.parent_folder;
        } else {
            curr_path = target_folder.fname;
            parent_node = target_folder.parent_folder;
        }
        return getPath(true, parent_node, target_file) + '/' + curr_path;
    }

    /**
     * 根据文件fid寻找fcb
     *
     * @param id 文件fid
     * @return -1表示未找到；文件fcb
     */
    @Override
    public Object findById(String id) {
        for (FCB fcb : FCB.File_Table) {
            if (Objects.equals(fcb.fid, id)) {
                return fcb;
            }
        }
        return -1;
    }

    @Override
    public int updateFile(String file_name, String content) {//更新写入
        FCB target = findFileOrFolderByName(file_name, FCB.root);
        if (target == null || target.token == FCB.FOLDER) {
            System.out.println("该文件不存在");//异常处理
            return 0;
        }
        if (target.authority == FCB.READONLY) {
            System.out.println("文件权限不够！");//异常处理
            return -1;
        } else {
//            clearFileInDisk();
            target.content = content;
            target.size = getSize(target.content);
//            target.disk_position=
            return 1;
        }
    }

    public int getSize(String content) {
        return content.getBytes().length;
    }

    /**
     * 文件读取
     *
     * @param file_name 文件名
     * @return 0文件不存在，-1权限不够，List<String>文件内容
     */
    @Override
    public Object readFile(String file_name) {
        FCB target = findFileOrFolderByName(file_name, FCB.root);
        if (target == null || target.token == FCB.FOLDER) {
            System.out.println("该文件不存在");//异常处理
            return 0;
        }
        if (target.authority == -1) {//访问权限还未设置
            System.out.println("文件权限不够，无法读取");
            return -1;
        } else
            return target.content;
    }

    /**
     * 删除文件
     *
     * @param file_name 文件名
     * @return 返回操作值
     */
    @Override
    public int deleteFile(String file_name) {
        FCB target = findFileOrFolderByName(file_name, FCB.root);
        if (target == null || target.token == FCB.FOLDER) {
            System.out.println("该文件不存在");//异常处理
            return 0;
        }
        //clearFileInDisk()
        FCB.File_Table.remove(target);
        target.parent_folder.child_nodes.remove(target);
        return 1;
    }

    /**
     * 根据文件名/文件夹名找fcb
     *
     * @param name        文件名
     * @param parent_node 父节点
     * @return 文件fcb
     */
    @Override
    public FCB findFileOrFolderByName(String name, FCB parent_node) {
        if (parent_node.child_nodes.isEmpty()) {
            return null;
        }
        for (FCB child_node : parent_node.child_nodes) {
            if (name.equals(child_node.fname)) {
                return child_node;
            }
        }
        for (FCB child_node : parent_node.child_nodes) {
            if (child_node.token == FCB.FOLDER) {//文件夹token为2
                FCB target = findFileOrFolderByName(name, child_node);
                if (target != null) {
                    return target;
                }
            }
        }
        return null;
    }

    /**
     * 重命名文件
     *
     * @param old_name 旧名称
     * @param new_name 新名称
     * @return 返回操作值
     */
    @Override
    public int renameFile(String old_name, String new_name) {
        FCB file_or_folder = findFileOrFolderByName(old_name, FCB.root);
        if (file_or_folder == null) {
            System.out.println("找不到该文件！"); //异常处理
            return 0;
        }
        for (FCB child_node : file_or_folder.parent_folder.child_nodes) {
            if (new_name.equals(child_node.fname)) {
                System.out.println("新名称在该路径下已存在！");
                return -1;
            }
        }
        file_or_folder.fname = new_name;
        return 1;
    }

    /**
     *
     * @param path
     * @param IR 和controller的指令交互
     * @return
     */
    @Override
    public Object pathToOP(String path, HashMap IR) {//调不了非static
        path = path.replace(" ", "");
        String[] split = path.split("/");
        String[] new_path = new String[0];
        if (split[0].equals("")) {
            new_path = Arrays.copyOfRange(split, 1, split.length);
        }
        if (new_path.length < 1 || !new_path[0].equals("root")) {
            return 0;
        }
        FCB parent_node = FCB.root;
        ArrayList<String> child_names = new ArrayList<>();
        for (int i = 0; i < parent_node.child_nodes.size(); i++) {
            child_names.add(parent_node.child_nodes.get(i).fname);
        }
//        for (FCB child : parent_node.child_nodes) {//null算一个对象
//            child_names.add(child.fname);
//        }
        for (int i = 1; i < new_path.length; i++) {
            if (i == new_path.length - 1) {
                if (IR.isEmpty()) {
                    return parent_node.child_nodes.get(child_names.indexOf(new_path[i]));
                } else if (IR.get("operator").equals("createFile")) {
                    return createFileOrFolder(false, new_path[i], parent_node);
                } else if (IR.get("operator").equals("createFolder")) {
                    return createFileOrFolder(true, new_path[i], parent_node);
                } else {
                    //文件不存在
                    if (!child_names.contains(new_path[i])) {
                        return 0;
                    }
                    FCB target = parent_node.child_nodes.get(child_names.indexOf(new_path[i]));
                    if (IR.get("operator").equals("readFile")) {
                        if (target.authority == FCB.WRITEONLY || target.authority == FCB.NEITHER_READ_NOR_WRITE) {//权限不够
                            return -1;
                        } else
                            return target.content;
                    } else if (IR.get("operator").equals("updateFile")) {
                        if (target.authority == FCB.READONLY || target.authority == FCB.NEITHER_READ_NOR_WRITE) {//权限不够
                            return -1;
                        } else {//更新写入
                            target.content = ((String) IR.get("content"));
                            target.size = getSize(target.content);
//                            target.disk_position
                            return 1;
                        }
                    } else if (IR.get("operator").equals("deleteFile")) {
                        if (target.token == FCB.FOLDER) {
                            return 0;
                        } else {
                            FCB.File_Table.remove(target);
                            target.parent_folder.child_nodes.remove(target);
                            return 1;
                        }
                    } else if (IR.get("operator").equals("rename")) {
                        if (child_names.contains((String)IR.get("newName"))){
                            System.out.println("该名称已存在");
                            return -1;
                        }else{
                            target.fname= (String) IR.get("newName");
                            return 1;
                        }
                    } else if (IR.get("operator").equals("changeAuthority")) {
                        target.authority= Integer.parseInt((String) IR.get("authority"));
                        return 1;
                    }
                }
            }
            else if(child_names.contains(new_path[i])){
                parent_node = parent_node.child_nodes.get(child_names.indexOf(new_path[i]));
                child_names.clear();
                //改了
                for (int j = 0; j < parent_node.child_nodes.size(); j++) {
                    child_names.add(parent_node.child_nodes.get(j).fname);
                }
            }
            else
                return 0;
        }
        return null;
    }

    public Object pathToList(String path, HashMap IR) {
        path = path.replace(" ", "");
        String[] split = path.split("/");
        String[] new_path = new String[0];
        if (split[0].equals("")) {
            new_path = Arrays.copyOfRange(split, 1, split.length);
        }
        if (new_path.length < 1 || !new_path[0].equals("root")) {
            return 0;
        }
        FCB parent_node = FCB.root;
        ArrayList<String> child_names = new ArrayList<>();
        for (int i = 0; i < parent_node.child_nodes.size(); i++) {
            child_names.add(parent_node.child_nodes.get(i).fname);
        }

        for (int i = 0; i < new_path.length; i++) {
            if (i == new_path.length - 1) {
                if (IR.get("operator").equals("list")){
                    return parent_node.child_nodes;
                }
            }
            else if(child_names.contains(new_path[i+1])){
                parent_node = parent_node.child_nodes.get(child_names.indexOf(new_path[i+1]));
                child_names.clear();
                //改了
                for (int j = 0; j < parent_node.child_nodes.size(); j++) {
                    child_names.add(parent_node.child_nodes.get(j).fname);
                }
            }
            else
                return 0;
        }
        return 10086;
    }


}
