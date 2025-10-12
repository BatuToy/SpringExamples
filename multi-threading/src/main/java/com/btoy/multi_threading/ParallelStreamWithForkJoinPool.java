package com.btoy.multi_threading;

import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.stream.LongStream;

/*
 * @created 12/10/2025 ~~ 09:33
 * author: batu
 */
class ParallelStreamWithForkJoinPool {

    private static final Logger logger = Logger.getLogger(ParallelStreamWithForkJoinPool.class.getSimpleName());
    private static final int THREAD_SIZE = 10;

    private <R> R withParallelityForkJoinPool(Integer threads, Callable<R> task) {
        ForkJoinPool pool = new ForkJoinPool(threads,
                factory -> {
                    ForkJoinWorkerThread forkJoinWorkerThread = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(factory);
                    forkJoinWorkerThread.setName("task-example" + forkJoinWorkerThread.getPoolIndex());
                    return forkJoinWorkerThread;
                },
                null,
                false);
        ForkJoinTask<R> joinTask = pool.submit(task);
        R data = joinTask.join();
        return data;
    }

    private static Long work(long dataIndex) {
        logger.info("data - " + dataIndex + "~~" + Thread.currentThread().getName());
        return dataIndex;
    }

    private void parallelWork() {
        List<Long> data = LongStream.range(0L, 1_000_000L).boxed().toList();
        long start = System.currentTimeMillis();
        Double average = withParallelityForkJoinPool(THREAD_SIZE, () ->
            data.parallelStream().mapToDouble(ParallelStreamWithForkJoinPool::work).summaryStatistics().getAverage()
        );
        long end = System.currentTimeMillis();
        logger.info("Parallel task takes_ "+ (end - start) +" millis and finished successfully with number of threads= " + THREAD_SIZE + " and the result= " + average);
    }

}
