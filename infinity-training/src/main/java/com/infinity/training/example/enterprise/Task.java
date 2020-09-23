package com.infinity.training.example.enterprise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 任务
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/09/22 22:40
 */
@Data
@Builder
@AllArgsConstructor
public class Task {

    /**
     * 任务标识
     */
    private String taskNo;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务状态(0-初始，1-执行中，2-完成)
     */
    private Integer status;

    /**
     * 所属雇员工号
     */
    private String employeeNo;

    public Task() {
    }

    /**
     * 被随机分配
     */
    public void randomAssigned() {
        EnterpriseConstants.randomEmployee().takeTask(this);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskNo='" + taskNo + '\'' +
                ", name='" + name + '\'' +
                ", employeeNo='" + employeeNo + '\'' +
                '}';
    }
}
