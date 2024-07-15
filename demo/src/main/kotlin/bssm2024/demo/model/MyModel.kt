package bssm2024.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "my_model")
data class MyModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0, // 해당 어노테이션은 자바 어노테이션이고, 이건 코틀린이기 때문에 = 0 을 추가하여 자동 생성이 되게끔 생성자에 설정을 해야...

    var name: String,
) {
}