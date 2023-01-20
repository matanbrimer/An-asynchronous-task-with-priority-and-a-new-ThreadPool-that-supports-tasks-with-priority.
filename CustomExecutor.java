package ex2.part_2;

import java.util.concurrent.*;

public class  CustomExecutor <T> extends ThreadPoolExecutor  {

        int arr[] = new int[10];
        int min_threads=(Runtime.getRuntime().availableProcessors())/2;
        int max_threads=(Runtime.getRuntime().availableProcessors())-1;
         //PriorityBlockingQueue<Runnable> queue= new PriorityBlockingQueue<>(min_threads,(task,task2)->((Task)task).compareTo((Task)task2));

    //  ThreadPoolExecutor pool = new ThreadPoolExecutor(min_threads,max_threads,300,TimeUnit.MILLISECONDS,queue);
        int max =100;

    /**
     * constructor to the CustomExecutor, using the constructor of  ThreadPoolExecutor -
     * which get also the queue (type PriorityBlockingQueue) , with new comparator we want to compare the tasks with.
     *
     */
        public CustomExecutor(){
            super((Runtime.getRuntime().availableProcessors())/2,(Runtime.getRuntime().availableProcessors())-1
            ,300,TimeUnit.MILLISECONDS,new PriorityBlockingQueue<>((Runtime.getRuntime().availableProcessors())/2
                            ,(task,task2)->((Task)task).compareTo((Task)task2)));
        }

    /**
     *
     * @param task - which will be submitted to the queue in the threadPoolExecutor
     *             and when the tasks will be added to the queue with a priority,
     *              tasks with the lower priority will be executed first.
     *             because of our comparator we're using.
     *
     * @return the Future<T> object , means the result of the Task that was submitted
     */
    public Future<T> submit(Task task){
            arr[task.getPriority()]++;
            return super.submit(task.getTask());
    }

    /**
     *
     * @param task the task to submit
     * @param taskType the type , included the priority
     * @return the Future<T> object , means the result of the Task that was submitted
     */
    public Future<T> submit(Callable task, TaskType taskType){
        Callable t = Task.createTask(task,taskType);
        return submit((Task)t);
    }

    /**
     *
     * @param task the task to submit
     * @return the Future<T> object , means the result of the Task that was submitted
     */
    public Future<T> submit(Callable task){
        Callable t = Task.createTask(task);
        return submit((Task)t);
    }

    /**
     *
     * @return The highest priority of a found task
     * Now in queue , using help array , and not the queue itself
     */
    public int getCurrentMax(){

        for (int i =1; i<=10;i++){
            if(arr[i-1]>0){
                return i;
            }
        }
   return 0;
        }

    /**
     *this function Updates the set of priorities so that he continues to remember which priorities of tasks he has left
     * @param thread the thread that will run task {@code r}
     * @param run the task that will be executed
     */
    @Override
    protected void beforeExecute(Thread thread, Runnable run) {
        int priority = getCurrentMax();
        if (1<=priority && priority<=10)
            arr[priority-1]--;
    }

    /**
     * a method to bridge between the Task, which is callable object to a runnable object
     * that we need for the priority queue.
     * @param callable the callable task being wrapped
     * @return RunnableFuture<T> object
     *
     */
    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        int priority = getCurrentMax();
        TaskType type = TaskType.IO;
        if (1<=priority && priority<=10)
            type.setPriority(priority);
        return Task.createTask(callable ,type);
    }

    /**
    * this method Terminates the activity of an instance of type CustomExecutor.
     * @return true if all the task were done and the threads were closed
     */
    public boolean gracefullyTerminate(){
                super.shutdown();
         if (super.isShutdown()){
             super.terminated();
             return true;
         }
         return false;

    }

}
