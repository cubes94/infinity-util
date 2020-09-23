package com.infinity.training.example.enterprise;

import java.time.LocalDate;
import java.util.*;

/**
 * 常量
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/09/22 22:48
 */
public class EnterpriseConstants {

    public static final Random RANDOM = new Random();

    public static final Queue<Task> ALL_TASKS = new PriorityQueue<>(Comparator.comparingInt(Task::getStatus));

    public static final List<Employee> ALL_EMPLOYEES = new ArrayList<>();

    static {
        ALL_TASKS.offer(Task.builder().taskNo("T00001").name("任务1").status(0).build());
        ALL_TASKS.offer(Task.builder().taskNo("T00002").name("任务2").status(0).build());
        ALL_TASKS.offer(Task.builder().taskNo("T00003").name("任务3").status(0).build());
        ALL_TASKS.offer(Task.builder().taskNo("T00004").name("任务4").status(0).build());
        ALL_TASKS.offer(Task.builder().taskNo("T00005").name("任务5").status(0).build());
        ALL_TASKS.offer(Task.builder().taskNo("T00006").name("任务6").status(0).build());

        ALL_EMPLOYEES.add(Employee.builder().employeeNo("E00001").name("员工1").birthday(LocalDate.now()).gender(0).salary(300d).build());
        ALL_EMPLOYEES.add(Employee.builder().employeeNo("E00002").name("员工2").birthday(LocalDate.now()).gender(1).salary(400d).build());
        ALL_EMPLOYEES.add(Employee.builder().employeeNo("E00003").name("员工3").birthday(LocalDate.now()).gender(0).salary(100d).build());
        ALL_EMPLOYEES.add(Employee.builder().employeeNo("E00004").name("员工4").birthday(LocalDate.now()).gender(1).salary(500d).build());
        ALL_EMPLOYEES.add(Employee.builder().employeeNo("E00005").name("员工5").birthday(LocalDate.now()).gender(0).salary(200d).build());
        ALL_EMPLOYEES.add(Employee.builder().employeeNo("E00006").name("员工6").birthday(LocalDate.now()).gender(1).salary(700d).build());
    }

    /**
     * search task
     *
     * @param taskNo taskNo
     * @return task
     */
    public static Task getTaskByTaskNo(String taskNo) {
        for (Task task : ALL_TASKS) {
            if (Objects.equals(task.getTaskNo(), taskNo)) {
                return task;
            }
        }
        return null;
    }

    /**
     * search employee
     *
     * @param taskNo taskNo
     * @return employee
     */
    public static Employee getEmployeeByTaskNo(String taskNo) {
        for (Task task : ALL_TASKS) {
            if (Objects.equals(task.getTaskNo(), taskNo)) {
                return task.getEmployeeNo() == null ? null : getEmployeeByEmployeeNo(task.getEmployeeNo());
            }
        }
        return null;
    }

    /**
     * search employee
     *
     * @param employeeNo employeeNo
     * @return employee
     */
    public static Employee getEmployeeByEmployeeNo(String employeeNo) {
        for (Employee employee : ALL_EMPLOYEES) {
            if (Objects.equals(employee.getEmployeeNo(), employeeNo)) {
                return employee;
            }
        }
        return null;
    }

    /**
     * peek first init task
     *
     * @return first init task
     */
    public static Task peekInitTask() {
        return ALL_TASKS.peek();
    }

    /**
     * poll first init task
     *
     * @return first init task
     */
    public static Task pollInitTask() {
        return ALL_TASKS.poll();
    }

    /**
     * get random employee
     *
     * @return random employee
     */
    public static Employee randomEmployee() {
        return ALL_EMPLOYEES.get(RANDOM.nextInt(ALL_EMPLOYEES.size()));
    }
}
