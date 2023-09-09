package usyd.mingyi.springcloud.task;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.concurrent.Executor;


public class AsyncConfig {
    public static Executor customThreadPool;

    static {
       // ExecutorService executor = Executors.newCachedThreadPool();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 核心线程数
        executor.setMaxPoolSize(20); // 最大线程数
        executor.setQueueCapacity(50); // 队列容量
        executor.setTaskDecorator(task->()->{
                    try {
                        task.run();
                    }finally {
                        BaseContext.clearCurrentId();
                        //其实也可以在try中把父线程的ThreadLocal副本拷进子线程 然后就可以不用阿里的TransmittableThreadLocal
                    }
                }

        );
        executor.setThreadNamePrefix("CustomThreadPool-"); // 线程名前缀
        executor.initialize(); // 初始化线程池
        customThreadPool = TtlExecutors.getTtlExecutor(executor); //包装
    }
}
