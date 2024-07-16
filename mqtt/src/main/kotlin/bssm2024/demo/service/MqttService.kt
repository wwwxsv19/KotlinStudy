package bssm2024.demo.service

import bssm2024.demo.model.MqttData
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MqttService(val mqttDataRepository: MqttDataRepository) {
    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    interface MqttGateway {
        fun sendToMqtt(@Payload data: String)
    }

    fun saveData(clientId: String, message: String) {
        val data = MqttData(
            clientId = clientId,
            message = message,
            createdAt = LocalDateTime.now()
        )

        mqttDataRepository.save(data)
    }

    fun getData(clientId: String): List<MqttData> {
        return mqttDataRepository.findByClientId(clientId)
    }
}