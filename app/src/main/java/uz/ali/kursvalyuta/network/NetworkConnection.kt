package uz.ali.kursvalyuta.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkConnection {
    private var retrofit: Retrofit

    constructor() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        private val BASE_URL: String = "https://cbu.uz/oz/arkhiv-kursov-valyut/"

        private var instances: NetworkConnection? = null

        fun getInstance(): NetworkConnection {
            if (instances == null) {
                instances = NetworkConnection()
            }
            return instances as NetworkConnection
        }
    }

    fun getApiClient() = retrofit.create(ApiService::class.java)
}