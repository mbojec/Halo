package com.mbojec.halo.model

open class SingletonHolder<out T, in A, B, C, D>(private var creator: (A, B, C, D) -> T) {
    @Volatile private var instance: T? = null

    fun getInstanceAndInit(arg: A, arg2: B, arg3: C, arg4: D): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator(arg, arg2, arg3, arg4)
                instance = created
                created
            }
        }
    }

    fun clearInstance(){
        instance = null
    }
}


