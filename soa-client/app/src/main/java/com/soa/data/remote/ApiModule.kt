package com.soa.data.remote

import com.soa.data.remote.AppConstants.ACCEPT_HEADER
import com.soa.data.remote.AppConstants.LOGIN_API_BASE_URL
import com.soa.data.remote.AppConstants.APPLICATION_JSON
import com.soa.di.scope.ApplicationScope
import com.google.gson.GsonBuilder
import com.soa.data.remote.AppConstants.Retrofit.RETROFIT_LOGIN
import com.soa.data.remote.AppConstants.Retrofit.RETROFIT_VOTE
import com.soa.data.remote.AppConstants.VOTE_API_BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class ApiModule {

    @Provides
    @ApplicationScope
    internal fun provideHeaderInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .method(original.method, original.body)
            .build()

        chain.proceed(request)
    }

    @Provides
    @ApplicationScope
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @ApplicationScope
    internal fun provideHttpClient(
        interceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

    @Provides
    @ApplicationScope
    internal fun provideGsonConverterFactory(): Converter.Factory =
        GsonConverterFactory.create(GsonBuilder().create())

    @Provides
    @ApplicationScope
    @Named(RETROFIT_LOGIN)
    internal fun provideLoginRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverter: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(LOGIN_API_BASE_URL)
        .addConverterFactory(gsonConverter)
        .client(okHttpClient)
        .build()

    @Provides
    @ApplicationScope
    @Named(RETROFIT_VOTE)
    internal fun provideVoteRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverter: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(VOTE_API_BASE_URL)
        .addConverterFactory(gsonConverter)
        .client(okHttpClient)
        .build()

    @Provides
    @ApplicationScope
    internal fun provideLoginApi(@Named(RETROFIT_LOGIN) retrofit: Retrofit): LoginApi =
        retrofit.create(LoginApi::class.java)

    @Provides
    @ApplicationScope
    internal fun provideVoteApi(@Named(RETROFIT_VOTE) retrofit: Retrofit): VoteApi =
        retrofit.create(VoteApi::class.java)
}