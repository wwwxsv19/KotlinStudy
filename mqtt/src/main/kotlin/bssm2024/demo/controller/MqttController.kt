package bssm2024.demo.controller

import bssm2024.demo.dto.PingReq
import bssm2024.demo.service.MqttService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MqttController(private val mqttGateway: MqttService.MqttGateway) {
    @GetMapping("/mqtt/ping")
    fun ping() {
        mqttGateway.sendToMqtt("Hello world!")
    }

    @PostMapping("/mqtt/ping")
    fun ping(@RequestBody request: PingReq) {
        mqttGateway.sendToMqtt(request.message)
    }
}