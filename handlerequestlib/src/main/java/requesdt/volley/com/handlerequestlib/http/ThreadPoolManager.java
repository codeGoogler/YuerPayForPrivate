package requesdt.volley.com.handlerequestlib.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by baby on 2017/5/26.
 */
public class ThreadPoolManager {
    //请求队列
    private LinkedBlockingQueue<Runnable> qunue=new LinkedBlockingQueue<>();
    //执行任务的添加
    public void execute(Runnable runnable) {
        if (runnable != null) {
            try {
                qunue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //传送带的执行方法
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            while (true) {
                Runnable runnable=null;
                try {
                    //当队列中没有请求时，会停在这
                    runnable = qunue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (runnable != null) {
                    //执行请求（
//                    Log.i("david","请求队列      "+qunue.size());
//                    Log.i("david","线程池   "+threadPoolExecutor.getPoolSize());
                    threadPoolExecutor.execute(runnable);
                }
            }
        }
    };

    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadPoolManager() {
        threadPoolExecutor=new ThreadPoolExecutor(4,20,15, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4),rejectedExecutionHandler);
        threadPoolExecutor.execute(runnable);
    }

    private RejectedExecutionHandler rejectedExecutionHandler=new RejectedExecutionHandler(){

        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            try {
                qunue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private static ThreadPoolManager ourInstance = new ThreadPoolManager();

    public static ThreadPoolManager getInstance() {
        return ourInstance;
    }




}
