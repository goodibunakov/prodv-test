package ru.goodibunakov.prodvtest.api;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.goodibunakov.prodvtest.Constants;

class RetrofitClient {

    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            httpClient.addInterceptor(chain -> {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder()
                        .addQueryParameter("appid", Constants.API_KEY)
                        .addQueryParameter("units", "metric")
                        .addQueryParameter("lang", "ru")
                        .build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            });
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
