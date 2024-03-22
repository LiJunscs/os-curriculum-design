package com.os.controller;

import com.os.pojo.FCB;
import com.os.pojo.Result;
import com.os.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lj
 * @desc
 * @create: 2024.03.03
 **/
@RequestMapping("/file")
@RestController
public class FileController {
    /**
     *
     * 若一个类中需要使用另一个类的功能，只需要在类中加入另一个类的service实现即可
     * 例：当前类中需要一个与内存有关的功能，需要如下操作
     * @Autowired
     * private StorageService storageService;
     */
    @Autowired
    FileSystemService FSService;

    @PostMapping("/createFile")
    public Result createFile(@RequestBody Map<String, String> params){
        String new_file_path = params.get("new_file_path");
        //需要完整路径
        HashMap<String, String> IR = new HashMap<>();
        IR.put("operator","createFile");
        Object res = FSService.pathToOP(new_file_path,IR);
        if(res instanceof FCB){
            return Result.success("文件创建成功！");
        }
        else if(res.equals(-1)){
            return Result.fail("文件创建失败！","文件名称已存在！");
        }else{
            return Result.fail("文件创建失败！","文件名称不能为root！");
        }

    }
    @PostMapping("/createFolder")
    public Result createFolder(@RequestBody Map<String, String> params){
        String new_folder_path = params.get("new_folder_path");
        //需要完整路径
        HashMap<String, String> IR = new HashMap<>();
        IR.put("operator","createFolder");
        Object res = FSService.pathToOP(new_folder_path,IR);
        if(res instanceof FCB){
            return Result.success("文件夹创建成功！");
        }
        else if(res.equals(-1)){
            return Result.fail("文件夹创建失败！","文件夹名称已存在！");
        }else {
            return Result.fail("文件夹创建失败！","文件夹名称不能为root！");
        }
    }
    @GetMapping("/readFile")
    public Result readFile(@RequestParam String file_path){
        //需要完整路径
        HashMap<String, String> IR = new HashMap<>();
        IR.put("operator","readFile");
        Object res = FSService.pathToOP(file_path,IR);
        if(res.equals(-1)){
            return Result.fail("文件读取失败！","文件权限不够！");
        }else {
            return Result.success("文件读取成功！",res);
        }
    }

    @DeleteMapping("/deleteFile")
    public Result deleteFile(@RequestParam String file_path){
        //需要完整路径
        HashMap<String, String> IR = new HashMap<>();
        IR.put("operator","deleteFile");
        Object res = FSService.pathToOP(file_path,IR);
        if(res.equals(0)){
            return Result.fail("文件删除失败！","文件不存在！");
        }else {
            return Result.success("文件删除成功！");
        }
    }
    @PutMapping("/update")
    //TODO 参数是否合理
    public Result updateFile(@RequestBody Map<String, String> params){
        String file_path = params.get("file_path");
        String content = params.get("content");
        HashMap<String, String> IR = new HashMap<>();
        IR.put("operator","updateFile");
        IR.put("content",content);
        int res = (int) FSService.pathToOP(file_path,IR);
        if(res == -1){
            return Result.fail("文件更新失败！","没有写入权限！");
        }else {
            return Result.success("文件更新成功！");
        }
    }
    @PutMapping("/rename")
    //TODO 参数是否合理
    public Result renameFile(@RequestBody Map<String, String> params){
        String file_path = params.get("file_path");
        String newName = params.get("newName");
        HashMap<String, String> IR = new HashMap<>();
        IR.put("operator","rename");
        IR.put("newName",newName);
        int res = (int) FSService.pathToOP(file_path,IR);
        if(res == -1){
            return Result.fail("重命名失败！","该名称已存在！");
        }else {
            return Result.success("重命名成功！");
        }
    }

    @GetMapping("/list")
    public Result listFiles(@RequestParam String file_path) {
        HashMap<String, String> IR = new HashMap<>();
        IR.put("operator","list");
        List<FCB> file_tree = (List<FCB>) FSService.pathToList(file_path,IR);
        if (file_tree.isEmpty()){
            return Result.success("目录为空",file_tree);
        }
        return Result.success("获取目录下文件成功", file_tree);
    }

    @PutMapping("/changeAuthority")
    public Result changeAuthority(@RequestBody Map<String, String> params) {
        String file_path = params.get("file_path");
        String authority = params.get("authority");
        HashMap<String, String> IR = new HashMap<>();
        IR.put("operator", "changeAuthority");
        IR.put("authority",authority );
        int res = (int) FSService.pathToOP(file_path, IR);
        if (res == 0) {
            return Result.fail("权限更新失败！", "没有该文件！");
        } else {
            return Result.success("权限更新成功！");
        }
    }
}
