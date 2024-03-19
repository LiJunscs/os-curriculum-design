package com.os.controller;

import com.os.pojo.PCB;
import com.os.pojo.Result;
import com.os.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: lj
 * @desc
 * @create: 2024.03.03
 **/
@RequestMapping("/process")
@RestController
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping("/detail/{processId}")
    public Result getProcessDetail(@PathVariable("processId") String processId) {
        // TODO: 实现获取进程详情的逻辑
        PCB processDetail = processService.getProcessDetail(processId);
        return Result.success("获取进程详情成功", processDetail);
    }

    @GetMapping("/list")
    public Result listProcesses() {
        // TODO: 实现获取进程列表的逻辑
        List<PCB> processList = processService.getAllProcesses();
        return Result.success("获取进程列表成功", processList);
    }

    @PostMapping("/terminate/{processId}")
    public Result terminateProcess(@PathVariable("processId") String processId) {
        // TODO: 实现终止进程的逻辑
        return Result.success("进程终止成功");
    }

    @PostMapping("/create")
    public Result createProcess(@RequestBody PCB pcb) {
        // TODO: 实现创建进程的逻辑
        PCB createdProcess = null;
        return Result.success("进程创建成功", createdProcess);
    }
}

