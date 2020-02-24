package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateScheduleItemRequest
import omsu.imit.schedule.service.ScheduleItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/schedules")
class ScheduleItemController
@Autowired
constructor(private val scheduleItemService: ScheduleItemService) {

    @PostMapping(value = ["/{scheduleId}/items"])
    fun createScheduleItem(@PathVariable scheduleId: Int,
                           @Valid @RequestBody request: CreateScheduleItemRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(scheduleItemService.createScheduleItem(scheduleId, request))
    }

//    @GetMapping(value = ["/items/{itemId}"])
//    fun getScheduleItem(@PathVariable itemId: Int): ResponseEntity<*> {
//        return ResponseEntity.ok().body(scheduleItemService.getScheduleItemInfo(itemId))
//    }

//    @GetMapping(value = ["/lecturers/{lecturerId}"])
//    fun getScheduleByLecturer(@PathVariable lecturerId: Int): ResponseEntity<*> {
//        return ResponseEntity.ok().body(scheduleItemService.getScheduleItemsByLecturer(lecturerId))
//    }
}