package com.brmlab.shophelp.di

import com.brmlab.shophelp.App
import com.brmlab.shophelp.api.DescriptionApi
import com.brmlab.shophelp.api.MovieApi
import com.brmlab.shophelp.di.annotation.MainRetrofit
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class MovieModule {
    @Provides
    @Singleton
    fun gson(): Gson{
        return Gson()
    }



    @MainRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit{
        return Retrofit.Builder()
                .baseUrl(App.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    @Singleton
    fun provideApi(@MainRetrofit retrofit: Retrofit): MovieApi{
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDescApi(@MainRetrofit retrofit: Retrofit): DescriptionApi{
        return retrofit.create(DescriptionApi::class.java)
    }


}