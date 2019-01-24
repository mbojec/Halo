package com.mbojec.halo

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {

    private val mDiskIO: Executor
    private val mNetworkIO: Executor
    private val mMainThread: Executor

    constructor() : this(
        Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
        MainThreadExecutor())

    private constructor(diskIO: Executor, networkIO: Executor, mainThread: Executor){
        mDiskIO = diskIO
        mNetworkIO = networkIO
        mMainThread = mainThread
    }

    val diskIO: Executor
        get() = mDiskIO

    val networkIO: Executor
        get() = mNetworkIO

    val mainThread: Executor
        get() = mMainThread

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}