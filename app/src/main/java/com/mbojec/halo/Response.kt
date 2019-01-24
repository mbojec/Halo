package com.mbojec.halo
import com.mbojec.halo.Status.*

class Response private constructor(val status: Status, val data: String?, val error: Throwable?) {
    companion object {

        fun loading(): Response {
            return Response(LOADING, null, null)
        }

        fun success(): Response {
            return Response(SUCCESS, null, null)
        }

        fun failure(): Response {
            return Response(Status.FAILURE, null, null)
        }

        fun error(error: Throwable): Response {
            return Response(ERROR, null, error)
        }
    }
}