package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.User
import omsu.imit.schedule.repository.UserRepository
import omsu.imit.schedule.requests.ChangeUserTypeRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService
@Autowired
constructor(private val userRepository: UserRepository) {

    fun whoIAm(): User {
        val email = SecurityContextHolder.getContext().authentication.name
        return userRepository.findByEmail(email) ?: throw ScheduleGeneratorException(ErrorCode.USER_NOT_EXISTS)
    }

    fun changeUserType(userId: Int, request: ChangeUserTypeRequest): User {
        val user = userRepository.findById(userId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.USER_NOT_EXISTS)

        user.userRole = request.userRole
        userRepository.save(user)

        return user
    }
}
