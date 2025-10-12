package com.btoy.multi_threading;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;
import java.util.logging.Logger;

/*
 * @created 12/10/2025 ~~ 10:08
 * author: batu
 */
class DummyExecutorService {

    private static final Logger logger = Logger.getLogger(DummyExecutorService.class.getSimpleName());

    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 100;
    private static final long KEEP_ALIVE_TIME = 10;
    private static final int TASK_CAPACITY = 1000;

    private final ExecutorService executorService;
    private final HttpClient httpClient;

    DummyExecutorService() throws NoSuchAlgorithmException {
        this.executorService = setExecutorService();
        this.httpClient = setHttpClient(this.executorService);
    }

    private ExecutorService setExecutorService() {
        return new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(TASK_CAPACITY),
                (factory) -> {
                    Thread thread = new Thread();
                    thread.setName("batu - from executor pool" + Thread.currentThread().threadId());
                    thread.setDaemon(true);
                    return thread;
                    // All threads are lazily initialized !
                });
    }

    private HttpClient setHttpClient(ExecutorService executorService) throws NoSuchAlgorithmException {
        return HttpClient.newBuilder()
                .executor(executorService)
                .connectTimeout(Duration.ofSeconds(10))
                .sslContext(SSLContext.getDefault())
                .build();
    }

    private void dummy() {
        Future<String> future = executorService.submit( () -> {
            final String a = "a";
            final String b = "b";
            return a.concat(b);
        });
        try {
            if (future.isDone()) {
                String ab = future.get();
                logger.info(ab);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
