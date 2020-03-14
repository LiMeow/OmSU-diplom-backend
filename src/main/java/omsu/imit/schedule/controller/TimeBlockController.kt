package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateTimeBlockRequest
import omsu.imit.schedule.dto.request.EditTimeBlockRequest
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.service.TimeBlockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/timeblocks")
class TimeBlockController @Autowired
constructor(private val timeBlockService: TimeBlockService) {

    @PostMapping
    fun addTimeBlock(@Valid @RequestBody request: CreateTimeBlockRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(timeBlockService.createTimeBlock(request))
    }

    @GetMapping(value = ["/{timeBlockId}"])
    fun getTimeBlockById(@PathVariable timeBlockId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(timeBlockService.getTimeBlockById(timeBlockId))
    }

    @GetMapping
    fun getAllTimeBlocks(): ResponseEntity<*> {

        return ResponseEntity.ok().body(timeBlockService.getAllTimeBlocks())
    }

    @PutMapping(value = ["/{timeBlockId}"])
    fun editTimeBlock(@PathVariable timeBlockId: Int,
                      @RequestBody request: EditTimeBlockRequest): ResponseEntity<*> {

        return ResponseEntity.ok().body(timeBlockService.editTimeBlock(timeBlockId, request))
    }

    @DeleteMapping(value = ["/{timeBlockId}"])
    fun deleteTimeBlock(@PathVariable timeBlockId: Int): StatusResponse {
        timeBlockService.deleteTimeBlock(timeBlockId)
        return StatusResponse.OK
    }

    @DeleteMapping
    fun deleteAllTimeBlocks(): StatusResponse {
        timeBlockService.deleteAllTimeBlocks()
        return StatusResponse.OK
    }
}
