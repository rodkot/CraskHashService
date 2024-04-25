package ru.nsu.ccfit.crackhash.manager.config

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.stream.binder.rabbit.config.RabbitConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(RabbitConfiguration::class)
@EnableRabbit
class RabbitConfig {
    @Bean
    fun queueWorkerToManager() = Queue("worker-to-manager", true)

    @Bean
    fun directExchangeWorkerToManager() = DirectExchange("worker-to-manager")

    @Bean
    fun workerBinding(
        @Qualifier("queueWorkerToManager") queue: Queue,
        @Qualifier("directExchangeWorkerToManager") direct: DirectExchange,
    ) = BindingBuilder.bind(queue).to(direct).with("manager")

    @Bean
    fun messageConverter() = Jackson2JsonMessageConverter()
}