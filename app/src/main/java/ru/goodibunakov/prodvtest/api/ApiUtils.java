package ru.goodibunakov.prodvtest.api;

public class ApiUtils {

    public static ApiService getApiService() {
        return RetrofitClient
                .getClient()
                .create(ApiService.class);
    }
}
