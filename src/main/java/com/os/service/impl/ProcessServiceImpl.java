package com.os.service.impl;

import com.os.pojo.GenID;
import com.os.pojo.PCB;
import com.os.service.ProcessService;
import com.os.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lj
 * @desc
 * @create: 2024.03.03
 **/
@Service
public class ProcessServiceImpl implements ProcessService {

    // 调度策略
    public static final int SSTF = 1;
    public static final int RR = 2;
    public static final int PRIORITY = 3;


    @Autowired
    private StorageService storageService;
    private Integer schedulingPolicy = RR;

    private PCB runningPCB;
    private List<PCB> readyQueue;
    private List<PCB> blockQueue;


    @Override
    public PCB getProcessDetail(String processId) {
        return null;
    }

    @Override
    public List<PCB> getAllProcesses() {
        return null;
    }

    @Override
    public PCB createProcess(Integer priority, Integer pageCount, List<String> commands) {
        PCB pcb = new PCB(Long.parseLong(GenID.uniqueNum()), PCB.READY, priority, 0, pageCount, storageService.alloc(pageCount), commands);
        readyQueue.add(pcb);
        return pcb;
    }

    @Override
    public Integer dispatch(){

        switch (schedulingPolicy){
            case SSTF:
                dispatchBySSTF();
                break;
            case RR:
                dispatchByRR();
                break;
            case PRIORITY:
                dispatchByPriority();
                break;
            default:
                System.out.println("错误的调度方法");
        }
        return 0;
    }

    @Override
    public Boolean terminalProcess(Integer pid) {
        return true;
    }

    private void dispatchBySSTF(){

    }

    private void dispatchByRR(){

    }

    private void dispatchByPriority(){

    }
}
