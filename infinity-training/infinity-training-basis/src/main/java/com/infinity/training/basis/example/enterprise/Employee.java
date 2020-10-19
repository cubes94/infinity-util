package com.infinity.training.basis.example.enterprise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 雇员类
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/09/22 22:32
 */
@Data
@Builder
@AllArgsConstructor
public class Employee {

    /**
     * 雇员工号
     */
    private String employeeNo;

    /**
     * 雇员姓名
     */
    private String name;

    /**
     * 雇员性别
     */
    private Integer gender;

    /**
     * 雇员生日
     */
    private LocalDate birthday;

    /**
     * 雇员薪水
     */
    private Double salary;

    public Employee() {
    }

    /**
     * 接收任务
     *
     * @param task 任务
     */
    public void takeTask(Task task) {
        if (task == null || !Objects.equals(task.getStatus(), 0)) {
            throw new IllegalArgumentException("illegal task");
        }
        task.setEmployeeNo(this.employeeNo);
        task.setStatus(1);
    }

    /**
     * 接收任务
     */
    public synchronized void takeTask() {
        final Task task = EnterpriseConstants.peekInitTask();
        if (task != null) {
            takeTask(task);
            EnterpriseConstants.ALL_TASKS.poll();
        }
    }

    /**
     * 结束任务
     *
     * @param task 任务
     */
    public void finishTask(Task task) {
        if (task == null || !Objects.equals(task.getStatus(), 1) || !Objects.equals(task.getEmployeeNo(), this.employeeNo)) {
            throw new IllegalArgumentException("illegal task");
        }
        task.setStatus(3);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeNo='" + employeeNo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
