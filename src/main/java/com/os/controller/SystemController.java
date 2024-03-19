package com.os.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lj
 * @desc
 * @create: 2024.03.03
 **/
@RequestMapping("/system")
@RestController
public class SystemController {
    @GetMapping("/clock")
    public Integer clockUpdate(){
        // TODO 检测中断产生
        // TODO 检测当前正在运行的进程的运行状态
        // TODO 如果满足调度条件则进行调度
        // TODO 直到成功调度，返回系统时间
        return 0;
    }
}
