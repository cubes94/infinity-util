package com.infinity.training.basis.example.enterprise;

/**
 * 任务服务
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/09/22 22:39
 */
public interface TaskService {

    /**
     * 随机分配任务
     *
     * @param task 任务
     */
    void randomAssign(Task task);

    /**
     * 随机分配任务
     */
    void randomAssign();
}
