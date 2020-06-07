package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateScheduleItemRequest
import omsu.imit.schedule.dto.request.EditScheduleItemRequest
import omsu.imit.schedule.dto.response.ScheduleItemInfo
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.services.ScheduleItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/schedules")
class ScheduleItemController
@Autowired
constructor(private val scheduleItemService: ScheduleItemService) {

    @PostMapping(value = ["/{scheduleId}/items"])
    fun createScheduleItem(@PathVariable scheduleId: Int,
                           @Valid @RequestBody request: CreateScheduleItemRequest): ResponseEntity<MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>>> {
        return ResponseEntity.ok().body(scheduleItemService.createScheduleItem(scheduleId, request))
    }

    @GetMapping(value = ["/items/{itemId}"])
    fun getScheduleItem(@PathVariable itemId: Int): ResponseEntity<MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>>> {
        return ResponseEntity.ok().body(scheduleItemService.getScheduleItemInfo(itemId))
    }

    @PutMapping(value = ["/items/{itemId}"])
    fun editScheduleItem(@PathVariable itemId: Int,
                         @Valid @RequestBody request: EditScheduleItemRequest): ResponseEntity<MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>>> {
        return ResponseEntity.ok().body(scheduleItemService.editScheduleItem(itemId, request))
    }

    @DeleteMapping(value = ["/items/{itemId}"])
    fun deleteScheduleItem(@PathVariable itemId: Int): StatusResponse {
        scheduleItemService.deleteScheduleItem(itemId)
        return StatusResponse.OK
    }
}