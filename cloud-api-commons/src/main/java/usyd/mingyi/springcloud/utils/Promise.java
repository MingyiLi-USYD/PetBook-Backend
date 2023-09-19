package usyd.mingyi.springcloud.utils;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;


public class Promise<T> {

    public final static Executor customThreadPool;
    CompletableFuture<T> completableFuture;

    static {
        // ExecutorService executor = Executors.newCachedThreadPool();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 核心线程数
        executor.setMaxPoolSize(20); // 最大线程数
        executor.setQueueCapacity(50); // 队列容量
        executor.setTaskDecorator(task->{
                    Long currentId = BaseContext.getCurrentId();

                    return ()->{
                       BaseContext.setCurrentId(currentId);
                    try {
                        task.run();
                    }finally {
                        BaseContext.clearCurrentId();
                        //其实也可以在try前把父线程的ThreadLocal副本拷进子线程 然后就可以不用阿里的TransmittableThreadLocal
                    }
                };
        }

        );
        executor.setThreadNamePrefix("CustomThreadPool-"); // 线程名前缀
        executor.initialize(); // 初始化线程池
        customThreadPool = TtlExecutors.getTtlExecutor(executor); //包装
    }

    public CompletableFuture<T> getCompletableFuture() {
        return completableFuture;
    }

    public void setCompletableFuture(CompletableFuture<T> completableFuture) {
        this.completableFuture = completableFuture;
    }

    public Promise(Supplier<T> supplier) {
        this(supplier,customThreadPool);
    }

    public Promise(Supplier<T> supplier, Executor executor) {
        this.completableFuture = CompletableFuture.supplyAsync(supplier, executor);
    }
    public Promise(CompletableFuture<T> completableFuture) {
        this.completableFuture = completableFuture;
    }

    public static <T> CompletableFuture<T> buildTask(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, customThreadPool);
    }


    public static <T> Promise<T>  buildPromise(Supplier<T> supplier){
        return new Promise<>(supplier);
    }

    public <U> Promise<U> then(Function<? super T,? extends U> fn){
        return new Promise<>( completableFuture.thenApply(fn));
    }


    public static CompletableFuture<Void> all(CompletableFuture<?>... cfs) {
        return CompletableFuture.allOf(cfs);
    }
    public static CompletableFuture<Void> all(Promise<?>... promises) {
        CompletableFuture<?>[] cfs = Arrays.stream(promises)
                .map(Promise::getCompletableFuture)
                .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(cfs);
    }
    public static Void awaitAll(Promise<?>... promises){
        return all(promises).join();
    }

    public  T get() throws ExecutionException, InterruptedException {
        return this.getCompletableFuture().get();
    }

    public Promise<T> await(){
        this.completableFuture.join();
        return this;
    }



}
