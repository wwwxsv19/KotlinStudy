package bssm2024.demo.service

import bssm2024.demo.model.MyModel
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class MyModelService(val myModelRepository: MyModelRepository) {
    fun save(myModel: MyModel): MyModel {
        return myModelRepository.save(myModel)
    }

    fun findAll(): List<MyModel>
        = myModelRepository.findAll()

    /*
    fun findAll(): List<MyModel> {
        return myModelRepository.findAll()
    }
    */

    fun find(id: Long): MyModel?
        = myModelRepository.findById(id).getOrNull()
}

/*
* 
MyModelService 에서 사용할 MyModelRepository 를 처음에 바로 선언 가능 -> @Bean 안 써도 된다!
동일하게 save 함수에서 사용할 MyModel 도 myModel 로 바로 선언 가능
* 
*/