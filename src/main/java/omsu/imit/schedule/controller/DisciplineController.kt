package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.DisciplineRequest
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.service.DisciplineService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/disciplines")
class DisciplineController
@Autowired
constructor(private val disciplineService: DisciplineService) {

    @PostMapping
    fun createDiscipline(@Valid @RequestBody request: DisciplineRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(disciplineService.createDiscipline(request))
    }

    @GetMapping(value = ["/{disciplineId}"])
    fun getDiscipline(@PathVariable disciplineId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(disciplineService.getDiscipline(disciplineId))
    }

    @GetMapping
    fun getAllDisciplines(): ResponseEntity<*> {

        return ResponseEntity.ok().body(disciplineService.getAllDisciplines())
    }

    @PutMapping(value = ["/{disciplineId}"])
    fun editDiscipline(@PathVariable disciplineId: Int,
                       @Valid @RequestBody request: DisciplineRequest): ResponseEntity<*> {

        return ResponseEntity.ok().body(disciplineService.editDiscipline(disciplineId, request))
    }

    @DeleteMapping(value = ["/{disciplineId}"])
    fun deleteDiscipline(@PathVariable disciplineId: Int): StatusResponse {
        disciplineService.deleteDiscipline(disciplineId)
        return StatusResponse.OK
    }

}
