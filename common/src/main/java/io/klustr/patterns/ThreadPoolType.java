package io.klustr.patterns;


public enum ThreadPoolType {
    LinkedBlockingQueue,
    ArrayBlockingQueue,
    SynchronousQueue,
    CacheThreadPool,
    FixedThreadPool;
}
