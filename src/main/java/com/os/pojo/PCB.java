package com.os.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PCB {

    // 进程状态
    public static final Integer RUNNING = 1;
    public static final Integer READY = 2;
    public static final Integer BLOCKING = 3;

    // 指令执行结果
    public static final Integer NORMAL = 11;
    public static final Integer TRAP = 12;
    public static final Integer INTERRUPT = 13;


    private Long processId; // 进程ID
    private Integer state; // 进程状态
    private Integer priority; // 进程优先级
    private Integer programCounter; // 程序计数器
    private Integer pageOccupied; // 进程占用内存页数量
    private Integer vMemory;    // 虚拟内存起始地址
    private List<String> commands;

    public PCB(Long processId, Integer state, Integer priority, Integer programCounter, Integer pageOccupied, Integer vMemory, List<String> commands) {
        this.processId = processId;
        this.state = state;
        this.priority = priority;
        this.programCounter = programCounter;
        this.pageOccupied = pageOccupied;
        this.vMemory = vMemory;
        this.commands = commands;
    }

    public String run(){
        String tmp = "terminal";
        if(programCounter < commands.size()) {
            tmp = commands.get(programCounter);
            programCounter++;
        }
        return tmp;
    }
}
