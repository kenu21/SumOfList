package parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class Main {
    private static final int SIZE_LIST = 1_000_000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> list = new ArrayList<>(SIZE_LIST);
        for (int i = 0; i < SIZE_LIST; i++) {
            list.add(1);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        SumList sumList1 = new SumList(list.subList(0, 250000));
        Future<Integer> future1 = executorService.submit(sumList1);

        SumList sumList2 = new SumList(list.subList(250000, 500000));
        Future<Integer> future2 = executorService.submit(sumList2);

        SumList sumList3 = new SumList(list.subList(500000, 750000));
        Future<Integer> future3 = executorService.submit(sumList3);

        SumList sumList4 = new SumList(list.subList(750000, 1000000));
        Future<Integer> future4 = executorService.submit(sumList4);

        executorService.shutdown();
        int sum = future1.get().intValue() + future2.get().intValue()
                + future3.get().intValue() + future4.get().intValue();
        System.out.println(sum);

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        SumListFork sumListFork = new SumListFork(list, 0, SIZE_LIST);
        sum = forkJoinPool.invoke(sumListFork);
        System.out.println(sum);
    }
}
