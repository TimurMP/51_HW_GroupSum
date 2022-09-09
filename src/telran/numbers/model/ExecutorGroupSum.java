package telran.numbers.model;

import telran.numbers.tasks.OneGroupSum;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorGroupSum extends NumberSum {

    public ExecutorGroupSum(int[][] numbersGroup) {
        super(numbersGroup);
    }

    @Override
    public int computeSum() {
        OneGroupSum[] tasks = new OneGroupSum[numbersGroup.length];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new OneGroupSum(numbersGroup[i]);

        }
        ExecutorService executorService = Executors.newFixedThreadPool
                (Runtime.getRuntime().availableProcessors()/2);
//        CPUs divided by 2 for some reason gave better results (twice faster)
//        Example:
//        Test name: ExecutorGroupSum, time = 74, sum = 2138327843 (without CPU/2, or with CPU*2 I got 100+ ms)
//        Test name: ThreadGroupSum, time = 562, sum = 2138327843


        for (int i = 0; i < tasks.length; i++) {
            executorService.execute(tasks[i]);

        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Arrays.stream(tasks)
                .mapToInt(OneGroupSum::getSum)
                .sum();


    }

}
