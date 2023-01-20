package ex2;


import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class test {

    public static final Logger logger = (Logger) LoggerFactory.getLogger(test.class);
    @Test
    public void partialTest(){
        CustomExecutor customExecutor = new CustomExecutor();
        Task task = Task.createTask(()->{
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        Future sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = (int) sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = ()-> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = ()-> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        // var is used to infer the declared type automatically
        Future priceTask = customExecutor.submit(()-> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        Future reverseTask = customExecutor.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = (Double) priceTask.get();
            reversed = (String) reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Reversed String = " + reversed);
        logger.info(()->String.valueOf("Total Price = " + totalPrice));
        Future gask = customExecutor.submit(callable2, TaskType.COMPUTATIONAL);
        Future g = customExecutor.submit(callable2, TaskType.COMPUTATIONAL);

        logger.info(()-> "Reversed String = " + reversed);
        logger.info(()-> "Current maximum priority = " +
                customExecutor.getCurrentMax());


        Callable<Double> callable5 = ()-> {
            return 1000 * Math.pow(1.02, 5);
        };

        Future test = customExecutor.submit(callable5,TaskType.IO);
        try {
            Double d = (Double) test.get();
            logger.info(()->String.valueOf("Total==== = " + d));
        } catch (InterruptedException |ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(()->String.valueOf("return  = " + customExecutor.gracefullyTerminate()));
    }
}
