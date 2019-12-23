package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.DisciplineRequest
import omsu.imit.schedule.service.DisciplineService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/disciplines")
class DisciplineController
@Autowired
constructor(private val disciplineService: DisciplineService) {

    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createDiscipline(@Valid @RequestBody request: DisciplineRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(disciplineService.createDiscipline(request))
    }

    @GetMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getDiscipline(@PathVariable("id") disciplineId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(disciplineService.getDiscipline(disciplineId))
    }

    @GetMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllDisciplines(): ResponseEntity<*> {

        return ResponseEntity.ok().body(disciplineService.getAllDisciplines())
    }

    @PutMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun editDiscipline(@PathVariable("id") disciplineId: Int,
                       @Valid @RequestBody request: DisciplineRequest): ResponseEntity<*> {

        return ResponseEntity.ok().body(disciplineService.editDiscipline(disciplineId, request))
    }

    @DeleteMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteDiscipline(@PathVariable("id") disciplineId: Int): ResponseEntity<*> {
        disciplineService.deleteDiscipline(disciplineId)
        return ResponseEntity.noContent().build<Any>()
    }

}
