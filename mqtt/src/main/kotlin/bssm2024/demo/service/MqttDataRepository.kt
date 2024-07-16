package bssm2024.demo.service

import bssm2024.demo.model.MqttData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MqttDataRepository : JpaRepository<MqttData, Long> {
    fun findByClientId(clientId: String): List<MqttData>
}