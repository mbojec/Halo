package com.mbojec.halo.dagger

import com.mbojec.halo.*
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

        private const val DARK_SKY_BASE_URL = "DARK_SKY_BASE_URL"
        private const val DARK_SKY_API_KEY_INTERCEPTOR = "DARK_SKY_API_KEY_INTERCEPTOR"
        private const val DARK_SKY_RETROFIT_BUILDER = "DARK_SKY_RETROFIT_BUILDER"
        private const val DARK_SKY_RETROFIT = "DARK_SKY_RETROFIT"
        private const val DARK_SKY_OKHTTP_CLIENT = "DARK_SKY_OKHTTP_CLIENT"
        private const val DARK_SKY_LOGGING_INTERCEPTOR = "DARK_SKY_LOGGING_INTERCEPTOR"
    }

    @Provides
    @Named(MAPBOX_BASE_URL)
    fun provideBaseMapBoxUrlString() = "${Const.PROTOCOL}://${Const.BASE_MAPBOX_GEOCODING_API_URL}"

    @Provides
    @Named(DARK_SKY_BASE_URL)
    fun provideBaseDarkSkyUrlString() = "${Const.PROTOCOL}://${Const.BASE_DARK_SKY_API_URL}"

    @Provides
    @Named(MAPBOX_ACCESS_TOKEN)
    fun provideMapBoxAccessToken() = BuildConfig.MAPBOX_ACCESS_TOKEN

    @Provides
    @Singleton
    @Named(MAPBOX_OKHTTP_CLIENT)
    fun provideMapBoxHttpClient() = OkHttpClient.Builder()

    @Provides
    @Singleton
    @Named(DARK_SKY_OKHTTP_CLIENT)
    fun provideDarkSkyHttpClient() = OkHttpClient.Builder()

    @Provides
    @Singleton
    @Named(MAPBOX_LOGGING_INTERCEPTOR)
    fun provideMapBoxLogginInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    @Singleton
    @Named(DARK_SKY_LOGGING_INTERCEPTOR)
    fun provideDarkSKyLogginInterceptor() = HttpLoggingInterceptor()
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
    @Named(DARK_SKY_API_KEY_INTERCEPTOR)
    fun provideDarkSkyAccessTokenInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val httpUrl = original.url()
        val newHttpUrl = httpUrl.newBuilder().build()
        val requestBuilder = original.newBuilder().url(newHttpUrl)
        val request = requestBuilder.build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    @Named(MAPBOX_RETROFIT_BUILDER)
    fun provideRetrofitBuilderForMapBoxApi(@Named(MAPBOX_BASE_URL) baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    @Provides
    @Singleton
    @Named(DARK_SKY_RETROFIT_BUILDER)
    fun provideRetrofitBuilderForDarkSkyApi(@Named(DARK_SKY_BASE_URL) baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())


    @Provides
    @Singleton
    @Named(MAPBOX_RETROFIT)
    fun provideMapBoxRetrofit(@Named(MAPBOX_RETROFIT_BUILDER) builder: Retrofit.Builder,
                              @Named(MAPBOX_API_KEY_INTERCEPTOR) interceptor: Interceptor,
                              @Named(MAPBOX_OKHTTP_CLIENT) okHttpClient: OkHttpClient.Builder,
                              @Named(MAPBOX_LOGGING_INTERCEPTOR) loggingInterceptor: HttpLoggingInterceptor ) : Retrofit =
        builder.client(okHttpClient.addInterceptor(interceptor).addInterceptor(loggingInterceptor).build()).build()


    @Provides
    @Singleton
    @Named(DARK_SKY_RETROFIT)
    fun provideDarkSkyRetrofit(@Named(DARK_SKY_RETROFIT_BUILDER) builder: Retrofit.Builder,
                               @Named(DARK_SKY_API_KEY_INTERCEPTOR) interceptor: Interceptor,
                               @Named(DARK_SKY_OKHTTP_CLIENT) okHttpClient: OkHttpClient.Builder,
                               @Named(DARK_SKY_LOGGING_INTERCEPTOR) loggingInterceptor: HttpLoggingInterceptor ) : Retrofit =
        builder.client(okHttpClient.addInterceptor(interceptor).addInterceptor(loggingInterceptor).build()).build()

    @Provides
    @Singleton
    fun provideMapBoxClientClass(): Class<MapBoxApiClient> = MapBoxApiClient::class.java

    @Provides
    @Singleton
    fun provideDarkSkyClientClass(): Class<DarkSkyApiClient> = DarkSkyApiClient::class.java

    @Provides
    @Singleton
    fun provideMapBoxApiClient(@Named(MAPBOX_RETROFIT) retrofit: Retrofit,
                               serviceClass: Class<MapBoxApiClient>): MapBoxApiClient =
        retrofit.create(serviceClass)

    @Provides
    @Singleton
    fun provideDarkSyApiClient(@Named(DARK_SKY_RETROFIT) retrofit: Retrofit,
                               serviceClass: Class<DarkSkyApiClient>): DarkSkyApiClient =
        retrofit.create(serviceClass)

    @Provides
    @Singleton
    fun provideNetworkRepository(mapBoxApiClient: MapBoxApiClient, darkSkyApiClient: DarkSkyApiClient): NetworkRepository = NetworkRepository(mapBoxApiClient, darkSkyApiClient)
}