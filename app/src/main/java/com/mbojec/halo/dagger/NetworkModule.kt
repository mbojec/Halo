package com.mbojec.halo.dagger

import com.mbojec.halo.BuildConfig
import com.mbojec.halo.Const
import com.mbojec.halo.MapBoxApiClient
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule{
    companion object {
        private const val MAPBOX_BASE_URL = "MAPBOX_BASE_URL"
        private const val MAPBOX_ACCESS_TOKEN = "MAPBOX_ACCESS_TOKEN"
        private const val MAPBOX_API_KEY_INTERCEPTOR = "MAPBOX_API_KEY_INTERCEPTOR"
        private const val MAPBOX_RETROFIT_BUILDER = "MAPBOX_RETROFIT_BUILDER"
        private const val MAPBOX_RETROFIT = "MAPBOX_RETROFIT"
        private const val MAPBOX_OKHTTP_CLIENT = "MAPBOX_OKHTTP_CLIENT"
        private const val MAPBOX_LOGGING_INTERCEPTOR = "MAPBOX_LOGGING_INTERCEPTOR"
    }

    @Provides
    @Named(MAPBOX_BASE_URL)
    fun provideBaseMapBoxUrlString() = "${Const.PROTOCOL}://${Const.BASE_MAPBOX_GEOCODING_API_URL}"

    @Provides
    @Named(MAPBOX_ACCESS_TOKEN)
    fun provideMapBoxAccessToken() = BuildConfig.MAPBOX_ACCESS_TOKEN


    @Provides
    @Singleton
    @Named(MAPBOX_OKHTTP_CLIENT)
    fun provideMapBoxHttpClient() = OkHttpClient.Builder()

    @Provides
    @Singleton
    @Named(MAPBOX_LOGGING_INTERCEPTOR)
    fun provideMapBoxLogginInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    @Singleton
    @Named(MAPBOX_API_KEY_INTERCEPTOR)
    fun provideMapBoxAccessTokenInterceptor(@Named(MAPBOX_ACCESS_TOKEN) access_token: String) = Interceptor { chain ->
        val original = chain.request()
        val httpUrl = original.url()
        val newHttpUrl = httpUrl.newBuilder().addQueryParameter("access_token", access_token).build()
        val requestBuilder = original.newBuilder().url(newHttpUrl)
        val request = requestBuilder.build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    @Named(MAPBOX_RETROFIT_BUILDER)
    fun provideRetrofitBuilderForAccuWeatherApi(@Named(MAPBOX_BASE_URL) baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    @Provides
    @Singleton
    @Named(MAPBOX_RETROFIT)
    fun provideAccuWeatherRetrofit(@Named(MAPBOX_RETROFIT_BUILDER) builder: Retrofit.Builder,
                                   @Named(MAPBOX_API_KEY_INTERCEPTOR) interceptor: Interceptor,
                                   @Named(MAPBOX_OKHTTP_CLIENT) okHttpClient: OkHttpClient.Builder,
                                   @Named(MAPBOX_LOGGING_INTERCEPTOR) loggingInterceptor: HttpLoggingInterceptor ) : Retrofit =
        builder.client(okHttpClient.addInterceptor(interceptor).addInterceptor(loggingInterceptor).build()).build()


    @Provides
    @Singleton
    fun provideAccuWeatherClientClass(): Class<MapBoxApiClient> = MapBoxApiClient::class.java

    @Provides
    @Singleton
    fun provideAccuWeatherApiClient(@Named(MAPBOX_RETROFIT) retrofit: Retrofit,
                                    serviceClass: Class<MapBoxApiClient>): MapBoxApiClient =
        retrofit.create(serviceClass)
}