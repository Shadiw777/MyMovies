package com.example.mymovies.net;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class NetModule {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "ad1924f6431a6c29e4ee7df6838b08f9";
    private static final int CACHE_SIZE = 10 * 1024 * 1024;
    private static final int READ_TIMEOUT = 40;
    private static final int WRITE_TIMEOUT = 40;

    @Provides
    @Singleton
    public static MoviesApiService provideMovieApiService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(MoviesApiService.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
               // .cache(cache)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);

        httpClient.addInterceptor(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            HttpUrl httpUrl = chain.request().url()
                    .newBuilder().addQueryParameter("api_key", API_KEY).build();

            requestBuilder.url(httpUrl);
            return chain.proceed(requestBuilder.build());
        });

        return httpClient.build();
    }

//    @Provides
//    @Singleton
//    Cache provideHttpCache(Context context) {
//        return new Cache(context.getCacheDir(), CACHE_SIZE);
//    }

}