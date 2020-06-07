package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateClassroomRequest
import omsu.imit.schedule.dto.request.EditClassroomRequest
import omsu.imit.schedule.dto.response.ClassroomInfo
import omsu.imit.schedule.dto.response.ClassroomInfoByDate
import omsu.imit.schedule.dto.response.ClassroomShortInfo
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.services.ClassroomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

import javax.validation.Valid

@RestController
@RequestMapping("/classrooms")
class ClassroomController
@Autowired
constructor(private val classroomService: ClassroomService) {

    @PostMapping
    fun createClassroom(@Valid @RequestBody request: CreateClassroomRequest): ResponseEntity<ClassroomShortInfo> {
        return ResponseEntity.ok().body(classroomService.createClassroom(request))
    }

    @GetMapping(value = ["/{classroomId}"])
    fun getClassroomById(@PathVariable classroomId: Int): ResponseEntity<ClassroomInfo> {
        return ResponseEntity.ok().body(classroomService.getClassroomInfo(classroomId))
    }

    @GetMapping(value = ["/{classroomId}/events-by-date"])
    fun getClassroomWithEventsByDate(@PathVariable classroomId: Int,

                                     @RequestParam
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                     dateFrom: LocalDate,

                                     @RequestParam
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                     dateTo: LocalDate): ResponseEntity<ClassroomInfoByDate> {

        return ResponseEntity.ok().body(classroomService.getClassroomWithEventsByDate(classroomId, dateFrom, dateTo))
    }

    @GetMapping
    fun getClassroomsByTags(@RequestParam tags: List<Int>): ResponseEntity<List<ClassroomShortInfo>> {
        return ResponseEntity.ok().body(classroomService.getClassroomsByTags(tags))
    }

    @PutMapping(value = ["/{classroomId}"])
    fun editClassroom(@PathVariable classroomId: Int,
                      @RequestBody request: EditClassroomRequest): ResponseEntity<ClassroomShortInfo> {

        return ResponseEntity.ok().body(classroomService.editClassroom(classroomId, request))
    }

    @DeleteMapping(value = ["/{classroomId}"])
    fun deleteClassroom(@PathVariable classroomId: Int): StatusResponse {
        classroomService.deleteClassroom(classroomId)
        return StatusResponse.OK
    }
}
