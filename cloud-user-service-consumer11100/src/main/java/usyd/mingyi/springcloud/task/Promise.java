package usyd.mingyi.springcloud.task;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;


public class Promise<T> {

    public final static Executor customThreadPool = Executors.newCachedThreadPool();
    CompletableFuture<T> completableFuture;

    public CompletableFuture<T> getCompletableFuture() {
        return completableFuture;
    }

    public void setCompletableFuture(CompletableFuture<T> completableFuture) {
        this.completableFuture = completableFuture;
    }

    public Promise(Supplier<T> supplier) {
        this(supplier,customThreadPool);
    }

    public Promise(Supplier<T> supplier,Executor executor) {
        this.completableFuture = CompletableFuture.supplyAsync(supplier, executor);
    }
    public  Promise(CompletableFuture<T> completableFuture) {
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
    public static Void await(Promise<?>... promises){
        return all(promises).join();
    }

    public  T get() throws ExecutionException, InterruptedException {
        return this.getCompletableFuture().get();
    }



}
