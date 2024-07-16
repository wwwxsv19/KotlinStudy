package bssm2024.demo.controller

import bssm2024.demo.config.MqttConfig.Companion.sub_clientId
import bssm2024.demo.config.MqttConfig.Companion.pub_clientId
import bssm2024.demo.dto.PingReq
import bssm2024.demo.model.MqttData
import bssm2024.demo.service.MqttService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MqttController(
    private val mqttGateway: MqttService.MqttGateway,
    val mqttService: MqttService,
) {
//    @GetMapping("/mqtt/ping")
//    fun ping() {
//        mqttGateway.sendToMqtt("Hello world!")
//    }

    @PostMapping("/mqtt/ping")
    fun ping(@RequestBody request: PingReq) {
        mqttService.saveData(sub_clientId, request.message)
        mqttGateway.sendToMqtt("[" + pub_clientId + "]:" + request.message)
    }

    @GetMapping("/api/message")
    fun readMessage(@RequestParam clientId: String): List<MqttData> {
        return mqttService.getData(clientId)
    }
}