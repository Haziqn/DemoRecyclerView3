package com.example.haziq.demorecyclerview3;

import android.os.Handler;
import android.os.HandlerThread;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Haziq on 10/25/2017.
 */

public class ThreadFactory {
    private static final String TAG = "ThreadFactory";
    private static Map<String, ThreadDetail> threadDetails = new HashMap();

    public ThreadFactory() {
    }

    public static Handler getHandler(String name) {
        Map var1 = threadDetails;
        synchronized(threadDetails) {
            ThreadFactory.ThreadDetail threadDetail = (ThreadFactory.ThreadDetail)threadDetails.get(name);
            if(threadDetail == null) {
                threadDetail = new ThreadFactory.ThreadDetail(name);
                threadDetails.put(name, threadDetail);
            }

            return threadDetail.handler;
        }
    }

    public static void quit(String name) {
        Map var1 = threadDetails;
        synchronized(threadDetails) {
            ThreadFactory.ThreadDetail threadDetail = (ThreadFactory.ThreadDetail)threadDetails.get(name);
            if(threadDetail != null) {
                threadDetail.handlerThread.quit();
                threadDetails.remove(name);
            }

        }
    }

    public static void post(String name, Runnable runnable) {
        getHandler(name).post(runnable);
    }

    public static void postDelayed(String name, Runnable runnable, long delay) {
        getHandler(name).postDelayed(runnable, delay);
    }

    public static void removeCallbacks(String name, Runnable runnable) {
        getHandler(name).removeCallbacks(runnable);
    }

    public static void postAtFrontOfQueue(String name, Runnable runnable) {
        getHandler(name).postAtFrontOfQueue(runnable);
    }

    public static void interrupt(String name) {
        Map var1 = threadDetails;
        synchronized(threadDetails) {
            ThreadFactory.ThreadDetail threadDetail = (ThreadFactory.ThreadDetail)threadDetails.get(name);
            if(threadDetail != null) {
                threadDetail.interrupt();
            }

        }
    }

    private static class ThreadDetail {
        private HandlerThread handlerThread;
        private Handler handler;

        public ThreadDetail(String name) {
            this.handlerThread = new HandlerThread(name);
            this.handlerThread.start();
            this.handler = new Handler(this.handlerThread.getLooper());
        }

        public void interrupt() {
            this.handlerThread.interrupt();
        }
    }
}
