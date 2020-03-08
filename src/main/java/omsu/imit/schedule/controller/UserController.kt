package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.model.UserRole
import omsu.imit.schedule.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController
@Autowired
constructor(private val userService: UserService) {

    @PutMapping(value = ["/{userId}/change-role"])
    fun changeUserRole(@PathVariable userId: Int,
                       @RequestParam userRole: UserRole): ResponseEntity<*> {
        return ResponseEntity.ok().body(userService.changeUserType(userId, userRole))
    }

    @PutMapping(value = ["/{userId}/disable"])
    fun disableAccount(@PathVariable userId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(userService.disableAccount(userId))
    }

    @DeleteMapping(value = ["/userId"])
    fun deleteAccount(@PathVariable userId: Int): StatusResponse {
        userService.deleteAccount(userId)
        return StatusResponse.OK
    }

}