package dev.droid.chargingstationsapp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.droid.chargingstationsapp.StationsApplication
import dev.droid.chargingstationsapp.data.api.OpenChargeMapService
import dev.droid.chargingstationsapp.di.ApplicationContext
import dev.droid.chargingstationsapp.di.BaseUrl
import dev.droid.chargingstationsapp.utils.AppConstant.API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule(private val application : StationsApplication) {

    @ApplicationContext
    @Provides
    fun provideContext() : Context {
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl() : String = "https://api.openchargemap.io/v3/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory() : GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor : HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().readTimeout(
            120,
            TimeUnit.SECONDS
        ).writeTimeout(120, TimeUnit.SECONDS).connectTimeout(
            120,
            TimeUnit.SECONDS
        ).addInterceptor(Interceptor { chain : Interceptor.Chain ->
            val request = chain.request().newBuilder().header(
                "X-API-Key",
                API_KEY
            ).method(
                chain.request().method,
                chain.request().body
            ).build()
            chain.proceed(request)
        }).addInterceptor(interceptor).build()
    }


    @Provides
    @Singleton
    fun provideNetworkService(
            @BaseUrl baseUrl : String,
            gsonConverterFactory : GsonConverterFactory,
            client : OkHttpClient
    ) : OpenChargeMapService {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(gsonConverterFactory)
                .build()
                .create(OpenChargeMapService::class.java)
    }

}