package bssm2024.demo.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "mqtt_data")
data class MqttData (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val clientId: String,
    val message: String,
    val createdAt: LocalDateTime
)