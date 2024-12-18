package is.fistlab;

import is.fistlab.exceptions.mappers.InvalidFieldException;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@Slf4j
public class SequentialQueueProcessor {
    private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public SequentialQueueProcessor() {
        executorService.submit(() -> {
            while (true) {
                try {
                    log.info("current queue size: {}", taskQueue.size());
                    Runnable task = taskQueue.take();
                    try {
                        task.run();
                    } catch (Exception taskException) {
                        if(taskException instanceof InvalidFieldException) {
                            log.error("Invalid field: {}", taskException.getMessage());
                            break;
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
