package bssm2024.demo.controller

import bssm2024.demo.dto.SaveMyModelReq
import bssm2024.demo.dto.UpdateMyModelReq
import bssm2024.demo.model.MyModel
import bssm2024.demo.service.MyModelRepository
import bssm2024.demo.service.MyModelService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MyController(val myModelService: MyModelService) {

    @GetMapping("/test")
    fun hello(): String {
        return "hello world!"
    }

    @PostMapping("/my")
    fun saveMyModel(@RequestBody request: SaveMyModelReq): MyModel {
        return myModelService.save(MyModel(name = request.name))
    }

    @GetMapping("/mys")
    fun listMyModel(): List<MyModel>
        = myModelService.findAll()

    @PatchMapping("/mymy/{id}")
    fun updateMyModel(
        @PathVariable id: Long,
        @RequestBody request: UpdateMyModelReq
    ): MyModel {
        val myModel: MyModel = myModelService.find(id)
            ?: throw NotFoundException()

        myModel.name = request.name

        return myModelService.save(myModel)
    }
}