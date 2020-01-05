package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.CreateTimeBlockRequest
import omsu.imit.schedule.requests.EditTimeBlockRequest
import omsu.imit.schedule.service.TimeBlockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
@RequestMapping("/api/timeblocks")
class TimeBlockController @Autowired
constructor(private val timeBlockService: TimeBlockService) {

    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addTimeBlock(@Valid @RequestBody request: CreateTimeBlockRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(timeBlockService.addTimeBlock(request))
    }

    @GetMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTimeBlockById(@PathVariable("id") timeBlockId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(timeBlockService.getTimeBlockById(timeBlockId))
    }

    @GetMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTimeBlockById(): ResponseEntity<*> {

        return ResponseEntity.ok().body(timeBlockService.getTimeBlocks())
    }

    @PutMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun editTimeBlock(@PathVariable("id") timeBlockId: Int,
                      @RequestBody request: EditTimeBlockRequest): ResponseEntity<*> {

        return ResponseEntity.ok().body(timeBlockService.editTimeBlock(timeBlockId, request))
    }

    @DeleteMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteTimeBlock(@PathVariable("id") timeBlockId: Int): ResponseEntity<*> {
        timeBlockService.deleteTimeBlock(timeBlockId)
        return ResponseEntity.noContent().build<Any>()
    }

    @DeleteMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteTimeBlocks(): ResponseEntity<*> {
        timeBlockService.deleteAllTimeBlocks()
        return ResponseEntity.noContent().build<Any>()
    }
}
