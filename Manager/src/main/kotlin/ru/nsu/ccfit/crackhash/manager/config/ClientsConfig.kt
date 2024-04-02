package ru.nsu.ccfit.crackhash.manager.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.nsu.ccfit.crackhash.manager.worker.WorkerApi

@Configuration
class ClientsConfig(
    @Value("\${workers.base_url}")
    private val baseUrl: String,
) {
    @Bean
    fun getWorker() = run {
        Retrofit.Builder()
            .baseUrl("$baseUrl/internal/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create() as WorkerApi
    }


}