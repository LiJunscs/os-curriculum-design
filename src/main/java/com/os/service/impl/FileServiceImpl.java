package com.os.service.impl;

import com.os.pojo.FCB;
import com.os.pojo.PCB;
import com.os.service.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lj
 * @desc
 * @create: 2024.03.03
 **/
@Service
public class FileServiceImpl implements FileService {

    @Override
    public void deleteFile(String fileId) {

    }

    @Override
    public List<FCB> getAllFiles() {
        return null;
    }

    @Override
    public void updateFile(FCB fcb) {

    }

    @Override
    public void renameFile(FCB fcb) {

    }

    @Override
    public PCB readFile(String fileId) {
        return null;
    }
}
