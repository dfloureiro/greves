package com.dfl.grevesapp.webservices;


import android.content.Context;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 * <p>
 * api client setup
 */
public class ApiClient {

    private static final String BASE_URL_HA_GREVE = "http://hagreve.com/api/";
    private static Retrofit retrofitHaGreve = null;

    /**
     * get client hagreve.com
     *
     * @param context app context
     * @return retrofit client
     */
    public static Retrofit getClientHaGreve(Context context) {
        if (retrofitHaGreve == null) {
            retrofitHaGreve = new Retrofit.Builder()
                    .baseUrl(BASE_URL_HA_GREVE)
                    .client(setupClient(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitHaGreve;
    }

    /**
     * setup client to the retrofit
     *
     * @param context app context
     * @return client with the cache applied
     */
    private static OkHttpClient setupClient(Context context) {
        //setup cache
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        return new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .build();
    }


    /**
     * interceptor with the cache controls
     */
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            int maxAge = 60; //1 minute
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }
    };
}