package usyd.mingyi.springcloud.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;


public class Promise<U> {

/*    @Autowired
    Executor customThreadPool;*/
    public final static Executor customThreadPool = Executors.newCachedThreadPool();

    public static <U> CompletableFuture<U>  buildTask(Supplier<U> supplier){
        return CompletableFuture.supplyAsync(supplier, customThreadPool);
    }

/*    public static <U> Promise<U>  buildPromise(Supplier<U> supplier){
        return CompletableFuture.supplyAsync(supplier, customThreadPool);
    }*/




    public static CompletableFuture<Void>  PromiseAll(CompletableFuture<?>... cfs){
        return CompletableFuture.allOf(cfs);
    }


}
