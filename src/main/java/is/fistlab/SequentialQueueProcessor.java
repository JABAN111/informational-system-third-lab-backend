package is.fistlab;

import is.fistlab.exceptions.mappers.InvalidFieldException;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Slf4j
public class SequentialQueueProcessor {
    private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final PlatformTransactionManager transactionManager;

    public SequentialQueueProcessor(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;

        executorService.submit(() -> {
            while (true) {
                try {
                    log.info("current queue size: {}", taskQueue.size());
                    Runnable task = taskQueue.take();
                    try {
                        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
                        transactionTemplate.execute((TransactionStatus status) -> {
                            task.run();
                            return null;
                        });
                    } catch (Exception taskException) {
                        if(taskException instanceof InvalidFieldException) {
                            log.error("Invalid field: {}", taskException.getMessage());
                        }
                        log.error("Error occurred during task execution: ", taskException);
                    }
                    log.info("current after executing size: {}", taskQueue.size());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    public void submitTask(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to submit task to sequential queue", e);
        }
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdownNow();
    }
}