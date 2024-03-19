package com.os.service.impl;

import com.os.pojo.PCB;
import com.os.service.ProcessService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lj
 * @desc
 * @create: 2024.03.03
 **/
@Service
public class ProcessServiceImpl implements ProcessService {
    @Override
    public PCB getProcessDetail(String processId) {
        return null;
    }

    @Override
    public List<PCB> getAllProcesses() {
        return null;
    }
}
