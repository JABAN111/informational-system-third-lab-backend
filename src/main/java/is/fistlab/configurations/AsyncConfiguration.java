package is.fistlab.configurations;

import is.fistlab.RejectedExecutionHandlerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

@EnableAsync
@Configuration
@Slf4j
public class AsyncConfiguration {

//    @Override
    @Bean
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("AsyncTaskThread::");

//        executor.setQueueCapacity();
        executor.setRejectedExecutionHandler(new RejectedExecutionHandlerImpl());

        executor.setWaitForTasksToCompleteOnShutdown(true);
        log.info("start init");
//        if(true)
//            throw new RuntimeException("executor requested: " + executor);
        executor.initialize();
        return executor;
    }



//    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {

        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex,
                                                Method method, Object... params) {
                log.error(ex.getMessage(), ex);
                ex.printStackTrace();
            }
        };

    }
}