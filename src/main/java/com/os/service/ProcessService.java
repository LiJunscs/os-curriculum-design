package com.os.service;

import com.os.pojo.PCB;

import java.util.List;

/**
 * @author: lj
 * @desc
 * @create: 2024.03.03
 **/
public interface ProcessService {
    PCB getProcessDetail(String processId);

    List<PCB> getAllProcesses();

    PCB createProcess(Integer priority, Integer pageCount, List<String> commands);

    Boolean terminalProcess(Integer pid);

    Integer dispatch();
}
