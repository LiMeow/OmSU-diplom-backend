package omsu.imit.schedule.controller

import omsu.imit.schedule.service.DebugService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/debug/clear")
class DebugController @Autowired
constructor(private val debugService: DebugService) {


    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun clear(): ResponseEntity<*> {
        debugService.clear()
        return ResponseEntity.noContent().build<Any>()
    }


}
