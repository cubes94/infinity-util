package com.infinity.training.basis.example.enterprise.impl;

import com.infinity.training.basis.example.enterprise.EnterpriseConstants;
import com.infinity.training.basis.example.enterprise.Task;
import com.infinity.training.basis.example.enterprise.TaskService;

/**
 * 任务服务
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/09/22 22:39
 */
public class TaskServiceImpl implements TaskService {

    /**
     * 随机分配任务
     *
     * @param task 任务
     */
    @Override
    public void randomAssign(Task task) {
        EnterpriseConstants.randomEmployee().takeTask(task);
    }

    /**
     * 随机分配任务
     */
    @Override
    public void randomAssign() {
        while (EnterpriseConstants.peekInitTask() != null) {
            randomAssign(EnterpriseConstants.pollInitTask());
        }
    }
}
