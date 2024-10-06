package de.alexander13oster.manifestmetadatasdk

import android.app.Application
import android.content.pm.PackageManager
import com.skydoves.retrofit.adapters.arrow.EitherCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SdkModule {

    @Singleton
    @Provides
    fun provideDogApi(retrofit: Retrofit): DecisionApi = retrofit
        .create(DecisionApi::class.java)

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        application: Application,
    ): Retrofit = Retrofit
        .Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(EitherCallAdapterFactory.create())
        .baseUrl(getBaseUrl(application))
        .client(okHttpClient)
        .build()

    private fun getBaseUrl(application: Application): String = application
        .packageManager
        .getApplicationInfo(application.packageName, PackageManager.GET_META_DATA)
        .metaData
        ?.getString("sdk_base_url")
        ?: BASE_URL

    private const val BASE_URL = "https://yesno.wtf/"
}