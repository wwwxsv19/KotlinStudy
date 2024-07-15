package bssm2024.demo.service

import bssm2024.demo.model.MyModel
import org.springframework.data.jpa.repository.JpaRepository

interface MyModelRepository: JpaRepository<MyModel, Long> {
}