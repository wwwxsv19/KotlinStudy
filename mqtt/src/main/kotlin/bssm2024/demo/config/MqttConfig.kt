package bssm2024.demo.config

import bssm2024.demo.service.MqttService
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler
import java.time.LocalDateTime

@Configuration
class MqttConfig(val mqttService: MqttService) {
    private val brokerUrl = "tcp://10.150.150.90:1883"
//    private val brokerUrl = "tcp://localhost:1883"
    private val topic = "bssm"

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun mqttPahoClientFactory(): MqttPahoClientFactory {
        return DefaultMqttPahoClientFactory().apply {
            connectionOptions = MqttConnectOptions().apply {
                serverURIs = arrayOf(brokerUrl)
            }
        }
    }

    @Bean
    fun mqttOutboundChannel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    fun mqttOutbound(): MessageHandler {
        val handler = MqttPahoMessageHandler(pub_clientId, mqttPahoClientFactory()).apply {
            setAsync(true)
            setDefaultTopic(topic)
        }
        return handler
    }

    @Bean
    fun mqttInboundChannel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    fun inbound(): MqttPahoMessageDrivenChannelAdapter {
        val adapter = MqttPahoMessageDrivenChannelAdapter(
            brokerUrl,
            sub_clientId,
            mqttPahoClientFactory(),
            topic
        )
        adapter.setCompletionTimeout(5000)
        adapter.setConverter(DefaultPahoMessageConverter())
        adapter.setQos(1)
        adapter.outputChannel = mqttInboundChannel()
        return adapter
        }

    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    fun mqttInbound(): MessageHandler {
        return MessageHandler { message ->
            println("[${LocalDateTime.now()}] $message")

            val i = message.toString().split("=")[1]
            val d = i.split("]")[0]
            val anotherId = d.substring(1)
            val m = message.toString().split(":")[1]
            val msg = m.split(",")[0]

            mqttService.saveData(anotherId, msg)
        }
    }

    companion object {
        const val sub_clientId: String = "wxsv19"
        const val pub_clientId: String = "wxsv18"
    }
}