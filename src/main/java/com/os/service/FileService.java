package com.os.service;

import com.os.pojo.FCB;
import com.os.pojo.PCB;

import java.util.List;

/**
 * @author: lj
 * @desc
 * @create: 2024.03.03
 **/
public interface FileService {
    List<FCB> getAllFiles() ;

    void updateFile(FCB fcb) ;

    void renameFile(FCB fcb) ;

    PCB readFile(String fileId) ;

    void deleteFile(String fileId);
}
