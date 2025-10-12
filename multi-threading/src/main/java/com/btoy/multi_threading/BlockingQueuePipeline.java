package com.btoy.multi_threading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

/*
 * @created 11/10/2025 ~~ 22:45
 * author: batu
 */
public class BlockingQueuePipeline {

    private static final Integer INITIAL_CAPACITY = 100;

    private static final Logger logger = Logger.getLogger(BlockingQueuePipeline.class.getSimpleName());

    private final ArrayBlockingQueue<Job> input = new ArrayBlockingQueue<>(INITIAL_CAPACITY);
    private final ArrayBlockingQueue<Job> output = new ArrayBlockingQueue<>(INITIAL_CAPACITY);

    class Job {
        private final String foo;
        private final String bar;

        public Job(String foo, String bar) {
            this.foo = foo;
            this.bar = bar;
        }

        @Override
        public String toString() {
            return "Job{" +
                    "foo='" + foo + '\'' +
                    ", bar='" + bar + '\'' +
                    '}';
        }
    }


    void publish(Job job) throws InterruptedException {
        input.put(job);
    }

    void start() {
        Thread prepare= new Thread(() -> {
            try {
                Job job = input.take();
                output.put(job);
            } catch (InterruptedException interruptedException) {
                throw new RuntimeException(interruptedException.getMessage());
            } finally {
                if (!Thread.currentThread().isInterrupted()) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consume = new Thread( () -> {
            try {
                Job job = output.take();
                logger.info("Job consumed= " + job);
            } catch (InterruptedException interruptedException) {
                throw new RuntimeException(interruptedException.getMessage());
            } finally {
                if (!Thread.currentThread().isInterrupted()) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        prepare.start();
        consume.start();
    }
}
