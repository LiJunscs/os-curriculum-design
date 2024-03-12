package com.os.controller;

public class PCB {
    private String processId; // 进程ID
    private int state; // 进程状态
    private int priority; // 进程优先级
    private int programCounter; // 程序计数器
    private int memoryOccupied; // 进程占用内存大小

    // 构造函数
    public PCB(String processId, int state, int priority, int programCounter, int memoryOccupied) {
        this.processId = processId;
        this.state = state;
        this.priority = priority;
        this.programCounter = programCounter;
        this.memoryOccupied = memoryOccupied;
    }

    // 获取进程ID
    public String getProcessId() {
        return processId;
    }

    // 设置进程ID
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    // 获取进程状态
    public int getState() {
        return state;
    }

    // 设置进程状态
    public void setState(int state) {
        this.state = state;
    }

    // 获取进程优先级
    public int getPriority() {
        return priority;
    }

    // 设置进程优先级
    public void setPriority(int priority) {
        this.priority = priority;
    }

    // 获取程序计数器
    public int getProgramCounter() {
        return programCounter;
    }

    // 设置程序计数器
    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    // 获取进程占用内存大小
    public int getMemoryOccupied() {
        return memoryOccupied;
    }

    // 设置进程占用内存大小
    public void setMemoryOccupied(int memoryOccupied) {
        this.memoryOccupied = memoryOccupied;
    }
}
