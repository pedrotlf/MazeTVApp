package br.com.pedrotlf.mazetvapp.di

import br.com.pedrotlf.mazetvapp.data.api.MazeTvApi
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideRetrofitForMazeTvApi(): Retrofit {
        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS })
            .build()

        return Retrofit.Builder()
            .baseUrl(MazeTvApi.BASE_URL)
            .addConverterFactory(
                JacksonConverterFactory.create(
                    jacksonObjectMapper()
                        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                )
            )
            .client(client)
            .build()
    }

    @Provides
    fun provideMazeTvApi(retrofit: Retrofit): MazeTvApi = retrofit.create(MazeTvApi::class.java)
}