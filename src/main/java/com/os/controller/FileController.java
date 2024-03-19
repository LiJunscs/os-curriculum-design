package com.os.controller;

import com.os.pojo.FCB;
import com.os.pojo.PCB;
import com.os.pojo.Result;
import com.os.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private FileService fileService;

    @PostMapping("/createFile")
    public Result createFile(@RequestBody FCB fcb) {
        // TODO: 创建文件
        // 不返回FCB
        return Result.success("文件创建成功");
    }

    @DeleteMapping("/delete/{fileId}")
    public Result deleteFile(@PathVariable("fileId") String fileId) {
        // TODO: 删除文件
        fileService.deleteFile(fileId);
        return Result.success("文件删除成功");
    }

    @PostMapping("/createFolder")
    public Result createFolder(@RequestBody FCB fcb) {
        // TODO: 创建文件夹
        // 不返回文件夹
        return Result.success("文件夹创建成功");
    }

    @GetMapping("/list")
    public Result listFiles() {
        // TODO: 获取文件树
        List<FCB> fileList = fileService.getAllFiles();
        return Result.success("获取文件树成功", fileList);
    }

    @GetMapping("/read/{fileId}")
    public Result readFile(@PathVariable("fileId") String fileId) {
        // TODO: 读取文件
        PCB fileContent = fileService.readFile(fileId);
        return Result.success("读取文件成功", fileContent);
    }

    @PutMapping("/update")
    public Result updateFile(@RequestBody FCB fcb) {
        // TODO: 修改文件
        fileService.updateFile(fcb);
        return Result.success("写入文件成功");
    }

    @PutMapping("/rename")
    public Result renameFile(@RequestBody FCB fcb) {
        // TODO: 重命名
        fileService.renameFile(fcb);
        return Result.success("重命名成功");
    }
}
