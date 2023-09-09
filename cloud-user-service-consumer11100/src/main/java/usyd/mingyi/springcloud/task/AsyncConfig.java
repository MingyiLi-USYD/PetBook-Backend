package usyd.mingyi.springcloud.task;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // 启用异步任务支持
public class AsyncConfig {

/*    @Bean(name = "customThreadPool")
    public Executor customThreadPool() {
       // ExecutorService executor = Executors.newCachedThreadPool();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 核心线程数
        executor.setMaxPoolSize(20); // 最大线程数
        executor.setQueueCapacity(50); // 队列容量
  *//*      executor.setCorePoolSize(1); // 核心线程数
        executor.setMaxPoolSize(1); // 最大线程数
        executor.setQueueCapacity(0); // 队列容量
        测一下*//*
        executor.setThreadNamePrefix("CustomThreadPool-"); // 线程名前缀
        executor.initialize(); // 初始化线程池
        return TtlExecutors.getTtlExecutor(executor); //包装
    }*/
}
