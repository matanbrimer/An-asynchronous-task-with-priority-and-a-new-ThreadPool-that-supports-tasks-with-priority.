package ex2;


import java.util.concurrent.Callable;


import java.util.Comparator;
import java.util.concurrent.*;

public class Task<T>  extends FutureTask<T> implements Callable<T>,Comparable<Task<T>>{

    TaskType taskType;
    Callable<T> task;

    /**
     * full constructor
     * @param callable - Java's built-in type of a task that can be executed asynchronously with a return value
     * @param taskType which represents the task type.
     * Each type has a numerical value which determines the priority of the task
     */
    private Task(Callable<T> callable,TaskType taskType){
        super(callable);
        task =  callable;
        this.taskType =taskType;
    }

    /**
     * default constructor
     * @param callable -Java's built-in type of a task that can be executed asynchronously with a return value
     * here we Determine the task type and it's priority deductively
     */
    private Task(Callable<T> callable){
        super(callable);
        task = callable;
        this.taskType =taskType.OTHER;
    }

    /**
     * method to create task using the constructor
     * @param callable - the task
     * @param tt -type task
     * @return new task
     */
    public static Task  createTask(Callable callable,TaskType tt){
        return new Task(callable,tt);
    }
    /**
     * method to create task , using the constructor
     * @param callable - the task
     * @return new task
     */
    public static Task  createTask(Callable callable){
        return new Task(callable);
    }

    /**
     * getters and setters
     *
     */

    public int  getPriority(){
        return taskType.getPriorityValue();
    }
    public void setPriority(int priority){
        taskType.setPriority(priority);
    }
    public TaskType getTaskType() {
        return taskType;
    }
    public <Object> TaskType getType(){
        return taskType.getType();
    }
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    public Callable<T> getTask() {
        return task;
    }

    public void setTask(Callable<T> task) {
        this.task = task;
    }


    /**
     * override the default comparator - we want to compare two tasks
     * such as One task takes precedence over another if its priority value is smaller
     * @param o the object to be compared. (a task)
     * @return 1 if this new task 'o' is smaller than the task we compare to
     * , -1 the opposite state , and 0 if their priorities is equals.
     */
    @Override
    public int compareTo(Task<T> o) {
        if (this.getPriority() > o.getPriority())return 1;
        else if(this.getPriority() < o.getPriority())return -1;

        return 0;
    }

    /**
     *
     * @return the result of task after Execution.
     * @throws Exception
     */
    @Override
    public T call() throws Exception {
        return  task.call();
    }


}
